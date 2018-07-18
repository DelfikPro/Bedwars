package pro.delfik.bedwars.game;

import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.EnumTitleAction;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Team;
import pro.delfik.bedwars.Defaults;
import pro.delfik.bedwars.game.environment.Bed;
import pro.delfik.bedwars.game.environment.ResourceSpawner;
import pro.delfik.bedwars.game.trading.Resource;
import pro.delfik.bedwars.handle.TeamInfo;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BWTeam {
	
	public final Game game;
	
	private final Team team;
	private final Defaults.Teams color;
	private final List<Player> players = new LinkedList<>();
	private boolean hasBed = true;
	public Score score;
	
	private final Bed bed;
	private final Set<ResourceSpawner> bronze;
	private final Set<ResourceSpawner> iron;
	private final Set<Location> spawnpoints;
	
	public final Inventory enderinv = Bukkit.createInventory(null, 27, "§5Командный ларчик");
	
	public BWTeam(TeamInfo info, Game g) {
		this.game = g;
		this.color = info.getColor();
		this.spawnpoints = info.getSpawnpoints();
		this.bed = info.getBed();
		this.bronze = info.getBronze();
		this.iron = info.getIron();
		this.team = info.getTeam();
		this.score = info.getScore();
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public org.bukkit.scoreboard.Team getTeam() {
		return team;
	}
	public Defaults.Teams getTemplate() {
		return color;
	}
	
	public void addPlayer(Player p) {
		players.add(p);
		team.addPlayer(p);
		p.setDisplayName(color + p.getName());
		score.setScore(players.size());
		game.updateInv(this);
	}
	
	public boolean removePlayer(Player p) {
		return removePlayer(p, true);
	}
	public boolean hasBed() {
		return hasBed;
	}
	
	public void destroyBed(String breaker) {
		game.getWorld().setGameRuleValue("doTileDrops", "false");
		for (Location loc : bed.getLocs()) loc.getBlock().setType(Material.AIR);
		game.getWorld().setGameRuleValue("doTileDrops", "true");
		
		// Отправка жирной надписи на весь экран игрокам
		IChatBaseComponent cT = ChatSerializer.a("{\"text\":\"§f\"}");
		IChatBaseComponent cS = ChatSerializer.a("{\"text\":\"Ваша кровать разрушена игроком §7" + breaker + "\"}");
		for (Player p : players) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, cS));
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.TITLE, cT));
		}
		Bukkit.broadcastMessage(breaker + "§f разрушил " + color.getColor() + color.getAccusative() + " кровать");
		
		// Отрисовка крестика в скорборде, перенос скора в новый.
		score.getObjective().getScoreboard().resetScores(score.getEntry());
		score = score.getObjective().getScore("§c\u2716" + color + score.getEntry().substring(4));
		score.setScore(players.size());
		this.hasBed = false;
	}
	
	public boolean removePlayer(Player p, boolean check) {
		boolean b = players.remove(p);
		if (b) score.setScore(players.size());
		game.updateInv(this);
		//if (check) Bedwars.check();
		return b;
	}
	
	public Set<Location> getRespawnPoints() {
		return spawnpoints;
	}
	
	public boolean isHasBed() {
		return hasBed;
	}
	
	public void setHasBed(boolean hasBed) {
		this.hasBed = hasBed;
	}
	
	public String getName() {
		return color.getName();
	}
	
	public String getAccusative() {
		return color.getAccusative();
	}
	
	public Location[] getBed() {
		return bed.getLocs();
	}
	
	public Set<ResourceSpawner> getBronze() {
		return bronze;
	}
	
	public Set<ResourceSpawner> getIron() {
		return iron;
	}
	
	public Set<Location> getSpawnpoints() {
		return spawnpoints;
	}
	
	public boolean isAlive() {
		return !players.isEmpty() || hasBed;
	}
	
	public Defaults.Teams getColor() {
		return color;
	}
}
