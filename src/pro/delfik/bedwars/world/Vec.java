package pro.delfik.bedwars.world;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import static java.lang.Double.valueOf;

/**
 * Аналог Location, но без ссылки на мир.
 * Используется для конфигурации местоположений без указания мира.
 * @see Location
 * @see Vec3i
 */
public interface Vec {
	
	/**
	 * Конвертация в Bukkit-Vector.
	 * @return Вектор Bukkit с идентичными координатами.
	 */
	Vector toBukkitVector();
	
	/**
	 * Конвертация в Bukkit-Location в указанном мире.
	 * Позиция находится в пределах этого блока, полезно для указания на блок,
	 * Когда точное местоположение не имеет значения.
	 * @param world Мир, в котором будет находится местоположение.
	 * @return Bukkit-Location
	 * @see Location
	 */
	Location toBlock(World world);
	
	/**
	 * Конвертация в Bukkit-Location с точными координатами.
	 * Если точные координаты не указаны, возвращается позиция в центре блока.
	 * Полезно при телепортации игроков.
	 * @param world Мир, в котором будет находится местоположение.
	 * @return Bukkit-Location с точными координатами.
	 * @see Location
	 */
	Location toLocation(World world);
	
	/**
	 * Конвертация в округлённый до int Vec3i.
	 * @return Vec3i с округлёнными координатами.
	 */
	Vec3i toVec3i();
	
	/**
	 * Генерация объекта Vec из строки.
	 * @param string Сериализованный Vec.
	 * @return Десериализованный Vec.
	 */
	static Vec toVec(String string) {
		String[] s = string.split(", ");
		if (s.length == 5)
			return new Vec5d(valueOf(s[0]), valueOf(s[1]), valueOf(s[2]), valueOf(s[3]), valueOf(s[4]));
		else return new Vec3i(new Integer(s[0]), new Integer(s[1]), new Integer(s[2]));
	}
	
}
