package pro.delfik.bedwars.world;


import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;
import com.sk89q.worldedit.util.io.file.FilenameException;
import com.sk89q.worldedit.world.DataException;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import pro.delfik.lmao.util.Vec3i;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

/**
 * Класс для управления схематиками.
 * {@code loadSchematic(...)} асинхронно вставляет схематику в мир.
 */
public final class Schematics {
	
	private static final String EXTENSION = "schematic";
	
	private final WorldEdit we;
	private final EditSession editSession;
	
	/**
	 * @param world Мир, к которому будет привязана сессия редактирования, и в котором будут происходить все вставки.
	 */
	public Schematics(World world) {
		we = ((WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit")).getWorldEdit();
		editSession = new EditSession(new BukkitWorld(world), we.getConfiguration().maxChangeLimit);
	}
	
	/**
	 * @param file Название схематики, уже находящейся в папке "./plugins/WorldEdit/schematics" без расширения
	 * @param location Место, куда нужно вставить схематику
	 */
	public void loadSchematic(String file, Vec3i location) {
		try {
			loadSchematic(new File("plugins/WorldEdit/schematics/" + file + "." + EXTENSION), location);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * @param saveFile Файл схематики
	 * @param location Место, куда вставлять схематику
	 * @throws FilenameException когда никогда
	 * @throws DataException когда никогда
	 * @throws IOException когда никогда
	 * @throws MaxChangedBlocksException когда никогда
	 */
	public void loadSchematic(File saveFile, Vec3i location) throws FilenameException, DataException, IOException, MaxChangedBlocksException {
		// Преобразовываем файл джавы в "Безопасный" файл WE.
		saveFile = we.getSafeSaveFile(null, saveFile.getParentFile(), saveFile.getName(), EXTENSION, EXTENSION);

		// Непонятная магия, которую нельзя трогать.
		editSession.enableQueue();
		MCEditSchematicFormat.getFormat(saveFile).load(saveFile).paste(editSession, new Vector(location.x, location.y, location.z), true);
		editSession.flushQueue();
		we.flushBlockBag(null, editSession);
	}

	public static List<Chunk> getAllChunksBetween(World w, Vector a, Vector b) {
		Location aa = new Location(w, a.getX(), a.getY(), a.getZ());
		Location bb = new Location(w, b.getX(), b.getY(), b.getZ());
		Bukkit.broadcastMessage("§aPos1: §e" + aa.getBlockX() + "§a, §e" + aa.getBlockY() + "§a, §e" + aa.getBlockZ());
		Bukkit.broadcastMessage("§aPos2: §e" + bb.getBlockX() + "§a, §e" + bb.getBlockY() + "§a, §e" + bb.getBlockZ());
		Chunk A = aa.getChunk(), B = bb.getChunk();
		int ax = A.getX(), bx = B.getX(), az = A.getZ(), bz = B.getZ();
		List<Chunk> result = new ArrayList<>();
		for (int x = min(ax, bx); x < max(ax, bx); x++)
			for (int z = min(-az, bz); x < max(az, bz); x++)
				result.add(w.getChunkAt(x, z));
		return result;
	}
	
}

