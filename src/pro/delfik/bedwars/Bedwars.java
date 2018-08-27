package pro.delfik.bedwars;

import lib.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Bedwars {
	private static World lobby;
	private static final ItemStack JOIN_GAME = ItemBuilder.create(Material.EMERALD, "§f>> §a§lИграть §f<<");
	private static final ItemStack BAK_TO_LOBBY = ItemBuilder.create(Material.COMPASS, "§f>> §c§lВ лобби §f<<");


	public static void toLobby(Player p) {
		p.teleport(getLobby().getSpawnLocation());
		Inventory inv = p.getInventory();
		inv.setItem(0, JOIN_GAME);
		inv.setItem(8, BAK_TO_LOBBY);
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

	public static World getLobby() {
		return lobby == null ? lobby = Bukkit.getWorld("lobby") : lobby;
	}
}
