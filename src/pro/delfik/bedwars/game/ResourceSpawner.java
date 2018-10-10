package pro.delfik.bedwars.game;

import org.bukkit.Location;

/**
 * Спавнер ресурсов, регулярно дропающий тот или иной ресурс на заданное местоположение.
 * Для каждого местоположения спавна ресурсов нужна отдельная инстанция ResourceSpawner.
 * @see Resource
 */
public class ResourceSpawner {
	
	private final Resource resource;
	private final Location location;
	
	/**
	 * Создаёт инстанцию спавнера ресурсов.
	 * @param resource Тип спавнера.
	 * @param location Место спавнера.
	 */
	public ResourceSpawner(Resource resource, Location location) {
		this.resource = resource;
		this.location = location;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public Resource getResource() {
		return resource;
	}
	public void spawn() {
		location.getWorld().dropItemNaturally(location, resource.getItem());
	}

	public static ResourceSpawner[] EMPTY = new ResourceSpawner[] {new ResourceSpawner(null, null) {
		@Override
		public void spawn() {}
	}};
}
