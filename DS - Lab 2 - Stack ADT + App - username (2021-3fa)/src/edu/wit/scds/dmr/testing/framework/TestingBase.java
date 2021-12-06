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

import static edu.wit.scds.dmr.testing.framework.TestData.arrayToString ;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS ;

import java.io.File ;
import java.io.FileNotFoundException ;
import java.io.IOException ;
import java.io.PrintStream ;
import java.nio.file.AccessDeniedException ;
import java.nio.file.DirectoryStream ;
import java.nio.file.Files ;
import java.nio.file.NoSuchFileException ;
import java.nio.file.Path ;
import java.time.Duration ;
import java.util.ArrayList ;
import java.util.Calendar ;
import java.util.LinkedList ;
import java.util.List ;
import java.util.Queue ;

/**
 * Base class for unit testing
 *
 * @author David M Rosenberg
 * @version 1.0.0 2018-05-25 initial set of tests<br>
 * @version 1.1.0 2018-06-09 revise structure to use TestInfo instead of certain
 *     hard-coded text
 * @version 1.2.0 2018-09-02 add timeouts
 * @version 1.3.0 2019-01-14 more implementation
 * @version 1.3.1 2019-01-17 cosmetic changes
 * @version 2.0.0 2019-05-12
 *     <ul>
 *     <li>restructure tests
 *     <li>disable System.exit() during testing
 *     <li>start making each subtest independent so they'll all run even if one fails
 *     </ul>
 * @version 2.1.0 2019-05-17
 *     <ul>
 *     <li>rename class
 *     <li>remove unnecessary throws clauses from @BeforeXxx and @AfterXxx
 *     <li>more fully utilize JUnit 5.4 features
 *     <li>switch tests to data-driven
 *     </ul>
 * @version 3.0.0 2019-06-27
 *     <ul>
 *     <li>complete re-write with reusable testing infrastructure
 *     <li>tests are now data-driven
 *     <li>add summary test results
 *     </ul>
 * @version 3.1.0 2019-06-28 move detailed activity to log file
 * @version 4.0.0 2019-07-04 split general purpose utilities methods into separate
 *     class
 * @version 5.0.0 2019-10-07 revise for Stack ADT
 * @version 5.1.0 2020-01-26 cleanup toward DRCo coding standard compliance
 * @version 5.2.0 2020-05-14
 *     <ul>
 *     <li>cleanup comments
 *     <li>enhance null argument handling
 *     <li>in parseArguments(): correct numeric range bounds parsing; add support for
 *     data-supplied step, repeating group count, duplicate count
 *     <li>output formatting adjustments to improve alignment and readability
 *     <li>add PlaceholderException to support specific Exception detection
 *     <li>add support for detection and display of boolean/Boolean and
 *     char/Character types
 *     </ul>
 * @version 5.3.0 2020-06-03 add startTest() pass-through for backward compatibility
 * @version 5.4.0 2020-07-22
 *     <ul>
 *     <li>add instance field retrieval and modification methods
 *     <li>add method invocation support
 *     </ul>
 * @version 5.5.0 2020-08-07
 *     <ul>
 *     <li>consolidate/simplify field access methods
 *     <li>add collection retrieval methods
 *     </ul>
 * @version 6.0.0 2020-09-13 Split into general unit testing support and
 *     JUnit-specific support - this class implements the former.
 * @version 6.1.0 2021-04-11 Repackage as canned utility testing suite
 * @version 6.2.0 2021-05-01 in determineStubBehavior(), reset stub behavior
 *     indicator when isStubBehavior is false
 * @version 6.3.0 2021-05-01 change stub behavior tracking to only count methods
 *     marked as matching stub behavior if they pass
 * @version 6.4.0 2021-06-19
 *     <ul>
 *     <li>remove dependencies upon JUnit
 *     <li>remove {@code testinfo} parameter to {@code startTest()}
 *     <li>switch to fully object-oriented (eliminate static variables)
 *     </ul>
 * @version 6.5.0 2021-06-23
 *     <ul>
 *     <li>add {@code enableExit()} and {@code disableExit()}
 *     <li>start implementation of auto-determination of test package and class
 *     <li>add {@code closeLog()}
 *     <li>add {@code findFiles()}
 *     </ul>
 * @version 6.6.0 2021-08-06 start to add superclass support
 * @version 6.7.0 2021-08-08 more superclass support
 */
public class TestingBase
    {
    /*
     * State instance variables
     */

    /** the package in which the test class exists */
    protected final String testClassPackageName ;
    /** the simple name of the test class */
    protected final String testClassSimpleName ;
    /** the full name of the test class */
    protected final String testClassFullName ;
    /** the class object for the class under test */
    protected final Class<?> testClass ; 
    
    /** the package in which the test class exists */
    protected final String testSuperClassPackageName ;
    /** the simple name of the test class */
    protected final String testSuperClassSimpleName ;
    /** the full name of the test class */
    protected final String testSuperClassFullName ;
    /** the class object for the superclass of the class under test - could be null */
    protected final Class<?> testSuperClass ; 

    
    /** default display flag to indicate stub behavior */
    protected final static String DEFAULT_STUB_BEHAVIOR_INDICATOR = " s" ;
    
    
    /*
     * timeouts
     */
    /** default timeout: 2 second */
    protected static final long TEST_TIME_LIMIT_DEFAULT_SECONDS = 2 ;
    /** default timeout: 2 seconds */
    protected static final Duration TEST_TIME_LIMIT_DEFAULT =
                            Duration.ofSeconds( TEST_TIME_LIMIT_DEFAULT_SECONDS ) ;

    /** effectively disable timeout for debugging */
    protected static final long TEST_TIME_LIMIT_DEBUG_SECONDS = Integer.MAX_VALUE ;
    /** effectively disable timeout for debugging */
    protected static final Duration TEST_TIME_LIMIT_DEBUG =
                            Duration.ofSeconds( TEST_TIME_LIMIT_DEBUG_SECONDS ) ;

    /** current timeout duration */
    protected Duration testTimeLimit = TEST_TIME_LIMIT_DEFAULT ;

    
    /*
     * overall totals
     */

    /** total number of tests attempted */
    protected int totalTestsAttempted ;
    /** total number of tests that completed successfully */
    protected int totalTestsSucceeded ;

    /** accumulates test results for summary display once all tests finish */
    protected List<String> summaryTestResults ;

    // current test group (method under test)

    /** current test group (method) counter */
    protected int currentTestGroup ;
    /** current test group (method) name */
    protected String currentTestGroupName ;

    /** flag that the last test in a test group is executing */
    protected boolean lastTestInGroupIsRunning ;

    /** number of tests attempted in current test group (method) */
    protected int currentTestsAttempted ;
    /**
     * number of tests that completed successfully in current test group (method)
     */
    protected int currentTestsSucceeded ;

    /** flag that the currently executing test completed successfully */
    protected boolean currentTestPassed ;

    /**
     * counter for the number of tests within a test group that match the expected
     * stubbed method behavior
     */
    protected int stubBehaviorSeenCount ;
    /**
     * text appended to individual test id for tests that match the expected stubbed
     * method behavior
     */
    protected String stubBehaviorTag ;

    /** for test 'full' logging */
    protected PrintStream detailedLogStream ;

    /** saves the active security manager when testing starts */
    protected SecurityManager savedSecurityManager ;

    
    /*
     * constructors
     */
    
    /**
     * convenience constructor for 'well-formed' test drivers
     */
    protected TestingBase() // IN_PROCESS
        {
        this( null, null ) ;
        
        }   // no-arg constructor
    
    /**
     * @param specifiedTestClassPackageName
     *     the name of the package in which {@code specifiedTestClassSimpleName}
     *     exists
     * @param specifiedTestClassSimpleName
     *     the simple name of the class under test
     */
    protected TestingBase( String specifiedTestClassPackageName,
                           String specifiedTestClassSimpleName )
        {
        // IN_PROCESS
//        // auto-detect package and class to test
//        // - derived from test class' name
//        if ( specifiedTestClassPackageName == null )
//            {
//            specifiedTestClassPackageName = this.getClass().getPackageName() ;
//            }
//        
//        if ( specifiedTestClassSimpleName == null )
//            {
//            specifiedTestClassSimpleName = this.getClass().getSimpleName().replace( "DMRTests", "" ) ;
//            }
        
        /*
         * set class under test names
         */
        this.testClassPackageName = specifiedTestClassPackageName ;
        this.testClassSimpleName = specifiedTestClassSimpleName ;
        
        this.testClassFullName = ( this.testClassPackageName == null
                                    ? ""
                                    : this.testClassPackageName + "." ) +
                                 this.testClassSimpleName ;
        
        
        /*
         * enable detailed logging
         */
        // by default, send detailed log entries to the standard output stream
        this.detailedLogStream = System.out ;

        // create the detailed log - name is TestClass-yyyy-mm-dd-hhmmss.log
        Calendar now = Calendar.getInstance() ;
        String timestamp = String.format( "%TF-%<TH%<TM%<TS", now ) ;

        Path testLogsPath = new File( "./test-logs" ).toPath()
                                                     .toAbsolutePath()
                                                     .normalize() ;

        String outputFilename = String.format( "%s%c%s-%s.log",
                                               testLogsPath,
                                               File.separatorChar,
                                               this.getClass().getSimpleName(),
                                               timestamp ) ;

        // create the test-logs folder (if necessary) and the detailed log file
        // if either operation fails, detailed logging will go to the console
        try
            {
            testLogsPath = Files.createDirectories( testLogsPath ) ;

            this.detailedLogStream = new PrintStream( outputFilename ) ;
            writeConsole( "Detailed log in: %s%n%n", outputFilename ) ;
            }
        catch ( FileNotFoundException e )
            {
            writeSyserr( "Unable to create log file: %s%n\t%s%n\tusing System.out%n",
                         e.getMessage(),
                         outputFilename ) ;
            }
        catch ( IOException e )
            {
            writeSyserr( "Unable to create log folder: %s%n\t%s%n\tusing System.out%n",
                         e.getMessage(),
                         testLogsPath ) ;
            }
        
        writeConsole( "Starting tests of class %s%n%n", this.testClassSimpleName );
        
        
        /*
         * initialize the test class Class instance
         */
        try
            {
            this.testClass = Class.forName( this.testClassFullName ) ;
            
            this.testSuperClass = this.testClass.getSuperclass() ;
            this.testSuperClassPackageName = this.testSuperClass.getPackageName() ;
            this.testSuperClassFullName = this.testSuperClass.getName() ;
            this.testSuperClassSimpleName = this.testSuperClass.getSimpleName() ;
            }
        catch ( Throwable thrown )
            {
            String message = String.format( "unable to initialize environment for %s%s%s",
                                                       specifiedTestClassSimpleName,
                                                       ( this.testClassPackageName == null
                                                           ? ""
                                                           : " in package " ),
                                                       ( this.testClassPackageName == null
                                                           ? ""
                                                           : this.testClassPackageName ) ) ;
            writeConsole( "%s%n\t%s: %s%n",
                          message,
                          thrown.getClass().getSimpleName(),
                          thrown.getMessage() ) ;
                        
            // probably class not found but doesn't really matter - we're outta here
            throw new TestingException( message,
                                        thrown ) ;
            }
        
        
        /*
         * initialize testing-wide counters
         */
        this.totalTestsAttempted = 0 ;
        this.totalTestsSucceeded = 0 ;

        this.summaryTestResults = new LinkedList<>() ;

        this.currentTestGroup = 0 ;
        this.currentTestGroupName = "" ;

        // assume single tests (not repeating nor parameterized)
        this.lastTestInGroupIsRunning = true ;

        // there is no current test - indicate didn't pass
        this.currentTestPassed = false ;

        // there are no stub values seen yet
        this.stubBehaviorSeenCount = 0 ;
        

        /*
         * prevent System.exit() from terminating tests
         */
        // save the current security manager
        this.savedSecurityManager = System.getSecurityManager() ;

        // enable ours
        System.setSecurityManager( new NoExitSecurityManager() ) ;
        
        }   // end 2-arg constructor
    
    
    /**
     * Disable System.exit()
     */
    protected void disableExit()
        {
        
        // enable our security manager
        System.setSecurityManager( new NoExitSecurityManager() ) ;
        
        }   // end disableExit()
    
    
    /**
     * Re-enable System.exit()
     */
    protected void enableExit()
        {
        
        // re-enable the saved security manager
        System.setSecurityManager( this.savedSecurityManager ) ;
        
        }   // end enableExit()
    

    /**
     * Handle stub behavior using default indicator
     * 
     * @param isStubBehavior
     *     flag to indicate that the result of testing this dataset matches
     */
    protected void determineStubBehavior( boolean isStubBehavior )
        {
        determineStubBehavior( isStubBehavior, DEFAULT_STUB_BEHAVIOR_INDICATOR ) ;

        }	// end determineStubBehavior()


    /**
     * Handle stub behavior indicator
     * 
     * @param isStubBehavior
     *     flag to indicate that the result of testing this dataset matches
     * @param stubBehaviorIndicator
     *     text to flag that the current test data matches the expected behavior from
     *     stubbed methods
     */
    protected void determineStubBehavior( boolean isStubBehavior,
                                          String stubBehaviorIndicator )
        {
        // if the expected behavior of this test will match the stub behavior,
        // count it
        if ( isStubBehavior )
            {
            // set the tag
            this.stubBehaviorTag = stubBehaviorIndicator ;
            }
        else    // clear the tag
            {
            this.stubBehaviorTag = "" ;
            }

        }	// end determineStubBehavior()


    /**
     * Utility to parse a string of arguments into an array of corresponding entries
     * - for parameterized tests
     * 
     * @param arguments
     *     the string to parse
     * @return an array containing Longs or Strings or a zero-length array of Objects
     *     representing the entries in arguments or null if arguments is null
     */
    protected static Object[] parseArguments( String arguments )
        {
        // convert the arguments string to an array of its component entries
        List<Object> parsedArguments = null ;

        // parse the parameter if it's not null
        if ( arguments != null )
            {
            arguments = arguments.trim() ;

            if ( arguments.equals( "null" ) )
                {
                return null ;
                }

            parsedArguments = new ArrayList<>() ;

            String[] collectionContentsStrings ;

            if ( arguments.length() == 0 )
                {
                collectionContentsStrings = new String[ 0 ] ;
                }
            else
                {
                collectionContentsStrings = arguments.split( "[|]" ) ;
                }

            // trim the strings
            for ( int i = 0 ; i < collectionContentsStrings.length ; i++ )
                {
                collectionContentsStrings[ i ] = collectionContentsStrings[ i ].trim() ;

                if ( collectionContentsStrings[ i ].length() == 0 )
                    {
                    collectionContentsStrings[ i ] = "" ;
                    }
                }

            // convert the elements to an appropriate type
            for ( String collectionContentsString : collectionContentsStrings )
                {
                // check for a 0-length string
                if ( collectionContentsString.length() == 0 )
                    {
                    parsedArguments.add( "" ) ;
                    }
                // check for an explicit null (case sensitive)
                else if ( collectionContentsString.equals( "null" ) )
                    {
                    parsedArguments.add( null ) ;
                    }
                // try to convert to integers (long actually)
                else if ( Character.isDigit( collectionContentsString.charAt( 0 ) ) ||
                          ( ( collectionContentsString.length() >= 2 ) &&
                            ( collectionContentsString.charAt( 0 ) == '-' ) &&
                            Character.isDigit( collectionContentsString.charAt( 1 ) ) ) )
                    {
                    parsedArguments.add( Long.parseLong( collectionContentsString ) ) ;
                    }
                // see if we want a range of numbers
                else if ( collectionContentsString.charAt( 0 ) == '[' )
                    {	// add elements leftBound..rightBound by step
                    String[] parts = collectionContentsString
                                        .substring( 1,
                                                    collectionContentsString.length() - 1 )
                                        .split( "[:]" ) ;

                    int leftBound = Integer.parseInt( parts[ 0 ] ) ;

                    int rightBound = Integer.parseInt( parts[ 1 ] ) ;

                    int step = parts.length > 2
                                    ? parts[ 2 ].length() == 0
                                        ? 1
                                        : Integer.parseInt( parts[ 2 ] )
                                    : leftBound <= rightBound
                                        ? 1
                                        : -1 ;

                    int duplicates = parts.length > 3
                                        ? parts[ 3 ].length() == 0
                                            ? 1
                                            : Integer.parseInt( parts[ 3 ] )
                                        : 1 ;

                    int groups = parts.length > 4
                                    ? parts[ 4 ].length() == 0
                                        ? 1
                                        : Integer.parseInt( parts[ 4 ] )
                                    : 1 ;

                    // populate the list
                    for ( int groupI = 1 ; groupI <= groups ; groupI++ )
                        {
                        for ( long rangeI = leftBound ;
                              step > 0
                                  ? rangeI <= rightBound
                                  : rangeI >= rightBound ;
                              rangeI += step )
                            {
                            for ( int duplicateI = 1 ;
                                  duplicateI <= duplicates ;
                                  duplicateI++ )
                                {
                                parsedArguments.add( rangeI ) ;
                                }
                            }
                        }
                    }
                // see if we want an individual character
                else if ( ( collectionContentsString.length() == 3 ) &&
                          ( collectionContentsString.charAt( 0 ) == '\'' ) &&
                          ( collectionContentsString.charAt( 2 ) == '\'' ) )
                    {
                    parsedArguments.add( collectionContentsString.charAt( 1 ) ) ;
                    }
                // see if we want a boolean
                else if ( ( collectionContentsString.equals( "true" ) ) ||
                          ( collectionContentsString.equals( "false" ) ) )
                    {
                    parsedArguments.add( Boolean.parseBoolean( collectionContentsString ) ) ;
                    }
                // everything else we leave as a string
                else
                    {
                    parsedArguments.add( collectionContentsString ) ;
                    }
                }	// end for parse each element

            }	// end arguments isn't null

        // assertion: parsedArguments is either null or points to an array of
        // Longs, Strings, Characters, Booleans, nulls - may be a zero-length array

        return parsedArguments == null
                    ? null
                    : parsedArguments.toArray() ;

        }	// end parseArguments


    /**
     * Utility to pre-process test parameters - pass-through to method that takes
     * argument labels - for backward compatibility.
     *
     * @param isLastTest
     *     flag to indicate that this is the last dataset for this test
     * @param isStubBehavior
     *     flag to indicate that the result of testing this dataset matches the
     *     stubbed behavior
     * @param collectionContentsArguments
     *     contents of one or more collections to populate
     * @return the parsed collectionContentsArguments in order of appearance in the
     *     argument list
     */
    protected Object[][] startTest( boolean isLastTest,
                                    boolean isStubBehavior,
                                    String... collectionContentsArguments )
        {
        return startTest( isLastTest,
                          isStubBehavior,
                          null,
                          collectionContentsArguments ) ;

        }   // end startTest() pass-through


    /**
     * Utility to pre-process test parameters
     * 
     * @param isLastTest
     *     flag to indicate that this is the last dataset for this test
     * @param isStubBehavior
     *     flag to indicate that the result of testing this dataset matches the
     *     stubbed behavior
     * @param argumentLabels
     *     descriptive text for each of the collectionContentArguments elements
     * @param collectionContentsArguments
     *     contents of one or more collections to populate
     * @return the parsed collectionContentsArguments in order of appearance in the
     *     argument list
     */
    protected Object[][] startTest( boolean isLastTest,
                                    boolean isStubBehavior,
                                    String[] argumentLabels,
                                    String... collectionContentsArguments )
        {
        this.lastTestInGroupIsRunning = isLastTest ;

        // if the expected behavior of this test will match the stub behavior,
        // count it
        determineStubBehavior( isStubBehavior ) ;

        // create placeholder argument labels if none provided
        if ( argumentLabels == null )
            {
            argumentLabels = new String[ collectionContentsArguments.length ] ;

            argumentLabels[ 0 ] = "with" ;
            for ( int i = 1 ; i < argumentLabels.length ; i++ )
                {
                argumentLabels[ i ] = "and" ;
                }
            }

        // determine format for aligned argument labels
        int longestLabelLength = 0 ;
        for ( String argumentLabel : argumentLabels )
            {
            if ( argumentLabel.length() > longestLabelLength )
                {
                longestLabelLength = argumentLabel.length() ;
                }
            }
        final String labelFormat = "\t\t%-" + longestLabelLength + "s: %s%n" ;

        // count this test
        this.currentTestsAttempted++ ;

        Object[][] populatedCollections =
                                        new Object[ collectionContentsArguments.length ][] ;

        // convert the arguments representing the collection contents to an array
        for ( int i = 0 ; i < populatedCollections.length ; i++ )
            {
            populatedCollections[ i ] = parseArguments( collectionContentsArguments[ i ] ) ;
            }

        // display message describing this test
        writeLog( "[%,d, %,d%s] Testing: %s%n\tconfiguration:%n",
                  this.currentTestGroup,
                  this.currentTestsAttempted,
                  this.stubBehaviorTag,
                  this.currentTestGroupName ) ;

        for ( int i = 0 ; i < populatedCollections.length ; i++ )
            {
            writeLog( labelFormat,
                      argumentLabels[ i ],
                      arrayToString( populatedCollections[ i ] ) ) ;
            }

        return populatedCollections ;

        }	// end startTest() with 0 or more collections contents


    /**
     * Utility method to perform steps to conclude an unsuccessful test
     */
    protected void testFailed()
        {
        // display message indicating unsuccessful completion
        writeLog( "[%,d, %,d%s] Test failed%n%n",
                  this.currentTestGroup,
                  this.currentTestsAttempted,
                  this.stubBehaviorTag ) ;

        }	// end testFailed()


    /**
     * Utility method to perform steps to conclude a successful test
     */
    protected void testPassed()
        {
        // count this test success
        this.currentTestsSucceeded++ ;

        if ( !this.stubBehaviorTag.equals( "" ) )
            {
            this.stubBehaviorSeenCount++ ;
            }

        // display message indicating successful completion
        writeLog( "[%,d, %,d%s] Test passed%n%n",
                  this.currentTestGroup,
                  this.currentTestsAttempted,
                  this.stubBehaviorTag ) ;

        }	// end testPassed()
    

    // --------------------------------------------------
    //
    // The next section contains testing infrastructure declarations
    // and code
    //
    // --------------------------------------------------
    

    /*
     * Support methods
     */
    /**
     * Disable debugging mode by enabling test timeouts
     * 
     * @return true if debugging was previously enabled, false otherwise
     */
    public boolean disableDebugging()
        {
        return setDebug( false ) ;

        }	// end disableDebugging()


    /**
     * Enable debugging mode by suppressing test timeouts
     * 
     * @return true if debugging was previously enabled, false otherwise
     */
    public boolean enableDebugging()
        {
        return setDebug( true ) ;

        }	// end enableDebugging()


    /**
     * Enable or disable debugging by adjusting test timeouts.
     * 
     * @param wantToDebug
     *     true disables test timeouts; false (default) enables timeouts.
     * @return true if debugging was previously enabled, false otherwise
     */
    protected boolean setDebug( boolean wantToDebug )
        {
        boolean wasDebugging = this.testTimeLimit == TEST_TIME_LIMIT_DEBUG ;

        this.testTimeLimit = wantToDebug
                                ? TEST_TIME_LIMIT_DEBUG
                                : TEST_TIME_LIMIT_DEFAULT ;

        if ( wantToDebug )
            {
            writeSyserr( "%n%n----------%n%n%s: %s%n%n----------%n%n",
                         "WARNING",
                         "Debugging mode enabled%n\tMust submit with debugging mode disabled!" ) ;
            }

        return wasDebugging ;

        }	// end setDebug()


    /**
     * Display a log message to the console and detailed log file
     * 
     * @param format
     *     to {@code printf()}
     * @param parameters
     *     to {@code printf()}
     */
    public /* static */ void writeConsole( String format,
                                     Object... parameters )
        {
        System.out.printf( format, parameters ) ;

        if ( this.detailedLogStream != System.out )
            {
            writeLog( format, parameters ) ;
            }

        }	// end writeConsole()


    /**
     * Display a log message to the detailed log file
     * 
     * @param format
     *     to {@code printf()}
     * @param parameters
     *     to {@code printf()}
     */
    public /* static */ void writeLog( String format,
                                 Object... parameters )
        {
        this.detailedLogStream.printf( format, parameters ) ;

        }	// end writeLog()


    /**
     * Print a formatted message to System.err in its proper sequence wrt System.out
     * - limited effectiveness when running multiple threads
     * 
     * @param format
     *     to {@code printf()}
     * @param parameters
     *     to {@code printf()}
     */
    public static void writeSyserr( String format,
                                    Object... parameters )
        {
        System.out.flush() ;
        System.err.printf( format, parameters ) ;
        System.err.flush() ;

        }	// end writeSyserr()
    
    
    /**
     * Close the detailed log file - flushes buffered output
     */
    public void closeLog()
        {
        this.detailedLogStream.close() ;
        
        }   // end closeLog()
    
    
    /**
     * Locate all instances of the given file in the current project (starting in the current working directory)
     *
     * @param lookFor the name of the file to look for
     * @return a list of all matching paths
     */
    public static List<Path> findFiles( String lookFor )
        {
        return findFiles( lookFor, "./" ) ;
        
        }   // end 1-arg findFiles(String)
    
    
    /**
     * Locate all instances of the given file in the specified directory tree
     *
     * @param lookFor
     *     the name of the file to look for
     * @param startIn
     *     root of the directory tree to search
     * @return a list of all matching paths
     */
    public static List<Path> findFiles( String lookFor, String startIn )
        {
        List<Path> foundFiles = new LinkedList<>() ;
        Queue<Path> placesToLook = new LinkedList<>() ;
        placesToLook.add( Path.of( startIn ) ) ;
        
        do
            {
            Path lookingIn = placesToLook.remove() ;
            
            try ( DirectoryStream<Path> directoryContents = Files.newDirectoryStream( lookingIn ) ; )
                {
                for ( Path candidate : directoryContents )
                    {
                    if ( Files.isDirectory( candidate, NOFOLLOW_LINKS ) )
                        {
                        placesToLook.add( candidate ) ;
                        }
                    else if ( candidate.endsWith( lookFor ) )
                            {
                            foundFiles.add( candidate ) ;
                            }
                    }
                }
            catch ( @SuppressWarnings( "unused" ) NoSuchFileException | AccessDeniedException e )
                {
                continue ;
                }
            catch ( @SuppressWarnings( "unused" ) IOException e )
                {
                continue ;
                }
            }
        while ( !placesToLook.isEmpty() ) ;
        
        return foundFiles ;
        
        }   // end 2-arg findFile(String, String)

    }	// end class TestingBase