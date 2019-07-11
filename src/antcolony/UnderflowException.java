package antcolony;

/**
 *	Class UnderflowException
 *
 *	Generic class to represent the exception that occurs when an attempt is made
 *	to access an item in an empty stack
 *
 *	Written by Roger West, University of Illinois at Springfield
 */
public class UnderflowException extends RuntimeException
{

	/***************
	 *	constructors
	 **************/

	/**
	 *	create a new UnderflowException
	 */
	public UnderflowException()
	{
		
	}


	/**
	 *	create a new UnderflowException that will display the specified
	 *	message if thrown
	 *
	 *	@param msg - the message to be displayed
	 */
	public UnderflowException(String msg)
	{
		super(msg);
	}
}
