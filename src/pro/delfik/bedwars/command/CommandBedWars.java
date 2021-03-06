package pro.delfik.bedwars.command;

import implario.util.Converter;
import implario.util.Rank;
import net.minecraft.server.v1_8_R1.ChunkCoordIntPair;
import net.minecraft.server.v1_8_R1.EntityPlayer;
import net.minecraft.server.v1_8_R1.World;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R1.CraftChunk;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import pro.delfik.bedwars.GameListener;
import pro.delfik.bedwars.game.BWTeam;
import pro.delfik.bedwars.game.Color;
import pro.delfik.bedwars.game.Game;
import pro.delfik.bedwars.game.Resource;
import pro.delfik.bedwars.preparation.PublicGame;
import pro.delfik.bedwars.purchase.Purchase;
import pro.delfik.bedwars.util.Colors;
import pro.delfik.bedwars.util.CyclicIterator;
import pro.delfik.lmao.command.handle.CustomException;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.command.handle.NotEnoughArgumentsException;
import pro.delfik.lmao.outward.item.ItemBuilder;
import pro.delfik.lmao.user.Person;
import pro.delfik.lmao.util.U;
import pro.delfik.lmao.util.Vec;
import pro.delfik.lmao.util.Vec3i;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class CommandBedWars extends LmaoCommand {

	private static final Map<String, BiFunction<CommandSender, String[], String>> functions = new HashMap<>();

	static {
		functions.put("regen", CommandBedWars::regen);
		functions.put("pu", CommandBedWars::purchase);
		functions.put("r", CommandBedWars::resources);
		functions.put("dbgclicks", CommandBedWars::dbgclicks);
		functions.put("ab", CommandBedWars::actionbar);
		functions.put("ezic", CommandBedWars::ezic);
		functions.put("testgame", CommandBedWars::testgame);
		functions.put("tp", CommandBedWars::tp);
		functions.put("worldlist", CommandBedWars::worldlist);
		functions.put("near", CommandBedWars::near);
		functions.put("info", CommandBedWars::alive);
		functions.put("maps", CommandBedWars::maps);
		functions.put("win", CommandBedWars::win);
		functions.put("gc", CommandBedWars::gc);
		functions.put("preps", CommandBedWars::preps);
	}

	private static String preps(CommandSender commandSender, String[] strings) {
		for (PublicGame p : PublicGame.getAll())
			commandSender.sendMessage("§aPrep" + p.getSlot() + ": §e" + p.getSize() + "§a teams, §e" + p.getPlayers() + "§a players");
		return "§a§lВсего: §e" + PublicGame.getAll().size();
	}

	private static String gc(CommandSender commandSender, String[] strings) {
		requireRank(commandSender, Rank.DEV);
		Bukkit.broadcastMessage("§3§lПАМЯТЬ СЕРВЕРА ОЧИЩАЕТСЯ. БУДЬТЕ ТЕРПЕЛИВЫ.");
		long before = Runtime.getRuntime().freeMemory();
		long start = System.currentTimeMillis();
		System.gc();
		long end = System.currentTimeMillis();
		long after = Runtime.getRuntime().freeMemory();
		commandSender.sendMessage("§aОсвобождено " + (after - before) / (1024 * 1024) + "§aМБ памяти.");
		return "§aПотрачено §f" + (end - start) / 1000D + "§a сек.";
	}

	private static String win(CommandSender sender, String[] args) {
		requireRank(sender, Rank.KURATOR);
		Player p = ((Player) sender);
		Game g = Game.get(p);
		if (g == null) return "§cВы не в игре.";
		g.end(g.getTeam(p.getName()));
		return "§aПринудительная победа успешно произведена.";
	}

	private static String maps(CommandSender commandSender, String[] strings) {
		for (Map.Entry<Integer, List<pro.delfik.bedwars.game.Map>> e : pro.delfik.bedwars.game.Map.FORMAT.entrySet()) {
			commandSender.sendMessage("§aMaps for §f" + e.getKey() + "§a teams:");
			for (pro.delfik.bedwars.game.Map map : e.getValue())
				commandSender.sendMessage("§b - " + map);
		}
		return "§aСписок карт приведён.";
	}

	private static String alive(CommandSender commandSender, String[] strings) {
		Player p = (Player) commandSender;
		for (Game g : Game.running()) if (g != null) p.sendMessage("§aGame-" + g.getId() + "§a: §f" + g.getWorld().getName());
		Game g = Game.get(p);
		if (g == null) throw new CustomException("§cВы не в игре.");
		p.sendMessage("§aИгра §e" + g.getId() + "§a, Мир - §e" + g.getWorld().getName());
		for (BWTeam t : g.getTeams().values()) {
			p.sendMessage(t.getColor() + "§a: " + Converter.merge(t.getPlayers(), Person::getDisplayName, "§a, §7"));
			p.sendMessage("§aКровать в наличии: §e" + t.hasBed() + "§a, поражение: §e" + t.defeated());
		}
		return null;
	}

	private static String near(CommandSender commandSender, String[] strings) {
		Player p = (Player) commandSender;
		Vec3i vec = Vec3i.fromLocation(p.getLocation());
		int smallest = Integer.MAX_VALUE;
		Color nearest = null;
		pro.delfik.bedwars.game.Map map = Game.get(p).getMap();
		for (Map.Entry<Color, CyclicIterator<Vec>> e : map.getSpawns().entrySet()) {
			Vec l = e.getValue().current();
			int s = l.distanceIntSquared(vec);
			p.sendMessage("§aРасстояние до команды §e" + e.getKey() + "§a - " + Math.sqrt(s) + " м.");
			if (s <= smallest) {
				smallest = s;
				nearest = e.getKey();
			}
		}
		return "§aБлижайшая команда - " + nearest;
	}

	private static String worldlist(CommandSender commandSender, String[] strings) {
		for (org.bukkit.World world : Bukkit.getWorlds()) commandSender.sendMessage("§a- " + world.getName());
		return "§aСписок миров приведён.";
	}

	private static String tp(CommandSender commandSender, String[] strings) {
		requireRank(commandSender, Rank.ADMIN);
		requireArgs(strings, 1, "[Мир]");
		((Player) commandSender).teleport(pro.delfik.bedwars.game.Map.get("actuon").getCenter().toLocation(Bukkit.getWorld(strings[0])));
		return "§aВы были телепортированы в мир §e" + ((Player) commandSender).getWorld().getName();
	}

	private static String testgame(CommandSender commandSender, String[] args) {
		requireRank(commandSender, Rank.ULTIBUILDER);
		requireArgs(args, 2, "[Игрок] [Карта]");
		Player p = requirePlayer(args[0]);
		pro.delfik.bedwars.game.Map map = pro.delfik.bedwars.game.Map.get(args[1]);
		Colors<Collection<Person>> colors = new Colors<>();
		colors.put(Color.RED, Converter.asList(Person.get(p)));
		colors.put(Color.BLUE, Converter.asList(Person.get(commandSender)));
		CyclicIterator<Color> cc = new CyclicIterator<>(Color.values());
		for (int i = 2; i < args.length; i++) colors.get(cc.next()).add(requirePerson(args[i]));
		Game game = new Game(map, colors);
		return "§aВсё супер лолшто";
	}

	private static String ezic(CommandSender commandSender, String[] strings) {
		Person p = Person.get(commandSender);
		((CraftPlayer) p.getHandle()).getHandle().getDataWatcher().watch(9, (byte) 127);
		return "§d§lТы теперь ЙОБЗЫК = МИЛЫЙ!!!";
	}

	private static String actionbar(CommandSender commandSender, String[] strings) {
		Person.get(commandSender).sendActionBar(Converter.mergeArray(strings, 0, " ").replace('&', '\u00a7'));
		return null;
	}

	private static String dbgclicks(CommandSender commandSender, String[] strings) {
		requireRank(commandSender, Rank.DEV);
		GameListener.dbgclicks = !GameListener.dbgclicks;
		return "§aClick Debug Toggled.";
	}

	private static String resources(CommandSender commandSender, String[] strings) {
		requireRank(commandSender, Rank.ADMIN);
		Player p = ((Player) commandSender);
		for (Resource r : Resource.values()) p.getInventory().addItem(ItemBuilder.setAmount(r.getItem(), 64));
		return "§aРесурсы выданы.";
	}

	private static String purchase(CommandSender sender, String[] args) {
		requireRank(sender, Rank.KURATOR);
		Player p = ((Player) sender);
		Purchase.open(p);
		return "§aGUI закупа открыт.";
	}

	private static String regen(CommandSender sender, String[] strings) {
		requireRank(sender, Rank.DEV);
		Chunk loc = ((Player) sender).getLocation().getChunk();
		loc.getWorld().regenerateChunk(loc.getX(), loc.getZ());
		int diffX, diffZ;
		int viewDistance = Bukkit.getServer().getViewDistance() << 4;
		World mcWorld = ((CraftChunk) loc).getHandle().world;
		for (EntityPlayer ep : (List<EntityPlayer>) mcWorld.players) {
			diffX = (int) Math.abs(ep.locX - (loc.getX() << 4));
			diffZ = (int) Math.abs(ep.locZ - (loc.getZ() << 4));
			if (diffX <= viewDistance && diffZ <= viewDistance)
				ep.chunkCoordIntPairQueue.add(new ChunkCoordIntPair(loc.getX(), loc.getZ()));
		}
		return "§aЧанк успешно регенерирован.";
	}

	public CommandBedWars() {
		super("bedwars", Rank.TESTER, "Управление игрой BedWars.");
	}

	@Override
	protected void run(CommandSender sender, String cmd, String args[]) {
		requireRank(sender, Rank.ULTIBUILDER);
		if (args.length == 0)
			throw new CustomException("§c/bedwars [§f" + Converter.merge(functions.keySet(), s -> s, "§c, §f") + "§c]");
		String[] a = new String[args.length - 1];
		System.arraycopy(args, 1, a, 0, a.length);
		BiFunction<CommandSender, String[], String> function = functions.get(args[0].toLowerCase());
		if (function == null) throw new CustomException("§cПодкомана §f/aurum " + args[0] + "§c не найдена.");
		else try {
			String os = function.apply(sender, a);
			if (os != null && os.length() != 0) U.msg(sender, os);
		} catch (NotEnoughArgumentsException e) {
			throw new NotEnoughArgumentsException(args[0] + " " + e.getMessage());
		}
	}
}
