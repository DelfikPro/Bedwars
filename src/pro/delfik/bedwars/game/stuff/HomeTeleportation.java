package pro.delfik.bedwars.game.stuff;

import pro.delfik.bedwars.game.BWTeam;
import pro.delfik.bedwars.game.Game;
import pro.delfik.lmao.core.Person;
import pro.delfik.lmao.util.Cooldown;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class HomeTeleportation {
	private static final Set<Integer> teleportating = new HashSet<>();
	
	private final Person p;
	private final BWTeam team;
	private final Game game;
	private final Cooldown cd;
	
	public HomeTeleportation(Person p, BWTeam team, Game game) {
		this.p = p;
		this.team = team;
		this.game = game;
		cd = new Cooldown("tp" + p.getName(), 6, Collections.singletonList(p), this::perform);
		teleportating.add(p.getName().hashCode());
	}
	
	public void cancel() {
		cd.cancel();
		teleportating.remove
	}
	
	public void perform() {
		if (game.getState() != Game.State.GAME) return;
		p.sendSubtitle("§aвы тпхнулись"); // ToDo: Придумать адекватное сообщение при телепортации домой.
		p.teleport(game.getSpawnLocation(team.getColor()));
	}
	
	
}
