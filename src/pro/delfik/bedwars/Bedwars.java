package pro.delfik.bedwars;

import org.bukkit.plugin.java.JavaPlugin;
import pro.delfik.lmao.core.Registrar;

public class Bedwars extends JavaPlugin{
	@Override
	public void onEnable() {
		Registrar registrar = new Registrar(this);
	}
}
