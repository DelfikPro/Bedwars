package pro.delfik.bedwars.game.trading;

import lib.Generate;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Resource {
	BRONZE(Generate.itemstack(Material.CLAY_BRICK, 1, 0, "§6Бронза"), ChatColor.GOLD, "бронзы", "бронза"),
	IRON(Generate.itemstack(Material.IRON_INGOT, 1, 0, "§fЖелезо"), ChatColor.WHITE, "железа", "железо"),
	GOLD(Generate.itemstack(Material.GOLD_INGOT, 1, 0, "§eЗолото"), ChatColor.YELLOW, "золота", "золото");
	
	private final ItemStack item;
	private final ChatColor color;
	private final String name;
	private final String plural;
	
	Resource(ItemStack item, ChatColor color, String resursa, String resurs) {
		this.item = item;
		this.color = color;
		this.name = resurs;
		this.plural = resursa;
	}
	
	public ChatColor getColor() {
		return color;
	}
	
	public ItemStack getItem() {
		return item;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPlural() {
		return plural;
	}
}
