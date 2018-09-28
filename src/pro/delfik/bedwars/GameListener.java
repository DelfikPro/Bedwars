package pro.delfik.bedwars;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SandstoneType;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Sandstone;
import pro.delfik.bedwars.game.BWTeam;
import pro.delfik.bedwars.game.Color;
import pro.delfik.bedwars.game.Game;
import pro.delfik.bedwars.game.stuff.GPSTracker;
import pro.delfik.bedwars.game.stuff.HomeTeleportation;
import pro.delfik.bedwars.game.stuff.RescuePlatform;
import pro.delfik.bedwars.purchase.Purchase;
import pro.delfik.lmao.user.Person;
import pro.delfik.lmao.util.Vec3i;

import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

import static org.bukkit.Material.*;

public class GameListener implements Listener {
	public static boolean dbgclicks = false;

	@EventHandler
	public void inventoryClick(InventoryClickEvent event) {
		if (!dbgclicks) return;
		Player p = ((Player) event.getWhoClicked());
		p.sendMessage("§7[§eDEBUG§7] §aNumberKey: §f" + event.getHotbarButton() + "§a, Action: §f" + event.getAction() + "§a, ClickType: §f" + event.getClick());
	}
	
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Game game = Game.get(p.getWorld());
		if (game == null) return;
		BWTeam team = game.getTeam(p.getName());
		if (team == null) return;
		
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
			Vec3i vec = Vec3i.fromLocation(e.getClickedBlock().getLocation());
			
			switch (e.getClickedBlock().getType()) {
				case ENDER_PORTAL_FRAME:
					p.openInventory(Purchase.getInventory());
					e.setCancelled(true);
					return;
				case CHEST:
					if (!team.getChests().contains(vec)) {
						p.sendMessage("§cЭтот сундук не принадлежит вашей команде.");
						e.setCancelled(true);
					}
					break;
				case ENDER_CHEST:
					if (!p.isSneaking()) p.openInventory(team.getEnderChest());
					break;
				case BED_BLOCK:
					if (!e.isBlockInHand() && !p.isSneaking()) {
						e.setCancelled(true);
						p.sendMessage("§7(Всем) §dКровать§7: §fЯ знаю, тебе хочется спать...");
					}
					break;
			}
			
		}
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
			switch (e.getMaterial()) {
				case BLAZE_ROD:
					e.setCancelled(true);
					if (RescuePlatform.hasPlatform(p)) Person.get(p).sendTitle("§cПерезарядка");
					else {
						decrementHandItem(p);
						new RescuePlatform(Person.get(p));
					}
					break;
				case COMPASS:
					GPSTracker.updateFor(game, team, Person.get(p));
					break;
				case SULPHUR:
					HomeTeleportation tp = new HomeTeleportation(Person.get(p), team, game);
					break;
			}
		}
		
		
	}


	@EventHandler
	public void onItemSpawn(ItemSpawnEvent e) {
		switch (e.getEntity().getItemStack().getType()) {
			case OBSIDIAN:
			case BED:
				e.setCancelled(true);
				// Были ещё какие-то итемы, которым не следует дропаццо, так что лучше пусть будет switch
		}
	}

	public static EnumSet<Material> breakable = EnumSet.of(
			SANDSTONE, ENDER_STONE, IRON_BLOCK, GLASS, CHEST, ENDER_CHEST,
			GLOWSTONE, CAKE, LADDER, WEB, TRIPWIRE, BED_BLOCK, TNT
	);

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if (p.getGameMode() == GameMode.CREATIVE) return;
		Game game = Game.get(p);
		if (game == null) {
			e.setCancelled(true);
			return;
		}
		BWTeam team = game.getTeam(e.getPlayer().getName());
		if (team == null) {
			e.setCancelled(true);
			return;
		}
		Material m = e.getBlock().getType();
		if (!breakable.contains(m)) e.setCancelled(true);
		switch (e.getBlock().getType()) {
			case SANDSTONE:
				Sandstone sandstone = (Sandstone) e.getBlock();
				if (sandstone.getType() != SandstoneType.SMOOTH) e.setCancelled(true);
				break;
			case BED_BLOCK:
				// ToDo: Обработка поломки кровати
				Color bed = game.getMap().nearestTeam(e.getBlock().getLocation());
				if (bed == team.getColor()) {
					e.setCancelled(true);
					p.sendMessage("§c" + Messages.YOU_BREAKED_OWN_BED);
					return;
				}
				game.destroyBed(bed, p);
		}
	}

	@EventHandler
	public void onExplode(EntityExplodeEvent e) {
		List<Block> blocks = new LinkedList<>();
		for (Block block : e.blockList())
			if (block.getType() != BED_BLOCK && breakable.contains(block.getType())) blocks.add(block);
			e.blockList().clear();
			e.blockList().addAll(blocks);
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if (isSameBlock(e.getFrom(), e.getTo())) return;
		HomeTeleportation.cancelIfTeleporting(e.getPlayer());
	}

	private boolean isSameBlock(Location from, Location to) {
		return
				from.getBlockX() == to.getBlockX() &&
				from.getBlockY() == to.getBlockY() &&
				from.getBlockZ() == to.getBlockZ();
	}

	private static void decrementHandItem(Player p) {
		ItemStack hand = p.getInventory().getItemInHand();
		hand.setAmount(hand.getAmount() - 1);
		p.getInventory().setItemInHand(hand);
	}

}
