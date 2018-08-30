package pro.delfik.bedwars.preparation;

import lib.gui.VotingGUI;
import net.md_5.bungee.api.chat.TextComponent;
import pro.delfik.bedwars.game.Color;
import pro.delfik.bedwars.game.Game;
import pro.delfik.bedwars.util.Colors;
import pro.delfik.bedwars.util.FixedArrayList;
import pro.delfik.lmao.core.Person;
import pro.delfik.lmao.util.Cooldown;
import pro.delfik.lmao.util.U;
import implario.util.Converter;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamePreparation {

	private static final Map<String, GamePreparation> byPlayer = new HashMap<>();

	private final GameSize format;
	private final int slot;
	private final FixedArrayList<Person> players;
	private final MapVoting mapVoting;

	public GamePreparation(GameSize format, int slot) {
		this.format = format;
		this.slot = slot;
		players = new FixedArrayList<>(format.getTotal());
		NewGame.update(slot, this);
		mapVoting = new MapVoting(format);
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
			List<VotingGUI.Entry> votingResult = mapVoting.result();
			pro.delfik.bedwars.game.Map map = pro.delfik.bedwars.game.Map.get(Converter.random(votingResult).key);
			for (Person person : players) person.sendMessage("§aПо результатам голосования выбрана карта §f§l" + map.getName());
			Game game = new Game(map, shuffle(map));
		});
	}

	private Colors<Person[]> shuffle(pro.delfik.bedwars.game.Map map) {
		Collections.shuffle(players);
		Colors<Person[]> colors = new Colors<>();
		Person[] all = players.toArray(new Person[players.size()]);
		int globalIter = 0;
		for (Color color : map.getRegisteredColors()) {
			Person[] current = new Person[format.getPlayers()];
			colors.put(color, current);
			for (int i = 0; i < format.getPlayers(); i++) {
				if (globalIter >= all.length) return colors;
				current[i] = all[globalIter++];
			}
		}
		return colors;
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
