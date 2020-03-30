
public class ListNode {
	private Object item;
	private ListNode next;

	public ListNode() {
		next = null;
	}

	public ListNode(Object newItem) {
		item = newItem;
		next = null;
	}

	public ListNode(Object newItem, ListNode nextNode) {
		item = newItem;
		next = nextNode;
	}

	public void setItem(Object newItem) {
		item = newItem;
	}

	public Object getItem() {
		return item;
	}

	public void setNext(ListNode nextNode) {
		next = nextNode;
	}

	public ListNode getNext() {
		return next;
	}
}