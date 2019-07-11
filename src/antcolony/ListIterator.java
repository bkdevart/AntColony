package antcolony;

/**
 *	interface ListIterator
 *
 *	expands upon the functionality of Iterator by providing for bidirectional
 *	traversals
 *
 *	Written by Roger West, University of Illinois at Springfield
 *	Adapted from code written by Mark Weiss in Data Structures and Problem
 *	Solving Using Java, 2nd edition, 2002
 */
public interface ListIterator extends Iterator
{

	/**
	 *	return whether there is a previous item in the traversal
	 *	return true if yes, false otherwise
	 */
	public boolean hasPrevious();


	/**
	 *	go to the previous item in the traversal
	 */
	public void previous();
	
	
	/**
	 *	add the specified item at the current position of the traversal
	 */
	public boolean add(Object obj);


	/**
	 *	remove the current item in the traversal
	 */
	public boolean remove();

}





