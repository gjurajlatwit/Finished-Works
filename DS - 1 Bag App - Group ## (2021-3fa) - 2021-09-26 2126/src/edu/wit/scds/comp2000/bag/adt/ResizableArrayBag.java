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

package edu.wit.scds.comp2000.bag.adt ;

import edu.wit.scds.comp2000.bag.BagInterface ;

import java.util.Arrays ;

/**
 * A class that implements a bag of objects by using an array. The bag is never full.
 * The application may store {@code null} in this implementation.
 *
 * @author Frank M. Carrano, Timothy M. Henry
 * @version 5.0
 * @author David M Rosenberg
 * @version 5.1 2019-09-17
 *     <ul>
 *     <li>reformatted and minor changes
 *     <li>increased maximum capacity from 10,000 to 100,000 to support spell checker
 *     </ul>
 * @version 5.2 2020-01-26
 *     <ul>
 *     <li>increased maximum capacity to 250K
 *     <li>convert private method block comments to Javadoc
 *     </ul>
 * @version 5.3 2021-09-26 streamline various methods
 * @param <T>
 *     class of items the bag will hold
 */
public final class ResizableArrayBag<T> implements BagInterface<T>
    {

    private static final int DEFAULT_CAPACITY = 25 ; // Initial capacity of bag
    private static final int MAX_CAPACITY = 250_000 ;
    
    private T[] bag ;                       // Cannot be final due to doubling
    private int numberOfEntries ;
    
    private boolean integrityOK = false ;

    /** Creates an empty bag with the default capacity. */
    public ResizableArrayBag()
        {
        this( DEFAULT_CAPACITY ) ;

        }   // end no-arg constructor


    /**
     * Creates an empty bag having a given initial capacity.
     *
     * @param initialCapacity
     *     The initial desired capacity.
     */
    public ResizableArrayBag( final int initialCapacity )
        {
        checkCapacity( initialCapacity ) ;

        // The cast is safe because the new array contains null entries
        @SuppressWarnings( "unchecked" )
        final T[] tempBag = (T[]) new Object[ initialCapacity ] ;
        this.bag = tempBag ;
        
        this.numberOfEntries = 0 ;
        
        this.integrityOK = true ;

        }   // end 1-arg (initial size) constructor


    /**
     * Creates a bag containing given entries.
     *
     * @param contents
     *     An array of objects.
     */
    public ResizableArrayBag( final T[] contents )
        {
        checkCapacity( contents.length ) ;

        this.bag = Arrays.copyOf( contents, contents.length ) ;
        
        this.numberOfEntries = contents.length ;
        
        this.integrityOK = true ;

        }   // end 1-arg (source array) constructor


    /**
     * Adds a new entry to this bag.
     *
     * @param newEntry
     *     The object to be added as a new entry.
     * @return true
     */
    @Override
    public boolean add( final T newEntry )
        {
        checkIntegrity() ;

        if ( isArrayFull() )
            {
            doubleCapacity() ;
            } // end if

        this.bag[ this.numberOfEntries ] = newEntry ;
        this.numberOfEntries++ ;

        return true ;

        }   // end add()


    /** Removes all entries from this bag. */
    @Override
    public void clear()
        {
        while ( !isEmpty() )
            {
            remove() ;
            }

        }   // end clear()


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

        return getIndexOf( anEntry ) >= 0 ;         // or > -1

        }   // end contains()


    /**
     * Gets the current number of entries in this bag.
     *
     * @return The integer number of entries currently in this bag.
     */
    @Override
    public int getCurrentSize()
        {
        return this.numberOfEntries ;

        }   // end getCurrentSize()


    /**
     * Counts the number of times a given entry appears in this bag.
     *
     * @param anEntry
     *     The entry to be counted.
     * @return The number of times anEntry appears in this bag.
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

        }   // end getFrequencyOf()


    /**
     * Sees whether this bag is empty.
     *
     * @return true if this bag is empty, or false if not.
     */
    @Override
    public boolean isEmpty()
        {
        return this.numberOfEntries == 0 ;

        }   // end isEmpty()


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

        }   // end remove()


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

        return result != null ;

        }   // end remove()


    /**
     * Retrieves all entries that are in this bag.
     *
     * @return A newly allocated array of all the entries in this bag.
     */
    @Override
    public T[] toArray()
        {
        checkIntegrity() ;

        return Arrays.copyOf( this.bag, this.numberOfEntries ) ;

        }   // end toArray()


    /**
     * Throws an exception if the client requests a capacity that is too large.
     *
     * @param capacity
     *     the client requested capacity
     */
    private static void checkCapacity( final int capacity )
        {
        if ( capacity > MAX_CAPACITY )
            {
            throw new IllegalStateException( "Attempt to create a bag whose " +
                                             "capacity exceeds allowed " +
                                             "maximum of " + MAX_CAPACITY ) ;
            }

        }   // end checkCapacity()


    /**
     * Throws an exception if receiving object is not initialized.
     */
    private void checkIntegrity()
        {
        if ( !this.integrityOK )
            {
            throw new SecurityException( "ArrayBag object is corrupt." ) ;
            }

        }   // end checkIntegrity()


    /**
     * Doubles the size of the array bag.
     * <p>
     * Precondition: checkInitialization has been called.
     */
    private void doubleCapacity()
        {
        final int newLength = 2 * this.bag.length ;

        checkCapacity( newLength ) ;

        this.bag = Arrays.copyOf( this.bag, newLength ) ;

        }   // end doubleCapacity()


    /**
     * Locates a given entry within the array bag.
     * <p>
     * Precondition: checkIntegrity has been called.
     *
     * @param anEntry
     *     the entry to locate
     * @return the index of the entry, if located, or -1 otherwise
     */
    private int getIndexOf( final T anEntry )
        {

        for ( int index = 0; index < this.numberOfEntries; index++ )
            {
            if ( anEntry.equals( this.bag[ index ] ) )
                {
                return index ;
                } // end if
            } // end for

        return -1 ;

        }   // end getIndexOf()


    /**
     * Tests for an array full condition.
     *
     * @return true if the array bag is full, or false if not
     */
    private boolean isArrayFull()
        {
        return this.numberOfEntries == this.bag.length ;

        }   // end isArrayFull()


    /**
     * Removes and returns the entry at a given index within the array.
     * <p>
     * Precondition: 0 <= givenIndex < numberOfEntries.<br>
     * Precondition: checkIntegrity has been called.
     *
     * @param givenIndex
     *     the entry's position in the array
     * @return the entry or null if no such entry exists
     */
    private T removeEntry( final int givenIndex )
        {
        T result = null ;

        if ( !isEmpty() && ( givenIndex >= 0 ) )
            {
            result = this.bag[ givenIndex ] ;  // Entry to remove
            
            final int lastIndex = this.numberOfEntries - 1 ;
            this.bag[ givenIndex ] = this.bag[ lastIndex ] ;
                                    // Replace entry to remove with last entry
            this.bag[ lastIndex ] = null ;  // Remove reference in last entry
            
            this.numberOfEntries-- ;
            } // end if

        return result ;

        }   // end removeEntry()

    } // end class ResizableArrayBag