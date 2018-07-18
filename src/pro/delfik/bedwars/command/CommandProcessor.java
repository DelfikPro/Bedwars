package pro.delfik.bedwars.command;

import org.bukkit.command.CommandSender;
import pro.delfik.lmao.command.handle.NotEnoughArgumentsException;

@FunctionalInterface
public interface CommandProcessor {
	String run(CommandSender sender, String[] args) throws NotEnoughArgumentsException;
}
