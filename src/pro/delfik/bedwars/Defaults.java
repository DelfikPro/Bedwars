package pro.delfik.bedwars;

import lib.Generate;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class Defaults {
	public static final ItemStack SWORD = Generate.itemstack(Material.STONE_SWORD, 1, 0, "§fЧесалка для спины", "Начальный предмет");
	public static final ItemStack HELMET = Generate.itemstack(Material.LEATHER_HELMET, 1, 0, "Шапочка", "Начальный предмет");
	public static final ItemStack CHESTPLATE = Generate.itemstack(Material.LEATHER_CHESTPLATE, 1, 0, "Свитер", "Начальный предмет");
	public static final ItemStack LEGGINGS = Generate.itemstack(Material.LEATHER_LEGGINGS, 1, 0, "Джинсы", "Начальный предмет");
	public static final ItemStack BOOTS = Generate.itemstack(Material.LEATHER_BOOTS, 1, 0, "Кеды", "Начальный предмет");
	
	public static final String MAP_PREFIX = "_maps/";
	
	public static final int DELAY_BRONZE = 100;
	public static final int DELAY_IRON = 100;
	public static final int DELAY_GOLD = 100;
	
	public enum Teams {
		RED("Красная", "Красную", ChatColor.RED, Color.RED, (byte) 14),
		BLUE("Синяя", "Синюю", ChatColor.AQUA, Color.AQUA, (byte) 3),
		ORANGE("Оранжевая", "Оранжевую", ChatColor.GOLD, Color.ORANGE, (byte) 1),
		PINK("Розовая", "Розовую", ChatColor.LIGHT_PURPLE, Color.FUCHSIA, (byte) 6),
		GREEN("Зелёная", "Зелёную", ChatColor.GREEN, Color.LIME, (byte) 5);
		
		private final String name;
		private final String accusative;
		private final ChatColor color;
		private final Color armor;
		private final byte wool;
		public final ItemStack HELMET, CHESTPLATE, LEGGINGS, BOOTS;
		
		Teams(String name, String accusative, ChatColor color, Color armor, byte wool) {
			this.name = name;
			this.accusative = accusative;
			this.color = color;
			this.armor = armor;
			this.wool = wool;
			this.HELMET = Defaults.HELMET.clone();
			this.CHESTPLATE = Defaults.CHESTPLATE.clone();
			this.LEGGINGS = Defaults.LEGGINGS.clone();
			this.BOOTS = Defaults.BOOTS.clone();
			LeatherArmorMeta bootsM = (LeatherArmorMeta) BOOTS.getItemMeta(); bootsM.setColor(armor); BOOTS.setItemMeta(bootsM);
			LeatherArmorMeta leggsM = (LeatherArmorMeta) LEGGINGS.getItemMeta(); leggsM.setColor(armor); LEGGINGS.setItemMeta(leggsM);
			LeatherArmorMeta chestM = (LeatherArmorMeta) CHESTPLATE.getItemMeta(); chestM.setColor(armor); CHESTPLATE.setItemMeta(chestM);
			LeatherArmorMeta helmM = (LeatherArmorMeta) HELMET.getItemMeta(); helmM.setColor(armor); HELMET.setItemMeta(helmM);
		}
		
		public String getName() {
			return name;
		}
		
		public ChatColor getColor() {
			return color;
		}
		
		public String getAccusative() {
			return accusative;
		}
		
		public byte getWool() {
			return wool;
		}
		
		public Color getArmor() {
			return armor;
		}
	}
}
