package telran.util;

import java.util.Iterator;
import java.util.function.Predicate;

public class HashSet<T> implements Set<T> {
	private static final float FACTOR = 0.75f;
	IndexedList<T>[] hashTable;
	int size;

	@SuppressWarnings("unchecked")
	public HashSet(int initialSize) {
		hashTable = new IndexedList[initialSize];
	}

	public HashSet() {
		this(16);
	}

	@Override
	public Iterator<T> iterator() {
		return new HashSetIterator();
	}

	@Override
	public boolean add(T obj) {
		if (contains(obj))
			return false;
		size++;
		if (size > FACTOR * hashTable.length) {
			recreateHashTable();
		}
		int index = getHashTabelIndex(obj);
		if (hashTable[index] == null) {
			hashTable[index] = new IndexedLinkedList<T>();
		}
		hashTable[index].add(obj);
		return true;
	}

	private void recreateHashTable() {
		HashSet<T> tmp = new HashSet<>(hashTable.length * 2);
		for (IndexedList<T> list : hashTable) {
			if (list != null) {
				for (T obj : list) {
					tmp.add(obj);
				}
			}
		}
		hashTable = tmp.hashTable;
	}

	@Override
	public Set<T> filter(Predicate<T> predicate) {
		HashSet<T> res = new HashSet<T>();
		Iterator<T> itr = iterator();
		while (itr.hasNext()) {
			T obj = itr.next();
			if (predicate.test(obj)) {
				res.add(obj);
			}
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object remove(Object pattern) {
		if (!contains((T) pattern))
			return null;
		int index = getHashTabelIndex((T) pattern);
		hashTable[index].remove(pattern);
		size--;
		return pattern;
	}

	@Override
	public boolean removeIf(Predicate<T> predicate) {
		Iterator<T> itr = iterator();
		boolean res = false;
		while (itr.hasNext()) {
			T obj = itr.next();
			if (predicate.test(obj)) {
				 itr.remove();
				res = true;
			}
		}
		return res;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean contains(T pattern) {
		int index = getHashTabelIndex(pattern);
		return hashTable[index] != null && hashTable[index].indexOf(pattern) >= 0;
	}

	private int getHashTabelIndex(T pattern) {
		int hashCode = pattern.hashCode();
		int index = Math.abs(hashCode) % hashTable.length;
		return index;
	}

	private class HashSetIterator implements Iterator<T> {
		int curInd = 0;
		int curListInd = 0;
		int curTableInd = 0;
		IndexedLinkedList<T> listt;
		IndexedLinkedList.Node<T> res;
		IndexedLinkedList.Node<T> current;
        @Override
		public void remove() {
        	listt.removeNode(res);
    		size--;
		}

		@Override
		public boolean hasNext() {
			return curTableInd < size - 1;
		}

		@Override
		public T next() {
			for (int i = curInd; i < hashTable.length; i++) {
				listt = (IndexedLinkedList<T>) hashTable[i];
				if (listt != null) {
					if (current == null) {
						current = listt.getHead();
					}
					if (curListInd < listt.size()) {
						curInd = i;
						res = current;
						current = current.next;
						curListInd++;
						return res.obj;
					} else {
						current = null;
						curTableInd++;
					}
				}
				curListInd = 0;
			}
			return null;
		}
	}

}
