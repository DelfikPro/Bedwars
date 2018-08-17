package pro.delfik.bedwars.util;

import pro.delfik.bedwars.game.Color;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * EnumReference по Color.
 * @param <Value> Тип данных, хранимых в мапе
 * @see Color
 */
public class Colors<Value> extends EnumReference<Color, Value> {
	
	public Colors() {
		super(Color.class);
	}
	public Colors(Supplier<Value> defaultValue) {
		super(Color.class, defaultValue);
	}
	
	
	public <New> Colors<New> convert(Function<Value, New> converter) {
		Colors<New> colors = new Colors<>();
		super.convert(colors, converter);
		return colors;
	}
	
	public <New> Colors<New> convert(BiFunction<Color, Value, New> converter) {
		Colors<New> colors = new Colors<>();
		super.convert(colors, converter);
		return colors;
	}
	
}
