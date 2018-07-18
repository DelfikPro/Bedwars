package pro.delfik.world;


import java.io.File;
import java.io.IOException;

import com.sk89q.worldedit.schematic.MCEditSchematicFormat;
import com.sk89q.worldedit.util.io.file.FilenameException;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.EmptyClipboardException;
import com.sk89q.worldedit.LocalPlayer;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;

public class SchematicManager {
	private static final String EXTENSION = "schematic";
	
	private final WorldEdit we;
	private final LocalSession localSession;
	private final EditSession editSession;
	private final LocalPlayer localPlayer;
	
	public SchematicManager(WorldEditPlugin wep, Player player) {
		we = wep.getWorldEdit();
		localPlayer = wep.wrapPlayer(player);
		localSession = we.getSession(localPlayer);
		editSession = localSession.createEditSession(localPlayer);
	}
	
	public SchematicManager(WorldEditPlugin wep, World world) {
		we = wep.getWorldEdit();
		localPlayer = null;
		localSession = new LocalSession(we.getConfiguration());
		editSession = new EditSession(new BukkitWorld(world), we.getConfiguration().maxChangeLimit);
	}
	public void saveTerrain(File saveFile, Location l1, Location l2) throws DataException, IOException, FilenameException {
		Vector min = getMin(l1, l2);
		Vector max = getMax(l1, l2);
		
		saveFile = we.getSafeSaveFile(localPlayer,
				saveFile.getParentFile(), saveFile.getName(),
				EXTENSION, EXTENSION);
		
		editSession.enableQueue();
		CuboidClipboard clipboard = new CuboidClipboard(max.subtract(min).add(new Vector(1, 1, 1)), min);
		clipboard.copy(editSession);
		SchematicFormat.MCEDIT.save(clipboard, saveFile);
		editSession.flushQueue();
	}
	
	public void loadSchematic(File saveFile, Location loc) throws FilenameException, DataException, IOException, MaxChangedBlocksException, EmptyClipboardException {
		saveFile = we.getSafeSaveFile(localPlayer,
				saveFile.getParentFile(), saveFile.getName(),
				EXTENSION, EXTENSION);
		
		editSession.enableQueue();
		MCEditSchematicFormat.getFormat(saveFile).load(saveFile).paste(editSession, new Vector(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()), true);
//		localSession.setClipboard(SchematicFormat.MCEDIT.load(saveFile));
//		localSession.getClipboard().place(editSession, getPastePosition(loc), false);
		editSession.flushQueue();
		we.flushBlockBag(localPlayer, editSession);
	}
	
	public void loadSchematic(File saveFile) throws FilenameException, DataException, IOException, MaxChangedBlocksException, EmptyClipboardException {
		loadSchematic(saveFile, null);
	}
	
	private Vector getPastePosition(Location loc) throws EmptyClipboardException {
		if (loc == null)
			return localSession.getClipboard().getClipboard().getOrigin();
		else
			return new Vector(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}
	
	private Vector getMin(Location l1, Location l2) {
		return new Vector(
								 Math.min(l1.getBlockX(), l2.getBlockX()),
								 Math.min(l1.getBlockY(), l2.getBlockY()),
								 Math.min(l1.getBlockZ(), l2.getBlockZ())
		);
	}
	
	private Vector getMax(Location l1, Location l2) {
		return new Vector(
								 Math.max(l1.getBlockX(), l2.getBlockX()),
								 Math.max(l1.getBlockY(), l2.getBlockY()),
								 Math.max(l1.getBlockZ(), l2.getBlockZ())
		);
	}
}
