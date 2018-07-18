package pro.delfik.bedwars.game.usables;

import lib.Generate;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class GPSTracker implements Listener {
	public final static ItemStack ITEM = Generate.itemstack(Material.COMPASS, 1, 0, "§aGPS-Трекер",
			"§e§oПоможет вам найти оставшихся игроков.");
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.PHYSICAL) return;
		if (!e.getPlayer().getItemInHand().isSimilar(ITEM)) return;
		e.setCancelled(true);
	}
	
}
