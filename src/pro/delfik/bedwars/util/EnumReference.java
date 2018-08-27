package pro.delfik.bedwars.util;

import java.util.EnumMap;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Подобие EnumMap, но с некоторыми дополнительными методами.
 * Здесь существует стандартное значение, и если в мапе отсутствует ключ, вернётся дефолтное значение.
 *
 * @param <Key>
 * @param <Value>
 */
public abstract class EnumReference<Key extends Enum<Key>, Value> extends EnumMap<Key, Value> {
	
	// Генератор стандартного значения, обычно Value::new.
	private final Supplier<Value> defaultValueSupplier;
	
	protected EnumReference(Class<Key> clazz) {
		this(clazz, null);
	}
	
	/**
	 * @param clazz Класс<Key> для создания массива и проверки инстанций.
	 * @param defaultValueSupplier Генератор стандартного значения вида Value::new.
	 */
	protected EnumReference(Class<Key> clazz, Supplier<Value> defaultValueSupplier) {
		super(clazz);
		this.defaultValueSupplier = defaultValueSupplier;
	}
	
	/**
	 * Немного изменённый EnumMap#get(...), который возврашает стандартное значение, если
	 * заданый ключ не был найден.
	 * @param key Ключ, по которому нужно найти значение.
	 * @return Значение, если ключ найден, стандартное значение, если не найден, null, если стандартное неопределено.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Value get(Object key) {
		// Возвращение стандартным путём, если объект есть или нет стандартного значения.
		Value value = super.get(key);
		if (value != null || defaultValueSupplier == null) return value;
		// Добавление в мап стандартного значения (Для последующих обращений) и его возвращение.
		return computeIfAbsent((Key) key, k -> defaultValueSupplier.get());
	}
	
	/**
	 * Конвертирование всех значений в другой тип данных и последующая запись
	 * В предоставленный EnumReference.
	 * Конвертирование происходит по заданному методу (value) -> new.
	 * @param destination EnumReference, в который нужно производить запись результата.
	 * @param converter Конвертер из старого типа значений в новый.
	 * @param <New> Новый тип значений.
	 */
	public <New> void convert(EnumReference<? super Key, ? super New> destination, Function<Value, New> converter) {
		for (Entry<Key, Value> e : entrySet()) destination.put(e.getKey(), converter.apply(e.getValue()));
	}
	
	/**
	 * Конвертирование всех значений в другой тип данных и последующая запись
	 * В предоставленный EnumReference.
	 * Конвертирование происходит по заданному методу (key, value) -> new.
	 * @param destination EnumReference, в который нужно производить запись результата.
	 * @param converter Конвертер из старого типа значений в новый.
	 * @param <New> Новый тип значений.
	 */
	public <New> void convert(EnumReference<? super Key, ? super New> destination, BiFunction<Key, Value, New> converter) {
		for (Entry<Key, Value> e : entrySet()) destination.put(e.getKey(), converter.apply(e.getKey(), e.getValue()));
	}
	
	
	/**
	 * Итератор по значениям в мапе.
	 * @param consumer Функция, которую нужно применить к каждому элементу.
	 */
	public void forEach(Consumer<Value> consumer) {
		for (Value value : values()) consumer.accept(value);
	}
}
