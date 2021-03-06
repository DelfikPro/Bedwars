package pro.delfik.bedwars;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import pro.delfik.bedwars.game.Color;
import pro.delfik.bedwars.game.Map;
import pro.delfik.bedwars.game.Resource;
import pro.delfik.bedwars.util.Colors;
import pro.delfik.bedwars.util.Resources;
import pro.delfik.lmao.util.Vec;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static pro.delfik.lmao.util.Vec.toVecList;

/**
 * Класс, читающий конфигурацию карт, и не только.
 * Конфигурация записывается в YAML.
 * @version 1.0
 */
public class ConfigReader {
	
	/**
	 * Инициализация карт.
	 * Прочтение всех файлов .yml в папке ./BWmaps/
	 * С последующей конвертацией в объект Map.
	 * @see Map
	 */
	public static void initMaps() {
		File file = new File("BWmaps");
		if (!(file.exists() && file.isDirectory())) file.mkdir();
		for (File f : file.listFiles()) {
			if (!f.getName().endsWith(".yml")) continue;
			Map map = readMap(f);
			System.out.println("[BedWars] Карта " + map + " успешно зарегистрирована.");
		}
	}
	
	/**
	 * Чтение карты из файла.
	 * @param file Файл .yml с конфигурацией карты
	 * @return Объект Map, представляющий из себя карту со всеми прочитанными свойствами.
	 * P. S. Я не знаю, как это работает, но что бы ты ни делал, не трогай этот код!
	 */
	public static Map readMap(File file) {
		
		YamlConfiguration yml	 = YamlConfiguration.loadConfiguration(file);
		String name				 = yml.getString("name");
		String schematic		 = yml.getString("schematic");
		Vec center				 = Vec.toVec(yml.getString("schematicPosition"));
		List<Vec> middleGold	 = toVecList(yml.getStringList("middleGold"));
		int goldticks			 = yml.getInt("gold-delay");
		int ironticks			 = yml.getInt("iron-delay");
		int bronzeticks			 = yml.getInt("bronze-delay");

		// Парсинг команд
		Colors<List<Vec>> respawns = new Colors<>(),
							bronze = new Colors<>(),
							  iron = new Colors<>(),
							  gold = new Colors<>();
		Colors<Resources<List<Vec>>> resources = new Colors<>();

		ConfigurationSection teams = yml.getConfigurationSection("teams");
		int teamsAmount = teams.getKeys(false).size();
		for (String colorName : teams.getKeys(false)) {
			Color color = Color.get(colorName);
			if (color == null) {
				Bukkit.broadcastMessage("§4Ошибка: §cВ конфигурации карты §f" + name + "§c обнаружен");
				Bukkit.broadcastMessage("§cНеизвестный цвет команды: §f" + colorName);
				continue;
			}
			ConfigurationSection sec = teams.getConfigurationSection(colorName);
			respawns.put(color, toVecList(sec.getStringList("respawns")));
			Resources<List<Vec>> res = new Resources<>(a -> new ArrayList<>());
			
			ConfigurationSection rSec = sec.getConfigurationSection("resources");
			for (String resourceName : rSec.getKeys(false)) {
				Resource resource = Resource.get(resourceName);
				if (resource == null) continue;
				res.put(resource, toVecList(rSec.getStringList(resourceName)));
			}
			resources.put(color, res);
		}

		Resources<Integer> i = new Resources<>();
		i.put(Resource.GOLD, goldticks < 1 ? 200 : goldticks);
		i.put(Resource.IRON, ironticks < 1 ? 100 : ironticks);
		i.put(Resource.BRONZE, bronzeticks < 1 ? 15 : bronzeticks);

		return new Map(name, schematic, teamsAmount, center, respawns, resources, middleGold, i);
	}
}
