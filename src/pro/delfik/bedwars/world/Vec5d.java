package pro.delfik.bedwars.world;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class Vec5d implements Vec {
	
	private final double x, y, z;
	private final float yaw, pitch;
	
	public Vec5d(double x, double y, double z, double yaw, double pitch) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = (float) yaw;
		this.pitch = (float) pitch;
	}
	
	public double getX() {return x;}
	public double getY() {return y;}
	public double getZ() {return z;}
	public double getYaw() {return yaw;}
	public double getPitch() {return pitch;}
	
	@Override
	public Vector toBukkitVector() {
		return new Vector(x, y, z);
	}
	
	@Override
	public Location toBlock(World w) {
		return new Location(w, x, y, z);
	}
	
	@Override
	public Location toLocation(World w) {
		return new Location(w, x, y, z, yaw, pitch);
	}
	
	@Override
	public Vec3i toVec3i() {
		return new Vec3i((int) x, (int) y, (int) z);
	}
}
