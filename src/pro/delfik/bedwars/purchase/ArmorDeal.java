package pro.delfik.bedwars.purchase;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import pro.delfik.bedwars.game.Resource;
import pro.delfik.lmao.outward.item.ItemBuilder;

public class ArmorDeal extends Deal {

	private final ItemStack[] armor = new ItemStack[4];
	private final ItemStack display;

	public ArmorDeal(Resource resource, int cost, ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots) {
		super(null, resource, cost);
		armor[0] = helmet;
		armor[1] = chestplate;
		armor[2] = leggings;
		armor[3] = boots;
		display = new ItemBuilder(chestplate == null ? leggings : chestplate).addLore("§dЦена: " + resource.represent(cost)).build();
		byHash.put(hashCode(), this);
	}

	@Override
	public int hashCode() {
		return getItemHash(display);
	}

	@Override
	public ItemStack getDisplay() {
		return display;
	}

	@Override
	public void equip(HumanEntity p, ClickType click) {
		if (click.isShiftClick()) {
			for (ItemStack i : armor) if (i != null) Purchase.give(p, i);
		} else {
			ItemStack[] current = p.getInventory().getArmorContents();
			for (int i = armor.length - 1, j = 0; i >= 0; i--, j++) {
				ItemStack a = armor[j];
				if (a == null) continue;
				ItemStack c = current[i];
				if (c != null && c.getType() != Material.AIR && !c.getType().name().startsWith("LEATHER_")) Purchase.give(p, c);
				current[i] = a;
			}
			p.getInventory().setArmorContents(current);
		}
	}
}
