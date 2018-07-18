package pro.delfik.bedwars.game.trading.deal;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import pro.delfik.bedwars.game.trading.Resource;

public class ArmorDeal extends TradeDeal {
	
	private ItemStack helmet;
	private ItemStack chestplate;
	private ItemStack leggings;
	private ItemStack boots;
	
	public ArmorDeal(ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots, Resource resource, int cost, ItemStack display) {
		super(null, resource, cost, display, -1);
		this.helmet = helmet;
		this.chestplate = chestplate;
		this.leggings = leggings;
		this.boots = boots;
	}

	@Override
	public void equip(Player p) {
		PlayerInventory inv = p.getInventory();
		if (helmet != null) {
			if (inv.getHelmet() == null) inv.setHelmet(helmet);
			else if (inv.getHelmet().getType() == Material.LEATHER_HELMET) inv.setHelmet
					(helmet);
			else if (inv.firstEmpty() < 0) p.getWorld().dropItem(p.getLocation(), helmet).setPickupDelay(0);
			else p.getInventory().addItem(helmet);
		}
		if (chestplate != null) {
			if (inv.getChestplate() == null) inv.setChestplate(chestplate);
			else if (inv.getChestplate().getType() == Material.LEATHER_CHESTPLATE) inv.setChestplate(chestplate);
			else if (inv.firstEmpty() < 0) p.getWorld().dropItem(p.getLocation(), chestplate).setPickupDelay(0);
			else p.getInventory().addItem(chestplate);
		}
		if (leggings != null) {
			if (inv.getLeggings() == null) inv.setLeggings(leggings);
			else if (inv.getLeggings().getType() == Material.LEATHER_LEGGINGS) inv.setLeggings(leggings);
			else if (inv.firstEmpty() < 0) p.getWorld().dropItem(p.getLocation(), leggings).setPickupDelay(0);
			else p.getInventory().addItem(leggings);
		}
		if (boots != null) {
			if (inv.getBoots() == null) inv.setBoots(boots);
			else if (inv.getBoots().getType() == Material.LEATHER_BOOTS) inv.setBoots(boots);
			else if (inv.firstEmpty() < 0) p.getWorld().dropItem(p.getLocation(), boots).setPickupDelay(0);
			else p.getInventory().addItem(boots);
		}
	}

	@Override
	public boolean isStackable() {
		return false;
	}

	public ItemStack getHelmet() {
		return helmet;
	}

	public void setHelmet(ItemStack helmet) {
		this.helmet = helmet;
	}

	public ItemStack getChestplate() {
		return chestplate;
	}

	public void setChestplate(ItemStack chestplate) {
		this.chestplate = chestplate;
	}

	public ItemStack getLeggings() {
		return leggings;
	}

	public void setLeggings(ItemStack leggings) {
		this.leggings = leggings;
	}

	public ItemStack getBoots() {
		return boots;
	}

	public void setBoots(ItemStack boots) {
		this.boots = boots;
	}
	
	

}
