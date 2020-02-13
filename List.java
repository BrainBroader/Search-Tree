/**
 * Generic version of a single-link List . Uses {@link ListNode} for list nodes.
 */
public class List {
	private ListNode firstNode;
	private ListNode lastNode;
	private String name; // string like "list" used in printing

	/**
	 * constructor creates empty List with "list" as the name
	 */
	public List() {
		this("list");
	}

	/**
	 * constructor creates an empty List with a name.
	 * @param listName the list name
	 */
	public List(String listName) {
		name = listName;
		firstNode = lastNode = null;
	}

	public void insertAtFront(String insertItem) {
		ListNode node = new ListNode(insertItem);
		
		if (isEmpty()) // firstNode and lastNode refer to same object
			firstNode = lastNode = node;
		else { // firstNode refers to new node
			node.nextNode = firstNode;
			firstNode = node;
			// you can replace the two previous lines with this line: firstNode
			// = new ListNode( insertItem, firstNode );
		}
	} // end method insertAtFront

	

	public void insertAtBack(String insertItem) {
		ListNode node = new ListNode(insertItem);
		
		if (isEmpty()) // firstNode and lastNode refer to same Object
			firstNode = lastNode = node;
		else { // lastNode's nextNode refers to new node
			lastNode.nextNode = node;
			lastNode = node;
			// you can replace the two previous lines with this line: lastNode =
			// lastNode.nextNode = new ListNode( insertItem );
		}
	} // end method insertAtBack


	public String removeFromFront() throws EmptyListException {
		if (isEmpty()) // throw exception if List is empty
			throw new EmptyListException(name);

		String removedItem = firstNode.data; // retrieve data being removed

		// update references firstNode and lastNode
		if (firstNode == lastNode)
			firstNode = lastNode = null;
		else
			firstNode = firstNode.nextNode;

		return removedItem; // return removed node data
	} // end method removeFromFront


	public String removeFromBack() throws EmptyListException {
		if (isEmpty()) // throw exception if List is empty
			throw new EmptyListException(name);

		String removedItem = lastNode.data; // retrieve data being removed

		// update references firstNode and lastNode
		if (firstNode == lastNode)
			firstNode = lastNode = null;
		else // locate new last node
		{
			ListNode current = firstNode;

			// loop while current node does not refer to lastNode
			while (current.nextNode != lastNode)
				current = current.nextNode;

			lastNode = current; // current is new lastNode
			current.nextNode = null;
		} // end else

		return removedItem; // return removed node data
	} // end method removeFromBack

	/**
	 * Determine whether list is empty
	 * @return true if list is empty
	 */
	public boolean isEmpty() {
		return firstNode == null; // return true if List is empty
	} // end method isEmpty

	/**
	 * Prints the list's contents to System.out
	 */
	public void print() {
		if (isEmpty()) {
			System.out.printf("Empty %s\n", name);
			return;
		} // end if

		System.out.printf("The %s is: ", name);
		ListNode current = firstNode;

		// while not at end of list, output current node's data
		while (current != null) {
			System.out.printf("%s ", current.data);
			current = current.nextNode;
		} // end while

		System.out.println("\n");
	} // end method print

	//remove a specific item
	public void remove(String w){
		if(isEmpty()){
			return;
		}
		if (w.equalsIgnoreCase(firstNode.getObject()) ) removeFromFront();
		ListNode prev = firstNode;
		ListNode current = prev.nextNode;
		while( current!= null){
			if(w.equalsIgnoreCase(current.getObject())){
				prev.nextNode = prev.nextNode.nextNode;
				return;
			}
			prev = current;
			current = current.nextNode;




		}
	}

	//finds if a node exists in the list
	public boolean findNode(String w){
		if(isEmpty()){
			return true;
		}
		ListNode current = firstNode;
		while(current != null){
			if(w.equalsIgnoreCase(current.getObject())){
				return false;
			}
			current = current.nextNode;
		}
		return true;

	}

} // end class List
