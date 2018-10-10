package pro.delfik.bedwars.game;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pro.delfik.lmao.outward.item.I;

public enum Color {
	RED("§c", "Красные", 14, org.bukkit.Color.RED, "Красную"),
	BLUE("§9", "Синие", 11, org.bukkit.Color.BLUE, "Синюю");
	
	// Костыль для получения цвета команды по цвету шерсти
	private static final Color[] byWool = new Color[16];
	static {for (Color c : values()) byWool[c.ordinal()] = c;}
	
	private final String prefix, title, bed;
	private final byte woolColor;
	private final org.bukkit.Color armorColor;
	private final ItemStack[] armor;
	
	Color(String prefix, String name, int woolColor, org.bukkit.Color armorColor, String bed) {
		this.prefix = prefix;
		this.title = name;
		this.woolColor = (byte) woolColor;
		this.armorColor = armorColor;
		this.bed = bed;
		armor = new ItemStack[] {
				I.leatherArmor(Material.LEATHER_BOOTS		, armorColor, "§eСапоги-медленноходы", "Начальный предмет"),
				I.leatherArmor(Material.LEATHER_LEGGINGS	, armorColor, "§eНеберёзовые нештаны", "Начальный предмет"),
				I.leatherArmor(Material.LEATHER_CHESTPLATE	, armorColor, "§eХолодный свитер"	 , "Начальный предмет"),
				I.leatherArmor(Material.LEATHER_HELMET		, armorColor, "§eШапка-видимка"		 , "Начальный предмет"),
		};
	}
	
	/**
	 * @return Цвет брони для {@code LeatherArmorMeta#setColor()}
	 */
	public org.bukkit.Color getArmorColor() {
		return armorColor;
	}

	/**
	 * @return Число, которое нужно вписать в data шерсти, чтобы окрасить её в этот цвет.
	 */
	public byte getWoolColor() {
		return woolColor;
	}
	
	/**
	 * @return Цветовая нотация (§9, §c)
	 */
	public String getPrefix() {
		return prefix;
	}
	
	/**
	 * @return Цветовая нотация + русское название цвета во множественном числе
	 * Например: "§cКрасные", "§9Синие"
	 */
	@Override
	public String toString() {
		return prefix + title;
	}
	
	public static Color get(int woolColor) {
		if (woolColor > 15 || woolColor < 0) return null;
		return byWool[woolColor];
	}
	
	public static Color get(String colorName) {
		try {
			return valueOf(colorName.toUpperCase());
		} catch (IllegalArgumentException | NullPointerException ex) {
			return null;
		}
	}

	public String getBedBroken() {
		return prefix + bed + " кровать";
	}

	public ItemStack[] getArmor() {
		return armor;
	}
}
