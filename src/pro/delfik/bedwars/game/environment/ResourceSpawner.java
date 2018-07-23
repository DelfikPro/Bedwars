package pro.delfik.bedwars.game.environment;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import pro.delfik.bedwars.BWPlugin;
import pro.delfik.bedwars.Defaults;
import pro.delfik.bedwars.game.BWTeam;
import pro.delfik.bedwars.game.Game;
import pro.delfik.bedwars.game.trading.Resource;

import java.util.ArrayList;
import java.util.List;

public class ResourceSpawner {
	private final Location location;
	private final Resource resource;
	public static final List<ResourceSpawner> list = new ArrayList<>();
	
	public ResourceSpawner(Location loc, Resource res) {
		location = loc;
		resource = res;
		list.add(this);
	}
	
	public Location getLocation() {return location;}
	public Resource getResource() {return resource;}
	
	public void spawn() {location.getWorld().dropItemNaturally(location, resource.getItem()).setPickupDelay(0);}
	
	public static void scheduleBronze() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(BWPlugin.plugin, () -> {
			for (Game game : Game.byWorld.values())
				for (BWTeam team : game.getTeams()) if (team.isAlive()) team.getBronze().forEach(s -> s.spawn());
		}, Defaults.DELAY_BRONZE, Defaults.DELAY_BRONZE);
	}
	
	public static void scheduleIron() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(BWPlugin.plugin, () -> {
			for (Game game : Game.byWorld.values())
				for (BWTeam team : game.getTeams()) if (team.isAlive()) team.getIron().forEach(s -> s.spawn());
		}, Defaults.DELAY_IRON, Defaults.DELAY_IRON);
	}
	
	public static void scheduleGold() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(BWPlugin.plugin, () -> {
			for (Game game : Game.byWorld.values())
				game.gold.forEach(s -> s.spawn());
		}, Defaults.DELAY_GOLD, Defaults.DELAY_GOLD);
	}
	
}
