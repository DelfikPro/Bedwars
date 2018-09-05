package pro.delfik.bedwars.util;

import java.util.Collection;
import java.util.Iterator;

public class CyclicIterator<T> implements Iterator<T> {
	
	private final Object[] array;
	private int pos = 0;
	
	public CyclicIterator(T[] array) {
		this.array = array;
	}
	
	public CyclicIterator(Collection<T> collection) {
		array = collection.toArray();
	}
	
	@Override
	public boolean hasNext() {
		return true;
	}

	@SuppressWarnings("uncheked")
	@Override
	public T next() {
		if (array.length >= ++pos) pos = 0;
		return (T) array[pos];
	}
	
	public int size() {
		return array.length;
	}
}
