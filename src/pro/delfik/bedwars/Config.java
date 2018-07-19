package pro.delfik.bedwars;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public final class Config {
	private static Location lobbyLocation;
	private static World lobby;
	
	private Config() {}
	
	private static YamlConfiguration yml;
	private static File file;
	
	public static void init(String filename) {
		File file = new File(filename);
		if (!file.exists()) try {
			file.createNewFile();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		Config.file = file;
		yml = YamlConfiguration.loadConfiguration(file);
		initLobby();
	}
	
	private static void initLobby() {
		String lobbyname = yml.getString("lobbyworld");
		if (lobbyname != null) {
			lobby = Bukkit.getWorld(lobbyname);
			if (lobby == null) lobby = Bukkit.getWorlds().get(0);
		} else lobby = Bukkit.getWorlds().get(0);
		lobbyLocation = lobby.getSpawnLocation().add(0.5, 0.5, 0.5);
	}
	
	public static YamlConfiguration getYml() {
		return yml;
	}
	
	public static void save() {
		try {
			yml.save(file);
		} catch (IOException e) {throw new RuntimeException(e);}
	}
	
	public static Location getLobbyLocation() {
		return lobbyLocation;
	}
}
