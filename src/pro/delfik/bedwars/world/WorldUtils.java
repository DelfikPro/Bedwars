package pro.delfik.bedwars.world;

import org.bukkit.*;
import org.bukkit.entity.Player;
import pro.delfik.bedwars.Bedwars;

import java.io.File;

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
		return w;
		
	}
	
}
