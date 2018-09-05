package pro.delfik.bedwars.game;

import implario.util.Converter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import pro.delfik.bedwars.util.Colors;
import pro.delfik.bedwars.util.CyclicIterator;
import pro.delfik.bedwars.util.Resources;
import pro.delfik.lmao.util.Vec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Map {

	private static final HashMap<Integer, List<Map>> FORMAT = new HashMap<>();
	private static final HashMap<String, Map> LIST = new HashMap<>();

	private final String name, schematic;
	private final int teams;
	private final Vec center;
	private final Colors<CyclicIterator<Vec>> spawns;
	private final Colors<Resources<List<Vec>>> resourceSpawners;
	private final Material material;

	/**
	 * Создаёт инстанцию карты, которая потом используется всё время.
	 * @param name Человекочитаемойе название карты.
	 * @param schematic Название файла со схематикой в папке ./plugins/WorldEdit/schematics
	 * @param teams Количество команд на карте.
	 * @param center Позиция, из которой копировалась карта в схематику.
	 * @param spawns Референс с точками спавна для каждой команды.
	 * @param resources Референс с точками спавнеров ресурсов для каждой команды.
	 */
	public Map(String name, String schematic, int teams, Vec center, Colors<List<Vec>> spawns,
				Colors<Resources<List<Vec>>> resources) {
		this.name = name;
		this.schematic = schematic;
		this.teams = teams;
		this.center = center;
		this.spawns = spawns.convert(CyclicIterator::new);
		this.resourceSpawners = resources;
		this.material = Converter.randomEnum(Material.class);
		if (!FORMAT.containsKey(teams)) FORMAT.put(teams, new ArrayList<>());
		FORMAT.get(teams).add(this);
		LIST.put(schematic, this);
	}
	
	public Colors<Resources<List<Vec>>> getResourceSpawners() {
		return resourceSpawners;
	}
	
	public String getSchematic() {
		return schematic;
	}
	
	public String getName() {
		return name;
	}
	
	public int getTeams() {
		return teams;
	}
	
	public Vec getCenter() {
		return center;
	}
	
	/**
	 * Возвращает случайную точку спавна для игрока заданного цвета.
	 * @param color Цвет игрока (Лакай всегда голубой).
	 * @return Точка спавна.
	 */
	public Location getSpawnLocation(Color color, World w) {
		return spawns.get(color).next().toLocation(w);
	}

	public Set<Color> getRegisteredColors() {
		return spawns.keySet();
	}

	/**
	 * Ищет карты по количеству команд.
	 * @param format Формат карты, например 2 команды.
	 * @return Список карт с подходящим форматом.
	 */
	public static List<Map> getMaps(int format) {
		List<Map> list = FORMAT.get(format);
		return list != null ? list : new ArrayList<>();
	}

	public Material getMaterial() {
		return material;
	}

	public static Map get(String schematicName) {
		return LIST.get(schematicName);
	}
}
