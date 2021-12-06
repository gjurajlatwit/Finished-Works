
package Exceptions.Milk ;

/**
 * A class of runtime exceptions thrown when no milk is in the refrigerator.
 *
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @version 4.0
 */
public class NoMilkException extends RuntimeException
    {

    public NoMilkException()
        {
        super( "Out of milk!" ) ;
        
        } // end default constructor


    public NoMilkException( final String message )
        {
        super( message ) ;
        
        } // end constructor

    } // end NoMilkException