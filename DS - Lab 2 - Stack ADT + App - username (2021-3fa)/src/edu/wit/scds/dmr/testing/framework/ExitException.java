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
 * Exception representing an intercepted invocation of {@code System.exit()}
 * 
 * @author Dave Rosenberg
 * @version 1.0.0 2020-08-08 Initial implementation
 * @version 1.1.0 2021-04-11 Repackage as canned utility testing suite
 */

/**
 * Support System.exit() interception
 */
public class ExitException extends SecurityException
    {

    /** for serialization */
    static final long serialVersionUID = 1L ;

    /**
     * default constructor with no status
     */
    public ExitException()
        {
        super() ;

        }   // end no-arg constructor


    /**
     * constructor with status
     *
     * @param status
     *     the status given to {@code System.exit()}
     */
    public ExitException( final int status )
        {
        super( String.format( "%,d", status ) ) ;

        }   // end 1-arg constructor

    }   // end class ExitException