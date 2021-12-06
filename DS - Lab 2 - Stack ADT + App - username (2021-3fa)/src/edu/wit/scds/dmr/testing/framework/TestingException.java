/* @formatter:off
 *
 * Dave Rosenberg
 * Utilities: Testing infrastructure
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

package edu.wit.scds.dmr.testing.framework ;

/**
 * Runtime exception to wrap lower-level exceptions during testing - uses Exception
 * chaining
 *
 * @author Dave Rosenberg
 * @version 1.0.0 2020-09-13 Initial implementation
 * @version 1.1.0 2021-06-19 add no-arg and 1-arg String constructors
 */
public class TestingException extends RuntimeException
    {
    /**
     * Support serialization
     */
    private static final long serialVersionUID = 1L ;


    // constructors

    /**
     * default constructor with no arguments
     */
    public TestingException()
        {
        super() ;

        }   // end no-arg constructor
    

    /**
     * constructor with message
     * 
     * @param message 
     *     the message text associated with this exception
     */
    public TestingException( String message )
        {
        super( message ) ;

        }   // end constructor with descriptive message
    

    /**
     * @param cause
     *     the 'wrapped' exception
     */
    public TestingException( Throwable cause )
        {
        super( cause ) ;

        }   // end 'simple wrapper' constructor without message


    /**
     * @param message descriptive message related to the {@code cause}
     * @param cause the 'wrapped' exception
     */
    public TestingException( String message, Throwable cause )
        {
        super( message, cause ) ;

        }   // end 'wrapper' constructor with descriptive message

    }   // end class TestingException