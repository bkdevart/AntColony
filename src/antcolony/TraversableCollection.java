package antcolony;

/**
 *	TraversableCollection interface
 *	Encapsulates the functionality for a Collection that can be traversed
 *	using an iterator
 *
 *	Written by Roger West, University of Illinois at Springfield
 */
public interface TraversableCollection extends Collection
{

	/**
	 *	return a generic Iterator for the Collection
	 */
	public Iterator iterator();
	
	
	/**
	 *	return whether the Collection contains the specified item
	 */
	public boolean contains(Object obj);

}