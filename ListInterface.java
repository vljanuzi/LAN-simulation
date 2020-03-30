
// ********************************************************
// Interface ListInterface for the ADT list.
// *********************************************************
public interface ListInterface {

	public boolean isEmpty();
	// Determines whether a List is Empty (number of items is 0).
	// Precondition: None.
	// Postcondition: Returns true if the List is empty; otherwise returns false.

	public int size();
	// Returns the size of a List.
	// Precondition: None.
	// Postcondition: Returns the number of elements a list contains.

	public Object get(int index) throws ListIndexOutOfBoundsException;
	// Returns the item on the specified position.
	// Precondition: Index is the position of the Object to be returned
	// Postcondition: If the operation was successful (valid index), the item at
	// position index is returned.
	// If the index is invalid the operation is invalid and the
	// ListIndexOutOfBoundsException is thrown.

	public void removeAll();
	// Removes all elements of the list
	// Precondition: None
	// Postcondition: The list is empty.

	public void insert(Object newDataItem);
	// Adds an item at the beginning of the list
	// Precondition: The given newDataItem Object is to be inserted in the list.
	// Postcondition: The newDataItem is at the beginning of the list.

	public void add(int index, Object newDataItem) throws ListIndexOutOfBoundsException, ListException;
	// Adds a specific item at a specific position on the list.
	// Precondition: NewDataItem is to be inserted at position index.
	// Postcondition: If the operation was successful, the newDataItem
	// is inserted at the specified position index.
	// If the index is invalid the ListIndexOutOfBoundsException is thrown.

	public void append(Object newDataItem);
	// Adds an Item at the end of the list.
	// Precondition: NewDataItem is to be inserted.
	// Postcondition: The newDataItem Object is added at the end of the List.

	public Object showFront();
	// Retrieves the first element of the list
	// Precondition: None.
	// Postcondition: The first element of the list is returned (if existing).

	public Object showLast();
	// Retrieves the last element of the list
	// Precondition: None.
	// Postcondition: The last element of the list is returned (if existing).

	public void remove(int index) throws ListIndexOutOfBoundsException;
	// Removes an item from a specific position
	// Precondition: index is the position of the item that is
	// to be removed
	// Postcondition: If the index is valid the item
	// stored in that position is removed.
	// If the index is invalid the ListIndexOutOfBoundsException is thrown.

	public boolean exists(Object newDataItem);
	// Determines whether an Item is in the list
	// Precondition: newDataItem is to be checked if it is on the list or not
	// Postcondition: Returns true if newDataItem is founded in the list;
	// otherwise returns false.

	public void print();
	// prints all elements of the list
	// Precondition: none
	// Postcondition: all elements of the list are printed

	public void addSorted(Comparable obj);
	// Adds a comparable object in the alphabetically sorted list
	// Precondition: obj is the object to be inserted to the list without specifying
	// the position
	// Postcondition:obj is added to the list alphabetically

	public boolean comparesToItems(Comparable obj);
	// Compares two objects
	// Precondition: obj is the object to be compared with
	// Postcondition: returns true if a node pointing to a same object is found,
	// return false if not found

}
