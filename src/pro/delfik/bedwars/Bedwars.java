package pro.delfik.bedwars;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import pro.delfik.bedwars.game.Color;
import pro.delfik.bedwars.game.Items;
import pro.delfik.bedwars.purchase.Deal;
import pro.delfik.bedwars.purchase.favourites.GamerInfo;
import pro.delfik.lmao.outward.item.I;
import pro.delfik.lmao.user.Person;

import java.util.EnumSet;
import java.util.Map;

import static org.bukkit.Material.*;

public class Bedwars {
	private static World lobby;
	public static EnumSet<Material> trashSet = EnumSet.of(AIR, LEATHER_CHESTPLATE, LEATHER_BOOTS, LEATHER_HELMET, LEATHER_LEGGINGS);


	public static void toLobby(Player p) {
		p.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
		p.setLevel(0);
		p.setExp(0);
		p.setFoodLevel(20);
		p.setSaturation(20);
		p.setHealth(20);
		I.delay(() -> p.setAllowFlight(true), 2);
		p.setGameMode(GameMode.ADVENTURE);
		p.setDisplayName(Person.get(p).getRank().getPrefix() + "§7" + p.getName());
		PlayerInventory inv = p.getInventory();
		inv.clear();
		inv.setArmorContents(null);
		inv.setItem(0, Items.JOIN_GAME);
		inv.setItem(7, Items.GAME_LIST);
		inv.setItem(8, Items.BACK_TO_LOBBY);
		p.teleport(getLobby().getSpawnLocation());
	}

	/**
	 * Выдать игроку предмет. Если предмет не поместится в инвентарь,
	 * Он выпадет на землю рядом с игроком.
	 * @param p Игрок, которому нужно выдать предмет
	 * @param item Предмет для выдачи
	 */
	public static void give(Player p, ItemStack... item) {
		GamerInfo info = GamerInfo.ALL.get(p);
		for (ItemStack i : item) {
			Deal.Type type = Deal.Type.byItem(i);
			if (type == null) add(p, i);
			else {
				int slot = info.getDefaultSlots().getDefault(type);
				if (slot == -1) add(p, i);
				else {
					ItemStack c = p.getInventory().getItem(slot);
					p.getInventory().setItem(slot, i);
					if (c != null && !trashSet.contains(c.getType())) add(p, c);
				}
			}
		}
	}

	public static void add(Player p, ItemStack i) {
		World w = p.getWorld();
		Location l = p.getLocation();
		for (Map.Entry<Integer, ItemStack> e : p.getInventory().addItem(i.clone()).entrySet()) w.dropItem(l, e.getValue());
	}

	public static void toGame(Player p, Color c) {
		PlayerInventory inv = p.getInventory();
		p.setGameMode(GameMode.SURVIVAL);
		p.setAllowFlight(false);
		I.delay(() -> p.setAllowFlight(false), 2);
		p.setExp(0);
		p.setLevel(0);
		p.setFoodLevel(20);
		p.setSaturation(5);
		p.setDisplayName(c.getPrefix() + p.getName());
		inv.clear();
		GamerInfo i = GamerInfo.ALL.get(p);
		inv.setItem(i.getDefaultSlots().getOrDefault(Deal.Type.SWORD, 0), Items.getDeafultSword());
		I.delay(() -> inv.setArmorContents(Items.getDefaultArmor(p)), 2);
	}

	public static World getLobby() {
		return lobby == null ? lobby = Bukkit.getWorld("lobby") : lobby;
	}

	public static void toPreparation(Person p) {
		PlayerInventory inv = p.getHandle().getInventory();
		inv.setItem(1, Items.VOTE_FOR_MAP);
		inv.setItem(2, Items.LEAVE_GAME);
	}
}
