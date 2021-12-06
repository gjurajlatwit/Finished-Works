
package Exceptions.JoeMath ;

import Exceptions.SquareRootException ;
import Exceptions.OurMath.OurMath ;

/**
 * A class of static methods to perform various mathematical computations, including
 * the square root. Listing J3-4 in Segment J3.6 of Java Interlude 3.
 * 
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @version 5.0
 */
public class JoeMath
    {

    /**
     * Computes the square root of a real number.
     * 
     * @param value
     *     A real value whose square root is desired.
     * @return A string containing the square root.
     */
    public static String squareRoot( final double value )
        {
        String result = "" ;
        try
            {
            final Double temp = OurMath.squareRoot( value ) ;
            result = temp.toString() ;
            }
        catch ( @SuppressWarnings( "unused" ) final SquareRootException e )
            {
            final Double temp = OurMath.squareRoot( -value ) ;
            result = temp.toString() + "i" ;
            }

        return result ;
        } // end squareRoot

    // Other methods not relevant to this discussion could be here.
    } // end JoeMath
