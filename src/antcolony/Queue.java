package antcolony;

/**
 *	Queue interface
 *	Encapsulates the basic functionality for a queue: a FIFO data structure in
 *	which items may only be added to the rear of the queue, and items may only
 *	be removed from the front of the queue
 *	the next item that can be accessed is the item that was least recently added
 *
 *	The canonical queue methods are provided, although technically they are not
 *	necessary:
 *		getFront == get
 *		dequeue == remove
 *		enqueue == add
 *
 *	Written by Roger West, University of Illinois at Springfield
 *	Adapted from code written by Mark Weiss in Data Structures and Problem
 *	Solving Using Java, 2nd edition, 2002
 */
public interface Queue extends Collection
{

	/**
	 *	return the item at the front of the queue; item is not removed
	 */
	public Object getFront();


	/**
	 *	remove and return the item at the front of the queue
	 */
	public Object dequeue();


	/**
	 *	add the specified item to the rear of the queue
	 */
	public boolean enqueue(Object obj);

}





