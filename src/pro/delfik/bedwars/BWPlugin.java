package pro.delfik.bedwars;

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
	
	// Инстанция плагина.
	public static BWPlugin instance;
	
	// Массив с инстанциями команд
	private final LmaoCommand[] COMMANDS =
			{new CommandBedWars()};
	
	// Массив с инстанциями слушателей событий
	private final Listener[] LISTENERS =
			{new Purchase(), new GeneralListener(), new GameListener()};
	
	
	@Override
	public void onLoad() {
		instance = this; // Инициализация инстанции
	}
	
	@Override
	public void onEnable() {
		Registrar registrar = new Registrar(this);
		for (LmaoCommand cmd : COMMANDS) registrar.regCommand(cmd);
		for (Listener listener : LISTENERS) registrar.regEvent(listener);
		
		ConfigReader.initMaps();
	}
}
