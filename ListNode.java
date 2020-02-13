/**
 * ListNode represents a signe-link list node
 * Each node contains an T reference to data and a reference to the nextNode in the list. 
 */

class ListNode {
	// package access members; List can access these directly
	String data;
	ListNode nextNode;


	ListNode(String data) {
		this(data, null);
	}


	ListNode(String data, ListNode node) {
		this.data = data;
		nextNode = node;
	} 

	/**
	 * Returns this node's data
	 * @return the reference to node's data
	 */
	String getObject() {
		return data; 
	} 

	/**
	 * Get reference to next node
	 * @return the next node
	 */
	ListNode getNext() {
		return nextNode; 
	}
	
} 