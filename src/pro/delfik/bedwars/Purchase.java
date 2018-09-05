package pro.delfik.bedwars;

import pro.delfik.lmao.outward.gui.AdvancedGUI;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public class Purchase implements Listener {
	
	private static final AdvancedGUI gui = new AdvancedGUI("- TESTING -", 3, e -> {
		// ToDo: Purchase
	});
	
	public static Inventory getInventory() {
		return gui.getInventory();
	}
}
