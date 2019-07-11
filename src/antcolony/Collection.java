package antcolony;

/**
 *	Collection interface
 *	Encapsulates the basic functionality for a generic collection of objects
 *
 *	Written by Roger West, University of Illinois at Springfield
 */
public interface Collection
{

	/**
	 *	add the specified object to the Collection
	 *	the location of the newly added item is dependent on the type of
	 *	Collection
	 *	the only guarantee is that the number of items in the Collection will
	 *	be increased by 1, assuming the add was successful
	 *
	 *	returns true if the add was successful, false otherwise
	 */
	public boolean add(Object obj);


	/**
	 *	remove an item from the Collection
	 *	which item is removed depends on the type of Collection
	 *	the only guarantee is that if the Collection contains 1 or more items,
	 *	the number of items in the Collection will be decreased by 1
	 *
	 *	returns true if the remove was successful, false otherwise
	 */
	public boolean remove();


	/**
	 *	empty the Collection
	 *	the Collection may have all the items physically removed, or the items
	 *	may simply be logically removed, depending on the type of Collection
	 */
	public void clear();


	/**
	 *	return the number of items in the Collection
	 */
	public int size();


	/**
	 *	return true if the Collection contains 0 items, or false if the
	 *	Collection contains at least 1 item
	 */
	public boolean isEmpty();


	/**
	 *	return the next available item from the Collection
	 *	which item is returned depends on the type of Collection
	 */
	public Object get();

}





