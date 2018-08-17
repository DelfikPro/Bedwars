package pro.delfik.bedwars.command;

import org.bukkit.command.CommandSender;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.util.Rank;

public class CommandBedWars extends LmaoCommand {
	
	public CommandBedWars() {
		super("bedwars", Rank.KURATOR, "Управление игрой BedWars.");
	}
	
	@Override
	protected void run(CommandSender sender, String cmd, String[] args) {
	
	}
}
