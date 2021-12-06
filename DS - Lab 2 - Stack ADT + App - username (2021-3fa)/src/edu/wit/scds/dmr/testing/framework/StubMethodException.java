/* @formatter:off
 *
 * Dave Rosenberg
 * Utilities: Testing infrastructure
 * Summer, 2021
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
 * Runtime exception to indicate that a method is a stub
 *
 * @author Dave Rosenberg
 * @version 1.0.0 2021-06-13 Initial implementation
 */
public class StubMethodException extends RuntimeException
    {
    /**
     * Support serialization
     */
    private static final long serialVersionUID = 1L ;


    // constructors

    /**
     * default constructor with no arguments
     */
    public StubMethodException()
        {
        super() ;

        }   // end no-arg constructor
    

    /**
     * constructor with message
     * 
     * @param message 
     *     the message text associated with this exception
     */
    public StubMethodException( String message )
        {
        super( message ) ;

        }   // end constructor with descriptive message

    }   // end class StubMethodException