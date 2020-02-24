package telran.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

public class Array {
	private Object[] array;
	private int size = 0;

	public Array(int capacity) {
		array = new Object[capacity];
	}

	public Array() {
		this(16);
	}

	public void add(Object obj) {
		if (size == array.length) {
			allocateArray();
		}
		array[size++] = obj;
	}

	private void allocateArray() {
		array = Arrays.copyOf(array, array.length * 2);

	}

	public Object get(int index) {
		Object res = null;
		if (index >= 0 && index < size) {
			res = array[index];
		}
		return res;
	}

	public int size() {
		return size;
	}

	/**
	 * adds an object at a specified index
	 * 
	 * @param index
	 * @param obj
	 * @return true if index value in the range [0 - size] (size included) otherwise
	 *         false
	 */
	public boolean add(int index, Object obj) {
		boolean res = false;
		if (index >= 0 && index <= size) {
			if (size == array.length) {
				allocateArray();
			}
			res = true;
			System.arraycopy(array, index, array, index + 1, size - index);
			array[index] = obj;
			size++;
		}
		return res;
	}

	/**
	 * removes the object at a specified index
	 * 
	 * @param index
	 * @return reference to the removed object or null in the case of wrong index
	 */
	public Object remove(int index) {
		Object res = null;
		if (isIndexValid(index)) {
			res = array[index];
			if (index != size - 1) {
				// if size equals capacity index + 1 for the last element will cause error
				System.arraycopy(array, index + 1, array, index, size - index - 1);
			}
			size--;
			array[size] = null; // candidate to be collected as a garbage

		}
		return res;
	}

	/**
	 * sets new object at a specified index
	 * 
	 * @param index
	 * @param obj
	 * @return old reference to the object or null in the case of wrong index value
	 */
	public Object set(int index, Object obj) {
		Object res = null;
		if (isIndexValid(index)) {
			res = array[index];
			array[index] = obj;
		}
		return res;
	}

	private boolean isIndexValid(int index) {
		return index >= 0 && index < size;
	}

	public int indexOf(Object pattern) {
		int res = -1;
		if (pattern != null) {
			for (int i = 0; i < size; i++) {
				if (pattern.equals(array[i])) {
					res = i;
					break;
				}
			}
		}
		return res;

	}

	public int lastIndexOf(Object pattern) {
		int res = -1;
		if (pattern != null) {
			for (int i = size - 1; i >= 0; i--) {
				if (pattern.equals(array[i])) {
					res = i;
					break;
				}
			}
		}
		return res;

	}

	public int binarySearch(Object pattern, Comparator<Object> comp) {
		int left = 0;
		int right = size - 1;
		int middle = (left + right) / 2;
		while (left <= right && !pattern.equals(array[middle])) {
			if (comp.compare(pattern, array[middle]) < 0) {
				right = middle - 1;
			} else {
				left = middle + 1;
			}
			middle = (left + right) / 2;
		}
		return left > right ? -(left + 1) : middle;
	}

	/**
	 * the same binary search but based on Comparable<Object> rather than
	 * Comparator<Object>
	 * 
	 * @param pattern
	 * @return
	 */
	public int binarySearch(Object pattern) {

		return binarySearch(pattern, new ComparatorComparable());
	}

	/**
	 * sorting based on Comparator<Object> in the meantime the simple bubble sort
	 * that we did at class may be applied O[N^2]
	 * 
	 * @param comp
	 */
	public void sort(Comparator<Object> comp) {
		boolean flSort = false;
		int length = size;
		do {
			flSort = true;
			length--;
			for (int i = 0; i < length; i++) {
				if (comp.compare(array[i], array[i + 1]) > 0) {
					Object tmp = array[i];
					array[i] = array[i + 1];
					array[i + 1] = tmp;
					flSort = false;
				}
			}

		} while (!flSort);

	}

	/**
	 * sorting based on Comparable<Object>
	 */
	public void sort() {
		sort(new ComparatorComparable());
		// below the code with no ComparatorComparable
//	boolean flSort = false;
//	int length = size;
//	do {
//		flSort = true;
//		length--;
//		for (int i = 0; i < length; i++) {
//			if (((Comparable<Object>)array[i]).compareTo(array[i + 1]) > 0) {
//				Object tmp = array[i];
//				array[i] = array[i + 1];
//				array[i + 1] = tmp;
//				flSort = false;
//			}
//		}
//		
//		
//	}while (!flSort);
	}

	public Array filter(Predicate<Object> predicate) {
		Array res = new Array();
		for (int i = 0; i < size; i++) {
			if (predicate.test(array[i])) {
				res.add(array[i]);
			}
		}
		return res;
	}

	/**
	 * removes objects matching the given c
	 * 
	 * @param predicate
	 * @return true if at least one object has been removed
	 */
	public boolean removeIf(Predicate<Object> predicate) {
		boolean res = false;
		int i = 0;
		while (i < size) {
			if (predicate.test(array[i])) {
				res = true;
				remove(i);
			} else {
				i++;
			}
		}
		return res;
	}
/**
 * 	all odd numbers go before the even ones 
 *  and odd numbers be sorted in the ascending order
 *  and even numbers be sorted in the descending order
 * @param predicate - check for odd value
 * @author Vinny
 */
	public void sortingEvenOdd(Predicate<Object> predicate) {
		Object res[] = new Object[size];
		this.sort();
		int i = 0;
		int Min = 0;
		int Max = size - 1;
		while (i < size) {
			if (predicate.test(this.array[i])) {
				res[Max] = this.array[i];
				Max--;
			} else {
				res[Min] = this.array[i];
				Min++;
			}
			i++;
		}
		this.array = res;
	}
}
