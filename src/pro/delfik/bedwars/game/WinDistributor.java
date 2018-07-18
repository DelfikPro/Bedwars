package pro.delfik.bedwars.game;

import org.bukkit.entity.Player;

public class WinDistributor {
	public static void announce(Game g, BWTeam winner) {
		for (Player p : g.getWorld().getPlayers()) {
			p.sendMessage("§7===================================");
			p.sendMessage("§7= §7 §7 §7 " + winner.getColor().getColor() + winner.getName() + " команда §fодержала победу!");
			p.sendMessage("§7===================================");
			p.sendMessage("§aИгра закончится через 10 секунд.");
		}
	}
	
	public static BWTeam getWinner(Game game) {
		BWTeam winner = null;
		for (BWTeam t : game.getTeams()) {
			if (t.getPlayers().isEmpty() || t.getPlayers().size() == 0) continue;
			if (winner != null) throw new IllegalStateException();
			winner = t;
		}
		return winner;
	}
}
