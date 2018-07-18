package pro.delfik.bedwars.service;

import lib.Converter;
import lib.Generate;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pro.delfik.bedwars.Defaults;
import pro.delfik.bedwars.game.trading.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SettingUp {
	
	public static boolean enabled = false;
	
	public static final HashMap<Defaults.Teams, Set<LocInfo>> locs = new HashMap<>();
	public static final Set<LocInfo> global = new HashSet<>();
	
	public static void addSpawner(Defaults.Teams t, Resource r, Location loc) {
		add(t, Property.valueOf(r.name()), loc);
	}
	
	private static void add(Defaults.Teams t, Property property, Location loc) {
		if (t == null) global.add(new LocInfo(property, null, loc));
		else locs.get(t).add(new LocInfo(property, t, loc));
	}
	
	public static ItemStack generateTeamItem(Defaults.Teams t) {
		Set<LocInfo> ls = locs.get(t);
		if (ls == null) return null;
		int respawn = 0, iron = 0, bronze = 0, tradeomat = 0;
		for (LocInfo i : ls) {
			switch (i.key) {
				case IRON: iron++; break;
				case BRONZE: bronze++; break;
				case RESPAWN: respawn++; break;
				case TRADEOMAT: tradeomat++; break;
			}
		}
		return Generate.itemstack(Material.WOOL, 1, t.getWool(), t.getColor() + t.getName() + " команда",
				"§f" + respawn + " " + Property.RESPAWN.get(respawn),
				"§f" + tradeomat + " " + Property.TRADEOMAT.get(tradeomat),
				"§f" + bronze + " " + Property.BRONZE.get(bronze),
				"§f" + iron + " " + Property.IRON.get(iron)
		);
	}
	
	
	public static class LocInfo {
		public final Property key;
		public Defaults.Teams team;
		public Location location;
		
		public LocInfo(Property key, Defaults.Teams team, Location location) {
			this.key = key;
			this.team = team;
			this.location = location;
		}
	}
	
	public enum Property {
		IRON("генератор железа", "генератора железа", "генераторов железа", 4, "§f§lГенератор железа", Material.IRON_INGOT),
		BRONZE("генератор бронзы", "генератора бронзы", "генераторов бронзы", 3, "§f§lГенератор бронзы", Material.CLAY_BRICK),
		RESPAWN("точка возрождения", "точки возрождения", "точек возрождения", 1, "§f§lТочка возрождения", Material.NETHER_STAR),
		TRADEOMAT("торговый автомат", "торговых автомата", "торговых автоматов", 2, "§f§lТорговый автомат", Material.COMMAND);
		
		public final String one;
		public final String two;
		public final String more;
		public final String name;
		public final int row;
		public final Material material;
		
		Property(String one, String two, String more, int row, String name, Material material) {
			this.one = one;
			this.two = two;
			this.more = more;
			this.row = row;
			this.name = name;
			this.material = material;
		}
		
		public String get(int i) {
			return Converter.plural(i, one, two, more);
		}
	}
	
}
