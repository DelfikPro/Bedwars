package pro.delfik.bedwars;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Bedwars {
	public static void toLobby(Player p) {
		// ToDo: Тело метода BedWars.toLobby();
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
}
