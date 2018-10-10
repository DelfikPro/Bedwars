package pro.delfik.bedwars.game.stuff;

import org.bukkit.entity.Player;
import pro.delfik.bedwars.game.BWTeam;
import pro.delfik.bedwars.game.Game;
import pro.delfik.lmao.user.Person;

public class GPSTracker {
	
	public static void updateFor(Game g, BWTeam t, Person p) {
		Player target = null;
		double lowestDistance = Double.MAX_VALUE;
		for (BWTeam team : g.getTeams().values()) {
			if (team.defeated()) continue;
			if (t.getColor() == team.getColor()) continue;
			for (Person person : team.getPlayers()) {
				double distance = person.getHandle().getLocation().distance(p.getLocation());
				if (distance < lowestDistance) {
					lowestDistance = distance;
					target = person.getHandle();
				}
			}
		}
		if (target == null) {
			p.getHandle().setCompassTarget(p.getLocation());
			p.getHandle().updateInventory();
			p.sendTitle("§f");
			p.sendSubtitle("§7Врагов нет.");
		} else {
			p.getHandle().setCompassTarget(target.getLocation());
			p.getHandle().updateInventory();
			p.sendTitle("§f");
			p.sendSubtitle(target.getDisplayName() + " §e[§f" + ((int) lowestDistance) + " м§e]");
		}
	}
}
