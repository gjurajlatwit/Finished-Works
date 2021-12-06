/* @formatter:off
 *
 * Dave Rosenberg
 * Comp 2000 - Data Structures
 * Demonstration/Lab: Bag ADT
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

package bag ;

/**
 * An interface that describes the operations of a bag of objects.
 * 
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @version 5.0
 * 
 * @param <T>
 *     The class of item the Bag will hold. */
public interface BagInterface<T>
    {

    /**
     * Gets the current number of entries in this bag.
     * 
     * @return The integer number of entries currently in the bag.
     */
    public int getCurrentSize() ;


    /**
     * Sees whether this bag is empty.
     * 
     * @return true if the bag is empty, or false if not.
     */
    public boolean isEmpty() ;


    /**
     * Adds a new entry to this bag.
     * 
     * @param newEntry
     *     The object to be added as a new entry.
     * @return true if the addition is successful, or false if not.
     */
    public boolean add( T newEntry ) ;


    /**
     * Removes one unspecified entry from this bag, if possible.
     * 
     * @return Either the removed entry, if the removal. was successful, or null.
     */
    public T remove() ;


    /**
     * Removes one occurrence of a given entry from this bag, if possible.
     * 
     * @param anEntry
     *     The entry to be removed.
     * @return true if the removal was successful, or false if not.
     */
    public boolean remove( T anEntry ) ;


    /** Removes all entries from this bag. */
    public void clear() ;


    /**
     * Counts the number of times a given entry appears in this bag.
     * 
     * @param anEntry
     *     The entry to be counted.
     * @return The number of times anEntry appears in the bag.
     */
    public int getFrequencyOf( T anEntry ) ;


    /**
     * Tests whether this bag contains a given entry.
     * 
     * @param anEntry
     *     The entry to find.
     * @return true if the bag contains anEntry, or false if not.
     */
    public boolean contains( T anEntry ) ;


    /**
     * Retrieves all entries that are in this bag.
     * 
     * @return A newly allocated array of all the entries in the bag. Note: If the
     *     bag is empty, the returned array is empty.
     */
    public T[] toArray() ;
    
    } // end interface BagInterface
