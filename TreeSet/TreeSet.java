package telran.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Predicate;

public class TreeSet<T> implements Set<T> {
	private static class Node<T> {
		T obj;
		Node<T> parent;
		Node<T> left; // reference to a less (relative to comparator)
		Node<T> right; // reference to a greater

		public Node(T obj) {
			this.obj = obj;
		}
	}

	Comparator<T> comparator;
	Node<T> root;
	int size;

	public TreeSet(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	@SuppressWarnings("unchecked")
	public TreeSet() {
		this((Comparator<T>) Comparator.naturalOrder());
	}

	@Override
	public Iterator<T> iterator() {
		return new TreeSetIterator();
	}

	@Override
	public boolean add(T obj) {
		if (root == null) {
			addRoot(obj); // addRoot creates new Node that will be root
			return true;
		}
		Node<T> parent = getParent(obj);
		// if obj already exists (compare return 0) -> returns false

		if (parent == null) {
			return false;
		}
		Node<T> newNode = new Node<>(obj);
		if (comparator.compare(obj, parent.obj) < 0) {
			parent.left = newNode;
		} else {
			parent.right = newNode;
		}
		size++;
		newNode.parent = parent;
		return true;
	}

	private void addRoot(T obj) {
		size = 1;
		root = new Node<>(obj);

	}

	private Node<T> getParent(T obj) {
		Node<T> current = root;
		Node<T> parent = null;

		while (current != null) {
			parent = current;
			int cmp = comparator.compare(obj, current.obj);
			if (cmp == 0)
				return null;

			current = cmp < 0 ? current.left : current.right;
		}
		return parent;
	}

	@Override
	public Set<T> filter(Predicate<T> predicate) {
		TreeSet<T> res = new TreeSet<T>();
		for (T obj : this) {
			if (predicate.test(obj)) {
				res.add(obj);
			}
		}
		return res;
	}

	@Override
	public Object remove(Object pattern) {
		Object res = null;
		Node<T> node = findNode(pattern);
		if (node != null) {
			res = node.obj;
			removeNode(node);
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	public Node<T> findNode(Object pattern) {
		Node<T> current = root;
		while (comparator.compare(current.obj, (T) pattern) != 0) {

			if (comparator.compare(current.obj, (T) pattern) > 0)
				current = current.left;
			else
				current = current.right;

			if (current == null)
				return null;
		}
		return current;
	}

	private void removeNode(Node<T> node) {
		if (isJunction(node)) {
			Node<T> substitute = getLeastNode(node.right);
			node.obj = substitute.obj;
			node = substitute;
		}
		removeNonJunctionNode(node);
		size--;
	}

	private void removeNonJunctionNode(Node<T> node) {
		// remove root
		if (node.left == null && node.right == null && node.parent == null) {
			root = null;
		}
		// remove tree leaf
		if (node.left == null && node.right == null && node.parent != null) {
			if (node == node.parent.left)
				node.parent.left = null;
			else
				node.parent.right = null;
		}
		  //Removing a node that has a left subtree
        if (node.left != null) {
            //Change parent
            changeParent(node, node.left);
        }
        //Removing a node that has a right subtree
        if (node.right != null) {
            //Change parent
            changeParent(node, node.right);
        }
    }

    private void changeParent(Node<T> node, Node<T> changNode) {
        changNode.parent = node.parent;
        if (node == root) {
            root = changNode;
        } else if (node == node.parent.left) {
            node.parent.left = changNode;
        } else if (node == node.parent.right) {
            node.parent.right = changNode;
        }
    }

	private boolean isJunction(Node<T> node) {
		return node.left != null && node.right != null;
	}

	private Node<T> getLeastNode(Node<T> node) {
		Node<T> current = node;
		while (current.left != null) {
			current = current.left;
		}
		return current;
	}

	private Node<T> getParentFromLeft(Node<T> node) {
		while (node.parent != null && node.parent.right == node) {
			node = node.parent;
		}
		return node.parent;
	}

	@Override
	public boolean removeIf(Predicate<T> predicate) {
		Iterator<T> itr = iterator();
		int initSize = size;
		while (itr.hasNext()) {
			T obj = itr.next();
			if (predicate.test(obj)) {
				itr.remove();
			}
		}
		return initSize > size;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean contains(T pattern) {
		return size > 0 && getParent(pattern) == null;
	}

	private class TreeSetIterator implements Iterator<T> {
		Node<T> current = getLeastNode(root);
		Node<T> remove;

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public T next() {
			remove = current;
			T res = current.obj;
			current = current.right != null ? getLeastNode(current.right) : getParentFromLeft(current);

			return res;
		}

		@Override
		public void remove() {
			removeNode(remove);
		}
	}
}
