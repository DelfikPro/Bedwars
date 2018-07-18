package pro.delfik.bedwars;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import pro.delfik.bedwars.game.trading.TradeHandler;
import pro.delfik.bedwars.service.SettingGUI;
import pro.delfik.lmao.core.Registrar;

public class BWPlugin extends JavaPlugin {
	
	protected static Registrar r;
	public static Plugin plugin;
	
	@Override
	public void onEnable() {
		plugin = this;
		r = new Registrar(this);
		Init.commands();
		Init.events();
		Init.spawners();
		SettingGUI.init();
		TradeHandler.initTrades();
	}
	
	@Override
	public void onDisable() {
	
	}
}
