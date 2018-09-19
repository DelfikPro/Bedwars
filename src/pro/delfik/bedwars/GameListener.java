package pro.delfik.bedwars;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import pro.delfik.bedwars.game.BWTeam;
import pro.delfik.bedwars.game.Game;
import pro.delfik.bedwars.game.stuff.GPSTracker;
import pro.delfik.bedwars.game.stuff.HomeTeleportation;
import pro.delfik.bedwars.game.stuff.RescuePlatform;
import pro.delfik.bedwars.purchase.Purchase;
import pro.delfik.lmao.util.Vec3i;
import pro.delfik.lmao.core.Person;

public class GameListener implements Listener {
	
	
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
					p.openInventory(team.getEnderChest());
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
