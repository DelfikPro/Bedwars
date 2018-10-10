package pro.delfik.bedwars.preparation;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pro.delfik.lmao.outward.item.ItemBuilder;

import static implario.util.Converter.plural;

public enum GameFormat {
	DUEL(2, Material.GOLD_BLOCK, "§6§lДуэль"),
	FIGHT(4, Material.EMERALD_BLOCK, "§a§lСхватка"),
	GRINDER(8, Material.DIAMOND_BLOCK, "§b§lМясорубка");

	private final Material icon;
	private final String title, description[];
	private final int teams;

	GameFormat(int teams, Material icon, String title, String... description) {
		this.teams = teams;
		this.icon = icon;
		this.title = title;
		this.description = description;
	}

	public int getTeams() {
		return teams;
	}

	public Material getIcon() {
		return icon;
	}

	public String getTitle() {
		return title;
	}

	public String[] getDescription() {
		return description;
	}

	public static GameFormat get(int teams) {
		switch (teams) {
			case 2: return DUEL;
			case 4: return FIGHT;
			case 8: return GRINDER;
			default: return null;
		}
	}

	public ItemStack getDisplay() {
		return ItemBuilder.create(icon, "§f>> " + title + "§f <<", "§a" + teams + " команд" + plural(teams, "а", "ы", ""));
	}
}
