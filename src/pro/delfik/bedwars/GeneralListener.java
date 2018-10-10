package pro.delfik.bedwars;

import implario.io.FileIO;
import implario.util.Byteable;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import pro.delfik.bedwars.game.BWTeam;
import pro.delfik.bedwars.game.Game;
import pro.delfik.bedwars.preparation.GamePreparation;
import pro.delfik.bedwars.preparation.GameSelector;
import pro.delfik.bedwars.preparation.NewGame;
import pro.delfik.bedwars.purchase.Purchase;
import pro.delfik.bedwars.purchase.favourites.GamerInfo;
import pro.delfik.lmao.ev.EvChat;
import pro.delfik.lmao.outward.item.I;
import pro.delfik.lmao.user.Person;
import pro.delfik.lmao.util.TimedMap;
import pro.delfik.lmao.util.U;

import java.util.HashMap;

public class GeneralListener implements Listener {

	@EventHandler(priority = EventPriority.LOW)
	public void chat(AsyncPlayerChatEvent e) {
		Game g = Game.get(e.getPlayer().getWorld());
		e.setCancelled(true);
		if (g == null) {
			EvChat.chat(e.getPlayer().getName(), e.getMessage(), Bedwars.getLobby().getPlayers(), "§7(Лобби) ");
			return;
		}
		BWTeam team = g.getTeam(e.getPlayer().getName());
		String prefix, message = e.getMessage();
		if (team == null) {
			prefix = "§7(Зрители) ";
		} else if (message.charAt(0) == '!') {
			prefix = "§7(Всем) ";
			message = message.substring(1);
		} else {
			TextComponent text = U.constructComponent("§7(" + team.getColor().getPrefix() + "Свои§7) ", e.getPlayer(), "§7: §f" + message);
			for (Person teammate : team.getPlayers()) teammate.msg(text);
			return;
		}
		TextComponent text = U.constructComponent("§7(" + prefix + ") ", e.getPlayer(), "§7: §f" + message);
		EvChat.chat(e.getPlayer().getName(), message, g.getWorld().getPlayers(), prefix);
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) switch (e.getClickedBlock().getType()) {
			case WORKBENCH:
			case FURNACE:
			case ANVIL:
			case ENCHANTMENT_TABLE:
			case HOPPER:
			case BREWING_STAND:
				if (e.getPlayer().getGameMode() != GameMode.CREATIVE) e.setCancelled(true);
				break;
			case SPONGE:
				if (e.getPlayer().isSneaking()) break;
				Purchase.open(e.getPlayer());
				e.setCancelled(true);
				return;
		}
		if (Game.get(e.getPlayer()) != null) return;
		switch (e.getMaterial()) {
			case EMERALD:
				e.getPlayer().openInventory(NewGame.gui.inv());
				break;
			case SLIME_BALL:
				GamePreparation p = GamePreparation.byPlayer.get(e.getPlayer().getName());
				if (p != null) e.getPlayer().openInventory(p.getMapVoting().getGui().getInventory());
				break;
			case DARK_OAK_DOOR_ITEM:
				GamePreparation p1 = GamePreparation.byPlayer.get(e.getPlayer().getName());
				if (p1 != null) p1.remove(Person.get(e.getPlayer()));
				break;
			case COMPASS:
				U.send(e.getPlayer().getName(), "LOBBY_1");
				Person.get(e.getPlayer()).sendTitle("§f");
				Person.get(e.getPlayer()).sendSubtitle("§eВы телепортированы в лобби.");
				break;
			case WATCH:
				e.getPlayer().openInventory(GameSelector.getInventory());
				break;
		}
	}

	private static final HashMap<Player, ItemStack[]> invisibleArmor = new HashMap<>();

	@EventHandler
	public void onConsume(PlayerItemConsumeEvent e) {
		final Player p = e.getPlayer();
		ItemStack item = e.getItem();
		if (item.getType() != Material.POTION) return;
		final int slot = e.getPlayer().getInventory().getHeldItemSlot();
		I.delay(() -> {
			ItemStack i = p.getInventory().getItem(slot);
			if (i != null && i.getType() == Material.GLASS_BOTTLE) p.getInventory().setItem(slot, null);
		}, 0);
		if (item.getDurability() == 14) {
			Person.get(p).clearArrows();
			invisibleArmor.put(p, p.getInventory().getArmorContents());
			p.getInventory().setArmorContents(null);
			I.delay(() -> p.getInventory().setArmorContents(invisibleArmor.remove(p)), 300);
		}
	}

	public static final TimedMap<Player, String> lastDamage = new TimedMap<>(15);

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player)) return;
		if (!(e.getDamager() instanceof Player)) return;
		lastDamage.add(((Player) e.getEntity()), e.getDamager().getName());
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		e.setJoinMessage("");
		Bedwars.toLobby(e.getPlayer());
		byte[] bytes = FileIO.readBytes("playerSettings/" + e.getPlayer().getName().toLowerCase() + ".txt");
		if (bytes != null) Byteable.toByteable(bytes, GamerInfo.class);
		else GamerInfo.getDefault(e.getPlayer());
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		e.setQuitMessage("");
		GamerInfo info = GamerInfo.remove(e.getPlayer());
		FileIO.writeBytes("playerSettings/" + e.getPlayer().getName().toLowerCase() + ".txt", info.toByteZip().build());
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		invisibleArmor.remove(e.getEntity());
		e.setDroppedExp(0);
		e.setKeepInventory(true);
		String killer = lastDamage.list.remove(e.getEntity());
		if (killer == null) return;
		Player k = Bukkit.getPlayer(killer);
		if (k == null) return;
		k.giveExpLevels(1);
		Game game = Game.get(e.getEntity());
		if (game == null) return;
		String way = "убит";
		if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.VOID) way = "скинут";
		TextComponent tc = U.constructComponent(e.getEntity(), " был " + way + " игроком ", k, ".");
		for (Player player : game.getWorld().getPlayers()) player.spigot().sendMessage(tc);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.setExp(0);
		Person.get(p).clearArrows();
		if (Game.get(p) == null) Bedwars.toLobby(p);
	}

	@EventHandler
	public void onHunger(FoodLevelChangeEvent e) {
		if (Game.get(e.getEntity().getWorld()) == null) e.setFoodLevel(20);
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (Game.get(e.getEntity().getWorld()) == null) e.setCancelled(true);
		if (e.getCause() == EntityDamageEvent.DamageCause.VOID)
			if (e.getEntity() instanceof Player)
				if (e.getEntity().getWorld().getName().startsWith("BW_")) {
					e.setDamage(1000);
					Player p = ((Player) e.getEntity());
					String killer = GeneralListener.lastDamage.list.remove(p);
					if (killer == null) return;
					Player k = Bukkit.getPlayer(killer);
					if (k == null) return;
					k.giveExpLevels(1);
					Game game = Game.get(p);
					if (game == null) return;
					TextComponent tc = U.constructComponent(p, " был скинут в бездну игроком ", k, ".");
					for (Player player : game.getWorld().getPlayers()) player.spigot().sendMessage(tc);
				} else Bedwars.toLobby(((Player) e.getEntity()));
		if (e instanceof EntityDamageByEntityEvent) if (((EntityDamageByEntityEvent) e).getDamager() instanceof Player)
			if (((Player) ((EntityDamageByEntityEvent) e).getDamager()).getGameMode() == GameMode.CREATIVE)
				e.setCancelled(false);
	}

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e) {
		if (Bedwars.trashSet.contains(e.getItemDrop().getItemStack().getType())) e.getItemDrop().remove();
		if (Game.get(e.getPlayer().getWorld()) == null && e.getPlayer().getGameMode() != GameMode.CREATIVE) e.setCancelled(true);
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (Game.get(e.getWhoClicked().getWorld()) == null && e.getWhoClicked().getGameMode() != GameMode.CREATIVE) e.setCancelled(true);
	}

	@EventHandler
	public void onCraft(CraftItemEvent e) {
		e.setCancelled(true);
	}

}
