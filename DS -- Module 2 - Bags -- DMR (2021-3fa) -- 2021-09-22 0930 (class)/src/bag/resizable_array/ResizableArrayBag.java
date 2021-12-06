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

package bag.resizable_array ;

import java.util.Arrays ;

import bag.BagInterface ;

/**
 * A class that implements a bag of objects by using an array. The bag is never full.
 * 
 * @author Frank M. Carrano, Timothy M. Henry
 * @version 5.0
 * @param <T>
 *     The class of object the Bag will hold
 */
public final class ResizableArrayBag<T> implements BagInterface<T>
    {

    private T[] bag ;               // Cannot be final due to doubling
    private int numberOfEntries ;
    
    private boolean integrityOK = false ;
    
    private static final int DEFAULT_CAPACITY = 25 ; // Initial capacity of bag
    private static final int MAX_CAPACITY = 10_000 ;

    /** Creates an empty bag whose initial capacity is the default. */
    public ResizableArrayBag()
        {
        this( DEFAULT_CAPACITY ) ;
        
        } // end no-arg constructor


    /**
     * Creates an empty bag having a given initial capacity.
     * 
     * @param initialCapacity
     *     The integer capacity desired.
     */
    public ResizableArrayBag( final int initialCapacity )
        {
        checkCapacity( initialCapacity ) ;

        // The cast is safe because the new array contains null entries
        @SuppressWarnings( "unchecked" )
        final T[] tempBag = (T[]) new Object[ initialCapacity ] ; // Unchecked cast
        this.bag = tempBag ;
        
        this.numberOfEntries = 0 ;
        
        this.integrityOK = true ;
        
        } // end constructor


    /**
     * Creates a bag containing given entries.
     * 
     * @param contents
     *     An array of objects.
     */
    public ResizableArrayBag( final T[] contents )
        {
//        checkCapacity( contents.length ) ;
//        
//        this.bag = Arrays.copyOf( contents, contents.length ) ;
//        this.numberOfEntries = contents.length ;
//        
//        this.integrityOK = true ;
        this( contents.length ) ;
        
        for ( T newEntry : contents )
            {
            if ( newEntry != null )
                {
                addEntry( newEntry ) ;
                }
            }
        
        } // end constructor


    /**
     * Adds a new entry to this bag.
     * 
     * @param newEntry
     *     The object to be added as a new entry.
     * @return true if the add operation succeeded, false if newEntry is null.
     */
    public boolean add( final T newEntry )
        {
        checkIntegrity() ;
        
        if ( newEntry == null )
            {
            return false ;
            }
        
        if ( isArrayFull() )
            {
            doubleCapacity() ;
            } // end if

        addEntry( newEntry ) ;

        return true ;
        
        } // end add
    
    
    /**
     * Add the provided entry to the bag
     * 
     * @param newEntry
     *     the item to add to the bag
     */
    private void addEntry ( T newEntry )
        {
        this.bag[ this.numberOfEntries ] = newEntry ;
        this.numberOfEntries++ ;
        
        }   // end addEntry()


    /**
     * Retrieves all entries that are in this bag.
     * 
     * @return A newly allocated array of all the entries in this bag.
     */
    public T[] toArray()
        {
        checkIntegrity() ;

//        // The cast is safe because the new array contains null entries.
//        @SuppressWarnings( "unchecked" )
//        final T[] result = (T[]) new Object[ this.numberOfEntries ] ; // Unchecked cast
//        for ( int index = 0 ; index < this.numberOfEntries ; index++ )
//            {
//            result[ index ] = this.bag[ index ] ;
//            } // end for
//
//        return result ;
        
        return Arrays.copyOf( this.bag, this.numberOfEntries ) ;
        
        } // end toArray()


    /**
     * Sees whether this bag is empty.
     * 
     * @return True if this bag is empty, or false if not.
     */
    public boolean isEmpty()
        {
        return this.numberOfEntries == 0 ;
        
        } // end isEmpty()


    /**
     * Gets the current number of entries in this bag.
     * 
     * @return The integer number of entries currently in this bag.
     */
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
    public int getFrequencyOf( final T anEntry )
        {
        checkIntegrity() ;
        
        if ( anEntry == null )
            {
            return 0 ;
            }
        
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
     * @return True if this bag contains anEntry, or false otherwise.
     */
    public boolean contains( final T anEntry )
        {
        checkIntegrity() ;
        
        return getIndexOf( anEntry ) > -1 ; // or >= 0
        
        } // end contains()


    /** Removes all entries from this bag. */
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
    public T remove()
        {
        checkIntegrity() ;
        
        return removeEntry( this.numberOfEntries - 1 ) ;
        
        } // end remove()


    /**
     * Removes one occurrence of a given entry from this bag.
     * 
     * @param anEntry
     *     The entry to be removed.
     * @return True if the removal was successful, or false if not.
     */
    public boolean remove( final T anEntry )
        {
        checkIntegrity() ;
        
        final int index = getIndexOf( anEntry ) ;
        final T result = removeEntry( index ) ;
        
//        return anEntry.equals( result ) ;
        return result != null ;
        
        } // end remove()


    // Locates a given entry within the array bag.
    // Returns the index of the entry, if located,
    // or -1 otherwise.
    // Precondition: checkIntegrity has been called.
    private int getIndexOf( final T anEntry )
        {
        if ( anEntry == null )
            {
            return -1 ;
            }
        
//        int where = -1 ;
//        boolean found = false ;
//        int index = 0 ;
//
//        while ( !found && ( index < this.numberOfEntries ) )
//            {
//            if ( anEntry.equals( this.bag[ index ] ) )
//                {
//                found = true ;
//                where = index ;
//                } // end if
//            
//            index++ ;
//            } // end while
//
//        // Assertion: If where > -1, anEntry is in the array bag, and it
//        // equals bag[where]; otherwise, anEntry is not in the array.
//
//        return where ;
        
        for ( int i = 0; i < this.numberOfEntries; i++ )
            {
            if ( anEntry.equals( this.bag[ i ] ) )
                {
                return i ;
                }
            }
        
        return -1 ;
        
        } // end getIndexOf()


    // Removes and returns the entry at a given index within the array.
    // If no such entry exists, returns null.
    // Precondition: 0 <= givenIndex < numberOfEntries.
    // Precondition: checkIntegrity has been called.
    private T removeEntry( final int givenIndex )
        {
        T result = null ;

        if ( !isEmpty() && ( givenIndex >= 0 ) )
            {
            result = this.bag[ givenIndex ] ;          // Entry to remove
            
            final int lastIndex = this.numberOfEntries - 1 ;
            this.bag[ givenIndex ] = this.bag[ lastIndex ] ;
                                        // Replace entry to remove with last entry
            this.bag[ lastIndex ] = null ;  // Remove reference to last entry
            this.numberOfEntries-- ;
            
            } // end if

        return result ;
        
        } // end removeEntry()


    // Returns true if the array bag is full, or false if not.
    private boolean isArrayFull()
        {
        return this.numberOfEntries == this.bag.length ;
        
        } // end isArrayFull()


    // Doubles the size of the array bag.
    // Precondition: checkInitialization has been called.
    private void doubleCapacity()
        {
        final int newLength = 2 * this.bag.length ;
        
        checkCapacity( newLength ) ;
        
        this.bag = Arrays.copyOf( this.bag, newLength ) ;
        
        } // end doubleCapacity()


    // Throws an exception if the client requests a capacity that is too large.
    private void checkCapacity( final int capacity )
        {
        if ( capacity > MAX_CAPACITY )
            {
            throw new IllegalStateException( "Attempt to create a bag whose " +
                            "capacity exceeds allowed maximum of " + MAX_CAPACITY ) ;
            }
        
        } // end checkCapacity()


    // Throws an exception if receiving object is not initialized.
    private void checkIntegrity()
        {
        if ( !this.integrityOK )
            {
            throw new SecurityException( "ResizableArrayBag object is corrupt." ) ;
            }
        
        } // end checkIntegrity()
    
    } // end class ResizableArrayBag