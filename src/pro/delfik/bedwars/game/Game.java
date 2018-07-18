package pro.delfik.bedwars.game;

import lib.Generate;
import lib.gui.GUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import pro.delfik.bedwars.BWPlugin;
import pro.delfik.bedwars.Defaults;
import pro.delfik.bedwars.game.environment.ResourceSpawner;
import pro.delfik.bedwars.handle.TeamInfo;
import pro.delfik.bedwars.service.WorldManager;
import pro.delfik.lmao.core.Person;
import pro.delfik.lmao.util.U;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Game {
	
	public static final HashMap<String, Game> byWorld = new HashMap<>();
	
	private final String mapname;
	private State state = State.WAITING;
	private final BWTeam[] teams;
	private final World w;
	public final GUI inv = new GUI(Bukkit.createInventory(null, 27, "Выбор команды"));
	private final Scoreboard s;
	private final LinkedList<Person> players = new LinkedList<>();
	private final int maxInTeam;
	public final Set<ResourceSpawner> gold = new HashSet<>();
	
	public Game(Set<TeamInfo> teams, World w, Scoreboard s, int maxInTeam, String mapname) {
		this.w = w;
		this.s = s;
		this.mapname = mapname;
		this.maxInTeam = maxInTeam;
		byWorld.put(w.getName(), this);
		int i = 0;
		this.teams = new BWTeam[teams.size()];
		for (TeamInfo team : teams) {
			this.teams[i] = new BWTeam(team, this);
		}
	}
	
	
	public void end() {
		byWorld.remove(w.getName());
		state = State.ENDING;
		WinDistributor.announce(this, WinDistributor.getWinner(this));
		Bukkit.getScheduler().runTaskLater(BWPlugin.plugin, () -> w.getPlayers().forEach(p -> U.send(p.getName(), "LOBBY_1")), 200L);
		
		WorldManager.reset(w);
	}
	
	
	public Inventory getInventory() {
		return inv.getInventory();
	}
	
	public BWTeam[] getTeams() {
		return teams;
	}
	
	public LinkedList<Person> getPlayers() {
		return players;
	}
	
	public Scoreboard getScoreboard() {
		return s;
	}
	
	public World getWorld() {
		return w;
	}
	
	public State getState() {
		return state;
	}
	
	public void updateInv(BWTeam t) {
		List<String> lore = new LinkedList<>();
		t.getPlayers().forEach(p -> lore.add(p.getDisplayName()));
		if (lore.size() < maxInTeam) lore.add("§e>> Нажмите, чтобы присоединиться <<");
		Defaults.Teams o = t.getTemplate();
		ItemStack i = Generate.itemstack(Material.WOOL, 1, o.getWool(),
				o.getColor() + o.getName() + " команда §f[" + t.getPlayers().size() + "/" + maxInTeam + "]", lore.toArray(new String[] {}));
	}
	
	public enum State {
		WAITING(),
		STARTING(),
		GENERATION(),
		GAME(),
		ENDING()
	}
}
