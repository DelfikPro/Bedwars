package pro.delfik.bedwars.game;

import lib.gui.VotingGUI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import pro.delfik.bedwars.preparation.GameSize;
import pro.delfik.bedwars.util.Colors;
import pro.delfik.bedwars.util.CyclicIterator;
import pro.delfik.bedwars.util.Resources;
import pro.delfik.lmao.util.Vec;
import implario.util.Converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Map {

	private static final HashMap<GameSize, List<Map>> FORMAT = new HashMap<>();
	private static final HashMap<String, Map> LIST = new HashMap<>();

	private final String name, schematic;
	private final int teams, teamPlayers;
	private final Vec center;
	private final Colors<CyclicIterator<Vec>> spawns;
	private final Colors<Resources<List<Vec>>> resourceSpawners;
	private final Material material;

	/**
	 * Создаёт инстанцию карты, которая потом используется всё время.
	 * @param name Человекочитаемойе название карты.
	 * @param schematic Название файла со схематикой в папке ./plugins/WorldEdit/schematics
	 * @param teamPlayers Максимальное количество игроков в одной команде.
	 * @param teams Количество команд на карте.
	 * @param center Позиция, из которой копировалась карта в схематику.
	 * @param spawns Референс с точками спавна для каждой команды.
	 * @param resources Референс с точками спавнеров ресурсов для каждой команды.
	 */
	public Map(String name, String schematic, int teamPlayers, int teams, Vec center, Colors<List<Vec>> spawns,
				Colors<Resources<List<Vec>>> resources) {
		this.name = name;
		this.schematic = schematic;
		this.teams = teams;
		this.teamPlayers = teamPlayers;
		this.center = center;
		this.spawns = spawns.convert(CyclicIterator::new);
		this.resourceSpawners = resources;
		this.material = Converter.randomEnum(Material.class);
		GameSize format = new GameSize(teams, teamPlayers);
		if (!FORMAT.containsKey(format)) FORMAT.put(format, new ArrayList<>());
		FORMAT.get(format).add(this);
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
	
	public int getTeamPlayers() {
		return teamPlayers;
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
	 * Ищет карты по формату игры.
	 * @param format Формат карты, например 4х2.
	 * @return Список карт с подходящим форматом.
	 */
	public static List<Map> getMaps(GameSize format) {
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
