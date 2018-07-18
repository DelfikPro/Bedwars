package pro.delfik.bedwars.service;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class WorldManager {
	public static void reset(World w) {
		w.getPlayers().forEach(player -> player.kickPlayer("§cСервер §e" + w.getName() + "§c перезагружается."));
		for (Chunk c : w.getLoadedChunks()) c.unload();
		
		
	}
	
	
	public static void copyFileStructure(File source, File target) {
		try {
			ArrayList<String> ignore = new ArrayList<>(Arrays.asList("uid.dat", "session.lock"));
			if(!ignore.contains(source.getName())) {
				if(source.isDirectory()) {
					if(!target.exists())
						if (!target.mkdirs())
							throw new IOException("Couldn't create world directory!");
					String files[] = source.list();
					for (String file : files) {
						File srcFile = new File(source, file);
						File destFile = new File(target, file);
						copyFileStructure(srcFile, destFile);
					}
				} else {
					InputStream in = new FileInputStream(source);
					OutputStream out = new FileOutputStream(target);
					byte[] buffer = new byte[1024];
					int length;
					while ((length = in.read(buffer)) > 0)
						out.write(buffer, 0, length);
					in.close();
					out.close();
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static boolean unloadWorld(World world) {
		return world != null && Bukkit.getServer().unloadWorld(world, false);
	}
	
	public static World copyWorld(File originalWorld, String newWorldName) {
		Bukkit.broadcastMessage("GameReset §e> §6Копирование мира §e" + originalWorld.getAbsolutePath() + "§6 в папку §egame§6...");
		copyFileStructure(originalWorld, new File(Bukkit.getWorldContainer(), newWorldName));
		//Bukkit.broadcastMessage("GameReset §e> §aМир скопирован.");
		//Bukkit.broadcastMessage("GameReset §e> §6Загрузка мира...");
		WorldCreator wc = new WorldCreator(newWorldName);
		wc.type(WorldType.FLAT); wc.generatorSettings("3;1*minecraft:glass;127;");
		return wc.createWorld();
	}
	
	public static void deleteWorld(File path) {
		if (path.exists()) {
			File files[] = path.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					deleteWorld(file);
				} else {
					file.delete();
				}
			}
		}
	}
}
