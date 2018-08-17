package pro.delfik.bedwars.world;

import org.bukkit.Location;
import org.bukkit.World;

/**
 * Три числа int в одном объекте.
 * @see Vec
 */
public class Vec3i implements Vec {
	public final int x, y, z;
	
	public Vec3i(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public static Vec3i fromLocation(Location location) {
		return new Vec3i(location.getBlockX(), location.getBlockY(), location.getBlockZ());
	}
	
	@Override
	public Vec3i toVec3i() {return this;}
	public org.bukkit.util.Vector toBukkitVector() {
		return new org.bukkit.util.Vector(x, y, z);
	}
	public Location toBlock(World w) {
		return new Location(w, x, y, z);
	}
	public Location toLocation(World w) {
		return new Location(w, x + 0.5, y + 0.5, z + 0.5);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Vec3i)) return false;
		Vec3i o = ((Vec3i) obj);
		return o == this || (o.x == x && o.y == y && o.z == z);

	}
	
	@Override
	public int hashCode() {
		return x * 1000000 + y * 1000 + z;
	}
}
