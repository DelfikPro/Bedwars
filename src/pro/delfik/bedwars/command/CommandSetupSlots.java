package pro.delfik.bedwars.command;

import implario.util.Rank;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pro.delfik.bedwars.purchase.Deal;
import pro.delfik.bedwars.purchase.favourites.GamerInfo;
import pro.delfik.lmao.command.handle.LmaoCommand;
import pro.delfik.lmao.outward.inventory.GUI;
import pro.delfik.lmao.outward.item.I;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandSetupSlots extends LmaoCommand implements Listener {

	public static CommandSetupSlots instance;

	public CommandSetupSlots() {
		super("setup", Rank.PLAYER, "Настроить стандартные слоты для предметов");
		instance = this;
	}

	private static final HashMap<Player, ItemStack[]> savedInv = new HashMap<>();

	@Override
	protected void run(CommandSender sender, String command, String[] args) {
		Player player = (Player) sender;
		ItemStack[] current = player.getInventory().getContents();
		savedInv.put(player, current);
		GamerInfo g = GamerInfo.ALL.get(player);
		Inventory inv = GUI.create(18, "§1§lНастройка слотов");
		player.getInventory().clear();
		List<Deal.Type> list = new ArrayList<>();
		for (Map.Entry<Deal.Type, Integer> e : g.getDefaultSlots().entrySet()) {
			if (e.getValue() == -1) continue;
			player.getInventory().setItem(e.getValue(), e.getKey().getItem());
			list.add(e.getKey());
		}
		for (Deal.Type type : Deal.Type.values()) {
			if (list.contains(type)) continue;
			inv.addItem(type.getItem());
		}
		player.openInventory(inv);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getInventory().getTitle() == null || !e.getInventory().getTitle().startsWith("§1")) return;
		InventoryAction a = e.getAction();
		e.setCancelled(a.name().startsWith("DROP_"));
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		if (e.getInventory().getTitle() == null || !e.getInventory().getTitle().startsWith("§1")) return;
		Player player = (Player) e.getPlayer();
		GamerInfo g = GamerInfo.ALL.get(player);
		ItemStack contents[] = e.getPlayer().getInventory().getContents();
		g.getDefaultSlots().clear();
		for (int i = 0; i < contents.length; i++) {
			ItemStack item = contents[i];
			if (item == null || item.getType() == Material.AIR) continue;
			Deal.Type type = Deal.Type.byItem(item);
			if (type == null) continue;
			g.getDefaultSlots().put(type, i);
		}
		for (Deal.Type t : Deal.Type.values()) if (!g.getDefaultSlots().containsKey(t)) g.getDefaultSlots().put(t, -1);
		player.sendMessage("§a§lНастройки слотов сохранены.");
		player.getInventory().clear();
		I.delay(() -> {
			player.getInventory().setContents(savedInv.remove(player));
			player.updateInventory();
		}, 2);
	}
}
