
package Exceptions.OurMath ;

/**
 * A demonstration of a runtime exception using the class OurMath. Listing J3-3 in
 * Segment J3.4 of Java Interlude 3.
 * 
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @version 5.0
 */
public class OurMathDriver
    {

    public static void main( final String[] args )
        {
        System.out.print( "The square root of 9 is " ) ;
        System.out.println( OurMath.squareRoot( 9.0 ) ) ;

        System.out.print( "The square root of -9 is " ) ;
        System.out.println( OurMath.squareRoot( -9.0 ) ) ;

        System.out.print( "The square root of 16 is " ) ;
        System.out.println( OurMath.squareRoot( 16.0 ) ) ;

        System.out.print( "The square root of -16 is " ) ;
        System.out.println( OurMath.squareRoot( -16.0 ) ) ;
        } // end main
    } // end OurMathDriver

/* @formatter:off
 The square root of 9 is 3.0
 The square root of -9 is Exception in thread "main" SquareRootException: Attempted square root of a negative number.
	at OurMath.squareRoot(OurMath.java:18)
	at OurMathDriver.main(OurMathDriver.java:16)
 @formatter:on
*/
