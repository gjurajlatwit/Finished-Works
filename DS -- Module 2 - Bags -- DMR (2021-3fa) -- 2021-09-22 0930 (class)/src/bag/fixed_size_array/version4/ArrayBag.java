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

package bag.fixed_size_array.version4 ;

import bag.BagInterface ;

/**
 * A class of bags whose entries are stored in a fixed-size array.
 * 
 * @author Frank M. Carrano, Timothy M. Henry
 * @version 5.0
 * @param <T>
 *     the class of object the Bag will hold
 */
public final class ArrayBag<T> implements BagInterface<T>
    {

    private final T[] bag ;
    private int numberOfEntries ;
    private boolean integrityOK = false ;
    
    private static final int DEFAULT_CAPACITY = 25 ;
    private static final int MAX_CAPACITY = 10_000 ;

    /** Creates an empty bag whose initial capacity is 25. */
    public ArrayBag()
        {
        this( DEFAULT_CAPACITY ) ;
        
        } // end no-arg constructor


    /**
     * Creates an empty bag having a given capacity.
     * 
     * @param desiredCapacity
     *     The integer capacity desired.
     */
    public ArrayBag( final int desiredCapacity )
        {
        if ( desiredCapacity <= MAX_CAPACITY )
            {
            // The cast is safe because the new array contains null entries
            @SuppressWarnings( "unchecked" )
            final T[] tempBag = (T[]) new Object[ desiredCapacity ] ;
            this.bag = tempBag ;
            
            this.numberOfEntries = 0 ;
            
            this.integrityOK = true ;
            }
        else
            {
            throw new IllegalStateException( "Attempt to create a bag whose " +
                                             "capacity exceeds allowed maximum." ) ;
            }
        
        } // end 1-arg constructor


    /**
     * Adds a new entry to this bag.
     * 
     * @param newEntry
     *     The non-null object to be added as a new entry.
     * @return true if the addition is successful, or false if not.
     */
    @Override
    public boolean add( final T newEntry )
        {
        checkIntegrity() ;
        
        boolean result = true ;     // typically we will succeed
        
        if ( isArrayFull() )
            {
            result = false ;        // no room
            }
        else if ( newEntry == null )
            {
            result = false ;        // won't accept null
            }
        else
            {  // Assertion: result is true here
            this.bag[ this.numberOfEntries ] = newEntry ;
            this.numberOfEntries++ ;
            } // end if

        return result ;
        
        } // end add()


    /**
     * Retrieves all entries that are in this bag.
     * 
     * @return A newly allocated array of all the entries in this bag.
     */
    @Override
    public T[] toArray()
        {
        checkIntegrity() ;

        // The cast is safe because the new array contains null entries.
        @SuppressWarnings( "unchecked" )
        final T[] result = (T[]) new Object[ this.numberOfEntries ] ;

        for ( int index = 0 ; index < this.numberOfEntries ; index++ )
            {
            result[ index ] = this.bag[ index ] ;
            } // end for
        
        return result ;
        
        // Note: The body of this method could consist of one return statement,
        // if you call Arrays.copyOf()
        
        } // end toArray()


    /**
     * Sees whether this bag is empty.
     * 
     * @return true if this bag is empty, or false if not.
     */
    @Override
    public boolean isEmpty()
        {
        return this.numberOfEntries == 0 ;
        
        } // end isEmpty()


    /**
     * Gets the current number of entries in this bag.
     * 
     * @return The integer number of entries currently in this bag.
     */
    @Override
    public int getCurrentSize()
        {
        return this.numberOfEntries ;
        
        } // end getCurrentSize()


    /**
     * Counts the number of times a given entry appears in this bag.
     * 
     * @param anEntry
     *     The entry to be counted.
     * @return The number of times anEntry appears in this ba.
     */
    @Override
    public int getFrequencyOf( final T anEntry )
        {
        checkIntegrity() ;
        int counter = 0 ;
        
        if ( anEntry == null )
            {
            return 0 ;
            }

        for ( int index = 0 ; index < this.numberOfEntries ; index++ )
            {
            if ( anEntry.equals( this.bag[ index ] ) )    // can't get NullPointerException
//            if ( this.bag[ index ].equals( anEntry ) )
                {
                counter++ ;
                } // end if
            } // end for

        return counter ;
        
        } // end getFrequencyOf()


    /**
     * Tests whether this bag contains a given entry.
     * 
     * @param anEntry
     *     The entry to locate.
     * @return true if this bag contains anEntry, or false otherwise.
     */
    @Override
    public boolean contains( final T anEntry )
        {
        checkIntegrity() ;
        
        return getIndexOf( anEntry ) >= 0 ;
        
        } // end contains()


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
     * Removes one unspecified entry from this bag, if possible.
     * 
     * @return Either the removed entry, if the removal was successful, or null.
     */
    @Override
    public T remove()
        {
        checkIntegrity() ;
        
        final T result = removeEntry( this.numberOfEntries - 1 ) ;
        
        return result ;
        
        } // end remove()


    /**
     * Removes one occurrence of a given entry from this bag.
     * 
     * @param anEntry
     *     The entry to be removed.
     * @return true if the removal was successful, or false if not.
     */
    @Override
    public boolean remove( final T anEntry )
        {
        checkIntegrity() ;
        
        final int index = getIndexOf( anEntry ) ;
        final T result = removeEntry( index ) ;
        
//        return anEntry.equals( result ) ;   // potential NullPointerException
//        return result.equals( anEntry ) ;   // NullPointerException if anEntry not found
        return result != null ;
        
        } // end remove()


    // Returns true if the array bag is full, or false if not.
    private boolean isArrayFull()
        {
        return this.numberOfEntries == this.bag.length ;
        
        } // end isArrayFull()


    // Locates a given entry within the array bag.
    // Returns the index of the entry, if located,
    // or -1 otherwise.
    // Precondition: checkInitialization has been called.
    private int getIndexOf( final T anEntry )
        {
        int where = -1 ;
        boolean found = false ;
        int index = 0 ;
        
        if ( anEntry == null )
            {
            return -1 ; // can't find null
            }

        while ( !found && ( index < this.numberOfEntries ) )
            {
            if ( anEntry.equals( this.bag[ index ] ) )  // potential NullPointerException
                {
                found = true ;
                where = index ;
                } // end if
            index++ ;
            } // end while

        // Assertion: If where > -1, anEntry is in the array bag, and it
        // equals bag[where]; otherwise, anEntry is not in the array.

        return where ;
        
        } // end getIndexOf()


    // Removes and returns the entry at a given index within the array.
    // If no such entry exists, returns null.
    // Precondition: 0 <= givenIndex < numberOfEntries.
    // Precondition: checkInitialization has been called.
    private T removeEntry( final int givenIndex )
        {
        T result = null ;

        if ( !isEmpty() && ( givenIndex >= 0 ) )
            {
            result = this.bag[ givenIndex ] ;   // Entry to remove
            final int lastIndex = this.numberOfEntries - 1 ;
            this.bag[ givenIndex ] = this.bag[ lastIndex ] ;
                                // Replace entry to remove with last entry
            this.bag[ lastIndex ] = null ;  // Remove reference to last entry
            this.numberOfEntries-- ;
            } // end if

        return result ;
        
        } // end removeEntry()


    // Throws an exception if this object is not initialized.
    private void checkIntegrity()
        {
        if ( !this.integrityOK )
            {
            throw new SecurityException( "ArrayBag object is corrupt." ) ;
            }
        
        } // end checkIntegrity()
    
    } // end class ArrayBag
