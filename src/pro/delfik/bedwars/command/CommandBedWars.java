package pro.delfik.bedwars.command;

import com.sk89q.worldedit.EmptyClipboardException;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.util.io.file.FilenameException;
import lib.Converter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pro.delfik.bedwars.game.Game;
import pro.delfik.bedwars.handle.GameLoader;
import pro.delfik.bedwars.service.SettingGUI;
import pro.delfik.lmao.command.handle.Command;
import pro.delfik.lmao.command.handle.ImplarioCommand;
import pro.delfik.lmao.command.handle.NotEnoughArgumentsException;
import pro.delfik.lmao.permissions.Rank;
import pro.delfik.world.SchematicManager;

import java.io.IOException;
import java.util.HashMap;

public class CommandBedWars extends ImplarioCommand {
	
	private static final HashMap<String, CommandProcessor> functions = new HashMap<>();
	
	static {
		functions.put("worlds", CommandBedWars::worlds);
		functions.put("setup", CommandBedWars::setup);
		functions.put("addgame", CommandBedWars::addGame);
		functions.put("schempaste", CommandBedWars::schemPaste);
	}
	
	private static String schemPaste(CommandSender sender, String[] strings) throws NotEnoughArgumentsException {
		requireArgs(strings, 1, "[Название]");
		Player p = ((Player) sender);
		SchematicManager sm = new SchematicManager((WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit"), p);
		new Thread(() -> {
			try {
				sm.loadSchematic(new java.io.File("plugins/WorldEdit/schematics/" + strings[0] + ".schematic"), p.getLocation());
			} catch (FilenameException | DataException | IOException | EmptyClipboardException | MaxChangedBlocksException e) {
				e.printStackTrace();
			}
		}).start();
		return "§aУспешно, ожидайте.";
	}
	
	private static String addGame(CommandSender sender, String[] strings) throws NotEnoughArgumentsException {
		requireArgs(strings, 2, "addgame [Карта]");
		GameLoader loader = new GameLoader(strings[1]);
		loader.preload();
		loader.loadWorld();
		Game g = loader.load();
		return "§a" + g.getWorld().getName() + " загружена!";
	}
	
	private static String setup(CommandSender sender, String[] strings) {
		((Player) sender).openInventory(SettingGUI.gui.getInventory());
		return null;
	}
	
	private static String worlds(CommandSender sender, String[] strings) {
		sender.sendMessage("§aСписок миров:");
		Bukkit.getWorlds().forEach(w -> sender.sendMessage("§e- '§f" + w.getName() + "§e'"));
		return null;
	}
	
	
	@Command(name = "bedwars", rankRequired = Rank.ADMIN, description = "Управление играми BedWars", usage = "bedwars [...]")
	public void onCommand(CommandSender sender, String arg2, String[] args) throws NotEnoughArgumentsException {
		if (args.length == 0) sender.sendMessage("§c/bedwars [§f" + Converter.merge(functions.keySet(), s -> s, "§c, §f") + "§c]");
		else {
			String[] a = new String[args.length - 1];
			System.arraycopy(args, 1, a, 0, a.length);
			CommandProcessor function = functions.get(args[0].toLowerCase());
			if (function == null) sender.sendMessage("§cПодкомана §f/bedwars " + args[0] + "§c не найдена.");
			else try {
				String result = function.run(sender, a);
				if (result != null) sender.sendMessage(result);
			} catch (NotEnoughArgumentsException e) {
				throw new NotEnoughArgumentsException(args[0] + " " + e.usage);
			}
		}
	}
}
