package pro.delfik.bedwars.game.trading;

import lib.Generate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pro.delfik.bedwars.game.trading.deal.TradeDeal;

import java.util.ArrayList;
import java.util.List;

public class TradeSection {
	private String name;
	private ItemStack display;
	private Inventory inv;
	private List<TradeDeal> trades = new ArrayList<>();
	public static List<TradeSection> list = new ArrayList<>();
	public final static ItemStack BACK_TO_MAIN = Generate.itemstack(Material.BED, 1, 0, "§aНазад в меню");
	
	public TradeSection(String name, ItemStack display, List<TradeDeal> trades) {
		new TradeSection(name, display, -1, trades);
	}
	public TradeSection(String name, ItemStack display, int slot, List<TradeDeal> trades) {
		this.name = name;
		this.display = display;
		this.trades = trades;
		int rows = 1;
		for (TradeDeal deal : trades) {
			if (deal.getSlot() > 8) rows = 2;
		}
		this.inv = Bukkit.createInventory(null, (rows + 1) * 9, this.name);
		for (TradeDeal deal : trades) {
			if (deal.getSlot() < 0) this.inv.addItem(deal.getDisplay());
			else this.inv.setItem(deal.getSlot(), deal.getDisplay());
		}
		this.inv.setItem(13 + ((rows - 1) * 9), BACK_TO_MAIN);
		if (slot == -1) slot = TradeHandler.INVENTORY.getInventory().firstEmpty();
		TradeHandler.INVENTORY.put(slot, display, p -> p.openInventory(inv));
		list.add(this);
	}
	
	public static TradeSection get(ItemStack display) {
		for (TradeSection s : list) if (Generate.equalsItem(s.getDisplay(), display)) return s;
		return null;
	}
	
	public TradeDeal getTrade(ItemStack display) {
		for (TradeDeal d : trades) if (Generate.equalsItem(d.getDisplay(), display)) return d;
		return null;
	}
	
	public ItemStack getDisplay() {
		return display;
	}
	
	public void setDisplay(ItemStack display) {
		this.display = display;
	}
	
	public List<TradeDeal> getTrades() {
		return trades;
	}
	
	public void setTrades(List<TradeDeal> trades) {
		this.trades = trades;
		List<ItemStack> is = new ArrayList<>();
		for (TradeDeal deal : trades) {
			is.add(deal.getDisplay());
		}
		this.inv = Generate.inventory(this.name, 18, is.toArray(new ItemStack[is.size()]));
		this.inv.setItem(13, BACK_TO_MAIN);
	}
	
	public String getName() {
		return name;
	}
	
	public Inventory getInv() {
		return inv;
	}
}
