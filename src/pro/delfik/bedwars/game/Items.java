package pro.delfik.bedwars.game;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pro.delfik.lmao.outward.item.ItemBuilder;

public class Items {

	public static final ItemStack THOR_BONE = ItemBuilder.create(Material.BONE, "§f>> §b§lМетнуть молнию §f<<", "§7<§a§lКликабельно§7>");
	public static final ItemStack HOME_TELEPORTATION = ItemBuilder.create(Material.SULPHUR, "§f>> §e§lВжух на базу §f<<", "§7<§a§lКликабельно§7>");
	public static final ItemStack RESCUE_PLATFORM = ItemBuilder.create(Material.BLAZE_ROD, "§f>> §c§lСпастись §f<<",
			"§a§oСоздаёт под вашими ногами платформу.", "§aВы не получаете урона от падения.", "§7<§a§lКликабельно§7>");
	public static final ItemStack GPS_TRACKER = ItemBuilder.create(Material.COMPASS, "§aGPS-Трекер", "§e§oСтрелочка указывает на ближайшего врага.",
			"§e§oПри нажатии вычисляет расстояние до врага.");

}
