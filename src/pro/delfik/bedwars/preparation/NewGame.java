package pro.delfik.bedwars.preparation;

import lib.ItemBuilder;
import lib.gui.GUI;
import lib.gui.GeneralizedGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.function.BiConsumer;

public class NewGame {
	private static final BiConsumer<Player, Integer> CREATING = (player, slot) -> {

	};

	private static final GUI NEW_GAME = new GeneralizedGUI(Bukkit.createInventory(null, 9, "Список игр"), CREATING, null);

	public static void update(int slot, GamePreparation gamePreparation) {
		NEW_GAME.getInventory().setItem(0, ItemBuilder.create(Material.EMERALD_BLOCK,
				"§f§lИгра " + gamePreparation.getFormat().getTitle(), "§f>> §a§lПрисоединиться §f<<"));
	}
}
