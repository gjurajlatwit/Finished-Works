/* @formatter:off
 *
 * Dave Rosenberg
 * Comp 2000 - Data Structures
 * Demonstration: Bag ADT
 * Fall, 2021
 *
 * Usage restrictions:
 *
 * You may use this code for exploration, experimentation, and furthering your
 * learning for this course. You may not use this code for any other
 * assignments, in my course or elsewhere, without explicit permission, in
 * advance, from myself (and the instructor of any other course).
 *
 * Further, you may not post nor otherwise share this code with anyone other than
 * current students in my sections of this course. Violation of these usage
 * restrictions will be considered a violation of the Wentworth Institute of
 * Technology Academic Honesty Policy.
 *
 * Do not remove this notice.
 *
 * @formatter:on
 */

package bag.linked ;

import bag.BagInterface ;

/**
 * A class of bags whose entries are stored in a chain of linked nodes. The bag is
 * never full.
 *
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @version 5.0
 * @param <T>
 *     The class of object the Bag will hold
 */
public final class LinkedBag<T> implements BagInterface<T>
    {

    private Node firstNode ;       // Reference to first node
    private int numberOfEntries ;

    /**
     * Initialize state to empty
     */
    public LinkedBag()
        {
        this.firstNode = null ;
        this.numberOfEntries = 0 ;
        
        } // end no-arg constructor


    /**
     * Adds a new entry to this bag.
     *
     * @param newEntry
     *     The object to be added as a new entry
     * @return True if the addition is successful, or false if not.
     */
    @Override
    public boolean add( T newEntry )  	      // OutOfMemoryError possible
        {
        // Add to beginning of chain:
//        Node newNode = new Node( newEntry ) ;
//        newNode.next = this.firstNode ; // Make new node reference rest of chain
//                                        // (firstNode is null if chain is empty)
//        this.firstNode = newNode ;      // New node is at beginning of chain
        this.firstNode = new Node( newEntry, this.firstNode ) ;
        this.numberOfEntries++ ;

        return true ;
        
        } // end add()


    /**
     * Retrieves all entries that are in this bag.
     *
     * @return A newly allocated array of all the entries in this bag.
     */
    @Override
    public T[] toArray()
        {
        // The cast is safe because the new array contains null entries
        @SuppressWarnings( "unchecked" )
        T[] result = (T[]) new Object[ this.numberOfEntries ] ; // Unchecked cast

        int index = 0 ;
        Node currentNode = this.firstNode ;
        while ( ( index < this.numberOfEntries ) && ( currentNode != null ) )
            {
            result[ index ] = currentNode.data ;
            index++ ;
            currentNode = currentNode.next ;
            } // end while

        return result ;
        
        } // end toArray()


    /**
     * Sees whether this bag is empty.
     *
     * @return True if this bag is empty, or false if not.
     */
    @Override
    public boolean isEmpty()
        {
        return this.numberOfEntries == 0 ;
        
        } // end isEmpty()


    /**
     * Gets the number of entries currently in this bag.
     *
     * @return The integer number of entries currently in this bag.
     */
    @Override
    public int getCurrentSize()
        {
        return this.numberOfEntries ;
        
        } // end getCurrentSize()


    /**
     * Removes one unspecified entry from this bag, if possible.
     *
     * @return Either the removed entry, if the removal was successful, or null.
     */
    @Override
    public T remove()
        {
        T result = null ;
        if ( this.firstNode != null )
            {
            result = this.firstNode.data ;
            this.firstNode = this.firstNode.next ; // Remove first node from chain
            this.numberOfEntries-- ;
            } // end if

        return result ;
        
        } // end remove()


    /**
     * Removes one occurrence of a given entry from this bag, if possible.
     *
     * @param anEntry
     *     The entry to be removed.
     * @return True if the removal was successful, or false otherwise.
     */
    @Override
    public boolean remove( T anEntry )
        {
        boolean result = false ;
        Node nodeN = getReferenceTo( anEntry ) ;

        if ( nodeN != null )
            {
            nodeN.data = this.firstNode.data ; // Replace located entry with entry in
            // first node
            this.firstNode = this.firstNode.next ;  // Remove first node
            this.numberOfEntries-- ;
            result = true ;
            } // end if

        return result ;
        
        } // end remove()


    /** Removes all entries from this bag. */
    @Override
    public void clear()
        {
        while ( !isEmpty() )
            {
            remove() ;
            }
        
        } // end clear()


    /**
     * Counts the number of times a given entry appears in this bag.
     *
     * @param anEntry
     *     The entry to be counted.
     * @return The number of times anEntry appears in this bag.
     */
    @Override
    public int getFrequencyOf( T anEntry )
        {
        int frequency = 0 ;
        int loopCounter = 0 ;
        Node currentNode = this.firstNode ;

        while ( ( loopCounter < this.numberOfEntries ) && ( currentNode != null ) )
            {
            if ( anEntry.equals( currentNode.data ) )
                {
                frequency++ ;
                } // end if

            loopCounter++ ;
            currentNode = currentNode.next ;
            } // end while

        return frequency ;
        
        } // end getFrequencyOf()


    /**
     * Tests whether this bag contains a given entry.
     *
     * @param anEntry
     *     The entry to locate.
     * @return True if the bag contains anEntry, or false otherwise.
     */
    @Override
    public boolean contains( T anEntry )
        {
        if ( anEntry == null )
            {
            return false ;
            }
        
        Node currentNode = this.firstNode ;

        while ( currentNode != null )
            {
            if ( anEntry.equals( currentNode.data ) )
                {
                return true ;
                }
            
            currentNode = currentNode.next ;
            
            } // end while

        return false ;
        
        } // end contains()


    // Locates a given entry within this bag.
    // Returns a reference to the node containing the entry, if located,
    // or null otherwise.
    private Node getReferenceTo( T anEntry )
        {
        boolean found = false ;
        Node currentNode = this.firstNode ;

        while ( !found && ( currentNode != null ) )
            {
            if ( anEntry.equals( currentNode.data ) )
                {
                found = true ;
                }
            else
                {
                currentNode = currentNode.next ;
                }
            } // end while

        return currentNode ;
        
        } // end getReferenceTo()

    
    /**
     * Construct for elements of the linked chain
     */
    private class Node
        {

        private T data ; // Entry in bag
        private Node next ; // Link to next node

        private Node( T dataPortion )
            {
            this( dataPortion, null ) ;
            
            } // end 1-arg constructor


        private Node( T dataPortion, Node nextNode )
            {
            this.data = dataPortion ;
            this.next = nextNode ;
            
            } // end 2-arg constructor
        
        } // end inner class Node

    } // end class LinkedBag