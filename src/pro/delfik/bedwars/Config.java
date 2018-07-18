package pro.delfik.bedwars;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public final class Config {
	private Config() {}
	
	private static YamlConfiguration yml;
	private static File file;
	
	public static void init(String filename) {
		File file = new File("plugins/" + filename);
		if (!file.exists()) try {
			file.createNewFile();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		Config.file = file;
		yml = YamlConfiguration.loadConfiguration(file);
	}
	
	public static YamlConfiguration getYml() {
		return yml;
	}
	
	public static void save() {
		try {
			yml.save(file);
		} catch (IOException e) {throw new RuntimeException(e);}
	}
	
}
