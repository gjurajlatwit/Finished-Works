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

package bag.fixed_size_array.version1 ;

import bag.BagInterface ;

/**
 * A test of the constructors and the methods add and toArray, as defined in the
 * first draft of the class ArrayBag.
 * 
 * @author Frank M. Carrano, Timothy M. Henry
 * @version 5.0
 */
public class ArrayBagDemo1
    {

    /**
     * Test driver
     * 
     * @param args
     *     -unused-
     */
    public static void main( final String[] args )
        {
        // Adding to an initially empty bag with sufficient capacity
        System.out.println( "Testing an initially empty bag with " +
                            " sufficient capacity:" ) ;
        BagInterface<String> aBag = new ArrayBag<>() ;
        final String[] contentsOfBag1 = { "A", "A", "B", "A", "C", "A" } ;
        testAdd( aBag, contentsOfBag1 ) ;

        // Filling an initially empty bag to capacity
        System.out.println( "\nTesting an initially empty bag that " +
                            " will be filled to capacity:" ) ;
        aBag = new ArrayBag<>( 7 ) ;
        final String[] contentsOfBag2 = { "A", "B", "A", "C", "B", "C", "D",
                                          "another string" } ;
        testAdd( aBag, contentsOfBag2 ) ;
        
        } // end main()


    // Tests the method add.
    private static void testAdd( final BagInterface<String> aBag,
                                 final String[] content )
        {
        System.out.print( "Adding the following strings to the bag: " ) ;
        for ( int index = 0 ; index < content.length ; index++ )
            {
            if ( aBag.add( content[ index ] ) )
                {
                System.out.print( content[ index ] + " " ) ;
                }
            else
                {
                System.out.print( "\nUnable to add '" + content[ index ] +
                                  "' to the bag." ) ;
                }
            } // end for
        System.out.println() ;

        displayBag( aBag ) ;
        
        } // end testAdd()


    // Tests the method toArray while displaying the bag.
    private static void displayBag( final BagInterface<String> aBag )
        {
        System.out.println( "The bag contains the following string(s):" ) ;
        final Object[] bagArray = aBag.toArray() ;
        for ( final Object element : bagArray )
            {
            System.out.print( element + " " ) ;
            } // end for

        System.out.println() ;
        
        } // end displayBag()
    
    } // end class ArrayBagDemo1

/* @formatter:off
 * 
 * Testing an initially empty bag with  sufficient capacity:
 * Adding the following strings to the bag: A A B A C A 
 * The bag contains the following string(s):
 * A A B A C A 
 * 
 * Testing an initially empty bag that  will be filled to capacity:
 * Adding the following strings to the bag: A B A C B C D 
 * Unable to add 'another string' to the bag.
 * The bag contains the following string(s):
 * A B A C B C D 
 * 
 * @formatter:on
 */
