package antcolony;

/**
 *	Class ConcurrentModificationException
 *
 *	Generic class to represent the exception that occurs when a data structure
 *	is modified by two independent processes.
 *
 *	Written by Roger West, University of Illinois at Springfield
 */
public class ConcurrentModificationException extends RuntimeException
{
	
	/***************
	 *	constructors
	 **************/

	/**
	 *	create a new ConcurrentModificationException
	 */
	public ConcurrentModificationException( )
	{
		
	}

	
	/**
	 *	create a new ConcurrentModificationException that will display the specified
	 *	message if thrown
	 *
	 *	@param msg - the message to be displayed
	 */
	public ConcurrentModificationException(String message)
	{
		super(message);
	}
}