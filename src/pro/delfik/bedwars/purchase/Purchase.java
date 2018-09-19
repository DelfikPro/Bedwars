package pro.delfik.bedwars.purchase;

import org.bukkit.event.inventory.InventoryClickEvent;
import pro.delfik.lmao.outward.gui.AdvancedGUI;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public class Purchase implements Listener {
	
	private static final AdvancedGUI gui = new AdvancedGUI("- TESTING -", 3, Purchase::shopClick);
	
	public static Inventory getInventory() {
		return gui.getInventory();
	}

	private static void shopClick(InventoryClickEvent e) {

	}
}
