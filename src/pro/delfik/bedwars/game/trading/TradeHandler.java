package pro.delfik.bedwars.game.trading;

import lib.Converter;
import lib.Generate;
import lib.I;
import lib.gui.GUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;
import pro.delfik.bedwars.game.trading.deal.ArmorDeal;
import pro.delfik.bedwars.game.trading.deal.TradeDeal;
import pro.delfik.bedwars.game.trading.deal.WeaponDeal;
import pro.delfik.bedwars.game.usables.SavingPlatform;
import pro.delfik.bedwars.game.usables.ThorBone;

import java.util.ArrayList;
import java.util.List;

public class TradeHandler implements Listener {
	public static final GUI INVENTORY = new GUI(Bukkit.createInventory(null, 27, "Магазин"));
	
	public static void initTrades() {
		TradeSection.list = new ArrayList<>();
		
		
		// Блоки -----------------------------------------------------------------------------------------
		new TradeSection("Блоки", Generate.itemstack(Material.SANDSTONE, 1, 2, "§aБлоки", "§e§oКубики для строительства,", "§e§oобороны кровати или", "§e§oпросто для веселья."), Converter.toList(
				new TradeDeal(new ItemStack(Material.SANDSTONE, 2, (short) 2), Resource.BRONZE, 1,
									 Generate.itemstack(Material.SANDSTONE, 2, 2, "§aГладкий песчаник")),
				new TradeDeal(new ItemStack(Material.ENDER_STONE), Resource.BRONZE, 7,
									 Generate.itemstack(Material.ENDER_STONE, 1, 0, "§aЭндерняк")),
				new TradeDeal(new ItemStack(Material.IRON_BLOCK), Resource.IRON, 3,
									 Generate.itemstack(Material.IRON_BLOCK, 1, 0, "§aЖелезный блок")),
				new TradeDeal(new ItemStack(Material.GLOWSTONE), Resource.BRONZE, 4,
									 Generate.itemstack(Material.GLOWSTONE, 1, 0, "§aСветокамень")),
				new TradeDeal(new ItemStack(Material.GLASS), Resource.BRONZE, 2,
									 Generate.itemstack(Material.GLASS, 1, 0, "§aСтекло"))
		));
		
		// Броня -----------------------------------------------------------------------------------------
		
		ItemStack ironBoots = Generate.enchant(new ItemStack(Material.IRON_BOOTS), Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		ItemStack ironLeggings = Generate.enchant(new ItemStack(Material.IRON_LEGGINGS), Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		ItemStack ironHelmet = Generate.enchant(new ItemStack(Material.IRON_HELMET), Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		
		ItemStack iron1 = Generate.enchant(new ItemStack(Material.IRON_CHESTPLATE), Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		ItemStack iron2 = Generate.enchant(new ItemStack(Material.IRON_CHESTPLATE), Enchantment.PROTECTION_ENVIRONMENTAL, 2);
		
		ItemStack ironSetDisplay = Generate.enchant(Generate.itemstack(Material.IRON_LEGGINGS, 1, 0, "§fЖелезный сет", "§7§o - Железный шлем", "§7§o - Железные поножи", "§7§o - Железные ботинки"), Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		ItemStack iron1Display = Generate.enchant(Generate.itemstack(Material.IRON_CHESTPLATE, 1, 0, "§aЖелезный нагрудник (§elvl 1§a)"), Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		ItemStack iron2Display = Generate.enchant(Generate.itemstack(Material.IRON_CHESTPLATE, 1, 0, "§aЖелезный нагрудник (§elvl 2§a)"), Enchantment.PROTECTION_ENVIRONMENTAL, 2);
		
		ItemStack diamondBoots = new ItemStack(Material.DIAMOND_BOOTS);
		ItemStack diamondLeggings = new ItemStack(Material.DIAMOND_LEGGINGS);
		ItemStack diamondHelmet = new ItemStack(Material.DIAMOND_HELMET);
		
		
		ItemStack diamond0 = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemStack diamond1 = Generate.enchant(new ItemStack(Material.DIAMOND_CHESTPLATE), Enchantment
																								  .PROTECTION_ENVIRONMENTAL, 1);
		ItemStack diamond2 = Generate.enchant(new ItemStack(Material.DIAMOND_CHESTPLATE), Enchantment
																								  .PROTECTION_ENVIRONMENTAL, 2);
		ItemStack diamond3 = Generate.enchant(new ItemStack(Material.DIAMOND_CHESTPLATE), Enchantment
																								  .PROTECTION_ENVIRONMENTAL, 3);
		
		ItemStack diamondSetDisplay = Generate.itemstack(Material.DIAMOND_LEGGINGS, 1, 0,
				"§fАлмазный сет", "§7§o - Алмазный шлем", "§7§o - Алмазные поножи", "§7§o - Алмазные ботинки");
		ItemStack diamond0Display = Generate.itemstack(Material.DIAMOND_CHESTPLATE, 1, 0,
				"§aАлмазный нагрудник (§elvl 0§a)");
		ItemStack diamond1Display = Generate.enchant(Generate.itemstack(Material.DIAMOND_CHESTPLATE, 1, 0,
				"§bАлмазный нагрудник (§elvl 1§b)"), Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		ItemStack diamond2Display = Generate.enchant(Generate.itemstack(Material.DIAMOND_CHESTPLATE, 1, 0,
				"§cАлмазный нагрудник (§elvl 2§c)"), Enchantment.PROTECTION_ENVIRONMENTAL, 2);
		ItemStack diamond3Display = Generate.enchant(Generate.itemstack(Material.DIAMOND_CHESTPLATE, 1, 0,
				"§cАлмазный нагрудник (§elvl 3§c)"), Enchantment.PROTECTION_ENVIRONMENTAL, 3);
		
		
		new TradeSection("Броня", Generate.itemstack(Material.IRON_CHESTPLATE, 1, 0, "§aБроня", "§e§o - \"Врум-врум, танк-танк!\""), Converter.toList(
				new ArmorDeal(ironHelmet, null, ironLeggings, ironBoots, Resource.BRONZE, 6, ironSetDisplay),
				new ArmorDeal(null, iron1, null, null, Resource.IRON, 1, iron1Display),
				new ArmorDeal(null, iron2, null, null, Resource.IRON, 3, iron2Display),
				
				new ArmorDeal(diamondHelmet, null, diamondLeggings, diamondBoots, Resource.IRON, 9, diamondSetDisplay),
				new ArmorDeal(null, diamond0, null, null, Resource.IRON, 9, diamond0Display),
				new ArmorDeal(null, diamond1, null, null, Resource.IRON, 20, diamond1Display),
				new ArmorDeal(null, diamond2, null, null, Resource.GOLD, 3, diamond2Display),
				new ArmorDeal(null, diamond3, null, null, Resource.GOLD, 8, diamond3Display)
		));
		
		// Кирки -----------------------------------------------------------------------------------------
		
		ItemStack pick1 = Generate.enchant(Generate.itemstack(Material.STONE_PICKAXE, 1, 0, "§aКирка шахтёра"), "32:1", "34:1");
		ItemStack pick2 = Generate.enchant(Generate.itemstack(Material.IRON_PICKAXE, 1, 0, "§bКирка из закалённого железа"), "32:2", "34:1");
		ItemStack pick3 = Generate.enchant(Generate.itemstack(Material.DIAMOND_PICKAXE, 1, 0, "§dКирка-Ювелирка"), "32:2", "34:2", "33:1");
		ItemStack pick4 = Generate.enchant(Generate.itemstack(Material.DIAMOND_PICKAXE, 1, 0, "§cЛегендарный " +
																									  "отбойный молоток", "§e§o - Найден в кошельке грейза"), "32:4", "34:3", "33:1", "35:1");
		
		ItemStack sock = Generate.enchant(Generate.itemstack(Material.LEATHER_LEGGINGS, 1, 0, "§eТрусы флайтопа", "§a§o - Примани фанатку!"), "35:10");
		
		new TradeSection("Инструменты", Generate.itemstack(Material.GOLD_PICKAXE, 1, 0, "§aКирки", "§e§o - Игрушки для гриферов"), Converter.toList(
				new TradeDeal(pick1, Resource.BRONZE, 4),
				new TradeDeal(pick2, Resource.IRON, 2),
				new TradeDeal(pick3, Resource.GOLD, 1),
				new TradeDeal(pick4, Resource.GOLD, 4),
				
				new TradeDeal(sock, Resource.GOLD, 64, sock)
		));
		
		// Оружие ----------------------------------------------------------------------------------------
		
		ItemStack weapon1 = Generate.enchant(Generate.itemstack(Material.IRON_SWORD, 1, 0, "§fИголка"), "16:1");
		ItemStack weapon2 = Generate.enchant(Generate.itemstack(Material.DIAMOND_SWORD, 1, 0, "§aАлмеч"), "16:1",
				"34:1");
		ItemStack weapon3 = Generate.enchant(Generate.itemstack(Material.DIAMOND_SWORD, 1, 0, "§bЭкскалибур"),
				"16:2", "34:2", "19:1");
		ItemStack weapon4 = Generate.enchant(Generate.itemstack(Material.DIAMOND_SWORD, 1, 0, "§6Смертоносец"),
				"16:3", "34:2", "19:1");
		ItemStack weapon5 = Generate.enchant(Generate.itemstack(Material.DIAMOND_SWORD, 1, 0, "§cКиллмагедон",
				"§d§o - Легендарное оружие"), "16:5", "34:2", "19:2", "20:1");
		
		new TradeSection("Оружие", Generate.itemstack(Material.IRON_SWORD, 1, 0, "§aОружие", "§e§oРазного вида тыкалки,", "§e§oАммуниция для нагибатора"), Converter.toList(
				//new TradeDeal(wres1, Resource.BRONZE, 8, wdis1),
				new WeaponDeal(weapon1, Resource.BRONZE, 3),
				new WeaponDeal(weapon2, Resource.IRON, 1),
				new WeaponDeal(weapon3, Resource.IRON, 5),
				new WeaponDeal(weapon4, Resource.GOLD, 6),
				new WeaponDeal(weapon5, Resource.GOLD, 30)
		));
		
		
		// Луки -----------------------------------------------------------------------------------------
		
		ItemStack bow1 = Generate.enchant(Generate.itemstack(Material.BOW, 1, 0, "§eРепчатый лук"), "51:1");
		ItemStack bow2 = Generate.enchant(Generate.itemstack(Material.BOW, 1, 0, "§fБелый арбалет"), "48:1",
				"51:1");
		ItemStack bow3 = Generate.enchant(Generate.itemstack(Material.BOW, 1, 0, "§cЧёрный пистолет"), "48:2",
				"50:1", "51:1");
		ItemStack bow4 = Generate.enchant(Generate.itemstack(Material.BOW, 1, 0, "§cЛукер"), "48:3", "49:2",
				"50:1", "51:1");
		
		ItemStack ammo = Generate.itemstack(Material.ARROW, 1, 0, "§aКорейский патрон", "§e§oОбеспечивает " +
																								"бесконечные боеприпасы");
		
		new TradeSection("Луки", Generate.itemstack(Material.BOW, 1, 0, "§aЛуки", "§e§oСнайпер ты или косой,", "§e§oЛук тебе всё равно пригодится."), Converter.toList(
				//new TradeDeal(wres1, Resource.BRONZE, 8, wdis1),
				new TradeDeal(bow1, Resource.IRON, 7),
				new TradeDeal(bow2, Resource.GOLD, 1),
				new TradeDeal(bow3, Resource.GOLD, 5),
				new TradeDeal(bow4, Resource.GOLD, 30),
				
				new TradeDeal(ammo, Resource.GOLD, 1)
		));
		
		// Кушац ----------------------------------------------------------------------------------------
		
		ItemStack apple = Generate.itemstack(Material.APPLE, 1, 0, "§aСпелое яблочко", "§e§o- Сочетание витаминов" +
																							   " и вкуса.");
		ItemStack pork = Generate.itemstack(Material.COOKED_BEEF, 1, 0, "§aЖареный стейк", "§e§o- Сочнейший кусок" +
																								   " мяса прямо с фермы.");
		ItemStack cake = Generate.itemstack(Material.CAKE, 1, 0, "§aПраздничный тортик", "§e§o- Мечта сладкоежки" +
																								 ".", "§e§o- Отпразднуй победу над своими врагами!");
		ItemStack gapple = Generate.itemstack(Material.GOLDEN_APPLE, 1, 0, "§aДрагоценное блюдо", "§e§o- Силы чар, наложенных на него древними друидами",
				"§e§o §e§o способны исцелять раны");
		
		new TradeSection("Еда", Generate.itemstack(Material.GRILLED_PORK, 1, 0, "§aЕда", "§e§oСамые изысканные блюда от лучших поваров мира!", "§e§oВойна войной, а перекусить - дело святое."), Converter.toList(
				new TradeDeal(apple, Resource.BRONZE, 1),
				new TradeDeal(pork, Resource.BRONZE, 2),
				new TradeDeal(cake, Resource.IRON, 1),
				new TradeDeal(gapple, Resource.GOLD, 2)
		));
		
		// Сундуки --------------------------------------------------------------------------------------
		
		ItemStack chest = Generate.itemstack(Material.CHEST, 1, 0, "§eЧемодан");
		ItemStack enderchest = Generate.itemstack(Material.ENDER_CHEST, 1, 0, "§dКомандный ларчик",
				"§e§oПоложенные в него вещи можно", "§e§oзабрать из любого другого ларчика");
		
		new TradeSection("Сундуки", Generate.itemstack(Material.CHEST, 1, 0, "§aСундуки", "§e§oСпециально для хранения вкусняшек"), Converter.toList(
				new TradeDeal(chest, Resource.IRON, 1),
				new TradeDeal(enderchest, Resource.GOLD, 1)
		));
		
		// Разное ----------------------------------------------------------------------------------------
		
		new TradeSection("Специальное", Generate.itemstack(Material.TNT, 1, 0, "§bСпециальное", "§e§oВсё самое " +
																										"интересненькое"), 8, Converter.toList(
				new TradeDeal(new ItemStack(Material.LADDER), Resource.BRONZE, 1),
				new TradeDeal(new ItemStack(Material.WEB), Resource.BRONZE, 16, Generate.itemstack(Material.WEB, 1,
						0, "§aПаутинка", "§e§o- \"Чо по урону от падения, мудила?\"")),
				new TradeDeal(new ItemStack(Material.FISHING_ROD), Resource.IRON, 5, Generate.itemstack(Material.FISHING_ROD, 1, 0, "§aСнасть для ловли изичей")),
				new TradeDeal(new ItemStack(Material.FLINT_AND_STEEL), Resource.IRON, 7, Generate.itemstack(Material.FLINT_AND_STEEL, 1, 0, "§aЗажигалка")),
				new TradeDeal(new ItemStack(Material.TNT), Resource.GOLD, 3),
				new TradeDeal(new ItemStack(Material.ENDER_PEARL), Resource.GOLD, 13),
				new TradeDeal(ThorBone.ITEM, Resource.IRON, 3, 9),
				new TradeDeal(SavingPlatform.ITEM, Resource.IRON, 14, 12)
		));
		
		// Зельки ---------------------------------------------------------------------------------------
		
		ItemStack heal1 = new Potion(PotionType.INSTANT_HEAL, 1).toItemStack(1);
		ItemMeta heal1M = heal1.getItemMeta();
		heal1M.setDisplayName("§eХилка lvl 1");
		heal1.setItemMeta(heal1M);
		
		ItemStack heal2 = new Potion(PotionType.INSTANT_HEAL, 2).toItemStack(1);
		ItemMeta heal2M = heal2.getItemMeta();
		heal2M.setDisplayName("§eХилка lvl 2");
		heal2.setItemMeta(heal2M);
		
		ItemStack speed = new Potion(PotionType.SPEED, 1).toItemStack(1);
		ItemMeta speedM = speed.getItemMeta();
		speedM.setDisplayName("§bСкорость");
		speed.setItemMeta(speedM);
		
		ItemStack strength = new Potion(PotionType.STRENGTH, 1).toItemStack(1);
		ItemMeta strengthM = strength.getItemMeta();
		strengthM.setDisplayName("§cСила");
		strength.setItemMeta(strengthM);
		
		ItemStack invis = new Potion(PotionType.INVISIBILITY, 1).toItemStack(1);
		ItemMeta invisM = invis.getItemMeta();
		invisM.setDisplayName("§bНевидимость");
		invis.setItemMeta(invisM);
		
		new TradeSection("Зелья", Generate.itemstack(Material.POTION, 1, 8193, "§aЗелья", "§e§oЧудеса алхимии"), Converter.toList(
				new TradeDeal(heal1, Resource.IRON, 1),
				new TradeDeal(heal2, Resource.IRON, 3),
				new TradeDeal(speed, Resource.IRON, 4),
				new TradeDeal(strength, Resource.GOLD, 4),
				new TradeDeal(invis, Resource.IRON, 50)
		));
		
		// Обмен ресурсов --------------------------------------------------------------------------------
		
		ItemStack b2i = Generate.itemstack(Material.IRON_INGOT, 1, 0, "§f1 железо §fза§6 48 бронзы");
		ItemStack i2g = Generate.itemstack(Material.GOLD_INGOT, 1, 0, "§e1 золото §fза§f 14 железа");
		ItemStack i2b = Generate.itemstack(Material.CLAY_BRICK, 1, 0, "§632 бронзы §fза §f1 железо");
		ItemStack g2i = Generate.itemstack(Material.IRON_INGOT, 1, 0, "§f7 железа§f за §e1 золото");
		
		new TradeSection("Обмен ресурсов", Generate.itemstack(Material.GOLD_INGOT, 1, 0, "§aОбмен ресурсов"), 13, Converter.toList(
				new TradeDeal(Generate.itemstack(Material.IRON_INGOT, 1, 0, "§fЖелезо"), Resource.BRONZE, 48, b2i),
				new TradeDeal(Generate.itemstack(Material.GOLD_INGOT, 1, 0, "§eЗолото"), Resource.IRON, 14, i2g),
				new TradeDeal(Generate.itemstack(Material.CLAY_BRICK, 32, 0, "§6Бронза"), Resource.IRON, 1, i2b),
				new TradeDeal(Generate.itemstack(Material.IRON_INGOT, 7, 0, "§fЖелезо"), Resource.GOLD, 1, g2i)
		));
		INVENTORY.getInventory().setItem(26, Generate.itemstack(Material.INK_SACK, 1, 1, "§cСтарая система закупа", "§7§oЭта кнопка доступна со статуса §cОлдфаг"));
	}
	
	
	public static final List<String> SECTIONS = Converter.toList(INVENTORY.getInventory().getTitle());
	
	// Открытие магазина щелчком на рамку портала.
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if (e.getClickedBlock().getType() != Material.ENDER_PORTAL_FRAME) return;
		
		e.setCancelled(true);
		e.getPlayer().openInventory(INVENTORY.getInventory());
	}
	
	
	// Нажатие на секцию в инвенте
	@EventHandler
	public void changeSection(InventoryClickEvent e) {
		if (e.getSlot() < 0) return;
		if (!SECTIONS.contains(e.getInventory().getTitle())) return;
		Player p = (Player) e.getWhoClicked();
		if (Generate.equalsItem(e.getCurrentItem(), TradeSection.BACK_TO_MAIN)) {
			e.setCancelled(true);
			p.openInventory(INVENTORY.getInventory());
			return;
		}
		if (!e.getInventory().getTitle().equals("Магазин")) return;
		e.setCancelled(true);
		TradeSection section = TradeSection.get(e.getCurrentItem());
		if (section == null) return;
		p.openInventory(section.getInv());
	}
	
	// Покупка предмета
	
	public static void buy(Player p, TradeDeal deal) {
//		int amplifier = 1; if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) amplifier = 10; // 10 раз при нажатии шифта
		if (!p.getInventory().containsAtLeast(deal.getResource().getItem(), deal.getCost())) {
			return;
		}
search:
		for (int paid = 0; paid < deal.getCost(); ) {
			for (ItemStack is : p.getInventory().getContents()) {
				if (is == null) continue;
				if (I.compare(is, deal.getResource().getItem(), false)) {
					if (is.getAmount() == 1)
						p.getInventory().clear(p.getInventory().first(deal.getResource().getItem()));
					else is.setAmount(is.getAmount() - 1);
					paid++;
					continue search;
				}
			}
		}
		deal.equip(p);
//			if (!deal.isStackable()) {
//				return;
//			}
	}

//
//	// Покупка предмета
//	@EventHandler
//	public void buyItem(InventoryClickEvent e) {
//		if (e.getSlot() < 0) return;
//		if (!SECTIONS.contains(e.getInventory().getTitle())) return;
//		if (e.getInventory().getTitle().equals("Магазин")) {
//			e.setCancelled(true); return;
//		}
//		e.setCancelled(true);
//		if (e.getCurrentItem() == null) return;
//		Player p = (Player) e.getWhoClicked();
//		TradeSection section = TradeSection.get(e.getInventory().getName());
//		if (section == null) return;
//		TradeDeal td = section.getTrade(e.getCurrentItem());
//		int amplifier = 1;
//		if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) amplifier = 10;
//		if (td == null) return;
//		if (!p.getInventory().containsAtLeast(td.getResource().getItem(), td.getCost() * amplifier)) return;
//		i: for (int i = 0; i < td.getCost() * amplifier;) {
//			for (ItemStack is : p.getInventory().getContents()) {
//				if (is == null) continue;
//				if (Generate.equalsItem(is, td.getResource().getItem())) {
//					i++;
//					if (is.getAmount() == 1) p.getInventory().clear(p.getInventory().first(td.getResource().getItem())); else is.setAmount(is.getAmount() - 1);
//					continue i;
//				}
//			}
//		}
//		for (int i = 0; i < amplifier; i++) {
//			if (p.getInventory().firstEmpty() < 0 && !p.getInventory().contains(td.getResult())) p.getWorld().dropItem(p.getLocation(), td.getResult());
//			else p.getInventory().addItem(td.getResult());
//		}
//	}
}
