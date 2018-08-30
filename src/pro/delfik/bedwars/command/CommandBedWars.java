package pro.delfik.bedwars.command;

import net.minecraft.server.v1_8_R1.ChunkCoordIntPair;
import net.minecraft.server.v1_8_R1.EntityPlayer;
import net.minecraft.server.v1_8_R1.World;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R1.CraftChunk;
import org.bukkit.entity.Player;
import pro.delfik.bedwars.preparation.GamePreparation;
import pro.delfik.bedwars.preparation.GameSize;
import pro.delfik.lmao.command.handle.CustomException;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.command.handle.NotEnoughArgumentsException;
import pro.delfik.lmao.core.Person;
import pro.delfik.lmao.util.U;
import implario.util.Converter;
import implario.util.Rank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class CommandBedWars extends LmaoCommand {

	private static final Map<String, BiFunction<CommandSender, String[], String>> functions = new HashMap<>();

	static {
		functions.put("create", CommandBedWars::create);
		functions.put("regen", CommandBedWars::regen);
	}

	private static String regen(CommandSender sender, String[] strings) {
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

	private static String create(CommandSender sender, String[] args) {
		requireArgs(args, 1, "[Формат]");
		GamePreparation preparation = new GamePreparation(GameSize.get(args[0]), 0);
		preparation.add(Person.get(sender));
		preparation.start();
		return "§aВсё супер. Странно.";
	}

	public CommandBedWars() {
		super("bedwars", Rank.KURATOR, "Управление игрой BedWars.");
	}

	@Override
	protected void run(CommandSender sender, String cmd, String args[]) {
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
