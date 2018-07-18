package pro.delfik.bedwars.game.usables;

import lib.Generate;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import pro.delfik.bedwars.BWPlugin;
import pro.delfik.lmao.core.Person;
import pro.delfik.lmao.util.Cooldown;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SavingPlatform implements Listener {
	
	public static final ItemStack ITEM = Generate.itemstack(Material.BLAZE_ROD, 1, 0, "§aСпасительная платформа", "§e§o- При использовании спаунит платформу", "§e§o §e§o из стекла на 10 секунд");
	
	@EventHandler
	public void onUse(PlayerInteractEvent e) {
		if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if (!Generate.equalsItem(ITEM, e.getPlayer().getItemInHand())) return;
		Player p = e.getPlayer();
		Location loc = p.getLocation();
		if (loc.getY() < 4) loc.setY(4);
		loc.setY(loc.getY() - 2);
		World w = p.getWorld();
		int x = loc.getBlockX(), y = loc.getBlockY(), z = loc.getBlockZ();
		List<Location> list = new ArrayList<>();
		list.add(new Location(w, x, y, z));
		list.add(new Location(w, x + 1, y, z));
		list.add(new Location(w, x, y, z + 1));
		list.add(new Location(w, x + 1, y, z + 1));
		list.add(new Location(w, x - 1, y, z));
		list.add(new Location(w, x, y, z - 1));
		list.add(new Location(w, x - 1, y, z - 1));
		list.add(new Location(w, x - 1, y, z + 1));
		list.add(new Location(w, x + 1, y, z - 1));
		
		list.add(new Location(w, x + 2, y, z - 1));
		list.add(new Location(w, x + 2, y, z + 1));
		list.add(new Location(w, x - 2, y, z - 1));
		list.add(new Location(w, x - 2, y, z + 1));
		list.add(new Location(w, x - 1, y, z - 2));
		list.add(new Location(w, x - 1, y, z + 2));
		list.add(new Location(w, x + 1, y, z - 2));
		list.add(new Location(w, x + 1, y, z + 2));
		
		for (Location l : list) {
			w.playEffect(l, Effect.SMOKE, 5);
			if (l.getBlock().getType() == Material.AIR) l.getBlock().setType(Material.GLASS);
		}
		
		p.setFallDistance(0);
		loc.setY(loc.getY() + 2);
		p.teleport(loc);
		new Cooldown("saveplatform", 10, Collections.singletonList(Person.get(p.getName())), () -> {
			for (Location l : list) if (l.getBlock().getType() == Material.GLASS) l.getBlock().setType(Material.AIR);
		});
		ItemStack hand = p.getItemInHand(); hand.setAmount(hand.getAmount() - 1); p.setItemInHand(hand);
	}
}
