package pro.delfik.bedwars.preparation;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pro.delfik.bedwars.game.Map;
import pro.delfik.lmao.outward.inventory.GUI;
import pro.delfik.lmao.outward.inventory.SlotGUI;
import pro.delfik.lmao.outward.item.ItemBuilder;
import pro.delfik.lmao.user.Person;

import static implario.util.Converter.plural;

public class NewGame {

	public static final GUI gui = new SlotGUI("Выбор игры", PublicGame.HARDCODED_PREPARATION_LIMIT / 9, (p, slot, item) -> {
		PublicGame prep = PublicGame.get(slot);
		if (prep != null) {
			prep.add(Person.get(p));
			return;
		}
		if (slot >= PublicGame.HARDCODED_PREPARATION_LIMIT || slot < 0) return;
		p.openInventory(getFormatSelector(p, slot).inv());
	});
	public static final ItemStack EMPTY = ItemBuilder.create(Material.NETHER_STAR, "§7§lПустой сектор","§f>> §a§lСоздать игру §f<<");

	static {
		for (int i = 0; i < 36; i++) gui.inv().setItem(i, EMPTY);
	}

	public static void create(Player p, GameFormat format, int gpid) {
		p.closeInventory();
		if (Map.getMaps(format.getTeams()).isEmpty()) {
			p.sendMessage("§cКарт для данного формата не найдено.");
			p.closeInventory();
			return;
		}
		PublicGame prep = new PublicGame(format, gpid);
		prep.add(Person.get(p));
		prep.setOwner(p.getName());
	}
	public static GUI getFormatSelector(Player p, int gpid) {
		SlotGUI gui = new SlotGUI("Выберите формат", 1, (p1, slot, item) -> create(p, GameFormat.values()[slot - 3], gpid), true);
		int i = 3;
		for (GameFormat f : GameFormat.values()) gui.inv().setItem(i++, f.getDisplay());
		return gui;
	}


	public static void update(int slot, PublicGame p) {
		if (p == null) {
			gui.inv().setItem(slot, EMPTY);
			return;
		}
		int ps = p.getPlayers();
		gui.inv().setItem(slot, ItemBuilder.create(p.getSize().getIcon(), "§f§lСектор " + slot + " §e[§f" + ps + " игрок" + plural(ps, "", "а", "ов") + "§e]",
				"§f", "§fФормат: " + p.getSize().getTitle(), "§f>> §a§lПрисоединиться §f<<"));
	}

}
