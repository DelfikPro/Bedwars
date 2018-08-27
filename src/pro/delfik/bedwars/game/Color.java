package pro.delfik.bedwars.game;

public enum Color {
	RED("§c", "Красные", 14, org.bukkit.Color.RED),
	BLUE("§9", "Синие", 11, org.bukkit.Color.BLUE);
	
	// Костыль для получения цвета команды по цвету шерсти
	private static final Color[] byWool = new Color[16];
	static {for (Color c : values()) byWool[c.ordinal()] = c;}
	
	private final String prefix, title;
	private final byte woolColor;
	private final org.bukkit.Color armorColor;
	
	Color(String prefix, String name, int woolColor, org.bukkit.Color armorColor) {
		this.prefix = prefix;
		this.title = name;
		this.woolColor = (byte) woolColor;
		this.armorColor = armorColor;
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
}
