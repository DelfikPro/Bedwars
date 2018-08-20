package pro.delfik.bedwars.preparation;

import net.md_5.bungee.api.chat.TextComponent;
import pro.delfik.bedwars.game.Game;
import pro.delfik.bedwars.util.Colors;
import pro.delfik.bedwars.util.FixedArrayList;
import pro.delfik.lmao.core.Person;
import pro.delfik.lmao.util.Cooldown;
import pro.delfik.lmao.util.U;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GamePreparation {

	private static final Map<String, GamePreparation> byPlayer = new HashMap<>();

	private final GameSize format;
	private final int slot;
	private final FixedArrayList<Person> players;

	public GamePreparation(GameSize format, int slot) {
		this.format = format;
		this.slot = slot;
		players = new FixedArrayList<>(format.getTotal());
		NewGame.update(slot, this);

	}

	public GameSize getFormat() {
		return format;
	}

	public void add(Person p) {
		GamePreparation oldGame = byPlayer.get(p.getName());
		if (oldGame != null) oldGame.remove(p);
		players.add(p);
		byPlayer.put(p.getName(), this);
		TextComponent text = U.constructComponent("§a+ §e" + players.size() + "§f/§e" + format.getTotal() + "§7: ", p.getDisplayName(), "§f вступил игру.");
		for (Person person : players) U.msg(person.getHandle(), text);

		if (players.size() >= getFormat().getTotal()) start();
	}

	public void start() {
		new Cooldown("bwp" + slot, 5, players, () -> {
			pro.delfik.bedwars.game.Map map = mapVoting.result();
			Game game = new Game(map, shuffle(map));
		});
	}

	private Colors<Person[]> shuffle(pro.delfik.bedwars.game.Map map) {
		Collections.shuffle(players);
		Colors<Person[]> colors = new Colors<>();
		for (map.getRegisteredColors())
	}

	public void remove(Person p) {
		TextComponent text = U.constructComponent("§c- §e" + (players.size() - 1) + "§f/§e" + format.getTotal() + "§7: ", p.getDisplayName(), "§f покинул игру.");
		for (Person person : players) U.msg(person.getHandle(), text);
		players.remove(p);
		byPlayer.remove(p.getName());
	}

	public int getSlot() {
		return slot;
	}

}
