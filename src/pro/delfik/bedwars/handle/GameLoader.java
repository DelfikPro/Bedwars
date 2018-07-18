package pro.delfik.bedwars.handle;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import pro.delfik.bedwars.Defaults;
import pro.delfik.bedwars.game.BWTeam;
import pro.delfik.bedwars.game.Game;
import pro.delfik.bedwars.game.environment.Bed;
import pro.delfik.bedwars.service.WorldManager;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class GameLoader {
	private final String map;
	private final YamlConfiguration yml;
	private final File freshfolder;
	private final File destfolder;
	private World world;
	
	public GameLoader(String map) {
		try {
			this.map = map;
			this.freshfolder = new File(Defaults.MAP_PREFIX + map);
			this.destfolder = new File(map);
			this.yml = YamlConfiguration.loadConfiguration(new File(Defaults.MAP_PREFIX + map + File.separator + "config.yml"));
		} catch (Exception ex) {
			System.out.println("An error occurred while attempting to load neccessary files for " + map);
			throw ex;
		}
	}
	
	public void preload() {
		World w = Bukkit.getWorld(map);
		if (w != null) Bukkit.unloadWorld(w, false);
		WorldManager.deleteWorld(destfolder);
		WorldManager.copyFileStructure(freshfolder, destfolder);
	}
	
	public World loadWorld() {
		WorldCreator wc = new WorldCreator(map);
		wc.type(WorldType.FLAT); wc.generatorSettings("3;1*minecraft:glass;127;");
		return world = wc.createWorld();
	}
	
	public Game load() {
		if (world == null) throw new IllegalStateException("World " + map + " not loaded!");
		Scoreboard s = Bukkit.getScoreboardManager().getNewScoreboard();
		try {
			Objective o = s.registerNewObjective("stats", "dummy");
			o.setDisplayName("§b§lBedWars");
			o.setDisplaySlot(DisplaySlot.SIDEBAR);
			o.getScore("Карта: §l" + yml.getString("map")).setScore(99);
			o.getScore("§f§a").setScore(98);
			o.getScore("§f§b").setScore(-1);
			o.getScore("§a§lLmaoNetwork.ru").setScore(-2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ConfigurationSection tsec = yml.getConfigurationSection("teams");
		Set<String> keys = tsec.getKeys(false);
		Set<TeamInfo> teams = new HashSet<>();
		for (String team : tsec.getKeys(false)) {
			ConfigurationSection sec = tsec.getConfigurationSection(team);
			sec.getKeys(false).forEach(st -> Bukkit.broadcastMessage("§7§ost = " + st));
			Set<Location> respawns = parse(sec.getStringList("player-spawns"), world);
			Set<Location> bronze = parse(sec.getStringList("bronze-spawns"), world);
			Set<Location> iron = parse(sec.getStringList("iron-spawns"), world);
			Bed bed = parseBed(sec.getStringList("bed"), world);
			teams.add(new TeamInfo(s, Defaults.Teams.valueOf(sec.getString("color")), respawns, bed, bronze, iron));
		}
		return new Game(teams, world, s, yml.getInt("max-per-team"), yml.getString("map"));
	}
	
	private Bed parseBed(List<String> list, World w) {
		Iterator<Location> b = parseBlocks(list, w).iterator();
		return new Bed(b.next(), b.next());
	}
	
	private static Set<Location> parse(List<String> list, World w) {
		final Set<Location> locs = new HashSet<>();
		list.forEach(loc -> {
			String[] ss = loc.split(", ");
			double x = Double.parseDouble(ss[0]), y = Double.parseDouble(ss[1]), z = Double.parseDouble(ss[2]);
			float pitch = 0, yaw = 0;
			if (ss.length > 3) {
				pitch = Float.parseFloat(ss[4]);
				yaw = Float.parseFloat(ss[3]);
			}
			locs.add(new Location(w, x, y, z, yaw, pitch));
		});
		return locs;
	}
	private static Set<Location> parseBlocks(List<String> list, World w) {
		final Set<Location> locs = new HashSet<>();
		list.forEach(loc -> {
			String[] ss = loc.split(", ");
			int x = Integer.parseInt(ss[0]), y = Integer.parseInt(ss[1]), z = Integer.parseInt(ss[2]);
			locs.add(new Location(w, x, y, z).add(0.5, 0.5, 0.5));
		});
		return locs;
	}
}
