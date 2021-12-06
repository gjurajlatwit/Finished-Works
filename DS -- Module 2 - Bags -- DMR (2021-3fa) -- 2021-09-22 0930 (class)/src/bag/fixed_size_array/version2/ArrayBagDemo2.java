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
 * A test of the methods isEmpty, getCurrentSize, getFrequencyOf, and contains for
 * the class ArrayBag. Assumes the methods add and toArray are correct.
 * 
 * @author Frank M. Carrano, Timothy M. Henry
 * @version 5.0
 */
public class ArrayBagDemo2
    {

    /**
     * Test driver
     * 
     * @param args
     *     -unused-
     */
    public static void main( final String[] args )
        {
        // A bag that is not full
        BagInterface<String> aBag = new ArrayBag<>() ;

        // Tests on an empty bag
        testIsEmpty( aBag, true ) ;
        final String[] testStrings1 = { "A" } ;
        testFrequency( aBag, testStrings1 ) ;
        testContains( aBag, testStrings1 ) ;

        // Adding strings
        final String[] contentsOfBag1 = { "A", "A", "B", "A", "C", "A" } ;
        testAdd( aBag, contentsOfBag1 ) ;

        // Tests on a bag that is not empty
        testIsEmpty( aBag, false ) ;
        final String[] testStrings2 = { "A", "B", "C", "D", "Z" } ;
        testFrequency( aBag, testStrings2 ) ;
        testContains( aBag, testStrings2 ) ;

        // ----------------------------------------------------------------------

        // A bag that will be full
        aBag = new ArrayBag<>( 7 ) ;
        System.out.println( "\nA new empty bag:" ) ;

        // Tests on an empty bag
        testIsEmpty( aBag, true ) ;
        testFrequency( aBag, testStrings1 ) ;
        testContains( aBag, testStrings1 ) ;

        // Adding strings
        final String[] contentsOfBag2 = { "A", "B", "A", "C", "B", "C", "D" } ;
        testAdd( aBag, contentsOfBag2 ) ;

        // Tests on a bag that is full
        testIsEmpty( aBag, false ) ;
        testFrequency( aBag, testStrings2 ) ;
        testContains( aBag, testStrings2 ) ;
        
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


    // Tests the method isEmpty.
    // correctResult indicates what isEmpty should return.
    private static void testIsEmpty( final BagInterface<String> aBag,
                                     final boolean correctResult )
        {
        System.out.print( "\nTesting the method isEmpty with " ) ;
        if ( correctResult )
            {
            System.out.println( "an empty bag:" ) ;
            }
        else
            {
            System.out.println( "a bag that is not empty:" ) ;
            }

        System.out.print( "isEmpty finds the bag " ) ;
        if ( correctResult && aBag.isEmpty() )
            {
            System.out.println( "empty: OK." ) ;
            }
        else if ( correctResult )
            {
            System.out.println( "not empty, but it is empty: ERROR." ) ;
            }
        else if ( !correctResult && aBag.isEmpty() )
            {
            System.out.println( "empty, but it is not empty: ERROR." ) ;
            }
        else
            {
            System.out.println( "not empty: OK." ) ;
            }
        
        } // end testIsEmpty()


    // Tests the method getFrequencyOf.
    private static void testFrequency( final BagInterface<String> aBag,
                                       final String[] tests )
        {
        System.out.println( "\nTesting the method getFrequencyOf:" ) ;
        for ( int index = 0 ; index < tests.length ; index++ )
            {
            System.out.println( "In this bag, the count of " + tests[ index ] +
                                " is " + aBag.getFrequencyOf( tests[ index ] ) ) ;
            }
        
        } // end testFrequency()


    // Tests the method contains.
    private static void testContains( final BagInterface<String> aBag,
                                      final String[] tests )
        {
        System.out.println( "\nTesting the method contains:" ) ;
        for ( int index = 0 ; index < tests.length ; index++ )
            {
            System.out.println( "Does this bag contain " + tests[ index ] + "? " +
                                aBag.contains( tests[ index ] ) ) ;
            }
        
        } // end testContains()


    // Tests the method toArray while displaying the bag.
    private static void displayBag( final BagInterface<String> aBag )
        {
        System.out.println( "The bag contains " + aBag.getCurrentSize() +
                            " string(s), as follows:" ) ;
        final Object[] bagArray = aBag.toArray() ;
        for ( final Object element : bagArray )
            {
            System.out.print( element + " " ) ;
            } // end for

        System.out.println() ;
        
        } // end displayBag()
    
    } // end class ArrayBagDemo2

/* @formatter:off
 * 
 * Testing the method isEmpty with an empty bag:
 * isEmpty finds the bag empty: OK.
 * 
 * Testing the method getFrequencyOf:
 * In this bag, the count of A is 0
 * 
 * Testing the method contains:
 * Does this bag contain A? false
 * Adding the following strings to the bag: A A B A C A 
 * The bag contains 6 string(s), as follows:
 * A A B A C A 
 * 
 * Testing the method isEmpty with a bag that is not empty:
 * isEmpty finds the bag not empty: OK.
 * 
 * Testing the method getFrequencyOf:
 * In this bag, the count of A is 4
 * In this bag, the count of B is 1
 * In this bag, the count of C is 1
 * In this bag, the count of D is 0
 * In this bag, the count of Z is 0
 * 
 * Testing the method contains:
 * Does this bag contain A? true
 * Does this bag contain B? true
 * Does this bag contain C? true
 * Does this bag contain D? false
 * Does this bag contain Z? false
 * 
 * A new empty bag:
 * 
 * Testing the method isEmpty with an empty bag:
 * isEmpty finds the bag empty: OK.
 * 
 * Testing the method getFrequencyOf:
 * In this bag, the count of A is 0
 * 
 * Testing the method contains:
 * Does this bag contain A? false
 * Adding the following strings to the bag: A B A C B C D 
 * The bag contains 7 string(s), as follows:
 * A B A C B C D 
 * 
 * Testing the method isEmpty with a bag that is not empty:
 * isEmpty finds the bag not empty: OK.
 * 
 * Testing the method getFrequencyOf:
 * In this bag, the count of A is 2
 * In this bag, the count of B is 2
 * In this bag, the count of C is 2
 * In this bag, the count of D is 1
 * In this bag, the count of Z is 0
 * 
 * Testing the method contains:
 * Does this bag contain A? true
 * Does this bag contain B? true
 * Does this bag contain C? true
 * Does this bag contain D? true
 * Does this bag contain Z? false
 * 
 * @formatter:on
 */