package antcolony;

/**
 *	Class NoSuchElementException
 *
 *	Generic class to represent the exception that occurs an attempt is made to
 *	find an item in a data structure that does not exist in that data structure.
 *
 *	Written by Roger West, University of Illinois at Springfield
 */
public class NoSuchElementException extends RuntimeException
{

	/***************
	 *	constructors
	 **************/

	/**
	 *	create a new NoSuchElementException
	 */
	public NoSuchElementException()
	{
		
	}


	/**
	 *	create a new NoSuchElementException that will display the specified
	 *	message if thrown
	 *
	 *	@param msg - the message to be displayed
	 */
	public NoSuchElementException(String msg)
	{
		super(msg);
	}
}