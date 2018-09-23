package pro.delfik.bedwars.preparation;

import implario.util.Converter;
import net.md_5.bungee.api.chat.TextComponent;
import pro.delfik.bedwars.game.Color;
import pro.delfik.bedwars.game.Game;
import pro.delfik.bedwars.util.Colors;
import pro.delfik.bedwars.util.CyclicIterator;
import pro.delfik.bedwars.util.FixedArrayList;
import pro.delfik.lmao.user.Person;
import pro.delfik.lmao.outward.gui.VotingGUI;
import pro.delfik.lmao.util.Cooldown;
import pro.delfik.lmao.util.U;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamePreparation {

	private static final Map<String, GamePreparation> byPlayer = new HashMap<>();

	private final int size;
	private final int slot;
	private final ArrayList<Person> players;
	private final MapVoting mapVoting;

	public GamePreparation(int size, int slot) {
		this.size = size;
		this.slot = slot;
		players = new ArrayList<>();
		NewGame.update(slot, this);
		mapVoting = new MapVoting(size);
	}

	public int getSize() {
		return size;
	}

	public void add(Person p) {
		GamePreparation oldGame = byPlayer.get(p.getName());
		if (oldGame != null) oldGame.remove(p);
		players.add(p);
		byPlayer.put(p.getName(), this);
		TextComponent text = U.constructComponent("§a+ §e" + players.size() + "§f/§e20§7: ", p.getDisplayName(), "§f вступил игру.");
		for (Person person : players) U.msg(person.getHandle(), text);
	}

	public void start() {
		new Cooldown("bwp" + slot, 5, players, () -> {
			List<VotingGUI.Entry> votingResult = mapVoting.result();
			pro.delfik.bedwars.game.Map map = pro.delfik.bedwars.game.Map.get(Converter.random(votingResult).key);
			for (Person person : players) person.sendMessage("§aПо результатам голосования выбрана карта §f§l" + map.getName());
			Game game = new Game(map, shuffleEntire(map));
		});
	}

	public int playersPerTeam() {
		return (players.size() - 1) / size + 1;
	}

	private Colors<Collection<Person>> shuffleEntire(pro.delfik.bedwars.game.Map map) {
		Collections.shuffle(players);
		Colors<Collection<Person>> colors = new Colors<>();
		for (Color color : map.getRegisteredColors()) colors.put(color, new FixedArrayList<>(playersPerTeam()));
		CyclicIterator<Color> iterator = new CyclicIterator<>(map.getRegisteredColors());
		for (Person p : players) colors.get(iterator.next()).add(p);
		return colors;
	}

	public void remove(Person p) {
		TextComponent text = U.constructComponent("§c- §e" + (players.size() - 1) + "§f/§e20§7: ", p.getDisplayName(), "§f покинул игру.");
		for (Person person : players) U.msg(person.getHandle(), text);
		players.remove(p);
		byPlayer.remove(p.getName());
	}

	public int getSlot() {
		return slot;
	}

}
