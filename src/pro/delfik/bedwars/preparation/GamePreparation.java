package pro.delfik.bedwars.preparation;

import implario.util.Converter;
import net.md_5.bungee.api.chat.TextComponent;
import pro.delfik.bedwars.Bedwars;
import pro.delfik.bedwars.game.Color;
import pro.delfik.bedwars.game.Game;
import pro.delfik.bedwars.util.Colors;
import pro.delfik.bedwars.util.CyclicIterator;
import pro.delfik.bedwars.util.FixedArrayList;
import pro.delfik.lmao.outward.gui.VotingGUI;
import pro.delfik.lmao.user.Person;
import pro.delfik.lmao.util.Cooldown;
import pro.delfik.lmao.util.U;

import java.util.*;

public class GamePreparation {

	public static final Map<String, GamePreparation> byPlayer = new HashMap<>();
	public static final int HARDCODED_PREPARATION_LIMIT = 36;
	private static final FixedArrayList<GamePreparation> list = new FixedArrayList<>(HARDCODED_PREPARATION_LIMIT);

	private final GameFormat size;
	private final int slot;
	private final ArrayList<Person> players;
	private final MapVoting mapVoting;
	private String owner;
	private boolean starting = false;
	private Cooldown cooldown = null;

	public GamePreparation(GameFormat format, int slot) {
		this.size = format;
		this.slot = slot;
		players = new ArrayList<>();
		NewGame.update(slot, this);
		mapVoting = new MapVoting(size);
		list.set(slot, this);
	}

	public static GamePreparation get(int slot) {
		if (slot >= list.size() || slot < 0) return null;
		return list.get(slot);
	}

	public GameFormat getSize() {
		return size;
	}


	public void start() {
		starting = true;
		cooldown = new Cooldown("bwp" + slot, 10, players, () -> {
			List<VotingGUI.Entry> votingResult = mapVoting.result();
			pro.delfik.bedwars.game.Map map = pro.delfik.bedwars.game.Map.get(Converter.random(votingResult).key);
			for (Person person : players) {
				person.sendMessage("§aПо результатам голосования выбрана карта §f§l" + map.getName());
				person.sendSubtitle("Выбрана карта §e" + map.getName());
			}
			Game game = new Game(map, shuffleEntire(map));

			list.remove(slot);

			List<String> toRemove = new ArrayList<>();
			for (Map.Entry<String, GamePreparation> e : byPlayer.entrySet()) if (e.getValue() == this) toRemove.add(e.getKey());
			for (String key : toRemove) byPlayer.remove(key);

			NewGame.update(slot, null);
		});
	}

	public int playersPerTeam() {
		return (players.size() - 1) / size.getTeams() + 1;
	}

	private Colors<Collection<Person>> shuffleEntire(pro.delfik.bedwars.game.Map map) {
		Collections.shuffle(players);
		Colors<Collection<Person>> colors = new Colors<>();
		for (Color color : map.getRegisteredColors()) colors.put(color, new ArrayList<>());
		CyclicIterator<Color> iterator = new CyclicIterator<>(map.getRegisteredColors());
		for (Person p : players) colors.getDefault(iterator.next()).add(p);
		return colors;
	}

	public void add(Person p) {
		GamePreparation oldGame = byPlayer.get(p.getName());
		if (oldGame != null) oldGame.remove(p);
		players.add(p);
		byPlayer.put(p.getName(), this);
		TextComponent text = U.constructComponent("§a+ §e" + players.size() + "§f/§e20§7: ", p, "§f вступил игру.");
		for (Person person : players) U.msg(person.getHandle(), text);
		NewGame.update(slot, this);
		if (players.size() % size.getTeams() == 0 && !starting) start();
		Bedwars.toPreparation(p);
	}

	public void remove(Person p) {
		TextComponent text = U.constructComponent("§c- §e" + (players.size() - 1) + "§f/§e20§7: ", p.getDisplayName(), "§f покинул игру.");
		for (Person person : players) U.msg(person.getHandle(), text);
		players.remove(p);
		for (int i = 1; i < 3; i++)	p.getHandle().getInventory().setItem(i, null);
		byPlayer.remove(p.getName());
		if ((players.size() < size.getTeams() || players.size() % size.getTeams() != 0) && starting) cancel();
		if (players.isEmpty()) {
			NewGame.update(slot, null);
			list.remove(slot);
		} else NewGame.update(slot, this);
	}

	public void cancel() {
		if (cooldown != null) cooldown.cancel();
	}

	public int getSlot() {
		return slot;
	}

	public void setOwner(String name) {
		this.owner = name;
	}

	public int getPlayers() {
		return players.size();
	}

	public MapVoting getMapVoting() {
		return mapVoting;
	}
}
