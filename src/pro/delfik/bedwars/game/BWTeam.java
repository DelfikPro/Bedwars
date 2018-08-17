package pro.delfik.bedwars.game;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.inventory.Inventory;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Team;
import pro.delfik.bedwars.util.FixedArrayList;
import pro.delfik.bedwars.util.Resources;
import pro.delfik.bedwars.world.Vec;
import pro.delfik.bedwars.world.Vec3i;
import pro.delfik.lmao.core.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BWTeam {
	
	// Bukkit-команда
	private final Team handle;
	
	// Счётчик игроков на сайдбаре
	private final Score sidebarCounter;
	
	// Список игроков
	private final FixedArrayList<Person> players;
	
	// Цвет команды
	private final Color color;
	
	// Отсортированные спавнеры ресурсов
	private Resources<List<ResourceSpawner>> resourceSpawners = new Resources<>(ArrayList::new);
	
	// Сундуки, принадлежащие команде
	private final List<Vec3i> chests = new ArrayList<>();
	
	// Эндер-сундук команды
	private final Inventory enderChest = Bukkit.createInventory(null, 18, "§5Межпространственный чемодан");
	
	public BWTeam(Color c, Game game, Person[] players) {
		this.color = c;
		this.sidebarCounter = game.getObjective().getScore(color.toString());
		this.players = new FixedArrayList<>(players);
		this.handle = game.getScoreboard().registerNewTeam(c.name());
		handle.setCanSeeFriendlyInvisibles(true);
		handle.setAllowFriendlyFire(false);
		handle.setPrefix(c.getPrefix());
		this.players.forEach(person -> {
			person.getHandle().setScoreboard(game.getScoreboard());
			handle.addEntry(handle.getName());
			game.byName.put(person.getName(), this);
		});
		enableSpawners(game);
	}
	
	public Color getColor() {
		return color;
	}
	
	public Resources<List<ResourceSpawner>> getResourceSpawners() {
		return resourceSpawners;
	}
	
	public Collection<Person> getPlayers() {
		return players;
	}
	
	public Score getSidebarScore() {
		return sidebarCounter;
	}
	
	public Team getBukkitTeam() {
		return handle;
	}
	
	private void enableSpawners(Game game) {
		Resources<List<Vec>> resources = game.getMap().getResourceSpawners().get(color);
		World w = game.getWorld();
		
		resources.forEach((resource, vecs) -> {
			for (Vec vec : vecs) addResourceSpawner(new ResourceSpawner(resource, vec.toLocation(w)));
		});
	}
	
	public boolean defeated() {
		return false;
		// ToDo: BWTeam#defeated()
	}
	
	public List<Vec3i> getChests() {
		return chests;
	}
	
	private void addResourceSpawner(ResourceSpawner resourceSpawner) {
		resourceSpawners.computeIfAbsent(resourceSpawner.getResource(), key -> new ArrayList<>()).add(resourceSpawner);
	}
	
	public Inventory getEnderChest() {
	}
}
