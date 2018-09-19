package pro.delfik.bedwars.game;

import implario.util.Converter;
import pro.delfik.lmao.outward.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Тип ресурса для покупки вещей в магазине.
 * @version 2.0
 */
public enum Resource {
	BRONZE(Material.CLAY_BRICK, "§6", "Бронза", "бронзы", "бронзы", 20),
	GOLD(Material.GOLD_INGOT, "§e", "Золото", "золота", "золота" , 300),
	IRON(Material.IRON_INGOT, "§f", "Железо", "железа", "железа", 100);
	
	private final ItemStack item; // Предмет в инвентаре
	private final String title, one, couple, lot, color;	// Название ресурса
	private final int spawnTicks; // Спаунрейт этого ресурса в спанерах предметов в тиках (Как часто выпадает ресурс)
	
	/**
	 * Создание нового ресурса.
	 * @param m Материал (тип предмета), который будет иконкой этого ресурса.
	 * @param title Надпись на ресурсе, включая цветовую нотацию.
	 * @param spawnTicks Частота появления ресурса в спавнере в тиках.
	 */
	Resource(Material m, String color, String title, String couple, String lot, int spawnTicks) {
		this.item = new ItemBuilder(m).withDisplayName(title).withLore("§7Можно обменять в магазине", "§7На что-нибудь ценное").build();
		this.title = color + title;
		this.couple = couple;
		this.lot = lot;
		this.color = color;
		one = title.toLowerCase();
		this.spawnTicks = spawnTicks;
	}
	
	/**
	 * Получение ресурса по его названию.
	 * @param resourceName Название ресурса.
	 * @return Ресурс с заданным названием, либо null, если такого ресурса не существует.
	 */
	public static Resource get(String resourceName) {
		try {
			return valueOf(resourceName.toUpperCase());
		} catch (IllegalArgumentException | NullPointerException ex) {
			return null;
		}
	}
	
	/**
	 * @return Частота появления ресурса в спавнере в тиках.
	 */
	public int getSpawnTicks() {
		return spawnTicks;
	}
	
	/**
	 * @return Предмет, являющийся иконкой ресурса.
	 */
	public ItemStack getItem() {
		return item;
	}
	
	/**
	 * @return Название ресурса.
	 */
	public String getTitle() {
		return title;
	}

	public String represent(int cost) {
		return Converter.plural(cost, )
	}
}
