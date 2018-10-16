package pro.delfik.bedwars.game;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pro.delfik.bedwars.util.CyclicIterator;
import pro.delfik.lmao.outward.item.ItemBuilder;

public class Items {

	public static final ItemStack THOR_BONE = ItemBuilder.create(Material.BONE, "§f>> §b§lМетнуть молнию §f<<", "§7<§a§lКликабельно§7>",
			"§a§oВсе игроки в радиусе 5 блоков от метания получат разряд молнии.");
	public static final ItemStack HOME_TELEPORTATION = ItemBuilder.create(Material.SULPHUR, "§f>> §e§lВжух на базу §f<<", "§7<§a§lКликабельно§7>");
	public static final ItemStack RESCUE_PLATFORM = ItemBuilder.create(Material.BLAZE_ROD, "§f>> §c§lСпастись §f<<",
			"§a§oСоздаёт под вашими ногами платформу.", "§aВы не получаете урона от падения.", "§7<§a§lКликабельно§7>");
	public static final ItemStack GPS_TRACKER = ItemBuilder.create(Material.COMPASS, "§aGPS-Трекер", "§e§oСтрелочка указывает на ближайшего врага.",
			"§e§oПри нажатии вычисляет расстояние до врага.");

	public static final ItemStack JOIN_GAME = ItemBuilder.create(Material.EMERALD, "§f>> §a§lИграть §f<<");
	public static final ItemStack BACK_TO_LOBBY = ItemBuilder.create(Material.COMPASS, "§f>> §c§lВ лобби §f<<");
	public static final ItemStack VOTE_FOR_MAP = ItemBuilder.create(Material.SLIME_BALL, "§f>> §a§lГолосовать за карту§f <<", "§e§oВыбор карты путём голосования.");
	public static final ItemStack LEAVE_GAME = ItemBuilder.create(Material.DARK_OAK_DOOR_ITEM, "§f>> §c§lВыйти§f <<");
	public static final ItemStack GAME_LIST = ItemBuilder.create(Material.WATCH, "§f>> §6§lСписок игр§f <<");
	public static final ItemStack SELECT_TEAM = ItemBuilder.create(Material.NAME_TAG, "§f>> §b§lВыбрать команду§f <<");

	private static final CyclicIterator<ItemStack> SWORDS = new CyclicIterator<>(new ItemStack[] {
			ItemBuilder.create(Material.STONE_SWORD, "§fЕгипетский топор", "§a§oВ египте были топоры?"),
			ItemBuilder.create(Material.STONE_SWORD, "§fКаменная шпага", "§a§oПринадлежит чемпиону по фехтованию"),
			ItemBuilder.create(Material.STONE_SWORD, "§d§lСамое мощное оружие в игре", "§a(нет)"),
			ItemBuilder.create(Material.STONE_SWORD, "§6Чесалка для спины", "§a§oМуррррррр"),
			ItemBuilder.create(Material.STONE_SWORD, "§fКаменный меч", "§a§oЭтот меч был воткнут в священную", "§a§oГору §f§oВульто§a§o древними друидами.",
					"§a§oТолько достойный сможет вынуть", "§a§oЕго из скалы и использовать в полную мощь."),
	});

	public static ItemStack getDeafultSword() {
		return SWORDS.next();
	}

	public static ItemStack[] getDefaultArmor(Player p) {
		Game g = Game.get(p);
		if (g == null) return null;
		BWTeam t = g.getTeam(p.getName());
		if (t == null) return null;
		return t.getColor().getArmor();
	}
}
