package pro.delfik.bedwars.purchase;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pro.delfik.bedwars.Bedwars;
import pro.delfik.bedwars.game.Resource;
import pro.delfik.bedwars.purchase.favourites.Favorites;
import pro.delfik.bedwars.purchase.favourites.GamerInfo;
import pro.delfik.lmao.outward.inventory.GUI;
import pro.delfik.lmao.outward.item.ItemBuilder;

import java.util.List;

import static java.lang.Integer.min;
import static pro.delfik.lmao.outward.item.ItemBuilder.create;

public class Purchase implements Listener {

	public static final ItemStack BACK_TO_MAIN = create(Material.BED, "§e>> §c§lНазад§e <<");
	private static final GUI gui = new GUI(Bukkit.createInventory(null, 27, "§0§5§lМагазин"), false) {
		@Override
		public void click(InventoryClickEvent event) {
			shopClick(event);
		}
	};

	private static final List<Section> sections = BWHShop.setup();

	public static final ItemStack EMPTY = ItemBuilder.create(Material.WOOL, "§f>> §e§lИзбранное§f <<", "§aНажмите, чтобы установить", "§aПредмет в этот слот", "§aБыстрого доступа.");
	static {
		for (int i = 18; i < 27; i++) gui.inv().setItem(i, EMPTY);
		Favorites.fillSelectionGUI();
	}

	public static Inventory getInventory() {
		return gui.inv();
	}

	private static void shopClick(InventoryClickEvent event) {
		Player p = ((Player) event.getWhoClicked());
		GamerInfo i = GamerInfo.ALL.get(p);
		if (event.getHotbarButton() == -1) {
			int slot = event.getSlot();
			if (event.isRightClick() && slot > 17 && slot < 27) {
				Favorites.openSelection(p, slot - 18);
				return;
			}
			if (slot < 9) {
				event.getWhoClicked().openInventory(sections.get(event.getSlot()).gui.inv());
				return;
			} else if (slot == 13) {
				event.getWhoClicked().openInventory(sections.get(sections.size() - 1).gui.inv());
				return;
			}
			if (slot >= 18 && slot < 27) {
				Deal d = i.getfDeals()[slot - 18];
				if (d != null) {
					purchase(p, d, event.getClick());
					if (d.getProduct() != null && d.getProduct().getMaxStackSize() != 1 && event.isShiftClick())
						for (int j=0;j<9;j++) purchase(event.getWhoClicked(), d, event.getClick());
				}
				return;
			}
			return;
		}
		Deal d = i.getfDeals()[event.getHotbarButton()];
		if (d == null) return;
		purchase(p, d, ClickType.LEFT);
	}

	public static void give(HumanEntity p, ItemStack item) {
		Bedwars.give((Player) p, item);
	}

	public static void open(Player p) {
		GamerInfo info = GamerInfo.ALL.get(p);
		Inventory inv = GUI.create(27, "§0§5§lМагазин");
		for (Section s : sections)
			if (s.getIcon().getItemMeta().getDisplayName().contains("Обмен")) inv.setItem(13, s.getIcon());
			else inv.addItem(s.getIcon());
		Deal[] deals = info.getfDeals();
		for (int i = 0; i < deals.length; i++) {
			Deal d = deals[i];
			inv.setItem(i + 18, d == null ? EMPTY : d.getDisplay());
		}
		p.openInventory(new GUI(inv, true) {
			@Override
			public void click(InventoryClickEvent event) {
				shopClick(event);
			}
		}.listenAll().inv());
	}

	@EventHandler
	public void click(InventoryClickEvent event) {
		//if (event.getClick() == ClickType.DOUBLE_CLICK) return;
		if (event.getHotbarButton() != -1) {
			if (event.getInventory().getTitle().startsWith("§f")) event.setCancelled(true);
			click(new InventoryClickEvent(event.getView(), event.getSlotType(), event.getHotbarButton(), event.getClick(), event.getAction()));
			return;
		}
		Inventory inv = event.getClickedInventory();
		if (inv == null || inv.getTitle() == null || !inv.getTitle().startsWith("§f")) return;
		PurchaseGUI gui = PurchaseGUI.get(inv.getTitle());
		if (gui == null) return;
		event.setCancelled(true);
		Deal[] deals = gui.getDeals();
		int slot = event.getSlot();
		ItemStack item = event.getCurrentItem();
		if (item.getType() == Material.BED) {
			open(((Player) event.getWhoClicked()));
			return;
		}
		if (slot >= deals.length) return;
		Deal deal = deals[slot];
		if (deal == null) return;
		purchase(event.getWhoClicked(), deal, event.getClick());
		if (deal.getProduct() != null && deal.getProduct().getMaxStackSize() != 1 && event.isShiftClick())
			for (int i = 0; i < 9; i++) purchase(event.getWhoClicked(), deal, event.getClick());
		((Player) event.getWhoClicked()).updateInventory();
	}

	public static void purchase(HumanEntity p, Deal deal, ClickType click) {
		Resource r = deal.getResource();
		int c = deal.getCost();
		Inventory inv = p.getInventory();
		if (!inv.contains(r.getItem().getType(), c)) return;

		// Принятие ресурсов
		ItemStack[] contents = inv.getContents();
		for (int slot = 0, contentsLength = contents.length; slot < contentsLength; slot++) {
			ItemStack item = contents[slot];
			if (item == null) continue;
			if (c <= 0) break;
			if (item.getType() != r.getItem().getType()) continue;
			int a = item.getAmount();
			int takeFromThisSlot = min(a, c);
			if (a <= c) inv.setItem(slot, null);
			else item.setAmount(a - c);
			c -= takeFromThisSlot;
		}

		// Выдача ресурсов
		deal.equip(p, click);
	}

	public static class Section {
		private final PurchaseGUI gui;
		private final ItemStack icon;

		public Section(PurchaseGUI gui, ItemStack item) {
			this.gui = gui;
			this.icon = item;
			Purchase.gui.inv().addItem(icon);
		}

		public Section(PurchaseGUI gui, Material m, String title, String description) {
			this(gui, ItemBuilder.create(m, title, description.split("\n")));
		}
		public ItemStack getIcon() {
			return icon;
		}
		public PurchaseGUI getGui() {
			return gui;
		}
	}

}
