
package Exceptions.Milk ;

import static Exceptions.Milk.Refrigerator.* ;

/**
 * Demonstrates the behavior of a finally block. Listing J3-6 in Segment J3.11 of
 * Java Interlude 3.
 * 
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @version 5.0
 */
public class UseGetMilk1
    {

    public static void main( final String[] args )
        {
        useMilk() ;
        }
    
    public static void useMilk()
        {
        try
            {
            openRefrigerator() ;
            takeOutMilk() ;
            pourMilk() ;
            putBackMilk() ;
            }
        catch ( final NoMilkException e )
            {
            System.out.println( e.getMessage() ) ;
            }
        finally
            {
            closeRefrigerator() ;
            }
        
        } // end main
    
    } // end UseGetMilk1

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