package pro.delfik.bedwars.preparation;

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
}
