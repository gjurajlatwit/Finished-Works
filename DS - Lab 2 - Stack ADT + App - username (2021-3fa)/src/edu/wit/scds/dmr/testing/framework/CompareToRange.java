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

package edu.wit.scds.dmr.testing.framework;

/**
 * Support {@code compareTo()} tests
 *
 * @author Dave Rosenberg
 * @version 1.0.0 2021-06-20 Initial implementation - extracted from
 *     {@code TimeOfDayDMRTests.java}
 */
public enum CompareToRange
    {

    /** represents any value less than zero */
    NEGATIVE ( "<0" ),
    /** represents the value zero */
    ZERO ( "0" ),
    /** represents any value greater than zero */
    POSITIVE ( ">0" ),
    /** represents any non-numeric value */
    NOT_APPLICABLE ( "n/a" );

    private final String descriptiveText ;

    private CompareToRange( final String description )
        {
        this.descriptiveText = description ;

        }   // end constructor


    /**
     * Returns the opposite condition
     *
     * @return the opposite enum instance
     */
    public CompareToRange opposite()
        {
        return this == ZERO
                    ? ZERO
                    : ( this == NEGATIVE
                        ? POSITIVE
                        : this == POSITIVE
                            ? NEGATIVE
                            : NOT_APPLICABLE ) ;

        }   // end opposite()


    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
        {
        return this.descriptiveText ;

        }   // end toString()

    }   // end enum CompareToRange