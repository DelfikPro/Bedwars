package pro.delfik.bedwars.game;

import lib.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.EnumMap;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Тип ресурса для покупки вещей в магазине.
 * @version 2.0
 */
public enum Resource {
	BRONZE(Material.CLAY_BRICK, "§6Бронза", 15),
	GOLD(Material.GOLD_INGOT, "§eЗолото", 300),
	IRON(Material.IRON_INGOT, "§fЖелезо", 100);
	
	private final ItemStack item;    // Предмет в инвентаре
	private final String title;        // Название ресурса
	private final int spawnTicks;    // Спаунрейт этого ресурса в спанерах предметов в тиках (Как часто выпадает ресурс)
	
	/**
	 * Создание нового ресурса.
	 * @param m Материал (тип предмета), который будет иконкой этого ресурса.
	 * @param title Надпись на ресурсе, включая цветовую нотацию.
	 * @param spawnTicks Частота появления ресурса в спавнере в тиках.
	 */
	Resource(Material m, String title, int spawnTicks) {
		this.item = new ItemBuilder(m).withDisplayName(title).withLore("§7Можно обменять в магазине", "§7На что-нибудь ценное").build();
		this.title = title;
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
	
}
