/* @formatter:off
 *
 * Dave Rosenberg
 * Comp 2000 - Data Structures
 * Lab: Stack ADT & App
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

package edu.wit.scds.dmr.tests ;

import static edu.wit.scds.dmr.testing.framework.Reflection.invoke ;

//import edu.wit.scds.comp2000.stack.app.InfixExpressionEvaluator ;
//import edu.wit.scds.comp2000.stack.app.InfixExpressionEvaluatorMultiDigit ;
//import edu.wit.scds.comp2000.stack.app.InfixExpressionEvaluatorSingleDigit ;

import edu.wit.scds.dmr.testing.framework.ExitException ;
import edu.wit.scds.dmr.testing.framework.TestingBase ;

import java.io.File ;
import java.io.FileNotFoundException ;
import java.util.Scanner ;

/**
 * Test driver for InfixExpressionEvaluator.
 * <p>
 * Standard (minimum): Support for valid expressions containing:
 * <ul>
 * <li>single-digit, unsigned, decimal operands
 * <li>operators: {@code +, -, *, /}
 * <li>parenthesized subexpressions, including nested parentheses
 * <li>spaces, which are ignored
 * </ul>
 * and invalid expressions containing:
 * <ul>
 * <li>invalid characters
 * </ul>
 * <p>
 * Optional: Support for valid expressions containing all of the above plus:
 * <ul>
 * <li>multi-digit unsigned decimal operands.
 * </ul>
 * <p>
 * Optional: Support for valid expressions containing all of the above plus:
 * <ul>
 * <li>unbalanced parentheses
 * <li>multiple consecutive operators
 * <li>division by zero
 * <li>consecutive operands
 * </ul>
 *
 * @author Dave Rosenberg
 * @version 1.0.0 2019-02-08 initial implementation
 * @version 2.0.0 2019-06-08
 *     <ul>
 *     <li>add support for comments in expression files
 *     <li>fix error in calculation of incorrect results
 *     <li>revise output format to resemble JUnit tests
 *     <li>fix error in recognition of errors distinguishing valid/invalid expression
 *     evaluation
 *     <li>consolidate all testing into a single run
 *     <ul>
 *     <li>recognize single-digit, multi-digit, and invalid expressions
 *     <li>separate counts for each expression category
 *     <li>enhance reporting per category
 *     <li>calculate %-age correct by category
 *     </ul>
 *     </ul>
 * @version 2.0.1 2019-06-22 minor fix to comment
 * @version 2.1.0 2019-10-13 remove unused import; reverse order of equals() test to
 *     avoid NullPointerException
 * @version 2.1.1 2019-10-15 update to next test data file version
 * @version 2.2.0 2020-02-17 cleanup toward DRCo coding standard compliance
 * @version 2.3.0 2020-02-21 fix missing 'incorrect' message
 * @version 2.4.0 2020-10-07
 *     <ul>
 *     <li>minor revisions to reflect changes to assignment wrt invalid expressions
 *     <li>redirect output to detailed log file
 *     </ul>
 * @version 3.0.0 2021-06-23
 *     <ul>
 *     <li>track changes to testing infrastructure
 *     <li>convert to non-static to enable inheritance wrt infrastructure
 *     <li>move expressions data file
 *     </ul>

 */
public class InfixExpressionEvaluatorDMRTests extends TestingBase
    {

    /*
     * utility constants
     */

    private final static String TEST_PACKAGE_NAME = "edu.wit.scds.comp2000.stack.app" ;
//    private final static String TEST_CLASS_NAME = "InfixExpressionEvaluator" ;

    // choose the particular implementation invocation:
    // - single-digit, valid expressions:
    @SuppressWarnings( "unused" )
    private final static String TEST_CLASS_NAME_SINGLE_DIGIT =
                                    "InfixExpressionEvaluatorSingleDigit" ;
    // - multi-digit, valid expressions:
    @SuppressWarnings( "unused" )
    private final static String TEST_CLASS_NAME_MULTI_DIGIT =
                                    "InfixExpressionEvaluatorMultiDigit" ;
    // - multi-digit, invalid expressions:
    @SuppressWarnings( "unused" )
    private final static String TEST_CLASS_NAME_FULL = "InfixExpressionEvaluator" ;
                                                            // full solution

    private final static String TEST_CLASS_NAME = TEST_CLASS_NAME_FULL ;

//    private final static String TEST_DATA_DMR_PATH = "./test-data-dmr/" ;
    

    /**
     * constructor
     */
    protected InfixExpressionEvaluatorDMRTests()
        {
        super( TEST_PACKAGE_NAME, TEST_CLASS_NAME ) ;

        }   // end constructor


    /**
     * Test driver for the InfixExpressionEvaluator's evaluate()
     * 
     * @param args
     *     -unused-
     * 
     * @throws FileNotFoundException
     *     if the expressions file can't be opened
     */
    public static void main( final String[] args ) throws FileNotFoundException
        {
        InfixExpressionEvaluatorDMRTests testInstance =
                                        new InfixExpressionEvaluatorDMRTests() ;

        testInstance.runTests() ;
        
        }   // end main()
    

    /**
     * Actual test driver for the InfixExpressionEvaluator's evaluate()
     *
     * @throws FileNotFoundException
     *     if the expressions file can't be opened
     */
    private void runTests() throws FileNotFoundException
        {

        // convenience variable
//        final String expressionsFilename = TEST_DATA_DMR_PATH +
//                                        "Infix Expressions -- DMR -- 2021-10-14 2333.dat" ;
        final String expressionsFilename = findFiles( "infix-expressions.dat" ).get( 0 ).toString() ;
        writeConsole( "Using test data from: %s%n%n", expressionsFilename ) ;

        // counters for statistics reporting
        int lineCount = 0 ;

//        // indices into xxxCounts arrays
//        final int SINGLE_DIGIT_EXPRESSION = 0 ;
//        final int MULTI_DIGIT_EXPRESSION = 1 ;
//        final int INVALID_EXPRESSION = 2 ;
        final int[] expressionCounts = { 0, 0, 0 } ;
        final int[] correctResultCounts = { 0, 0, 0 } ;
        final String[] expressionTypes = { "single-digit", 
                                           "multi-digit",
                                           "invalid" } ;

        int expressionType = -1 ;

        // these declarations are outside try{} so they're available to the catch{}
        String fullLine = null ;
        String messagePrefix = "[]" ;
        
        /*
         * evaluate all expressions in the data file
         */
        try ( Scanner expressions = new Scanner( new File( expressionsFilename ) ) )
            {
            while ( expressions.hasNextLine() )
                {
                // get an expression from the file
                fullLine = expressions.nextLine() ;

                lineCount++ ;

                final String[] fullLineParts = fullLine.split( "#" ) ;

                // only evaluate the expression if there is one
                if ( ( fullLineParts.length > 0 ) &&
                     ( fullLineParts[ 0 ].length() > 0 ) )
                    {
                    /* @formatter:off
                     * 
                     * expression lines are formatted as: 
                     *  {t}: {expression} = {expected result}
                     * where
                     *  {t} is a single-digit representing the expression type
                     *  {expression} is the expression to evaluate
                     *  {expected result} is the correct response from
                     *      InfixExpressionEvaluator.evaluate()
                     * 
                     * @formatter:on
                     */
                    expressionType = fullLineParts[ 0 ].charAt( 0 ) - '0' ;
                    expressionCounts[ expressionType ]++ ;

                    // extract the expression and the expected result
                    final String[] expressionParts = fullLineParts[ 0 ].substring( 3 )
                                                                       .split( "=" ) ;

                    final String expression = expressionParts[ 0 ].trim() ;
                    final String expressionResult = expressionParts[ 1 ].trim() ;

                    boolean expressionIsValid = true ;

                    long expectedResult ;
                    long actualResult ;

                    // if we can convert the info to the right of the '=' to a value,
                    // the expression is valid, otherwise, it's the message
                    // evaluate() will include when it throws an ArithmeticException
                    try
                        {
                        expectedResult = Long.parseLong( expressionResult ) ;
                        }
                    catch ( @SuppressWarnings( "unused" ) final NumberFormatException e )
                        {
                        expectedResult = 0 ;
                        expressionIsValid = false ;
                        }   // end try
                    
                    messagePrefix = String.format( "[%,d, %s, %,d]",
                                                   lineCount,
                                                   expressionTypes[ expressionType ],
                                                   expressionCounts[ expressionType ] ) ;

                    // display what we're evaluating
                    writeLog( "%s expression: '%s'%n", messagePrefix, expression ) ;

                    if ( expressionIsValid )
                        {
                        writeLog( "%s expect: %,d%n",
                                  messagePrefix,
                                  expectedResult ) ;
                        }
                    else
                        {
                        writeLog( "%s expect: '%s'%n",
                                  messagePrefix,
                                  expressionResult ) ;
                        }   // end if

                    // evaluate the expression
                    try
                        {
//                        // uncomment the particular implementation invocation:
//                        //      - single-digit, valid expressions
//                        //      - multi-digit, valid expressions
//                        //      - single-digit, invalid expressions
////                        actualResult = InfixExpressionEvaluatorSingleDigit.evaluate( expression ) ;
////                        actualResult = InfixExpressionEvaluatorMultiDigit.evaluate( expression ) ;
//                        actualResult = InfixExpressionEvaluator.evaluate( expression ) ;

                        // evaluate the expression
                        actualResult = (long) invoke( super.testClass,
                                                      null,
                                                      "evaluate",
                                                      new Class<?>[] { String.class },
                                                      new Object[] { expression } ) ;

                        // no exception thrown: display the actual result
                        writeLog( "%s actual: %,d%n",
                                  messagePrefix,
                                  actualResult ) ;

                        // and whether it's correct
                        if ( ( expressionIsValid ) &&
                             ( actualResult == expectedResult ) )
                            {
                            correctResultCounts[ expressionType ]++ ;
                            // count it if it is

                            writeLog( "%s correct%n", messagePrefix ) ;
                            }
                        else    // incorrect - didn't throw an exception
                            {
                            writeLog( "%s incorrect%n", messagePrefix ) ;
                            }
                        }
                    catch ( final ArithmeticException e )
                        {   // all errors are reported by throwing an
                            // ArithmeticException with an informative message

                        if ( !expressionIsValid )
                            {
                            // display the actual result
                            writeLog( "%s actual: '%s'%n",
                                      messagePrefix,
                                      e.getMessage() ) ;

                            // and whether it's correct
                            if ( expressionResult.equals( e.getMessage() ) )
                                {
                                correctResultCounts[ expressionType ]++ ;

                                writeLog( "%s correct%n", messagePrefix ) ;
                                }
                            else    // incorrect
                                {
                                writeLog( "%s incorrect%n", messagePrefix ) ;
                                }
                            }
                        else    // incorrect - expression is valid
                            {
                            writeLog( "%s incorrect%n", messagePrefix ) ;
                            }
                        }
                    catch ( @SuppressWarnings( "unused" ) final ExitException e )
                        {
                        // ignore - message already displayed
                        // will continue testing...

                        writeLog( "%s incorrect%n", messagePrefix ) ;
                        }
                    catch ( final Throwable e )
                        {
                        writeLog( "%s actual: %s%s%n",
                                  messagePrefix,
                                  e.getClass().getSimpleName(),
                                  e.getMessage() == null
                                      ? ""
                                      : ": \"" + e.getMessage() + "\"" ) ;

                        writeLog( "%s incorrect%n", messagePrefix ) ;
                        }   // end try

                    writeLog( "%n" ) ;
                    }   // end only evaluate the expression if there is one
                else
                    {   // didn't evaluate anything - display the input
                        // but skip 'deleted' tests
                    if ( ( fullLine.length() >= 4) &&
                         !fullLine.substring( 0, 4 ).equals( "###-" ) )
                        {
                        writeLog( "[%,d] %s%n", lineCount, fullLine ) ;
                        }
                    
                    }

                }   // end while

            }
        catch ( final Throwable e )
            {
            // typically indicates an error in the expressions file
            writeLog( "%s Unexpected exception:%n\t'%s'%n\t'%s'%n\tinput: %s%n",
                      messagePrefix,
                      e.toString(),
                      e.getMessage(),
                      fullLine ) ;
            }   // end try

        // display test execution summary
        writeLog( "-----\n" ) ;

        for ( expressionType = 0 ;
              expressionType < expressionTypes.length ;
              expressionType++ )
            {
            if ( expressionCounts[ expressionType ] > 0 )
                {
                writeConsole( "%,d of %,d %s expressions (%3d%%) evaluated correctly%n",
                              correctResultCounts[ expressionType ],
                              expressionCounts[ expressionType ],
                              expressionTypes[ expressionType ],
                              ( correctResultCounts[ expressionType ] *
                                100 ) / expressionCounts[ expressionType ] ) ;
                }
            else
                {
                writeLog( "No %s expressions evaluated%n",
                          expressionTypes[ expressionType ] ) ;
                }
            }

        /*
         * cleanup and exit
         */

        // close the detailed log file
        super.closeLog() ;

        // re-enable System.exit() - was automatically disabled in TestingBase
        super.enableExit() ;

        }   // end main()

    }   // end class InfixExpressionEvaluatorDMRTests