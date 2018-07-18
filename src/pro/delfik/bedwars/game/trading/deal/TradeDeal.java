package pro.delfik.bedwars.game.trading.deal;

import lib.Converter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pro.delfik.bedwars.game.trading.Resource;

public class TradeDeal {
	private ItemStack result;
	private Resource resource;
	private int cost;
	private ItemStack display;
	private int slot;
	
	public TradeDeal(ItemStack result, Resource resource, int cost, ItemStack display) {
		this(result, resource, cost, display, -1);
	}
	
	public TradeDeal(ItemStack result, Resource resource, int cost) {
		this(result, resource, cost, -1);
	}
	public TradeDeal(ItemStack result, Resource resource, int cost, int slot) {
		this(result, resource, cost, result, slot);
	}
	public TradeDeal(ItemStack result, Resource resource, int cost, ItemStack display, int slot) {
		this.result = result;
		this.cost = cost;
		this.resource = resource;
		this.slot = slot;
		this.display = display.clone();
		ItemMeta m = display.getItemMeta();
		m.setLore(Converter.addInList(m.getLore(), "§dЦена: " + resource.getColor() + cost + " " + Converter.plural
																													 (cost, resource.getName(), resource.getPlural(), resource.getPlural())));
		this.display.setItemMeta(m);
	}
	
	public boolean isStackable() {
		return result.getType().getMaxStackSize() != 1;
		
	}
	
	public void equip(Player p) {
		p.getInventory().addItem(result).forEach((in, drop) -> p.getWorld().dropItem(p.getLocation(), drop));
	}
	
	public ItemStack getResult() {
		return result;
	}
	public void setResult(ItemStack result) {
		this.result = result;
	}
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public ItemStack getDisplay() {
		return display;
	}
	public int getSlot() {return slot;}
}
