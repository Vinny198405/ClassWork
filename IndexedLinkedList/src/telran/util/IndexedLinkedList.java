package telran.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Predicate;

public class IndexedLinkedList<T> implements IndexedList<T> {

	private static class Node<T> {
		public T obj;
		public Node<T> next;
		public Node<T> prev;

		public Node(T obj) {
			this.obj = obj;
		}
	}

	private class ListIterator implements Iterator<T> {
		Node<T> current = head;

		@Override
		public void remove() {
			if (current == null) {
				removeTail();
			} else {
				removeNode(current.prev);
			}
		}

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public T next() {
			T res = current.obj;
			current = current.next;
			return res;
		}
	}

	private Node<T> head;
	private Node<T> tail;
	private int size;
	private Array<T> array;

	public IndexedLinkedList() {
	}

	public IndexedLinkedList(int dummy) {
	}

	@Override
	public Iterator<T> iterator() {
		return new ListIterator();
	}

	@Override
	public void add(T obj) {
		array = null;
		Node<T> newNode = new Node<>(obj);
		addNodeTail(newNode);
	}

	@Override
	public boolean add(int index, T obj) {
		array = null;
		boolean res = true;
		Node<T> newNode = new Node<>(obj);

		if (index == 0) {
			addNodeHead(newNode);
		} else if (index == size) {
			addNodeTail(newNode);
		} else if (isValidIndex(index)) {
			Node<T> beforeNode = getNode(index);
			addNodeMiddle(newNode, beforeNode);
		} else {
			res = false;
		}
		return res;

	}

	private void addNodeMiddle(Node<T> newNode, Node<T> beforeNode) {
		newNode.next = beforeNode;
		newNode.prev = beforeNode.prev;
		beforeNode.prev.next = newNode;
		beforeNode.prev = newNode;
		size++;

	}

	private void addNodeHead(Node<T> newNode) {
		if (head == null) {
			head = tail = newNode;
		} else {
			newNode.next = head;
			head.prev = newNode;
			head = newNode;
		}
		size++;

	}

	private void addNodeTail(Node<T> newNode) {
		if (head == null) {
			head = tail = newNode;
		} else {
			tail.next = newNode;
			newNode.prev = tail;
			tail = newNode;
		}
		size++;

	}

	private Node<T> getNode(int ind) {
		return ind < size / 2 ? getFromLeft(ind) : getFromRight(ind);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int binarySearch(T pattern) {
		return binarySearch(pattern, (Comparator<T>) Comparator.naturalOrder());
	}

	@Override
	public int binarySearch(T pattern, Comparator<T> comp) {
		if (array == null) {
			sort(comp);
		}
		return array.binarySearch(pattern, comp);
//		int left = 0;
//		int right = size - 1;
//		int middle = (left + right) / 2;
//		T midVal = this.get(middle);
//		while (left <= right && !pattern.equals(midVal)) {
//			int cmp = comp.compare(midVal, pattern);
//			if (cmp < 0) {
//				left = middle + 1;
//			} else {
//				right = middle - 1;
//			}
//			middle = (left + right) / 2;
//			midVal = this.get(middle);
//		}
//		return left > right ? -(left + 1) : middle;
	}

	@Override
	public IndexedList<T> filter(Predicate<T> predicate) {
		Node<T> current = head;
		while (current != null) {
			if (!predicate.test(current.obj)) {
				removeNode(current);
			}
			current = current.next;
		}
		return this;
	}

	@Override
	public T get(int ind) {
		T res = null;
		if (isValidIndex(ind)) {
			Node<T> nodeRes = getNode(ind);
			res = nodeRes.obj;
		}
		return res;
	}

	private Node<T> getFromRight(int ind) {
		Node<T> current = tail;
		for (int i = size - 1; i > ind; i--) {
			current = current.prev;
		}
		return current;
	}

	private Node<T> getFromLeft(int ind) {

		Node<T> current = head;
		for (int i = 0; i < ind; i++) {
			current = current.next;
		}
		return current;
	}

	private boolean isValidIndex(int ind) {
		return ind >= 0 && ind < size;
	}

	@Override
	public int indexOf(Object pattern) {
		int res = -1;
		Node<T> current = head;
		if (pattern != null) {
			for (int i = 0; i < size; i++) {
				if (pattern.equals(current.obj)) {
					res = i;
					break;
				}
				current = current.next;
			}
		}
		return res;
	}

	@Override
	public int lastIndexOf(Object pattern) {
		int res = -1;
		Node<T> current = tail;
		if (pattern != null) {
			for (int i = size - 1; i >= 0; i--) {
				if (pattern.equals(current.obj)) {
					res = i;
					break;
				}
				current = current.prev;
			}
		}
		return res;
	}

	@Override
	public Object remove(int ind) {
		Object res = null;
		if (isValidIndex(ind)) {
			Node<T> removedNode = getNode(ind);
			res = removedNode.obj;
			removeNode(removedNode);

		}
		return res;
	}

	private void removeNode(Node<T> removedNode) {
		if (removedNode == head) {
			removeHead();
		} else if (removedNode == tail) {
			removeTail();
		} else {
			removeNodeMiddle(removedNode);
		}
	}

	private void removeNodeMiddle(Node<T> removedNode) {
		removedNode.next.prev = removedNode.prev;
		removedNode.prev.next = removedNode.next;
		size--;
	}

	private void removeTail() {
		if (head == tail) {
			head = tail = null;
		} else {
			tail.prev.next = null;
			tail = tail.prev;
		}
		size--;

	}

	private void removeHead() {
		if (head == tail) {
			head = tail = null;
		} else {
			head.next.prev = null;
			head = head.next;
		}
		size--;

	}

	@Override
	public Object remove(Object pattern) {
		Node<T> current = head;
		while (current != null && !current.obj.equals(pattern)) {
			current = current.next;
		}
		Object res = null;
		if (current != null) {
			res = current.obj;
			removeNode(current);
		}
		return res;
	}

	@Override
	public boolean removeIf(Predicate<T> predicate) {
		Iterator<T> itr = iterator();
		boolean res = false;
		while (itr.hasNext()) {
			T t = itr.next();
			if (predicate.test(t)) {
				itr.remove();
				res = true;
			}
		}
		return res;
	}

	@Override
	public Object set(int ind, T newObj) {
		Node<T> temp = getNode(ind);
		Object res = null;
		if (isValidIndex(ind)) {
			res = temp.obj;
			temp.obj = newObj;
		}
		return res;
	}

	@Override
	public int size() {
		return size;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void sort() {
		sort((Comparator<T>) Comparator.naturalOrder());
	}

	@Override
	public void sort(Comparator<T> comp) {
		Node<T> current = head;
		array = new Array<T>(size);
		for (int i = 0; i < size; i++) {
			array.add(current.obj);
			current = current.next;
		}
		array.sort(comp);

		current = head;
		for (int i = 0; i < size; i++) {
			current.obj = array.get(i);
			current = current.next;
		}
	}
}
