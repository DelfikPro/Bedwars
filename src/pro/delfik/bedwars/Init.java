package pro.delfik.bedwars;

import org.bukkit.event.Listener;
import pro.delfik.bedwars.command.CommandBedWars;
import pro.delfik.bedwars.game.environment.ResourceSpawner;
import pro.delfik.bedwars.game.trading.TradeHandler;
import pro.delfik.bedwars.game.usables.GPSTracker;
import pro.delfik.bedwars.game.usables.SavingPlatform;
import pro.delfik.bedwars.game.usables.ThorBone;
import pro.delfik.lmao.command.handle.LmaoCommand;

public class Init {
	
	public static void commands() {
		for (LmaoCommand cmd : new LmaoCommand[] {
				new CommandBedWars()
		}) BWPlugin.r.regCommand(cmd);
	}
	
	public static void spawners() {
		ResourceSpawner.scheduleBronze();
		ResourceSpawner.scheduleGold();
		ResourceSpawner.scheduleIron();
	}
	
	public static void events() {
		for (Listener l : new Listener[] {
				new ThorBone(), new GPSTracker(), new SavingPlatform(), new TradeHandler()
		}) BWPlugin.r.regEvent(l);
	}
}
