package pro.delfik.bedwars;

import org.bukkit.GameMode;
import org.bukkit.inventory.PlayerInventory;
import pro.delfik.bedwars.game.Items;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pro.delfik.lmao.user.Person;

public class Bedwars {
	private static World lobby;


	public static void toLobby(Player p) {
		p.teleport(getLobby().getSpawnLocation());
		p.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
		p.setExp(0);
		p.setExhaustion(20);
		p.setSaturation(20);
		p.setAllowFlight(true);
		p.setGameMode(GameMode.ADVENTURE);
		p.setDisplayName(Person.get(p).getRank().getPrefix() + "§7" + p.getName());
		PlayerInventory inv = p.getInventory();
		inv.clear();
		inv.setArmorContents(null);
		inv.setItem(0, Items.JOIN_GAME);
		inv.setItem(8, Items.BACK_TO_LOBBY);
	}

	/**
	 * Выдать игроку предмет. Если предмет не поместится в инвентарь,
	 * Он выпадет на землю рядом с игроком.
	 * @param p Игрок, которому нужно выдать предмет
	 * @param item Предмет для выдачи
	 */
	public static void give(Player p, ItemStack... item) {
		for (ItemStack i : p.getInventory().addItem(item).values())
			p.getWorld().dropItemNaturally(p.getLocation(), i);
	}

	public static void toGame(Player p) {
		Inventory inv = p.getInventory();
		p.setGameMode(GameMode.SURVIVAL);
		p.setAllowFlight(false);
		p.setExp(0);
		p.setSaturation(20);
		p.setExhaustion(20);
		inv.clear();
		inv.setItem(0, Items.getDeafultSword());
	}

	public static World getLobby() {
		return lobby == null ? lobby = Bukkit.getWorld("lobby") : lobby;
	}
}
