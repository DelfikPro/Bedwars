package pro.delfik.bedwars;

import lib.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class Items {
	
	private Items() {}
	
	public static final ItemStack JOIN_GAME = new ItemBuilder(Material.PAPER).withDisplayName("§f>> §a§lНачать игру §f<<")
		.withLore("§e- TESTING ITEM -").build();
	public static final ItemStack NULL = new ItemStack(Material.AIR);

}
