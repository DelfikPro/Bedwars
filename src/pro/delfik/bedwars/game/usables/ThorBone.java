package pro.delfik.bedwars.game.usables;

import lib.Generate;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class ThorBone implements Listener {
	public static final ItemStack ITEM = Generate.itemstack(Material.BONE, 1, 0, "§aКость тора", "§eНажмите ПКМ для использования");
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		ItemStack hand = e.getPlayer().getItemInHand();
		if (!Generate.equalsItem(hand, ITEM)) return;
		e.setCancelled(true);
		hand.setAmount(hand.getAmount() - 1);
		e.getPlayer().setItemInHand(hand);
		Block block = null;
		for (Block b : e.getPlayer().getLineOfSight((Set<Material>) null, 200)) {
			if (b.getType() != Material.AIR) {
				b.getWorld().strikeLightning(b.getLocation());
				return;
			}
			block = b;
		}
		block.getWorld().strikeLightning(block.getLocation());
	}
}
