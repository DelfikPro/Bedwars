package pro.delfik.bedwars.purchase;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import pro.delfik.lmao.outward.inventory.GUI;

import java.util.Collection;

public class PurchaseGUI extends GUI {

	private final Collection<Deal> deals;

	public PurchaseGUI(String title, int rows, Collection<Deal> deals) {
		super(create(rows * 9, title), false);
		this.deals = deals;
	}

	@Override
	public void click(InventoryClickEvent event) {
		Player p = ((Player) event.getWhoClicked());
		p.sendMessage("§7[§eDEBUG§7] §aNumberKey: §f" + event.getHotbarButton() + "§a, Action: §f" + event.getAction() + "§a, ClickType: §f" + event.getClick());
	}
}
