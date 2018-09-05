package pro.delfik.bedwars.game;

import pro.delfik.lmao.outward.item.I;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import pro.delfik.bedwars.util.Colors;
import pro.delfik.bedwars.util.FixedArrayList;
import pro.delfik.bedwars.util.Resources;
import pro.delfik.bedwars.world.Schematics;
import pro.delfik.bedwars.world.WorldUtils;
import pro.delfik.lmao.core.Person;
import pro.delfik.lmao.util.Cooldown;
import implario.util.Converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class Game {
	
	// Ограничение на максимальное количество одновременных игр
	public static final int MAX_RUNNING_GAMES = 5;
	
	// Текущие игры
	private static final FixedArrayList<Game> RUNNING = new FixedArrayList<>(MAX_RUNNING_GAMES);
	
	public static Game get(World world) {
		String name = world.getName();
		if (!name.startsWith("BW_")) return null;
		int i = Converter.toInt(name.substring(3), -1);
		return i == -1 ? null : i < MAX_RUNNING_GAMES ? RUNNING.get(i) : null;
	}
	
	// ID мира, в котором идёт игра
	private final int id;
	
	// Карта, на которой идёт игра
	private final Map map;
	
	// Референс по командам, участвующим в игре
	private final Colors<BWTeam> teams;
	
	// Скорбоард для хранения команд и информации в сайдбаре
	private final Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
	
	// Задача в скорборде (Отображение информации в сайдбаре)
	private final Objective objective = scoreboard.registerNewObjective("bedwars", "dummy");
	
	// Мир, в котором идёт игра
	private final World world;
	
	// Состояние игры
	private volatile State state = State.NOTHING;
	
	// Референс на таски, которые нужно отменить, чтобы прекратить спавн ресурсов
	private final Resources<BukkitTask> resourceTasks = new Resources<>();
	
	// Мап для получения команды по нику игрока
	protected final HashMap<String, BWTeam> byName = new HashMap<>();
	
	/**
	 * Создание новой игры и генерация карты
	 *
	 * @param map     Карта, на которой будет идти игра.
	 * @param players Отсортированные по командам игроки.
	 */
	public Game(Map map, Colors<Collection<Person>> players) {
		
		// Проверка на наличие свободных карт и запись в список текущих игр.
		int id = RUNNING.firstEmpty();
		if (id == -1) throw new IllegalStateException("Нет свободных карт!");
		this.id = id;
		RUNNING.set(id, this);
		
		this.map = map;
		
		// Настройка скорборда
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName("§c§lBed§f§lWars");
		
		// Настройка команд
		teams = players.convert((color, people) -> new BWTeam(color, this, people.toArray(new Person[people.size()])));
		
		// Прогрузка мира
		world = loadWorld();
		
		// Генерация карты
		buildMap();
		startCooldown();
	}
	
	public void startCooldown() {
		setState(State.COOLDOWN);
		new Cooldown("BW_" + id, 3, getPlayers(), this::start);
	}
	
	public State getState() {
		return state;
	}
	
	private void setState(State state) {
		this.state = state;
	}
	
	public List<Person> getPlayers() {
		List<Person> list = new ArrayList<>();
		for (BWTeam t : teams.values()) list.addAll(t.getPlayers());
		return list;
	}
	
	/**
	 * Быстрая итерация по всем игрокам, принадлежащим этой игре.
	 * @param consumer Функция, которую нужно применить к каждому из игроков.
	 */
	protected void forPlayers(Consumer<Person> consumer) {
		for (BWTeam team : teams.values()) team.getPlayers().forEach(consumer);
	}
	
	/**
	 * Загрузить (При необходимости) и подготовить мир к вставке карты.
	 * @return Готовый к вставке карты мир
	 */
	private World loadWorld() {
		String worldName = "BW_" + id;
		World world = WorldUtils.loadWorld(worldName);
		WorldUtils.clear(world);
		return world;
	}
	
	/**
	 * Вставить карту из схематики в мир.
	 */
	private void buildMap() {
		setState(State.GENERATING);
		new Thread(() -> {
			Schematics schematics = new Schematics(world);
			schematics.loadSchematic(map.getName(), map.getCenter().toVec3i());
			// После успешной вставки схематики можно начинать игру.
			this.startCooldown();
		}).start();
	}
	
	/**
	 * Начать игру.
	 * Телепортировать игроков на карту, включить спавнеры ресурсов.
	 */
	public void start() {
		for (BWTeam team : teams.values()) {
			
			for (Person p : team.getPlayers()) {
				p.teleport(map.getSpawnLocation(team.getColor(), world));
				p.sendTitle("§aИгра началась!");
			}
		}
		enableSpawners();
	}
	
	/**
	 * Начать раздавать ресурсы из спавнеров.
	 */
	private void enableSpawners() {
		for (Resource resource : Resource.values()) {
			I.timer(() -> {
				for (BWTeam t : teams.values())
					t.getResourceSpawners().get(resource).forEach(ResourceSpawner::spawn);
			}, resource.getSpawnTicks());
		}
	}
	
	public Map getMap() {
		return map;
	}
	
	public int getId() {
		return id;
	}
	
	public Colors<BWTeam> getTeams() {
		return teams;
	}
	
	protected Objective getObjective() {
		return objective;
	}
	
	protected Scoreboard getScoreboard() {
		return scoreboard;
	}
	
	public World getWorld() {
		return world;
	}
	
	public BWTeam getTeam(String playername) {
		return byName.get(playername);
	}
	
	public Location getSpawnLocation(Color color) {
		return map.getSpawnLocation(color, world);
	}

	public static Game get(Integer slot) {
		if (slot < 0 || slot > MAX_RUNNING_GAMES) throw new IllegalArgumentException();
		else return RUNNING.get(slot);
	}

	public static Game get(Player player) {
		return get(player.getWorld());
	}

	/**
	 * Состояние игры.
	 * NOTHING: Сектор свободен, здесь можно начать игру.
	 * GENERATING: Генерация карты.
	 * COOLDOWN: Отсчёт до начала игры.
	 * GAME: Процесс игры, битва игроков.
	 * ENDING: Состояние после конца игры, выдача наград и отсчёт до конца.
	 * RESETTING: Очищение мира после игры.
	 */
	public enum State {
		NOTHING("§aОжидание игроков", Material.EMERALD_BLOCK),
		GENERATING("§bГенерация карты", Material.DIAMOND_BLOCK),
		COOLDOWN("§bНачало игры", Material.DIAMOND_BLOCK),
		GAME("§6Идёт игра", Material.GOLD_BLOCK),
		ENDING("§cКонец игры", Material.REDSTONE_BLOCK),
		RESETTING("§6Сброс карты", Material.COMMAND);
		
		private final String title;
		private final Material material;

		State(String title, Material material) {
			this.title = title;
			this.material = material;
		}
		
		public String getTitle() {
			return title;
		}
		
		public Material getMaterial() {
			return material;
		}
	}
}
