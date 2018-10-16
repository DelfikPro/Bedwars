package pro.delfik.bedwars;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import pro.delfik.bedwars.command.CommandBedWars;
import pro.delfik.bedwars.command.CommandKill;
import pro.delfik.bedwars.command.CommandSetupSlots;
import pro.delfik.bedwars.game.Game;
import pro.delfik.bedwars.game.stuff.ThorBone;
import pro.delfik.bedwars.purchase.Purchase;
import pro.delfik.bedwars.purchase.favourites.GamerInfo;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.misc.Garpoon;
import pro.delfik.lmao.util.Registrar;

/**
 * Главный класс плагина, наследующий JavaPlugin.
 * Здесь регистрируются команды и слушатели событий.
 * @version 4.0
 */
public class BWPlugin extends JavaPlugin {
	
	public static BWPlugin instance;
	
	private final LmaoCommand[] COMMANDS =
			{new CommandBedWars(), new CommandKill(), new CommandSetupSlots()};

	private final Listener[] LISTENERS =
			{new Purchase(), new GeneralListener(), new GameListener(), CommandSetupSlots.instance, new ThorBone()};
	
	
	@Override
	public void onLoad() {
		instance = this;
	}
	
	@Override
	public void onEnable() {
		Garpoon.disable();
		Registrar registrar = new Registrar(this);
		for (LmaoCommand cmd : COMMANDS) registrar.regCommand(cmd);
		for (Listener listener : LISTENERS) registrar.regEvent(listener);

		ConfigReader.initMaps();
	}

	@Override
	public void onDisable() {
		for (Game game : Game.running()) if (game != null) game.stop();
		for (Player p : Bukkit.getOnlinePlayers()) GamerInfo.saveAndRemove(p);
	}
}
