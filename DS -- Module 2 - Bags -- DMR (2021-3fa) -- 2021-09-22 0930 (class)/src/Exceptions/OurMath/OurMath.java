
package Exceptions.OurMath ;

import Exceptions.SquareRootException ;

/**
 * A class of static methods to perform various mathematical computations, including
 * the square root. Listing J3-2 in Segment J3.3 of Java Interlude 3.
 * 
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @version 5.0
 */
public class OurMath
    {

    /**
     * Computes the square root of a nonnegative real number.
     * 
     * @param value
     *     A real value whose square root is desired.
     * @return The square root of the given value.
     * @throws SquareRootException
     *     if value < 0.
     */
    public static double squareRoot( final double value ) throws SquareRootException
        {
        if ( value < 0 )
            {
            throw new SquareRootException() ;
            }
        else
            {
            return Math.sqrt( value ) ;
            }
        
        } // end squareRoot

    // Other methods not relevant to this discussion go here.
    
    } // end OurMath
