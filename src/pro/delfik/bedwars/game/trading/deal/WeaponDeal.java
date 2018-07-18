package pro.delfik.bedwars.game.trading.deal;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pro.delfik.bedwars.Defaults;
import pro.delfik.bedwars.game.trading.Resource;

public class WeaponDeal extends TradeDeal {
	public WeaponDeal(ItemStack result, Resource resource, int cost, ItemStack display) {
		super(result, resource, cost, display);
	}

	public WeaponDeal(ItemStack result, Resource resource, int cost) {
		super(result, resource, cost);
	}

	public WeaponDeal(ItemStack result, Resource resource, int cost, int slot) {
		super(result, resource, cost, slot);
	}

	public WeaponDeal(ItemStack result, Resource resource, int cost, ItemStack display, int slot) {
		super(result, resource, cost, display, slot);
	}

	@Override
	public void equip(Player p) {
		if (p.getInventory().contains(Defaults.SWORD)) {
			int slot = p.getInventory().first(Defaults.SWORD);
			p.getInventory().setItem(slot, getResult());
		} else super.equip(p);
	}
}
