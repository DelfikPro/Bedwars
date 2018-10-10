package pro.delfik.bedwars.game;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.inventory.Inventory;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Team;
import pro.delfik.bedwars.util.CyclicIterator;
import pro.delfik.bedwars.util.FixedArrayList;
import pro.delfik.bedwars.util.Resources;
import pro.delfik.lmao.user.Person;
import pro.delfik.lmao.util.Vec;
import pro.delfik.lmao.util.Vec3i;

import java.util.ArrayList;
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
	private Resources<CyclicIterator<ResourceSpawner>> resourceSpawners = new Resources<>(a -> new CyclicIterator<>(ResourceSpawner.EMPTY));
	
	// Сундуки, принадлежащие команде
	private final List<Vec3i> chests = new ArrayList<>();
	
	// Эндер-сундук команды
	private final Inventory enderChest = Bukkit.createInventory(null, 18, "§5§lГиперсундук");

	private boolean hasBed = true;
	
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
			handle.addEntry(person.getName());
			handle.setDisplayName(c.getPrefix() + person.getName());
			game.byName.put(person.getName(), this);
		});
	}
	
	public Color getColor() {
		return color;
	}
	
	public Resources<CyclicIterator<ResourceSpawner>> getResourceSpawners() {
		return resourceSpawners;
	}
	
	public List<Person> getPlayers() {
		return players.removeGaps();
	}
	
	public Score getSidebarScore() {
		return sidebarCounter;
	}
	
	public Team getBukkitTeam() {
		return handle;
	}
	
	public void enableSpawners(Game game) {
		Resources<List<Vec>> resources = game.getMap().getResourceSpawners().getDefault(color);
		World w = game.getWorld();
		
		resources.forEach((resource, vecs) -> {
			for (Vec vec : vecs) addResourceSpawner(new ResourceSpawner(resource, vec.toLocation(w)));
		});
	}
	
	public boolean defeated() {
		return players.isEmpty();
	}
	
	public List<Vec3i> getChests() {
		return chests;
	}
	
	private void addResourceSpawner(ResourceSpawner resourceSpawner) {
		resourceSpawners.getDefault(resourceSpawner.getResource()).add(resourceSpawner);
	}
	
	public Inventory getEnderChest() {
		return enderChest;
	}

	public boolean hasBed() {
		return hasBed;
	}

	public void remove(Person p) {
		players.remove(p);
	}

	public void setHasBed(boolean b) {
		hasBed = b;
	}

}
