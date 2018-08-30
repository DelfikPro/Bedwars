package pro.delfik.bedwars.preparation;

import implario.util.Converter;

import static pro.delfik.util.Converter.toInt;

public class GameSize {
	private final int teams, players;

	public GameSize(int teams, int players) {
		this.teams = teams;
		this.players = players;
	}

	public int getPlayers() {
		return players;
	}
	public int getTeams() {
		return teams;
	}
	public String getTitle() {
		return players + "x" + teams;
	}
	public int getTotal() {
		return players * teams;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof GameSize)) return false;
		GameSize size = ((GameSize) o);
		return size.teams == teams && size.players == players;
	}

	@Override
	public int hashCode() {
		return teams * 10 + players;
	}

	public static GameSize get(String arg) {
		String[] a = arg.split("x");
		if (a.length != 2) return null;
		return new GameSize(toInt(a[0]), toInt(a[1]));
	}
}
