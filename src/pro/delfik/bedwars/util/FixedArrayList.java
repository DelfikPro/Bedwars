package pro.delfik.bedwars.util;

import java.util.*;
import java.util.function.BiFunction;

/**
 * Список на основе массива с фиксированной длинной, которая задаётся в конструкторе.
 * Метод add() добавляет элемент в первое пустое место. Если пустого места не осталось, ничего не происходит.
 * Метод remove() заменять элемент (при наличии) на {@code null}, никаких сдвигов не происходит.
 *
 * @param <T> Тип данных, хранящихся в коллекции
 */
public class FixedArrayList<T> implements List<T> {
	private final Object[] array;
	
	public FixedArrayList(int size) {
		array = new Object[size];
	}
	public FixedArrayList(Object[] array) {this.array = array;}
	
	public int firstEmpty() {
		return indexOf(null);
	}
	@Override
	public int indexOf(Object o) {
		return indexOf(o, Objects::equals);
	}
	public int indexOf(Object o, BiFunction<T, T, Boolean> comparator) {
		for (int i = 0; i < array.length; i++) if (comparator.apply((T) o, (T) array[i])) return i;
		return -1;
	}
	
	@Override
	public int lastIndexOf(Object o) {
		for (int i = array.length - 1; i >= 0; i--) if (Objects.equals(o, array[i])) return i;
		return -1;
	}
	
	@Override
	public int size() {
		return array.length;
	}
	@Override
	public boolean contains(Object o) {
		return indexOf(o) != -1;
	}
	public boolean contains(Object o, BiFunction<T, T, Boolean> comparator) {
		return indexOf(o, comparator) != -1;
	}
	
	@Override
	public boolean add(T t) {
		int i = firstEmpty();
		if (i == -1) return false;
		array[i] = t;
		return true;
	}
	
	@Override
	public T get(int index) {
		return (T) array[index];
	}
	
	@Override
	public Object[] toArray() {
		return array;
	}
	
	@Override
	public String toString() {
		if (size() == 0) return "FixedArrayList{}";
		StringBuilder b = new StringBuilder(array[0].toString());
		for (int i = 1; i < array.length; i++) b.append(", ").append(array[i]);
		return "FixedArrayList{" + b + "}";
	}
	
	@Override
	public int hashCode() {
		int hash = size();
		for (Object o : array) hash += o == null ? 0 : o.hashCode();
		return hash;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof FixedArrayList && Arrays.equals(((FixedArrayList) obj).array, array);
	}
	
	@Override
	public T set(int index, T element) {
		T t = (T) array[index];
		array[index] = element;
		return t;
	}
	
	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object o : c) if (!contains(o)) return false;
		return true;
	}
	
	@Override
	public T remove(int index) {
		T t = (T) array[index];
		array[index] = null;
		return t;
	}
	
	@Override
	public boolean isEmpty() {
		for (Object o : array) if (o != null) return false;
		return true;
	}
	
	@Override
	public void clear() {
		for (int i = 0; i < array.length; i++) array[i] = null;
	}
	
	@Override
	public <T1> T1[] toArray(T1[] a) {
		System.arraycopy(array, 0, a, 0, array.length);
		return a;
	}
	
	@Override
	public boolean addAll(Collection<? extends T> c) {
		for (T t : c) add(t);
		return true;
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		for (Object t : c) remove(t);
		return true;
	}
	
	@Override
	public boolean retainAll(Collection<?> c) {
		for (int i = 0; i < array.length; i++) {
			Object o = array[i];
			if (!c.contains(o)) array[i] = null;
		}
		return true;
	}
	@Override
	public boolean remove(Object o) {
		for (int i = 0; i < array.length; i++) if (Objects.equals(array[i], o)) array[i] = null;
		return true;
	}
	
	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public ListIterator<T> listIterator(int index) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		if (fromIndex > array.length || toIndex > array.length || fromIndex < 0 || toIndex < 0) throw new IllegalArgumentException();
		Object[] o = new Object[toIndex - fromIndex + 1];
		System.arraycopy(array, fromIndex, o, 0, o.length);
		return new FixedArrayList<>(o);
	}
	
	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			private int position = 0;
			@Override
			public boolean hasNext() {
				return position < array.length - 1;
			}
			@Override
			public T next() {
				return (T) array[position++];
			}
		};
	}
	
	@Override
	public void add(int index, T element) {
		if (get(index) != null) set(index, element);
	}
}
