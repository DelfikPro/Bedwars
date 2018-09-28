package pro.delfik.bedwars.purchase;

import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

import static pro.delfik.lmao.outward.inventory.GUI.create;

public class PurchaseGUI implements Listener {

	private static final Map<String, PurchaseGUI> map = new HashMap<>();
	private final Deal[] deals;
	private final Inventory inv;

	public PurchaseGUI(String title, int rows, Deal[] deals) {
		inv = create(rows * 9, "§f§0" + title);
		for (Deal deal : this.deals = deals) if (deal != null) {
			if (deal instanceof SlotDeal) inv.setItem(((SlotDeal) deal).getSlot(), deal.getDisplay());
			else inv.addItem(deal.getDisplay());
		}
		inv.setItem(inv.getSize() - 5, Purchase.BACK_TO_MAIN);
		map.put(inv.getTitle(), this);
	}

	public static PurchaseGUI get(String name) {
		return map.get(name);
	}

	public Inventory inv() {
		return inv;
	}

	public Deal[] getDeals() {
		return deals;
	}
}
