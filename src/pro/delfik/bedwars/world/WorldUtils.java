package pro.delfik.bedwars.world;

import com.sk89q.worldedit.Vector;
import org.bukkit.*;
import org.bukkit.entity.Player;
import pro.delfik.bedwars.Bedwars;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

/**
 * Полезные методы при работе с мирами.
 * @version 2.0
 */
public class WorldUtils {
	
	/**
	 * Очищает мир (Регенерирует) без выгрузки самого мира.
	 * @param w Мир для очистки.
	 */
	public static void clear(World w) {
		
		// Если в мире ещё остались какие-то игроки, департировать их в лобби.
		for (Player p : w.getPlayers()) Bedwars.toLobby(p);
		
		// Выгрузить все чанки
		Chunk[] loadedChunks = w.getLoadedChunks();
		for (Chunk chunk : loadedChunks) chunk.unload(false);
		
		// Удалить файлы с чанками из папки мира
		File region = new File(w.getWorldFolder(), "region");
		if (!(region.exists() && region.isDirectory())) return;
		for (File file : region.listFiles()) if (!file.isDirectory()) file.delete();
		
	}
	
	/**
	 * Прогрузить мир с нужными настройками (Генерировать новые чанки пустыми)
	 * @param name Название мира.
	 * @return Прогруженный мир.
	 */
	public static World loadWorld(String name) {
		
		// Если мир уже прогружен, вернуть его.
		World w = Bukkit.getWorld(name);
		if (w != null) return w;
		
		// Прогрузить или создать новый мир через WorldCreator
		WorldCreator c = new WorldCreator(name);
		c.type(WorldType.FLAT);
		c.generatorSettings("3;1*minecraft:air;127;");
		c.generateStructures(false);
		w = Bukkit.createWorld(c);
		w.setMonsterSpawnLimit(0);
		w.setGameRuleValue("doDaylightCycle", "false");
		w.setGameRuleValue("doMobSpawning", "false");
		w.setGameRuleValue("randomTickSpeed", "0");
		w.setGameRuleValue("showDeathMessages", "false");
		w.setGameRuleValue("mobGriefing", "false");
		w.setGameRuleValue("doFireTick", "false");
		return w;
		
	}

	public static List<Chunk> getAllChunksBetween(World w, Vector a, Vector b) {
		Location aa = new Location(w, a.getX(), a.getY(), a.getZ());
		Location bb = new Location(w, b.getX(), b.getY(), b.getZ());
		Chunk A = aa.getChunk(), B = bb.getChunk();
		int ax = A.getX(), bx = B.getX(), az = A.getZ(), bz = B.getZ();
		List<Chunk> result = new ArrayList<>();
		System.out.println("Clearing map in " + w.getName() + ": a(" + ax + ", " + az + "), b(" + bx + ", " + bz + ")");
		for (int x = min(ax, bx); x < max(ax, bx); x++)
			for (int z = min(az, bz); z < max(az, bz); z++)
				result.add(w.getChunkAt(x, z));
		return result;
	}
}
