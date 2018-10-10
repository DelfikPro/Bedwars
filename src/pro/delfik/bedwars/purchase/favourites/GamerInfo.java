package pro.delfik.bedwars.purchase.favourites;

import implario.util.ByteUnzip;
import implario.util.ByteZip;
import implario.util.Byteable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pro.delfik.bedwars.purchase.Deal;
import pro.delfik.bedwars.purchase.Deal.Type;
import pro.delfik.bedwars.util.EnumReference;

import java.util.HashMap;

public class GamerInfo implements Byteable {
	public static final HashMap<Player, GamerInfo> ALL = new HashMap<>();

	private final String name;
	private final Deal[] fDeals;
	private final EnumReference<Type, Integer> defaultSlots = new EnumReference<Type, Integer>(Type.class, Type::getDefaultSlot) {};

	public GamerInfo(ByteUnzip unzip) {
		name = unzip.getString();
		fDeals = new Deal[9];
		for (int i = 0; i < 9; i++) fDeals[i] = Deal.byHash.get(unzip.getInt());
		byte[] slots = unzip.getBytes();
		for (int i = 0; i < slots.length; i++) if (slots[i] != -1) defaultSlots.put(Type.values()[i], (int) slots[i]);
		ALL.put(Bukkit.getPlayer(name), this);
	}

	public GamerInfo(Player p, int[] favouriteDeals, byte[] slots) {
		this.name = p.getName();
		this.fDeals = new Deal[9];
		for (int i = 0; i < 9; i++) fDeals[i] = Deal.byHash.get(favouriteDeals[i]);
		for (int i = 0; i < slots.length; i++) defaultSlots.put(Type.values()[i], (int) slots[i]);
		ALL.put(p, this);
	}

	@Override
	public ByteZip toByteZip() {
		ByteZip zip = new ByteZip().add(name.toLowerCase());
		for (Deal fDeal : fDeals) zip.add(fDeal == null ? 0 : fDeal.hashCode());
		byte[] b = new byte[12];
		for (int i = 0; i < Type.values().length; i++) {
			Type t = Type.values()[i];
			b[i] = defaultSlots.getDefault(t).byteValue();
		}
		return zip.add(b);
	}

	public static GamerInfo getDefault(Player p) {
		return new GamerInfo(p, new int[9], new byte[0]);
	}

	public int getSlotFor(Type type) {
		return defaultSlots.getDefault(type);
	}

	public static GamerInfo remove(Player player) {
		return ALL.remove(player);
	}

	public Deal[] getfDeals() {
		return fDeals;
	}

	public String getName() {
		return name;
	}

	public EnumReference<Type, Integer> getDefaultSlots() {
		return defaultSlots;
	}
}
