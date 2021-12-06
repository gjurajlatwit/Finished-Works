
package Exceptions ;

/**
 * A class of runtime exceptions thrown when an attempt is made to find the square
 * root of a negative number. Listing J3-1 in Segment J3.1 of Java Interlude 3.
 * 
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @version 5.0
 */
public class SquareRootException extends RuntimeException
    {

    public SquareRootException()
        {
        super( "Attempted square root of a negative number." ) ;
        
        } // end default constructor


    public SquareRootException( final String message )
        {
        super( message ) ;
        
        } // end constructor
    
    } // end SquareRootException
