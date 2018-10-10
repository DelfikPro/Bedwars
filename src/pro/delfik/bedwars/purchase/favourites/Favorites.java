package pro.delfik.bedwars.purchase.favourites;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pro.delfik.bedwars.purchase.Deal;
import pro.delfik.bedwars.purchase.Purchase;
import pro.delfik.lmao.outward.inventory.GUI;
import pro.delfik.lmao.outward.inventory.SlotGUI;

public class Favorites {

	private static final Inventory SELECT = GUI.create(54, "Выберите товар");

	public static void fillSelectionGUI() {
		int i = 0;
		for (Deal value : Deal.byHash.values())
			if (value.getCost() < 99 && i < 54) SELECT.setItem(i++, value.getDisplay());
	}

	public static void openSelection(Player p, int slot) {
		GUI gui = new SlotGUI(SELECT, (player, $, item) -> {
			setFavorite(p, slot, Deal.byHash.get(Deal.getItemHash(item)));
			Purchase.open(p);
		}, true);
		p.openInventory(gui.inv());
	}

	private static void setFavorite(Player p, int slot, Deal deal) {
		GamerInfo g = GamerInfo.ALL.get(p);
		if (g == null) return;
		g.getfDeals()[slot] = deal;
	}

}
