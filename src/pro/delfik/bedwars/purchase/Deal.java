package pro.delfik.bedwars.purchase;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import pro.delfik.bedwars.game.Resource;
import pro.delfik.lmao.outward.item.ItemBuilder;

public class Deal {

	private final ItemStack product;
	private final Resource resource;
	private final int cost;
	private ItemStack display;

	public Deal(ItemStack product, Resource resource, int cost) {
		this.product = product;
		this.resource = resource;
		this.cost = cost;
	}

	public int getCost() {
		return cost;
	}

	public Resource getResource() {
		return resource;
	}

	public ItemStack getProduct() {
		return product;
	}

	public ItemStack getDisplay() {
		return display != null ? display : (display = ItemBuilder.addLore(product, "§dЦена: " + resource.represent(cost)));
	}

	public void equip(HumanEntity p, ClickType click) {
		// ToDo: Закидывание предметов в командный сундук при СКМ.
		Purchase.give(p, product);
	}
}
