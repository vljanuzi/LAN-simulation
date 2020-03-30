
import java.lang.Comparable;

public class ReferenceBasedList implements ListInterface {
	private ListNode head;
	private ListNode tail;
	int numItems;

	public ReferenceBasedList() {
		head = tail = null;
		numItems = 0;
	}

	public int size() {
		return numItems;
	}

	public boolean isEmpty() {
		return (numItems == 0);
	}

	public void removeAll() {
		head = tail = null;
		numItems = 0;
	}

	private ListNode find(int index) {
		ListNode curr = head;
		for (int skip = 1; skip < index; skip++)
			curr = curr.getNext();
		return curr;
	}

	public Object get(int index) throws ListIndexOutOfBoundsException {
		if (index >= 1 && index <= numItems) {
			ListNode curr = find(index);
			return curr.getItem();
		} else {
			throw new ListIndexOutOfBoundsException("List index out of bounds exception on get");
		}
	}

	public void add(int index, Object newDataItem) throws ListIndexOutOfBoundsException {
		if (index >= 1 && index <= numItems + 1) {
			if (index == 1) {
				ListNode newNode = new ListNode(newDataItem, head);
				head = newNode;

				if (tail == null)
					tail = head;
			} else if (index == numItems + 1) {
				ListNode newNode = new ListNode(newDataItem);
				tail.setNext(newNode);
				tail = newNode;
			} else {
				ListNode prev = find(index - 1);
				ListNode newNode = new ListNode(newDataItem, prev.getNext());
				prev.setNext(newNode);
			}
			numItems++;
		} else {
			throw new ListIndexOutOfBoundsException("List index out of bounds exception on add");
		}
	}

	public void insert(Object newDataItem) {
		this.add(1, newDataItem);
	}

	public void append(Object newDataItem) {
		this.add(numItems + 1, newDataItem);
	}

	public Object showFront() {
		return this.get(1);
	}

	public Object showLast() {
		return this.get(numItems);
	}

	public void remove(int index) throws ListIndexOutOfBoundsException {
		if (index >= 1 && index <= numItems) {
			if (index == 1) {
				head = head.getNext();
				if (head == null)
					tail = null;
			} else {
				ListNode prev = find(index - 1);
				ListNode curr = prev.getNext();
				prev.setNext(curr.getNext());
				if (index == numItems)
					tail = prev;
			}
			numItems--;
		} else {
			throw new ListIndexOutOfBoundsException("List index out of bounds exception on remove");
		}
	}

	public boolean exists(Object dataItem) {
		for (ListNode tmp = head; tmp != null; tmp = tmp.getNext())
			if (tmp.getItem().equals(dataItem))
				return true;
		return false;
	}

	public void print() {
		ListNode curr = head;
		while (curr != null) {
			System.out.println(curr.getItem().toString());
			curr = curr.getNext();
		}
	}

	public void addSorted(Comparable obj) {
		ListNode temp = new ListNode(obj);
		if (head == null) {
			head = tail = temp;
			numItems++;
		} else if (obj.compareTo(head.getItem()) <= 0) {
			this.insert(obj);
		} else {
			ListNode curr = head;
			while (curr != null) {
				if (curr.getNext() == null) {
					this.append(obj);
					break;
				}
				if (obj.compareTo(curr.getNext().getItem()) < 0) {
					temp.setNext(curr.getNext());
					curr.setNext(temp);
					numItems++;
					break;
				}
				curr = curr.getNext();
			}
		}
	}

	public boolean comparesToItems(Comparable obj) {
		ListNode curr = head;
		while (curr != null) {
			if (obj.compareTo(curr.getItem()) == 0) {
				return true;
			}
			curr = curr.getNext();
		}
		return false;
	}
}
