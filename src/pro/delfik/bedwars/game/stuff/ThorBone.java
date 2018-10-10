package pro.delfik.bedwars.game.stuff;

import implario.util.Converter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import pro.delfik.bedwars.GameListener;
import pro.delfik.lmao.user.Person;

import java.util.ArrayList;
import java.util.List;

public class ThorBone implements Listener {

	@EventHandler
	public void on_PODZHARIT_DRUGOGO_IGROKA_NAHUY(PlayerInteractEvent e) {
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK && e.getAction() != Action.RIGHT_CLICK_AIR) return;
		if (e.getPlayer().getItemInHand() == null || e.getPlayer().getItemInHand().getType() != Material.BONE) return;
		e.setCancelled(true);
		GameListener.decrementHandItem(e.getPlayer());
		Block starring = e.getPlayer().getTargetBlock(Converter.asSet(Material.AIR), 200);
		if (starring == null || starring.getType() == Material.AIR) {
			e.getPlayer().sendMessage("§cВы должны смотреть на блок.");
			return;
		}
		World w = starring.getWorld();
		Location a = starring.getLocation();
		List<Player> KAPOW = new ArrayList<>();
		for (Player target : w.getPlayers()) {
			if (target.getDisplayName().charAt(1) == e.getPlayer().getDisplayName().charAt(1)) continue;
			Location b = target.getLocation();
			if (
					abs(b.getBlockX() - a.getBlockX()) < 4 &&
					abs(b.getBlockY() - a.getBlockY()) < 4 &&
					abs(b.getBlockZ() - a.getBlockZ()) < 4
			) KAPOW.add(target);
		}
		for (Player player : KAPOW) w.strikeLightning(player.getLocation());
		int striked = KAPOW.isEmpty() ? 0 : KAPOW.size();
		String message = "§6Поражено молнией: §e§l" + striked + " игрок" + Converter.plural(striked, "", "а", "ов");
		Person.get(e.getPlayer()).sendActionBar(message);
	}

	private int abs(int i) {
		return i < 0 ? -i : i;
	}

}
