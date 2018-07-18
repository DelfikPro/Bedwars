package pro.delfik.bedwars.handle;

import org.bukkit.Location;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import pro.delfik.bedwars.Defaults;
import pro.delfik.bedwars.game.environment.Bed;
import pro.delfik.bedwars.game.environment.ResourceSpawner;
import pro.delfik.bedwars.game.trading.Resource;

import java.util.HashSet;
import java.util.Set;

public class TeamInfo {
	private final Team team;
	private final Defaults.Teams color;
	private final Score score;
	private final Bed bed;
	private final Set<ResourceSpawner> bronze = new HashSet<>();
	private final Set<ResourceSpawner> iron = new HashSet<>();
	private final Set<Location> spawnpoints;
	
	public TeamInfo(Scoreboard s, Defaults.Teams color, Set<Location> spawnpoints, Bed bed, Set<Location> bronze, Set<Location> iron) {
		this.color = color;
		this.spawnpoints = spawnpoints;
		this.bed = bed;
		bronze.forEach(l -> this.bronze.add(new ResourceSpawner(l, Resource.BRONZE)));
		iron.forEach(l -> this.iron.add(new ResourceSpawner(l, Resource.IRON)));
		Team t = s.getTeam(color.getName());
		if (t != null) t.unregister();
		this.team = s.registerNewTeam(color.getName());
		team.setPrefix(color.getColor().toString());
		team.setAllowFriendlyFire(false);
		team.setCanSeeFriendlyInvisibles(true);
		score = s.getObjective(DisplaySlot.SIDEBAR).getScore("§a\u2714 " + color.getColor() + color.getName()); this.score.setScore(0);
	}
	
	public Score getScore() {
		return score;
	}
	public Team getTeam() {
		return team;
	}
	public Bed getBed() {
		return bed;
	}
	public Set<Location> getSpawnpoints() {
		return spawnpoints;
	}
	public Set<ResourceSpawner> getBronze() {
		return bronze;
	}
	public Set<ResourceSpawner> getIron() {
		return iron;
	}
	public Defaults.Teams getColor() {
		return color;
	}
}
