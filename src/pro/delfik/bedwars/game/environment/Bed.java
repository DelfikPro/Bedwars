package pro.delfik.bedwars.game.environment;

import org.bukkit.Location;

public class Bed {
	private final Location l1, l2;
	
	public Bed(Location l1, Location l2) {
		this.l1 = l1;
		this.l2 = l2;
	}
	
	public boolean matches(Location loc) {
		return (loc.getBlockX() == l1.getBlockX() && loc.getBlockZ() == l1.getBlockZ()) ||
				(loc.getBlockX() == l2.getBlockX() && loc.getBlockZ() == l2.getBlockZ());
	}
	
	public Location[] getLocs() {
		return new Location[] {l1, l2};
	}
}
