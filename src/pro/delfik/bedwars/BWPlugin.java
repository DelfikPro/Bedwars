package pro.delfik.bedwars;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import pro.delfik.bedwars.command.CommandBedWars;
import pro.delfik.bedwars.purchase.Purchase;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.util.Registrar;

/**
 * Главный класс плагина, наследующий JavaPlugin.
 * Здесь регистрируются команды и слушатели событий.
 * @version 4.0
 */
public class BWPlugin extends JavaPlugin {
	
	public static BWPlugin instance;
	
	private final LmaoCommand[] COMMANDS =
			{new CommandBedWars()};

	private final Listener[] LISTENERS =
			{new Purchase(), new GeneralListener(), new GameListener()};
	
	
	@Override
	public void onLoad() {
		instance = this;
	}
	
	@Override
	public void onEnable() {
		Registrar registrar = new Registrar(this);
		for (LmaoCommand cmd : COMMANDS) registrar.regCommand(cmd);
		for (Listener listener : LISTENERS) registrar.regEvent(listener);

		Bukkit.getPluginCommand("kill").setExecutor((s, c, l, a) -> {
			((Player) s).setHealth(0);
			s.sendMessage("§6Вы самоубились.");
			return true;
		});

		ConfigReader.initMaps();
	}
}
