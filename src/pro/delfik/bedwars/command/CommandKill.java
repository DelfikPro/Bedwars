package pro.delfik.bedwars.command;

import implario.util.Rank;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pro.delfik.bedwars.GeneralListener;
import pro.delfik.bedwars.game.Game;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.util.U;

public class CommandKill extends LmaoCommand {

	public CommandKill() {
		super("kill", Rank.PLAYER, "Харакири!");
	}

	public void run(CommandSender s, String l, String[] a) {
		Player p = ((Player) s);
		String killer = GeneralListener.lastDamage.list.remove(p);
		if (killer != null) {
			Player k = Bukkit.getPlayer(killer);
			if (k == null) return;
			k.giveExpLevels(1);
			Game game = Game.get(p);
			if (game == null) return;
			TextComponent tc = U.constructComponent(s, " был склонён к суициду игроком ", k, ".");
			for (Player player : game.getWorld().getPlayers()) player.spigot().sendMessage(tc);
		}
		p.setHealth(0);
		s.sendMessage("§6Вы самоубились.");
	}
}
