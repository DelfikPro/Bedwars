package pro.delfik.bedwars.preparation;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pro.delfik.bedwars.game.Game;
import pro.delfik.bedwars.game.Game.State;
import pro.delfik.lmao.outward.inventory.GUI;
import pro.delfik.lmao.outward.inventory.SlotGUI;
import pro.delfik.lmao.outward.item.ItemBuilder;

public class GameSelector {


	private static final GUI ALL_GAMES = new SlotGUI("Список секторов", 2, GameSelector::join);

	private static void join(Player player, int slot, ItemStack itemStack) {
		if (slot > Game.MAX_RUNNING_GAMES || slot < 0) return;
		if (itemStack.getType() == Material.GOLD_BLOCK) {
			player.closeInventory();
			player.sendMessage("§eВ разработке.");
			// ToDo: Spectating running games.
		}
	}

	private static final String[] ALPHABET = {
			"Альфа", "Браво", "Чарли", "Дельта", "Эхо", "Фита", "Гидра",
			"Олли", "Индо", "Юлия", "Кило", "Лима", "Мега", "Нова",
			"Оскар", "Пиро", "Квебе", "Ромео", "Сиерра", "Танго", "Юни",
			"Вижн", "Вольт", "Рентген", "Янки", "Зулу"
	};

	static {
		for (int i = 0; i < Game.MAX_RUNNING_GAMES; i++) dummy(i);
	}

	private static void dummy(int slot) {
		State state = State.NOTHING;
		ItemStack item = ItemBuilder.create(state.getMaterial(), "§f§lСектор §e" + ALPHABET[slot],"§f", state.getTitle(), "§cВход закрыт.", "§f");
		ALL_GAMES.inv().setItem(slot, item);
	}

	public static void update(Game game) {
		State state = game.getState();
		if (state == State.NOTHING) {
			dummy(game.getId());
			return;
		}
		ItemStack item = ItemBuilder.create(state.getMaterial(), "§f§lСектор §e" + ALPHABET[game.getId()],"§f", state.getTitle(),
				"§f§lКарта: §e" + game.getMap().getName(), state == State.GAME ? "§f>> §e§lНаблюдать §f<<" : "§cВход закрыт.", "§f");
		ALL_GAMES.inv().setItem(game.getId(), item);
	}

	public static Inventory getInventory() {
		return ALL_GAMES.inv();
	}
}
