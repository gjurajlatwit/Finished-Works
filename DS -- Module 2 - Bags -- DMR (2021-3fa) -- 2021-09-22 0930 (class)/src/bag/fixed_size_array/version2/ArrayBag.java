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

package bag.fixed_size_array.version2 ;

import bag.BagInterface ;

/**
 * A class that implements a bag of objects by using an array. INCOMPLETE DEFINITION;
 * includes security checks and definitions for the methods isEmpty, getCurrentSize,
 * getFrequencyOf, and contains.
 *
 * @author Frank M. Carrano, Timothy M. Henry
 * @version 5.0
 * @param <T>
 *     The class of object the Bag will hold
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
     *     The object to be added as a new entry.
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
        // if you call Arrays.copyOf
        
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

        for ( int index = 0 ; index < this.numberOfEntries ; index++ )
            {
            if ( anEntry.equals( this.bag[ index ] ) )
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

        boolean found = false ;
        int index = 0 ;

        while ( !found && ( index < this.numberOfEntries ) )
            {
            if ( anEntry.equals( this.bag[ index ] ) )
                {
                found = true ;
                } // end if
            index++ ;
            } // end while

        return found ;
        
        } // end contains()


    /**
     * Removes one unspecified entry from this bag, if possible.
     *
     * @return Either the removed entry, if the removal was successful, or null.
     */
    @Override
    public T remove()
        {
        checkIntegrity() ;
        
        T result = null ;

        if ( this.numberOfEntries > 0 )
            {
            result = this.bag[ this.numberOfEntries - 1 ] ;
            this.bag[ this.numberOfEntries - 1 ] = null ;
            this.numberOfEntries-- ;
            }   // end if

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
        return false ; // STUB
        
        } // end remove()


    /** Removes all entries from this bag. */
    @Override
    public void clear()
        {
        // STUB
        
        } // end clear()


    /** Returns true if the array bag is full, or false if not. */
    private boolean isArrayFull()
        {
        return this.numberOfEntries == this.bag.length ;
        
        } // end isArrayFull()


    /** Throws an exception if this object is not initialized. */
    private void checkIntegrity()
        {
        if ( !this.integrityOK )
            {
            throw new SecurityException( "ArrayBag object is corrupt." ) ;
            }
        
        } // end checkIntegrity()

    } // end class ArrayBag
