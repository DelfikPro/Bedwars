package pro.delfik.bedwars;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pro.delfik.lmao.core.Person;

public class OnlineHandler implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PlayerJoinEvent e) {
		Person p = Person.get(e.getPlayer());
		if (p == null) e.getPlayer().sendMessage("§clmao.core.Person.get(\"§f" + e.getPlayer().getName() + "§c\") is §9§lnull");
		
		
	}
	
	
	public static void resetPlayerToLobby(Person player) {
		Player p = player.getHandle();
		p.teleport(Config.getLobbyLocation());
		p.setHealth(20);
		p.setAllowFlight(true);
		p.setItemOnCursor(Items.NULL);
		p.closeInventory();
		p.getInventory().clear();
		p.getInventory().setItem(0, Items.JOIN_GAME);
	}
}
