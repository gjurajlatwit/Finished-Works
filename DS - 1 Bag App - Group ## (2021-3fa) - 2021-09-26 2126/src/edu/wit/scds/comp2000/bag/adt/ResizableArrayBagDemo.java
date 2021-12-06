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

/**
 * A demonstration of the class ResizableArrayBag
 *
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @version 5.0
 * @author David M Rosenberg
 * @version 5.1.0 reformat per class standard
 */
public class ResizableArrayBagDemo
    {

    /**
     * Test driver
     *
     * @param args
     *     unused
     */
    public static void main( final String[] args )
        {
        // A bag whose initial capacity is small
        final BagInterface<String> aBag = new ResizableArrayBag<>( 3 ) ;
        testIsEmpty( aBag, true ) ;

        System.out.println( "Adding to the bag more strings than its initial capacity." ) ;
        final String[] contentsOfBag = { "A", "D", "B", "A", "C", "A", "D" } ;
        testAdd( aBag, contentsOfBag ) ;

        testIsEmpty( aBag, false ) ;
        final String[] testStrings2 = { "A", "B", "C", "D", "Z" } ;
        testFrequency( aBag, testStrings2 ) ;
        testContains( aBag, testStrings2 ) ;

        // Removing strings
        final String[] testStrings3 = { "", "B", "A", "C", "Z" } ;
        testRemove( aBag, testStrings3 ) ;

        System.out.println( "\nClearing the bag:" ) ;
        aBag.clear() ;
        testIsEmpty( aBag, true ) ;
        displayBag( aBag ) ;

        }  // end main()


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

        }  // end displayBag()


    // Tests the method add.
    private static void testAdd( final BagInterface<String> aBag,
                                 final String[] content )
        {
        System.out.print( "Adding to the bag: " ) ;
        for ( final String element : content )
            {
            aBag.add( element ) ;
            System.out.print( element + " " ) ;
            } // end for
        System.out.println() ;

        displayBag( aBag ) ;

        }  // end testAdd()


    // Tests the method contains.
    private static void testContains( final BagInterface<String> aBag,
                                      final String[] tests )
        {
        System.out.println( "\nTesting the method contains:" ) ;
        for ( final String test : tests )
            {
            System.out.println( "Does this bag contain " + test + "? " +
                                aBag.contains( test ) ) ;
            }

        }    // end testContains()


    // Tests the method getFrequencyOf.
    private static void testFrequency( final BagInterface<String> aBag,
                                       final String[] tests )
        {
        System.out.println( "\nTesting the method getFrequencyOf:" ) ;
        for ( final String test : tests )
            {
            System.out.println( "In this bag, the count of " + test + " is " +
                                aBag.getFrequencyOf( test ) ) ;
            }

        }    // end testFrequency()


    // Tests the method isEmpty.
    // correctResult indicates what isEmpty should return.
    private static void testIsEmpty( final BagInterface<String> aBag,
                                     final boolean correctResult )
        {
        System.out.print( "Testing isEmpty with " ) ;
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
        System.out.println() ;

        }  // end testIsEmpty()


    // Tests the two remove methods.
    private static void testRemove( final BagInterface<String> aBag,
                                    final String[] tests )
        {
        for ( final String aString : tests )
            {
            if ( aString.equals( "" ) || ( aString == null ) )
                {
                // Test remove()
                System.out.println( "\nRemoving a string from the bag:" ) ;
                final String removedString = aBag.remove() ;
                System.out.println( "remove() returns " + removedString ) ;
                }
            else
                {
                // Test remove(aString)
                System.out.println( "\nRemoving \"" + aString +
                                    "\" from the bag:" ) ;
                final boolean result = aBag.remove( aString ) ;
                System.out.println( "remove(\"" + aString + "\") returns " +
                                    result ) ;
                } // end if

            displayBag( aBag ) ;
            } // end for

        }  // end testRemove()

    }   // end class ResizableArrayBagDemo

//  @formatter:off
/*
 Expected output:


 Testing isEmpty with an empty bag:
 isEmpty finds the bag empty: OK.

 Adding to the bag more strings than its initial capacity.
 Adding to the bag: A D B A C A D
 The bag contains 7 string(s), as follows:
 A D B A C A D
 Testing isEmpty with a bag that is not empty:
 isEmpty finds the bag not empty: OK.


 Testing the method getFrequencyOf:
 In this bag, the count of A is 3
 In this bag, the count of B is 1
 In this bag, the count of C is 1
 In this bag, the count of D is 2
 In this bag, the count of Z is 0

 Testing the method contains:
 Does this bag contain A? true
 Does this bag contain B? true
 Does this bag contain C? true
 Does this bag contain D? true
 Does this bag contain Z? false

 Removing a string from the bag:
 remove() returns D
 The bag contains 6 string(s), as follows:
 A D B A C A

 Removing "B" from the bag:
 remove("B") returns true
 The bag contains 5 string(s), as follows:
 A D A A C

 Removing "A" from the bag:
 remove("A") returns true
 The bag contains 4 string(s), as follows:
 C D A A

 Removing "C" from the bag:
 remove("C") returns true
 The bag contains 3 string(s), as follows:
 A D A

 Removing "Z" from the bag:
 remove("Z") returns false
 The bag contains 3 string(s), as follows:
 A D A

 Clearing the bag:
 Testing isEmpty with an empty bag:
 isEmpty finds the bag empty: OK.

 The bag contains 0 string(s), as follows:
*/
//  @formatter:on
