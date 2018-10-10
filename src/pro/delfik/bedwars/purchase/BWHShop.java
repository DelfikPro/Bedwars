package pro.delfik.bedwars.purchase;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;
import pro.delfik.bedwars.game.Items;
import pro.delfik.lmao.outward.item.ItemBuilder;
import pro.delfik.lmao.outward.item.PotionBuilder;

import java.util.List;

import static implario.util.Converter.asList;
import static org.bukkit.Material.ARROW;
import static org.bukkit.Material.BARRIER;
import static pro.delfik.bedwars.game.Resource.BRONZE;
import static pro.delfik.bedwars.game.Resource.GOLD;
import static pro.delfik.bedwars.game.Resource.IRON;
import static pro.delfik.bedwars.purchase.SlotDeal.generify;
import static pro.delfik.lmao.outward.item.Ench.*;
import static pro.delfik.lmao.outward.item.ItemBuilder.create;

public class BWHShop {

	public static List<Purchase.Section> setup() {
		return asList(
				new Purchase.Section(new PurchaseGUI("Блоки", 2, generify(new Deal[] {
						new Deal(new ItemStack(Material.SANDSTONE, 2, (short) 2), BRONZE, 1),
						new Deal(new ItemStack(Material.ENDER_STONE), BRONZE, 7),
						new Deal(new ItemStack(Material.IRON_BLOCK), IRON, 3),
						new Deal(new ItemStack(Material.GLOWSTONE), BRONZE, 4),
						new Deal(new ItemStack(Material.GLASS), BRONZE, 4),
						new SlotDeal(new ItemStack(Material.CHEST), IRON, 1, 6),
						new SlotDeal(create(Material.ENDER_CHEST, "§dГиперсундук", "§a§oРесурсы, положенные в него",
								"§a§oМожно забрать из любого другого гиперсундука.", "§f", "§a§oДоступ к этому сундуку есть только у вашей команды."), GOLD, 1, 7),
						new SlotDeal(new ItemStack(Material.SANDSTONE, 64, (short) 2), BRONZE, 32, 8)
				})), create(Material.QUARTZ_BLOCK, "§a§lБлоки","§c§oР§6§oа§e§oз§a§oн§b§oо§3§oц§9§oв§5§oе§d§oт§c§oн§6§oы§e§oе кубики!")),

				new Purchase.Section(new PurchaseGUI("Броня", 2, new Deal[] {

						new ArmorDeal(BRONZE, 6,
								new ItemBuilder(Material.IRON_HELMET).enchant(PROTECTION_1).withDisplayName("§aРыцарские доспехи").build(),
								null,
								new ItemBuilder(Material.IRON_LEGGINGS).enchant(PROTECTION_1).withDisplayName("§aРыцарские доспехи").build(),
								new ItemBuilder(Material.IRON_BOOTS).enchant(PROTECTION_1).withDisplayName("§aРыцарские доспехи").build()
						),

						new ArmorDeal(IRON, 1, null, new ItemBuilder(Material.IRON_CHESTPLATE)
								.enchant(PROTECTION_1).withDisplayName("§aНагрудник §d§lI").withLore("§7<§f§lОбычный§7>").build(), null, null),

						new ArmorDeal(IRON, 3, null, new ItemBuilder(Material.IRON_CHESTPLATE)
								.enchant(PROTECTION_2).withDisplayName("§aНагрудник §d§lII").withLore("§7<§e§lОсобый§7>").build(), null, null),

						new ArmorDeal(IRON, 9,
								new ItemBuilder(Material.DIAMOND_HELMET).withDisplayName("§bЭнергeтическая броня").build(),
								null,
								new ItemBuilder(Material.DIAMOND_LEGGINGS).withDisplayName("§bЭнергeтическая броня").build(),
								new ItemBuilder(Material.DIAMOND_BOOTS).withDisplayName("§bЭнергeтическая броня").build()
						),

						new ArmorDeal(IRON, 9, null, new ItemBuilder(Material.DIAMOND_CHESTPLATE)
								.withDisplayName("§bНагрудник").withLore("§7<§e§lОсобый§7>").build(), null, null),

						new ArmorDeal(IRON, 20, null, new ItemBuilder(Material.DIAMOND_CHESTPLATE)
								.enchant(PROTECTION_1).withDisplayName("§bНагрудник §d§lIII").withLore("§7<§6§lРедкий§7>").build(), null, null),

						new ArmorDeal(GOLD, 3, null, new ItemBuilder(Material.DIAMOND_CHESTPLATE)
								.enchant(PROTECTION_2).withDisplayName("§bНагрудник §d§lIV").withLore("§7<§d§lЭпический§7>").build(), null, null),

						new ArmorDeal(GOLD, 8, null, new ItemBuilder(Material.DIAMOND_CHESTPLATE)
								.enchant(PROTECTION_3).withDisplayName("§bНагрудник §d§lV").withLore("§7<§c§lЛегендарный§7>", "§b§oПротосская реликвия.").build(), null, null),
				}), create(Material.DIAMOND_CHESTPLATE, "§e§lБроня", "§e§oУкрепи свой имунитет при помощи", "§e§oДревней протосской брони", "§e§oВсего за 7.99$ золотом!")),

				new Purchase.Section(new PurchaseGUI("Оружие", 2, new Deal[] {
						new Deal(new ItemBuilder(Material.IRON_SWORD).withDisplayName("§fИголка").withLore("§7<§f§lОбычный§7>").enchant(SHARPNESS_1).build(), BRONZE, 3),
						new Deal(new ItemBuilder(Material.DIAMOND_SWORD).withDisplayName("§aАлмеч").withLore("§7<§e§lОсобый§7>").enchant(SHARPNESS_1).build(), IRON, 1),
						new Deal(new ItemBuilder(Material.DIAMOND_SWORD).withDisplayName("§bЭкскалибур").withLore("§7<§6§lРедкий§7>")
								.enchant(SHARPNESS_2, KNOCKBACK_1).build(), IRON, 5),
						new Deal(new ItemBuilder(Material.DIAMOND_SWORD).withDisplayName("§6Бесполезная хуета").withLore("§a§oДаже делфик полезнее", "§7<§d§lЭпический§7>")
								.enchant(SHARPNESS_3, KNOCKBACK_1).build(), GOLD, 6),
						new Deal(new ItemBuilder(Material.DIAMOND_SWORD).withDisplayName("§cКровавая Мэри").withLore("§7<§c§lЛегендарный§7>")
								.enchant(SHARPNESS_5, FIRE_ASPECT, KNOCKBACK_2).build(), GOLD, 30)
				}), create(Material.IRON_SWORD, "§c§lОружие", "§e§oОружие ближнего боя.", "§e§oЗатыкай врагов своим ЛКМ-ом до смерти!")),

				new Purchase.Section(new PurchaseGUI("Инструменты", 2, new Deal[] {
						new Deal(new ItemBuilder(Material.STONE_PICKAXE).withDisplayName("§fКирка шахтёра").withLore("§7<§f§lОбычная§7>").enchant(EFFIECENCY_1).build(), BRONZE, 4),
						new Deal(new ItemBuilder(Material.IRON_PICKAXE).withDisplayName("§dМолли").withLore("§7<§6§lРедкая§7>").enchant(EFFIECENCY_1).build(), IRON, 2),
						new Deal(new ItemBuilder(Material.DIAMOND_PICKAXE).withDisplayName("§bКирка-ювелирка").withLore("§7<§d§lЭпическая§7>")
								.enchant(EFFIECENCY_1, SILK_TOUCH).build(), GOLD, 1),
						new Deal(new ItemBuilder(Material.DIAMOND_PICKAXE).withDisplayName("§cКирка.").withLore("§e§oИ этим всё сказано.", "§7<§c§lЛегендарная§7>")
								.enchant(EFFIECENCY_3, FIRE_ASPECT, KNOCKBACK_1).build(), GOLD, 3),
				}), create(Material.IRON_PICKAXE, "§e§lИнструменты", "§e§oАрсенал грифера:", "§e§oЛомать, разрушать, уничтожать, разбивать!")),

				new Purchase.Section(new PurchaseGUI("Луки", 2, generify(new Deal[] {
						new Deal(new ItemBuilder(Material.BOW).withDisplayName("§aСпортивный лук").withLore("§7<§f§lОбычный§7>")
								.enchant(INFINITY).build(), IRON, 7),
						new Deal(new ItemBuilder(Material.BOW).withDisplayName("§fБелый арбалет").withLore("§7<§e§lОсобый§7>")
								.enchant(INFINITY, POWER_1).build(), GOLD, 1),
						new Deal(new ItemBuilder(Material.BOW).withDisplayName("§6Чёрный пистолет").withLore("§7<§6§lРедкий§7>")
								.enchant(INFINITY, POWER_1, ARROW_FIRE, ARROW_KNOCKBACK).build(), GOLD, 7),
						new Deal(new ItemBuilder(Material.BOW).withDisplayName("§cТетива смерти").withLore("§7<§d§lЭпический§7>")
								.enchant(INFINITY, POWER_3, ARROW_FIRE, ARROW_KNOCKBACK).build(), GOLD, 30),
						new Deal(create(ARROW, "§6Бесконечный патрон", "§d§o - 'Невероятной судьбы'"), GOLD, 1),
						new SlotDeal(new ItemBuilder(Material.BOW).withDisplayName("§c§lЛуковица").withLore("§e§oЗаставляет врагов плакать", "§7<§c§lЛегендарный§7>")
								.enchant(INFINITY, POWER_5, ARROW_KNOCKBACK, ARROW_FIRE).build(), GOLD, 100, 8)
				})), create(Material.BOW, "§c§lЛуки", "§e§oОружие для дальних дистанций.", "§6§oОсторожно:", "§e§oВас могут обвинить в лукодрочерстве!")),

				new Purchase.Section(new PurchaseGUI("Хавчик", 2, generify(new Deal[] {
						new Deal(create(Material.APPLE, "§cНаливное яблочко", "§a§oУважает чувства вегетарианцев."), BRONZE, 1),
						new Deal(create(Material.PUMPKIN_PIE, "§cПирожок с тыквой", "§a§oУважает чувства вегетарианцев."), BRONZE, 2),
						new Deal(create(Material.CAKE, "§cБисквитный торт", "§a§oУважает чувства вегетарианцев."), BRONZE, 16),
						new Deal(create(Material.GOLDEN_APPLE, "§6Драгоценный фрукт", "§cЗолото 585-ой пробы, сертифицированное §lВАК",
								"§a§oУважает чувства вегетарианцев."), GOLD, 2),
						new SlotDeal(ItemBuilder.setAmount(create(Material.PUMPKIN_PIE, "§cПирожок с тыквой",
								"§a§oУважает чувства вегитарианцев."), 10), BRONZE, 20, 8)
				})), create(Material.APPLE, "§d§lКушац!", "§e§oНям-ням!")),

				new Purchase.Section(new PurchaseGUI("ПУСТОТА", 1, new Deal[0]), create(BARRIER, "§c§lПУСТОТА", "§6§oЗдесь пусто!")),

				new Purchase.Section(new PurchaseGUI("Зелья", 2, new Deal[] {
						new Deal(new PotionBuilder(PotionType.INSTANT_HEAL).withDisplayName("§dАптечка").build(), IRON, 3),
						new Deal(new PotionBuilder(PotionType.INSTANT_HEAL).withAmplifier(1).withDisplayName("§dБольшая аптечка").build(), IRON, 5),
						new Deal(new PotionBuilder(PotionType.SPEED).withDuration(180 * 20).withDisplayName("§bСахарный настой").build(), IRON, 4),
						new Deal(new PotionBuilder(PotionType.STRENGTH).withDuration(180 * 20).withDisplayName("§cОлимпийский допинг").build(), GOLD, 4),
						new Deal(new PotionBuilder(PotionType.INVISIBILITY).withDuration(15 * 20).withDisplayName("§7Ведьмин отвар")
								.withLore("§a§oПревращает тебя в хамелеона!").build(), IRON, 50),
				}), new PotionBuilder(PotionType.STRENGTH).withDisplayName("§d§lЗелья").withLore("§e§oРаскрой свою мощь при помощи", "§e§oТаинственной магии алхимиков!").build()),

				new Purchase.Section(new PurchaseGUI("Разное", 3, generify(new Deal[] {
						new Deal(new ItemStack(Material.LADDER), BRONZE, 1),
						new Deal(new ItemStack(Material.WEB), BRONZE, 16),
						new Deal(new ItemStack(Material.FISHING_ROD), IRON, 5),
						new Deal(new ItemStack(Material.TNT), GOLD, 5),
						new Deal(new ItemStack(Material.FLINT_AND_STEEL), IRON, 7),
						new Deal(new ItemStack(Material.ENDER_PEARL), GOLD, 13),

						new SlotDeal(Items.THOR_BONE, IRON,3, 9),
						new SlotDeal(Items.RESCUE_PLATFORM, IRON,14, 10),
						new SlotDeal(Items.HOME_TELEPORTATION, IRON,3, 11),
						new SlotDeal(Items.GPS_TRACKER, IRON,10, 12),
						new SlotDeal(ItemBuilder.create(Material.STRING, "§aШёлковая нить", "§2Первый сорт."), GOLD, 2, 13)
				})), Material.TNT, "§6§lРазное", "§e§oВсё, чего желает ваша душа:\n§e§oПаутинка, спасительные платформы,\n§e§oШпионские гаджеты, и даже...\n§c§oВзрывчатка!"),

				new Purchase.Section(new PurchaseGUI("Обмен ресурсов", 2, new Deal[] {
						new Deal(ItemBuilder.setAmount(BRONZE.getItem(), 32), IRON, 1),
						new Deal(ItemBuilder.setAmount(IRON.getItem(), 1), BRONZE, 48),
						new Deal(ItemBuilder.setAmount(GOLD.getItem(), 1), IRON, 14),
						new Deal(ItemBuilder.setAmount(IRON.getItem(), 7), GOLD, 1)
				}), Material.GOLD_INGOT, "§e§lОбмен ресурсов", "§e§oЗдесь можно получить дорогие ресурсы\n§e§oЗа дешёвые, и наоборот.")
		);
	}
}
