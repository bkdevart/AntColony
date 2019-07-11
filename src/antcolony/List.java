package antcolony;

/**
 *	List interface
 *	Encapsulates the basic functionality for a list: a data structure in which
 *	each item is associated with a particular position, or index, in the list
 *
 *	Written by Roger West, University of Illinois at Springfield
 *	Adapted from code written by Mark Weiss in Data Structures and Problem
 *	Solving Using Java, 2nd edition, 2002
 */
public interface List extends TraversableCollection
{

	/**
	 *	return the item at the specified index
	 */
	public Object get(int index);


	/**
	 *	insert the specified item at the specified index
	 */
	public boolean add(int index, Object obj);


	/**
	 *	replace the item at the specified index with the specified item
	 */
	public Object set(int index, Object obj);


	/**
	 *	remove the item at the specified index
	 */
	public boolean remove(int index);


	/**
	 *	return an iterator specialized for traversing a List
	 *	traversal begins at the specified index
	 */
	public ListIterator listIterator(int index);


	/**
	 *	return the index of the specified item
	 */
	public int indexOf(Object obj);
	
	
	/**
	 *	remove the specified item from the List
	 */
	public boolean remove(Object obj);

}