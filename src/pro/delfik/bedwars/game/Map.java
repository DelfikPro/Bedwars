package pro.delfik.bedwars.game;

import org.bukkit.Location;
import org.bukkit.World;
import pro.delfik.bedwars.util.Colors;
import pro.delfik.bedwars.util.CyclicIterator;
import pro.delfik.bedwars.util.Resources;
import pro.delfik.bedwars.world.Vec;

import java.util.List;

public class Map {
	
	private final String name, schematic;
	private final int teams, teamPlayers;
	private final Vec center;
	private final Colors<CyclicIterator<Vec>> spawns;
	private final Colors<Resources<List<Vec>>> resourceSpawners;
	
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
}
