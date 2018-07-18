package pro.delfik.bedwars.service;

import lib.Generate;
import lib.gui.GUI;
import lib.gui.GUILoading;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pro.delfik.bedwars.Defaults;

import java.util.HashSet;

public class SettingGUI {
	
	public static String pr = "§9{§dBW§9} §a";
	public static GUI gui;
	public static GUI resources;
	public static GUI teams;
	
	public static void init() {
		gui = new GUI(Bukkit.createInventory(null, 9, "Настройка карты"));
		resources = new GUI(Bukkit.createInventory(null, 9, "Настройка генераторов ресурсов"));
		teams = new GUI(Bukkit.createInventory(null, 18, "Настройка команд"));
		gui.put(0, Generate.itemstack(Material.CLAY_BRICK, 1, 0, "§a§lГенераторы ресурсов"), p -> p.openInventory(resources.getInventory()));
		gui.put(1, Generate.itemstack(Material.WOOL, 1, 14, "§a§lНастройка команд"), p -> p.openInventory(teams.getInventory()));
		
		teams.put(4, Generate.charge(Color.LIME, "§f[§a+§f] §aДобавить команду"), p -> addingTeam(p));
		
	}
	
	private static void addingTeam(Player p) {
		p.openInventory(GUILoading.i());
		GUI g = new GUI(Bukkit.createInventory(null, 9, "Выберите расцветку"));
		for (Defaults.Teams t : Defaults.Teams.values()) {
			if (SettingUp.locs.containsKey(t)) continue;
			g.put(g.getInventory().firstEmpty(), Generate.itemstack(Material.WOOL, 1, t.getWool(), t.getColor() + t.getName() + " §fрасцветка"),
					player -> {
						player.closeInventory();
						player.sendMessage(pr + "Команда успешно добавлена. Выбранный пресет: §e" + t.getColor() + t.getName() + " §fрасцветка.");
						addTeam(t);
					});
		}
		p.openInventory(g.getInventory());
	}
	
	public static void addTeam(Defaults.Teams t) {
		SettingUp.locs.put(t, new HashSet<>());
		teams.put(SettingUp.locs.size() + 8, Generate.itemstack(Material.WOOL, 1, t.getWool(), t.getColor() + t.getName() + " §fкоманда"), p -> editTeam(p, t));
	}
	
	private static void editTeam(Player p, Defaults.Teams t) {
		p.openInventory(GUILoading.i());
		GUI edit = new GUI(Bukkit.createInventory(null, 45, "Изменить " + t.getColor() + t.getAccusative() + " команду"));
		int respawn = 0, iron = 0, bronze = 0, tradeomat = 0;
		for (SettingUp.LocInfo i : SettingUp.locs.get(t)) {
			int slot = 0;
			switch (i.key) {
				case IRON: slot = iron++; break;
				case BRONZE: slot = bronze++; break;
				case RESPAWN: slot = respawn++; break;
				case TRADEOMAT: slot = tradeomat++; break;
			}
			edit.put(i.key.row * 9 + slot, Generate.itemstack(i.key.material, 1, 0, i.key.name,
					"§e" + i.location.getBlockX() + "§f, §e" + i.location.getBlockY() + "§f, §e" + i.location.getBlockZ()),
					pp -> editLocInfo(pp, t, i.location));
		}
		edit.put(17, Generate.charge(Color.LIME, "§f[§a+§f] §aДобавить точку возрождения"), pp -> {
			SettingUp.locs.get(t).add(new SettingUp.LocInfo(SettingUp.Property.RESPAWN, t, pp.getLocation()));
			pp.sendMessage("§aТочка возрождения успешно добавлена: §e");
			pp.closeInventory();
		});
		edit.put(26, Generate.charge(Color.LIME, "§f[§a+§f] §aДобавить торговый аппарат"), pp -> {});
		edit.put(35, Generate.charge(Color.LIME, "§f[§a+§f] §aДобавить генератор бронзы"), pp -> {});
		edit.put(44, Generate.charge(Color.LIME, "§f[§a+§f] §aДобавить генератор железа"), pp -> {});
		p.openInventory(edit.getInventory());
	}
	
	private static void editLocInfo(Player pp, Defaults.Teams t, Location location) {
	}
}
