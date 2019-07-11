package antcolony;

/**
 *	LinkedList class
 *
 *	implementation of a doubly linked list
 *
 *	Written by Roger West, University of Illinois at Springfield
 *	Adapted from code written by Mark Weiss in Data Structures and Problem
 *	Solving Using Java, 2nd edition, 2002
 */
public class LinkedList implements List
{
	/************
	 *	constants
	 ***********/
	
	/** return value for unsuccessful ai.searches */
	private static final ListNode NOT_FOUND = null;
	
	
	/*************
	 *	attributes
	 ************/
	
	/** current number of items in list */
	private int theSize;
	
	/** reference to list header node */
	private ListNode head;
	
	/** reference to list tail node */
	private ListNode tail;
	
	/** current number of modifications to list */
	private int modCount;


	/***************
	 *	constructors
	 **************/

	/**
	 *	return a new, empty LinkedList
	 */
	public LinkedList()
	{
		// empty this LinkedList
		clear();
	}


	/**********
	 *	methods
	 *********/

	/**
	 *	return a reference to the node at the position of the specified item
	 */
	private ListNode findPos(Object obj)
	{
		// traverse from beginning of this LinkedList
		for(ListNode p = head.next; p != tail; p = p.next)
			if(obj == null)
			{
				// null items must be compared with '==' operator
				if(p.theItem == null)
					return p;
			}
			// non-null items must be compared with equals method
			else if(obj.equals(p.theItem))
				return p;

		// item not in this LinkedList
		return NOT_FOUND;
	}
	

	/**
	 *	place the specified item in the first position of the list (position 0)
	 */
	public boolean addFirst(Object obj)
	{
		// invoke add method for adding at a specific position
		add(0, obj);
		
		// add successful
		return true;
	}


	/**
	 *	place the specified item in the last position of the list (position theSize - 1)
	 */
	public boolean addLast(Object obj)
	{
		// invoke add method for adding at a specific position
		add(theSize, obj);
		
		// add successful
		return true;
	}


	/**
	 *	return the first item in the list
	 *
	 *	throws NoSuchElementException if list is empty
	 */
	public Object getFirst()
	{
		// throw exception if this LinkedList is empty
		if(isEmpty())
			throw new NoSuchElementException();

		// return item at beginning of this LinkedList
		return getNode(0).theItem;
	}


	/**
	 *	return the last item in the list
	 *
	 *	throws NoSuchElementException if list is empty
	 */
	public Object getLast()
	{
		// throw exception if this LinkedList is empty
		if(isEmpty())
			throw new NoSuchElementException();
		
		// return item at beginning of this LinkedList
		return getNode(theSize - 1).theItem;
	}


	/**
	 *	remove and return the first item in the list
	 *
	 *	throws NoSuchElementException if list is empty
	 */
	public Object removeFirst()
	{
		// throw exception if this LinkedList is empty
		if(isEmpty())
			throw new NoSuchElementException();

		// remove item at beginning of this LinkedList
		return remove(getNode(0));
	}


	/**
	 *	remove and return the last item in the list
	 *
	 *	throws NoSuchElementException if list is empty
	 */
	public Object removeLast()
	{
		// throw exception if this LinkedList is empty
		if(isEmpty())
			throw new NoSuchElementException();

		// remove item at end of this LinkedList
		return remove(getNode(theSize - 1));
	}


	/**
	 *	return a reference to the node at the specified position
	 *
	 *	throws IndexOutOfBoundsException if position is invalid
	 */
	private ListNode getNode(int index)
	{
		// stores current node
		ListNode p;

		// throw exception if invalid index
		if(index < 0 || index > size())
			throw new IndexOutOfBoundsException("getNode index: " + index + "; size: " + theSize);

		// determine which end of this LinkedList to start traversal
		if(index < theSize / 2)
		{
			// start traversal at beginning of this LinkedList
			p = head.next;
			
			// traverse to specified index
			for(int i = 0; i < index; i++)
				p = p.next;            
		}
		else
		{
			// start traversal at end of this LinkedList
			p = tail;
			
			// traverse to specified index
			for(int i = theSize; i > index; i--)
				p = p.previous;
		}

		// return node at specified index
		return p;
	}


	/**
	 *	remove the specified node, and return the item in that node
	 */
	private Object remove(ListNode node)
	{
		// reconnect next node and previous node references to bypass node to be removed
		node.next.previous = node.previous;
		node.previous.next = node.next;
		
		// subtract 1 from size of this LinkedList
		theSize--;
		
		// indicate a modification has been made
		modCount++;

		// return item in node that was removed
		return node.theItem;
	}

	
	/**************************************
	 *	methods inherited from class Object
	 *************************************/

	/**
	 *	return a String representation of the LinkedList
	 *
	 *	items are listed in order from beginning to end in comma-delimited fashion
	 */
	public String toString()
	{
		String s = "";
		
		for (int i = 0; i < theSize; i++)
		{
			if (i == theSize - 1)
				s += getNode(i).theItem.toString();
			else
				s += getNode(i).theItem.toString() + ", ";
		}
		
		return s;
	}


	/**********************************************
	 *	methods inherited from interface Collection
	 *********************************************/
	
	/**
	 *	add the specified item to the end of the LinkedList
	 */
	public boolean add(Object obj)
	{
		addLast(obj);
		
		// add successful
		return true;
	}


	/**
	 *	remove the last item in the LinkedList
	 */
	public boolean remove()
	{
		removeLast();
		
		// remove sucessful
		return true;
	}


	/**
	 *	empty the LinkedList
	 *
	 *	size will be set to zero
	 *	clearing the LinkedList counts as a modification of the list
	 */
	public void clear()
	{
		// reset header node
		head = new ListNode("HEAD", null, null);
		
		// reset tail node
        tail = new ListNode("TAIL", head, null);
        
        // header references tail in an empty LinkedList
        head.next = tail;
        
        // reset size to 0
		theSize = 0;
		
		// emptying list counts as a modification
		modCount++;
	}


	/**
	 *	return the number of items in the ArrayList
	 */
	public int size()
	{
		return theSize;
	}


	/**
	 *	return true if the LinkedList is empty
	 */
	public boolean isEmpty()
	{
		return theSize == 0;
	}


	/**
	 *	return the item at the end of the LinkedList
	 */
	public Object get()
	{
		return getLast();
	}
	
	
	/*********************************************************
	 *	methods inherited from interface TraversableCollection
	 ********************************************************/
	
	/**
	 *	return an Iterator beginning at position 0
	 */
	public Iterator iterator()
	{
		return new LinkedListIterator(0);
	}
	
	
	/**
	 *	return true if the specified item is in the list
	 */
	public boolean contains(Object obj)
	{
		// if item is in this LinkedList, findPos will return valid index
		return findPos(obj) != NOT_FOUND;
	}
	
	
	/****************************************
	 *	methods inherited from interface List
	 ***************************************/
	
	/**
	 *	return the item at the specified position
	 *
	 *	throws IndexOutOfBoundsException if position is invalid
	 */
	public Object get(int index)
	{
		return getNode(index).theItem;
	}
	

	/**
	 *	add the specified item to the LinkedList at the specified position
	 *
	 *	throws IndexOutOfBoundsException if position is invalid
	 */
	public boolean add(int index, Object obj)
	{
		// find insertion point
		ListNode p = getNode(index);
		
		// create new node
		ListNode newNode = new ListNode(obj, p.previous, p);
		
		// connect preceding node to new node
		newNode.previous.next = newNode;
		
		// connect succeeding node to new node
		p.previous = newNode;
		
		// add 1 to size of this LinkedList
		theSize++;
		
		// indicate a modification has been made
		modCount++;
		
		// add successful
		return true;
	}


	/**
	 *	replace the item at the specified position
	 *
	 *	throws IndexOutOfBoundsException if position is invalid
	 */
	public Object set(int index, Object obj)
	{
		// get node at specified index
		ListNode p = getNode(index);
		
		// store item currently in node
		Object oldVal = p.theItem;

		// replace old item with new item
		p.theItem = obj;
		
		// return old item
		return oldVal;
	}


	/**
	 *	remove the item at the specified position
	 *
	 *	throws IndexOutOfBoundsException if position is invalid
	 */
	public boolean remove(int index)
	{
		// remove item
		remove(getNode(index));
		
		// remove successful
		return true;
	}


	/**
	 *	return a ListIterator that begins at the specified position
	 */
	public ListIterator listIterator(int index)
	{
		return new LinkedListIterator(index);
	}


	/**
	 *	return the position of the specified item, if it is in the list
	 *
	 *	returns -1 if item is not in list
	 */
	public int indexOf(Object obj)
	{
		// stores index of item
		int index = 0;
		
		// search for item
		for(ListNode p = head.next; p != tail; p = p.next)
		{
			if(obj == null)
			{
				// null items must be compared by '==' operator
				if(p.theItem == null)
					return index;
			}
			// non-null items must be compared by equals method
			else if(obj.equals(p.theItem))
				return index;
			
			index++;
		}

		// item not in this LinkedList
		return -1;
	}
	
	
	/**
	 *	remove the first occurrence of the specified item
	 *
	 *	returns true if removal was successful
	 *	returns false if item is not in list
	 */
	public boolean remove(Object obj)
	{
		// find position of first occurrence of item
		ListNode pos = findPos(obj);

		if(pos == NOT_FOUND)
		{
			// item not in this LinkedList
			return false;
		}		
		else
		{
			// item found; remove it
			remove(pos);
			
			// remove successful
			return true;
		}     
    }


	/****************
	 *	inner classes
	 ***************/
	
	/**
	 *	inner class LinkedListIterator
	 *
	 *	traverses a LinkedList in both forward and backward directions
	 *	maintains the current position in the traversal
	 *
	 *	instances of LinkedListIterator will be invalidated when LinkedList methods
	 *	are used to modify the list
	 */
	private class LinkedListIterator implements ListIterator
	{

		/*************
		 *	attributes
		 ************/
		
		/** current position */
		private int current;
		
		/** number of list modifications the iterator is aware of */
		private int expectedModCount;


		/***************
		 *	constructors
		 **************/

		/**
		 *	return a LinkedListIterator that begins at the specified position
		 *
		 *	throws IndexOutOfBoundsException if position is invalid
		 */
		public LinkedListIterator(int index)
		{
			// throw exception if invalid index
			if(index < 0 || index >= theSize)
				throw new IndexOutOfBoundsException();
			
			// set current index to specified index
			current = index;
			
			// sync mod counts of this LinkedListIterator and LinkedList
			expectedModCount = modCount;
		}
		
		
		/********************************************
		 *	methods inherited from interface Iterator
		 *******************************************/
		
		/**
		 *	return the item at the iterator's current position
		 *
		 *	throws IndexOutOfBoundsException if there is no current item
		 */
		public Object getCurrent()
		{
			// throw exception if invalid index
			if(current < 0 || current >= theSize)
				throw new IndexOutOfBoundsException();
			
			// return item at current position in traversal
			return getNode(current).theItem;
		}


		/**
		 *	return true if there is a next item
		 *	there is a next item as long as the iterator's current position < theSize
		 *
		 *	throws ConcurrentModificationException if iterator has been invalidated
		 */
		public boolean hasNext()
		{
			// throw exception if LinkedList modified outside this LinkedListIterator
			if(expectedModCount != modCount)
				throw new ConcurrentModificationException();

			// next item in traversal if current index hasn't gone past end
			return current < theSize;
		}


		/**
		 *	advance forward one position
		 *
		 *	throws NoSuchElementException if there is no next item
		 */
		public void next()
		{
			// throw exception if no next item in traversal
			if(!hasNext())
				throw new NoSuchElementException();

			// set current to next position
			current++;
		}

		
		/**
		 *	add the specified object to the LinkedList
		 *	object is inserted at the iterator's current position
		 *
		 *	throws ConcurrentModificationException if iterator has been invalidated
		 */
		public boolean add(Object obj)
		{
			// throw exception if Linkedlist modified outside this LinkedListIterator
			if(expectedModCount != modCount)
				throw new ConcurrentModificationException();
			
			// add item by invoking LinkedList add method
			LinkedList.this.add(current, obj);
			
			// sync mod count of this LinkedListIterator with the LinkedList
			expectedModCount++;
			
			// add successful
			return true;
		}


		/**
		 *	remove the item at the iterator's current position
		 *
		 *	throws ConcurrentModificationException if iterator has been invalidated
		 */
		public boolean remove()
		{
			// throw exception if LinkedList modified outside this LinkedListIterator
			if(expectedModCount != modCount)
				throw new ConcurrentModificationException();

			// remove item by invoking LinkedList remove method
			LinkedList.this.remove(current);

			// sync mod count of this LinkedListIterator with the LinkedList
			expectedModCount++;
			
			// remove successful
			return true;
		}
		
		
		/************************************************
		 *	methods inherited from interface ListIterator
		 ***********************************************/
		
		/**
		 *	return true if there is a previous item
		 *	there is a previous item as long as the iterator's current position >= 0
		 *
		 *	throws ConcurrentModificationException if iterator has been invalidated
		 */
		public boolean hasPrevious()
		{
			// throw exception if LinkedList modified outside this LinkedListIterator
			if(expectedModCount != modCount)
				throw new ConcurrentModificationException();

			// if previous item exists, current >= 0 will be true
			return current >= 0;
		}


		/**
		 *	move backward one position
		 *
		 *	throws NoSuchElementException if there is no previous item
		 */
		public void previous()
		{
			// throw exception if no previous item in traversal
			if(!hasPrevious())
				throw new NoSuchElementException();
			
			// set current to previous position
			current--;
		}

	}


	/**
	 *	nested class ListNode
	 *
	 *	encapsulates the fundamental building block of a LinkedList
	 *	contains a data item, and references to both the next and previous nodes
	 *	in the list
	 */
	private static class ListNode
	{

		/*************
		 *	attributes
		 ************/
		
		/** the data item */
		Object theItem;
		
		/** reference to the next node in the list */
		ListNode next;
		
		/** reference to the previous node in the list */
		ListNode previous;


		/**************
		 *	constructor
		 *************/
		
		/**
		 *	create a new ListNode containing the specified item, and references
		 *	to the new node's next and previous nodes
		 */
		public ListNode(Object item, ListNode previousNode, ListNode nextNode)
		{
			theItem = item;
			previous = previousNode;
			next = nextNode;
		}

	}

}



