package pro.delfik.bedwars.preparation;

import lib.ItemBuilder;
import lib.gui.GUI;
import lib.gui.GeneralizedGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pro.delfik.bedwars.game.Game;
import pro.delfik.bedwars.game.Game.State;
import pro.delfik.bedwars.util.FixedArrayList;

import java.util.function.BiConsumer;

public class GameSelector {

	private static final BiConsumer<Player, Integer> JOIN = (player, slot) -> {
		if (slot > Game.MAX_RUNNING_GAMES || slot < 0) return;
		Unit unit = Unit.units.get(slot);
		switch (unit.getState()) {
			case NOTHING:

		}

	};


	private static final GUI ALL_GAMES = new GeneralizedGUI(Bukkit.createInventory(null, 18, "Список секторов"), JOIN, null);

	public static class Unit {
		private static final FixedArrayList<Unit> units = new FixedArrayList<>(Game.MAX_RUNNING_GAMES);

		private final int slot;
		private final Game game;

		public Unit(int slot) {
			units.set(slot, this);
			this.slot = slot;
			game = Game.get(slot);
		}
		public ItemStack generateItem() {
			State state = getState();
			return ItemBuilder.create(state.getMaterial(), "§f§lСектор " + (slot + 1), "§f", state.getTitle(),
					state == State.GAME ? "§f>> §e§lНаблюдать §f<<" : "§cВход закрыт.", "§f");
		}
		public State getState() {
			return game == null ? State.NOTHING : game.getState();
		}
	}
}
