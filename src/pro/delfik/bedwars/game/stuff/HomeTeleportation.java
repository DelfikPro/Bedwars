package pro.delfik.bedwars.game.stuff;

import pro.delfik.lmao.outward.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pro.delfik.bedwars.Bedwars;
import pro.delfik.bedwars.game.BWTeam;
import pro.delfik.bedwars.game.Game;
import pro.delfik.lmao.core.Person;
import pro.delfik.lmao.util.Cooldown;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HomeTeleportation {
	private static final Map<String, HomeTeleportation> teleportating = new HashMap<>();
	public static final ItemStack ITEM = ItemBuilder.create(Material.SULPHUR, "§f>> §e§lВжух на базу §f<<");
	
	private final Person p;
	private final BWTeam team;
	private final Game game;
	private final Cooldown cd;
	
	public HomeTeleportation(Person p, BWTeam team, Game game) {
		this.p = p;
		this.team = team;
		this.game = game;
		cd = new Cooldown("tp" + p.getName(), 6, Collections.singletonList(p), this::perform);
		teleportating.put(p.getName(), this);
	}
	
	public void cancel() {
		cd.cancel();
		teleportating.remove(p.getName());
		p.sendSubtitle("§cТелепортация отменена.");
		Bedwars.give(p.getHandle(), ITEM);
		p.sendTitle("");
	}
	
	public void perform() {
		if (game.getState() != Game.State.GAME) return;
		p.sendSubtitle("§aты дома"); // ToDo: Придумать адекватное сообщение при телепортации домой.
		p.sendTitle("§f");
		p.teleport(game.getSpawnLocation(team.getColor()));
	}


	public static HomeTeleportation get(Player player) {
		return teleportating.get(player.getName());
	}
	public static void cancelIfTeleporting(Player p) {
		HomeTeleportation tp = get(p);
		if (tp != null) tp.cancel();
	}
}