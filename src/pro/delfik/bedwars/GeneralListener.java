package pro.delfik.bedwars;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import pro.delfik.bedwars.game.BWTeam;
import pro.delfik.bedwars.game.Game;
import pro.delfik.bedwars.purchase.Purchase;
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
		EvChat.chat(e.getPlayer().getName(), e.getMessage(), g.getWorld().getPlayers(), prefix);
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
				e.setCancelled(true);
				break;
			case SPONGE:
				if (e.getPlayer().isSneaking()) break;
				e.getPlayer().openInventory(Purchase.getInventory());
				e.setCancelled(true);
				return;
		}
		if (Game.get(e.getPlayer()) != null) return;
		switch (e.getMaterial()) {
			case EMERALD:

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

	public static final TimedMap<Player, String> lastDamage = new TimedMap<>(10);

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player)) return;
		if (!(e.getDamager() instanceof Player)) return;
		lastDamage.add(((Player) e.getEntity()), e.getDamager().getName());
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		invisibleArmor.remove(e.getEntity());
		e.setDroppedExp(0);
		e.setKeepInventory(true);
		String killer = lastDamage.get(e.getEntity());
		Player k = Bukkit.getPlayer(killer);
		if (k != null) k.giveExpLevels(1);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onRespawn(PlayerRespawnEvent e) {
		e.getPlayer().getInventory().clear();
		e.getPlayer().getInventory().setArmorContents(null);
		e.getPlayer().setExp(0);
	}

	@EventHandler
	public void onCraft(CraftItemEvent e) {
		e.setCancelled(true);
	}

}
