
package Exceptions.Milk ;

/**
 * Demonstrates the behavior of a finally block. Listing J3-6 in Segment J3.11 of
 * Java Interlude 3.
 *
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @version 5.0
 */
public class Refrigerator
    {

    public static void openRefrigerator()
        {
        System.out.println( "Open the refrigerator door." ) ;

        } // end openRefrigerator


    public static void takeOutMilk() throws NoMilkException
        {
        if ( Math.random() < 0.6 )
            {
            System.out.println( "Take out the milk." ) ;
            }
        else
            {
            throw new NoMilkException( "Out of Milk!" ) ;
            }

        } // end takeOutMilk


    public static void pourMilk()
        {
        System.out.println( "Pour the milk." ) ;

        } // end pourMilk


    public static void putBackMilk()
        {
        System.out.println( "Put the milk back." ) ;

        } // end putBackMilk


    public static void closeRefrigerator()
        {
        System.out.println( "Close the refrigerator door." ) ;

        } // end closeRefrigerator

    } // end GetMilk

/* @formatter:off
OUTPUT 1:
 Open the refrigerator door.
 Take out the milk.
 Pour the milk.
 Put the milk back.
 Close the refrigerator door.

OUTPUT 2:
 Open the refrigerator door.
 Out of Milk!
 Close the refrigerator door.
 @formatter:on
*/