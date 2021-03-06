package pro.delfik.bedwars.game.stuff;

import org.bukkit.entity.Player;
import pro.delfik.bedwars.Bedwars;
import pro.delfik.bedwars.game.BWTeam;
import pro.delfik.bedwars.game.Game;
import pro.delfik.bedwars.game.Items;
import pro.delfik.lmao.user.Person;
import pro.delfik.lmao.util.Cooldown;

import java.util.HashMap;
import java.util.Map;

import static implario.util.Converter.asList;

public class HomeTeleportation {
	private static final Map<String, HomeTeleportation> teleportating = new HashMap<>();

	private final Person p;
	private final BWTeam team;
	private final Game game;
	private final Cooldown cd;
	
	public HomeTeleportation(Person p, BWTeam team, Game game) {
		this.p = p;
		this.team = team;
		this.game = game;
		cd = new Cooldown("tp" + p.getName(), 6, asList(p), this::perform);
		teleportating.put(p.getName(), this);
	}
	
	public void cancel() {
		cd.cancel();
		teleportating.remove(p.getName());
		p.sendSubtitle("§cТелепортация отменена.");
		Bedwars.give(p.getHandle(), Items.HOME_TELEPORTATION);
		p.sendTitle("");
	}
	
	public void perform() {
		if (game.getState() != Game.State.GAME) return;
		p.sendSubtitle("§aДобро пожаловать домой.");
		p.sendTitle("§f");
		p.teleport(game.getSpawnLocation(team.getColor()));
		teleportating.remove(p.getName());
	}


	public static HomeTeleportation get(Player player) {
		return teleportating.get(player.getName());
	}
	public static void cancelIfTeleporting(Player p) {
		HomeTeleportation tp = get(p);
		if (tp != null) tp.cancel();
	}
}
