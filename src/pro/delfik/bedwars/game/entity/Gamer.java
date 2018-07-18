package pro.delfik.bedwars.game.entity;

import pro.delfik.bedwars.game.BWTeam;
import pro.delfik.bedwars.game.Game;
import pro.delfik.lmao.core.Person;

import java.util.HashMap;

public class Gamer {
	
	public static final HashMap<String, Gamer> list = new HashMap<>();
	private BWTeam team = null;
	private Game game = null;
	private final Person person;
	
	public Gamer(Person person) {
		this.person = person;
		list.put(person.getName(), this);
	}
	
	public BWTeam getTeam() {
		return team;
	}
	
	public Game getGame() {
		return game;
	}
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	public void setTeam(BWTeam team) {
		if (game == null || !team.game.equals(game)) setGame(team.game);
		this.team = team;
		team.addPlayer(person.getHandle());
	}
}
