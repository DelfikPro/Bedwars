package pro.delfik.bedwars.purchase;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionType;
import pro.delfik.bedwars.game.Resource;
import pro.delfik.lmao.outward.item.ItemBuilder;
import pro.delfik.lmao.outward.item.PotionBuilder;

import java.util.HashMap;
import java.util.LinkedHashMap;

import static pro.delfik.lmao.outward.item.ItemBuilder.create;

public class Deal {

	public static final HashMap<Integer, Deal> byHash = new LinkedHashMap<>();

	private final ItemStack product;
	private final Resource resource;
	private final int cost;
	private ItemStack display;

	public Deal(ItemStack product, Resource resource, int cost) {
		this.product = product;
		this.resource = resource;
		this.cost = cost;
		if (product != null && cost < 65 && product.getType() != Material.STRING) byHash.put(hashCode(), this);
	}

	@Override
	public int hashCode() {
		return getItemHash(product);
	}

	public static int getItemHash(ItemStack i) {
		ItemMeta meta = i.getItemMeta();
		return (meta.hasDisplayName() ? meta.getDisplayName().hashCode() : i.getType().name().hashCode()) * i.getAmount();
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
		if (getType() == Type.SWORD) {
			int swordSlot = p.getInventory().first(Material.STONE_SWORD);
			if (swordSlot >= 0) {
				p.getInventory().setItem(swordSlot, product);
				return;
			}
		}
		Purchase.give(p, product);
	}

	private Type getType() {
		return Type.byItem(product);
	}

	public enum Type {
		FISHING_ROD(new ItemStack(Material.FISHING_ROD), 0),
		BLOCKS(create(Material.SANDSTONE, "§eБлоки"), 1),
		SWORD(create(Material.STONE_SWORD, "§cМеч"), 2),
		BOW(create(Material.BOW, "§cЛук"), 3),
		WEB(new ItemStack(Material.WEB), 4),
		PICKAXE(create(Material.STONE_PICKAXE, "§aКирка"), 5),
		FOOD(create(Material.APPLE, "§eЕда"), 6),
		HEAL(new PotionBuilder(PotionType.INSTANT_HEAL).build(), 7),
		STRENGTH(new PotionBuilder(PotionType.STRENGTH).withDuration(3600).build(), 8),
		PLATFORM(create(Material.BLAZE_ROD, "§aСпасительная платформа"), 9),
		ARROW(new ItemStack(Material.ARROW), 10),
		TP_HOME(create(Material.SULPHUR, "§aТелепортация на базу"), 11);

		private final ItemStack item;
		private final byte id;

		Type(ItemStack item, int id) {
			this.item = item;
			this.id = (byte) id;
		}

		public ItemStack getItem() {
			return item;
		}


		public static Type byItem(ItemStack i) {
			switch (i.getType()) {
				case SANDSTONE:
					return BLOCKS;
				case APPLE:
				case PUMPKIN_PIE:
				case GOLDEN_APPLE:
				case CAKE:
					return FOOD;
				case STONE_SWORD:
				case IRON_SWORD:
				case DIAMOND_SWORD:
					return SWORD;
				case BOW:
					return BOW;
				case STONE_PICKAXE:
				case IRON_PICKAXE:
				case DIAMOND_PICKAXE:
					return PICKAXE;
				case ARROW:
					return ARROW;
				case BLAZE_ROD:
					return PLATFORM;
				case SULPHUR:
					return TP_HOME;
				case POTION:
					if (i.getDurability() == 9) return STRENGTH;
					if (i.getDurability() == 5 || i.getDurability() == 37) return HEAL;
				case FISHING_ROD:
					return FISHING_ROD;
				case WEB:
					return WEB;
				default:
					return null;
			}
		}
	}
}
