package pro.delfik.bedwars.game.stuff;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import pro.delfik.lmao.core.Person;
import pro.delfik.lmao.util.Cooldown;
import pro.delfik.lmao.util.TimedMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RescuePlatform {
	
	private static final Material PLATFORM_MATERIAL = Material.STAINED_GLASS;
	
	private static final TimedMap<Player, RescuePlatform> platforms = new TimedMap<>(20);
	private final List<Location> blocks;
	
	public RescuePlatform(Person p) {
		if (platforms.get(p.getHandle()) != null) throw new RuntimeException(p.getName() + " поставил больше одной платформы.");
		platforms.add(p.getHandle(), this);
		Location loc = p.getLocation();
		if (loc.getBlockY() < 5) loc.setY(5);
		this.blocks = build(loc);
		p.teleport(loc);
		new Cooldown("rp" + p.getName(), 10, Collections.singletonList(p), this::remove);
	}
	
	public static boolean hasPlatform(Player p) {
		return platforms.get(p) != null;
	}
	
	public void remove() {
		for (Location loc : blocks) {
			Block b = loc.getBlock();
			if (b.getType() == PLATFORM_MATERIAL) b.setType(Material.AIR);
		}
	}
	
	public static List<Location> build(Location loc) {
		List<Location> list = new ArrayList<>();
		for (int x = 0; x < 5; x++) {
			for (int z = 0; z < 5; z++) {
				Location l = loc.add(x, -3, z);
				if (x % 2 == 0 && z % 2 == 0) continue;
				if (x == 2 && z == 2) continue;
				if (l.getBlock().getType() != Material.AIR) continue;
				l.getBlock().setType(PLATFORM_MATERIAL);
				list.add(l);
			}
		}
		return list;
	}
	
}
