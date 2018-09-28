package pro.delfik.bedwars.purchase;

import implario.util.ArrayUtils;
import org.bukkit.inventory.ItemStack;
import pro.delfik.bedwars.game.Resource;

public class SlotDeal extends Deal {

	private final int slot;
	public SlotDeal(ItemStack product, Resource resource, int cost, int slot) {
		super(product, resource, cost);
		this.slot = slot;
	}

	public int getSlot() {
		return slot;
	}

	public static Deal[] generify(Deal[] deals) {
		Deal[] ds = new Deal[18];
		for (Deal d : deals) if (d instanceof SlotDeal) ds[((SlotDeal) d).slot] = d; else ds[ArrayUtils.firstEmpty(ds)] = d;
		return ds;
	}

}
