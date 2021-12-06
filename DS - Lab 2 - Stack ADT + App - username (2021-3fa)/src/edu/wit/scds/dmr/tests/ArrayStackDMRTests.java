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

import static edu.wit.scds.dmr.testing.framework.Reflection.* ;
import static org.junit.jupiter.api.Assertions.* ;

import edu.wit.scds.comp2000.stack.ArrayStackCapacity ;
import edu.wit.scds.comp2000.stack.StackInterface ;
import edu.wit.scds.comp2000.stack.adt.ArrayStack ;
import edu.wit.scds.dmr.testing.framework.JUnitTestingBase ;

import org.junit.jupiter.api.Disabled ;
import org.junit.jupiter.api.DisplayName ;
import org.junit.jupiter.api.Order ;
import org.junit.jupiter.api.Test ;
import org.junit.jupiter.api.TestInstance ;
import org.junit.jupiter.api.TestMethodOrder ;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation ;
import org.junit.jupiter.api.TestInstance.Lifecycle ;

import java.util.EmptyStackException ;


/**
 * JUnit tests for the ArrayStack class. All public and package visible methods are
 * tested. These tests require the API for the ArrayStack class implement
 * {@code TestableArrayStackInterface<T>}.
 * 
 * @author David M Rosenberg
 * @version 1.0.0 2018-06-08 initial set of tests<br>
 * @version 1.1.0 2018-06-09 revise structure to use TestInfo instead of certain
 *     hard-coded text<br>
 * @version 1.1.1 2018-06-27 enhanced tearDownAfterClass()
 * @version 1.2.0 2018-09-02 add timeouts
 * @version 1.3.0 2019-02-08 add tests of toArray() via toString() to testFullStack()
 *     and testStackGrowth()
 * @version 2.0.0 2019-05-07
 *     <ul>
 *     <li>start restructuring tests
 *     <li>disable System.exit() during testing
 *     <li>start making each subtest independent so they'll all run even if one fails
 *     <li>add tests for instantiating Stacks with initial capacity of 0 and negative
 *     </ul>
 * @version 2.1.0 2019-06-30
 *     <ul>
 *     <li>replace infrastructure with enhanced code
 *     <li>switch interface to use TestableArrayStackInterface
 *     </ul>
 * @version 2.2.0 2020-02-17 cleanup toward DRCo coding standard compliance
 * @version 2.3.0 2020-02-27 fix restoration of stack in {@code copyStackIntoArray}
 * @version 2.4.0 2020-10-07
 *     <ul>
 *     <li>general cleanup
 *     <li>continued migration to new testing infrastructure
 *     <li>switch from TestableStackInterface to StackInterface
 *     </ul>
 * @version 2.5.0 2021-10-14 replace use of deprecated Integer constructor with
 *     {@code Integer.valueOf()}
 */
@DisplayName( "ArrayStack" )
@TestInstance( Lifecycle.PER_CLASS )
@TestMethodOrder( OrderAnnotation.class )
class ArrayStackDMRTests extends JUnitTestingBase
	{

    /*
     * utility constants
     */

    private final static String TEST_PACKAGE_NAME = "edu.wit.scds.comp2000.stack.adt" ;
    private final static String TEST_CLASS_NAME = "ArrayStack" ;
    
    @SuppressWarnings( "unused" )
    private final static String TEST_DATA_DMR_PATH = "./test-data-dmr/" ;
    

	// Note: These automatically track the constants of the same names in ArrayStack.java
	private static final int DEFAULT_CAPACITY = ArrayStackCapacity.DEFAULT.capacityValue ;
	private static final int MAX_CAPACITY = ArrayStackCapacity.MAXIMUM.capacityValue ;

	/*
	 * local constants
	 */
	private static final int SMALL_CAPACITY	 = ArrayStackCapacity.SMALL.capacityValue ;
	private static final int MEDIUM_CAPACITY = ArrayStackCapacity.MEDIUM.capacityValue ;
	private static final int LARGE_CAPACITY	 = ArrayStackCapacity.LARGE.capacityValue ;

	private static final int STACK_1_BASE	 = 100 ;
	private static final int STACK_2_BASE	 = 100_000 ;
	
	
    /**
     * constructor
     */
    protected ArrayStackDMRTests()
        {
        super( TEST_PACKAGE_NAME, TEST_CLASS_NAME ) ;

        }   // end constructor

    
	/*
	 * API method tests
	 */


	/*
	 * test constructors
	 */

	/**
	 * Test method for
	 * {@link edu.wit.scds.comp2000.stack.adt.ArrayStack#ArrayStack()}.
	 */
	@Test
	@Order( 1100 )
	@DisplayName( "new ArrayStack()" )
	@Disabled                              // FUTURE
    void testArrayStack()
        {
        assertTimeout( super.testTimeLimit, () ->
            {
            this.lastTestInGroupIsRunning = true ;
            this.currentTestPassed = false ;
            
            this.currentTestsAttempted++ ;
            
            int testSize = DEFAULT_CAPACITY ;

            writeLog( "[%,d, %,d] Testing: DEFAULT_CAPACITY (%,d) stack (no arg)%n",
                      this.currentTestGroup, this.currentTestsAttempted,
                      testSize ) ;

            // instantiate the stack
            StackInterface<String> testStack = new ArrayStack<>( testSize ) ;

            // verify state: valid, empty, bottom at high-end of array
            writeLog( "\tverifying state: empty, valid, DEFAULT_CAPACITY%n" ) ;

            int testTopIndex = getIntField( testStack, "topIndex" ) ;
            assertEquals( testSize, testTopIndex ) ;

            boolean testIntegrityOK = getBooleanField( testStack, "integrityOK" ) ;
            assertTrue( testIntegrityOK ) ;

            Object[] testStackStack = (Object[]) getReferenceField( testStack,
                                                                    "stack" ) ;
            assertEquals( testSize, testStackStack.length ) ;

            this.currentTestPassed = true ;
            } ) ;

        }	// end testArrayStack()


	/**
	 * Test method for
	 * {@link edu.wit.scds.comp2000.stack.adt.ArrayStack#ArrayStack(int)}.
	 */
	@Test
	@Order( 1200 )
	@DisplayName( "new ArrayStack( specified initial capacity )" )
	@Disabled                              // FUTURE
	void testArrayStackInt()
		{
        assertTimeout( super.testTimeLimit, () ->
            {
            this.lastTestInGroupIsRunning = true ;
            this.currentTestPassed = false ;
            
            this.currentTestsAttempted++ ;

            int testSize = MAX_CAPACITY ;

            writeLog( "[%,d, %,d] Testing: DEFAULT_CAPACITY (%,d) stack (no arg)%n",
                      this.currentTestGroup, this.currentTestsAttempted,
                      testSize ) ;

            // instantiate the stack
            StackInterface<String> testStack = new ArrayStack<>( testSize ) ;

            // verify state: valid, empty, bottom at high-end of array
            writeLog( "\tverifying state: empty, valid, capacity: %,d%n", testSize ) ;

            int testTopIndex = getIntField( testStack, "topIndex" ) ;
            assertEquals( testSize, testTopIndex ) ;

            boolean testIntegrityOK = getBooleanField( testStack, "integrityOK" ) ;
            assertTrue( testIntegrityOK ) ;

            Object[] testStackStack = (Object[]) getReferenceField( testStack,
                                                                    "stack" ) ;
            assertEquals( testSize, testStackStack.length ) ;

            this.currentTestPassed = true ;
            } ) ;

		}	// end testArrayStackInt()


	/*
	 * test API methods
	 */

	/**
	 * Test method for
	 * {@link edu.wit.scds.comp2000.stack.adt.ArrayStack#clear()}.
	 */
	@Test
	@Order( 1300 )
	@DisplayName( "clear()" )
	@Disabled
	void testClear()
		{
		this.lastTestInGroupIsRunning = true ;
		this.currentTestPassed = false ;
		
		fail( "Not yet implemented" ) ;   // FUTURE

		this.currentTestPassed = true ;
		
		}	// end testClear()


	/**
	 * Test method for
	 * {@link edu.wit.scds.comp2000.stack.adt.ArrayStack#isEmpty()}.
	 */
	@Test
	@Order( 1400 )
	@DisplayName( "isEmpty()" )
	@Disabled
	void testIsEmpty()
		{
        this.lastTestInGroupIsRunning = true ;
        this.currentTestPassed = false ;
        
        fail( "Not yet implemented" ) ;   // FUTURE

        this.currentTestPassed = true ;
        
		}	// end testIsEmpty()


	/**
	 * Test method for
	 * {@link edu.wit.scds.comp2000.stack.adt.ArrayStack#peek()}.
	 */
	@Test
	@Order( 1500 )
	@DisplayName( "peek()" )
	@Disabled
	void testPeek()
		{
        this.lastTestInGroupIsRunning = true ;
        this.currentTestPassed = false ;
        
        fail( "Not yet implemented" ) ;   // FUTURE

        this.currentTestPassed = true ;
        
		}	// end testPeek()


	/**
	 * Test method for {@link edu.wit.scds.comp2000.stack.adt.ArrayStack#pop()}.
	 */
	@Test
	@Order( 1600 )
	@DisplayName( "pop()" )
	@Disabled
	void testPop()
		{
        this.lastTestInGroupIsRunning = true ;
        this.currentTestPassed = false ;
        
        fail( "Not yet implemented" ) ;   // FUTURE

        this.currentTestPassed = true ;
        
		}	// end testPop()


	/**
	 * Test method for
	 * {@link edu.wit.scds.comp2000.stack.adt.ArrayStack#push(java.lang.Object)}.
	 */
	@Test
	@Order( 1700 )
	@DisplayName( "push()" )
	@Disabled
	void testPush()
		{
        this.lastTestInGroupIsRunning = true ;
        this.currentTestPassed = false ;
        
        fail( "Not yet implemented" ) ;   // FUTURE

        this.currentTestPassed = true ;
        
		}	// end testPush()
	

	// ----------


	/*
	 * behavioral tests
	 */

	/**
	 * Test instantiating stacks.
	 */
	@Test
	@Order( 10100 )
	@DisplayName( "Instantiate Stack" )
	void testInstantiateStack()
		{
		assertAll( () ->
			{
			assertTimeoutPreemptively( super.testTimeLimit,
               () ->
                   {
                   this.lastTestInGroupIsRunning =   false ;
                   this.currentTestPassed =          false ;

                   StackInterface<Object> testStack =
                                                null ;

                   int testSize =               DEFAULT_CAPACITY ;

                   this.currentTestsAttempted++ ;
                   writeLog( "[%,d, %,d] Testing: DEFAULT_CAPACITY (%,d) stack (no arg)%n",
                             this.currentTestGroup, this.currentTestsAttempted,
                             testSize ) ;

                   testStack =                  new ArrayStack<>() ;

                   // verify state: valid, empty, bottom at high-end of array
                   writeLog( "\tverifying state: empty, valid, DEFAULT_CAPACITY (%,d)%n",
                             testSize ) ;

                   int testTopIndex = getIntField( testStack,
                                                   "topIndex" ) ;
                   assertEquals( testSize, testTopIndex ) ;

                   boolean testIntegrityOK = getBooleanField( testStack,
                                                              "integrityOK" ) ;
                   assertTrue( testIntegrityOK ) ;

                   Object[] testStackStack = (Object[]) getReferenceField( testStack,
                                                                           "stack" ) ;
                   assertEquals( testSize,
                                 testStackStack.length ) ;

                   this.currentTestPassed = true ;
                   testPassed() ;
                   } ) ;
			},
       () ->
           {
           assertTimeoutPreemptively( super.testTimeLimit,
              () ->
                  {
                  this.lastTestInGroupIsRunning =    false ;
                  this.currentTestPassed =           false ;

                  StackInterface<Object> testStack =
                                                null ;

                  int testSize =                DEFAULT_CAPACITY ;

                  this.currentTestsAttempted++ ;
                  writeLog( "[%,d, %,d] Testing: DEFAULT_CAPACITY (%,d) stack (explicit)%n",
                            this.currentTestGroup, this.currentTestsAttempted,
                            testSize ) ;

                  testStack =                   new ArrayStack<>( testSize ) ;

                  // verify state: valid, empty, bottom at high-end of array
                  writeLog( "\tverifying state: empty, valid, capacity: %,d%n",
                            testSize) ;

                  int testTopIndex = getIntField( testStack,
                                                  "topIndex" ) ;
                  assertEquals( testSize, testTopIndex ) ;

                  boolean testIntegrityOK = getBooleanField( testStack,
                                                             "integrityOK" ) ;
                  assertTrue( testIntegrityOK ) ;

                  Object[] testStackStack = (Object[]) getReferenceField( testStack,
                                                                          "stack" ) ;
                  assertEquals( testSize,
                                testStackStack.length ) ;

                  this.currentTestPassed = true ;
                  testPassed() ;
                  } ) ;
           },
       () ->
           {
           assertTimeoutPreemptively( super.testTimeLimit,
              () ->
                  {
                  this.lastTestInGroupIsRunning =    false ;
                  this.currentTestPassed =           false ;

                  StackInterface<Object> testStack =    null ;

                  int testSize =                MEDIUM_CAPACITY ;

                  this.currentTestsAttempted++ ;
                  writeLog( "[%,d, %,d] Testing: MEDIUM_CAPACITY (%,d) stack%n",
                            this.currentTestGroup, this.currentTestsAttempted,
                            testSize ) ;

                  testStack =                   new ArrayStack<>( testSize ) ;

                  // verify state: valid, empty, bottom at high-end of array
                  writeLog( "\tverifying state: empty, valid, capacity: %,d%n",
                            testSize) ;

                  int testTopIndex = getIntField( testStack,
                                                  "topIndex" ) ;
                  assertEquals( testSize, testTopIndex ) ;

                  boolean testIntegrityOK = getBooleanField( testStack,
                                                             "integrityOK" ) ;
                  assertTrue( testIntegrityOK ) ;

                  Object[] testStackStack = (Object[]) getReferenceField( testStack,
                                                                          "stack" ) ;
                  assertEquals( testSize,
                                testStackStack.length ) ;

                  this.currentTestPassed = true ;
                  testPassed() ;
                  } ) ;
	           },
           () ->
	           {
	           assertTimeoutPreemptively( super.testTimeLimit,
                  () ->
                      {
                      this.lastTestInGroupIsRunning =    false ;
                      this.currentTestPassed =       false ;

                      StackInterface<Object> testStack =    null ;

                      int testSize =            LARGE_CAPACITY ;

                      this.currentTestsAttempted++ ;
                      writeLog( "[%,d, %,d] Testing: LARGE_CAPACITY (%,d) stack%n",
                                this.currentTestGroup, this.currentTestsAttempted,
                                testSize ) ;

                      testStack =               new ArrayStack<>( testSize ) ;

                      // verify state: valid, empty, bottom at high-end of array
                      writeLog( "\tverifying state: empty, valid, capacity: %,d%n",
                                testSize) ;

                      int testTopIndex = getIntField( testStack,
                                                      "topIndex" ) ;
                      assertEquals( testSize, testTopIndex ) ;

                      boolean testIntegrityOK = getBooleanField( testStack,
                                                                 "integrityOK" ) ;
                      assertTrue( testIntegrityOK ) ;

                      Object[] testStackStack = (Object[]) getReferenceField( testStack,
                                                                              "stack" ) ;
                      assertEquals( testSize,
                                    testStackStack.length ) ;

                      this.currentTestPassed = true ;
                      testPassed() ;
                      } ) ;
	           },
           () ->
	           {
	           assertTimeoutPreemptively( super.testTimeLimit,
                  () ->
                      {
                      this.lastTestInGroupIsRunning =    false ;
                      this.currentTestPassed =       false ;

                      StackInterface<Object> testStack =    null ;

                      int testSize =            MAX_CAPACITY ;

                      this.currentTestsAttempted++ ;
                      writeLog( "[%,d, %,d] Testing: MAX_CAPACITY (%,d) stack%n",
                                this.currentTestGroup, this.currentTestsAttempted,
                                testSize ) ;

                      testStack =               new ArrayStack<>( testSize ) ;

                      // verify state: valid, empty, bottom at high-end of array
                      writeLog( "\tverifying state: empty, valid, capacity: %,d%n",
                                testSize) ;

                      int testTopIndex = getIntField( testStack,
                                                      "topIndex" ) ;
                      assertEquals( testSize, testTopIndex ) ;

                      boolean testIntegrityOK = getBooleanField( testStack,
                                                                 "integrityOK" ) ;
                      assertTrue( testIntegrityOK ) ;

                      Object[] testStackStack = (Object[]) getReferenceField( testStack,
                                                                              "stack" ) ;
                      assertEquals( testSize,
                                    testStackStack.length ) ;

                      this.currentTestPassed = true ;
                      testPassed() ;
                      } ) ;
	           },
           () ->
	           {
	           assertTimeoutPreemptively( super.testTimeLimit,
                  () ->
                      {
                      this.lastTestInGroupIsRunning =    false ;
                      this.currentTestPassed =       false ;

                      int testSize =            MAX_CAPACITY + 1 ;

                      this.currentTestsAttempted++ ;
                      writeLog( "[%,d, %,d] Testing: over MAX_CAPACITY (%,d) stack%n",
                                this.currentTestGroup, this.currentTestsAttempted,
                                testSize ) ;

                      assertThrows( IllegalStateException.class,
                                    () -> new ArrayStack<>( testSize ) ) ;

                      this.currentTestPassed = true ;
                      testPassed() ;
                      } ) ;
	           },
           () ->
	           {
	           assertTimeoutPreemptively( super.testTimeLimit,
                  () ->
                      {
                      this.lastTestInGroupIsRunning =    false ;
                      this.currentTestPassed =       false ;

                      int testSize =            0 ;

                      this.currentTestsAttempted++ ;
                      writeLog( "[%,d, %,d] Testing: zero initial capacity (%,d) stack%n",
                                this.currentTestGroup, this.currentTestsAttempted,
                                testSize ) ;

                      assertThrows( IllegalStateException.class,
                                    () -> new ArrayStack<>( testSize ) ) ;

                      this.currentTestPassed = true ;
                      testPassed() ;
                      } ) ;
	           },
           () ->
	           {
	           assertTimeoutPreemptively( super.testTimeLimit,
                  () ->
                      {
                      this.lastTestInGroupIsRunning =    true ;
                      this.currentTestPassed =       false ;

                      int testSize =            -1 ;

                      this.currentTestsAttempted++ ;
                      writeLog( "[%,d, %,d] Testing: negative initial capacity (%,d) stack%n",
                                this.currentTestGroup, this.currentTestsAttempted,
                                testSize ) ;

                      assertThrows( IllegalStateException.class,
                                    () -> new ArrayStack<>( testSize ) ) ;

                      this.currentTestPassed = true ;
                      } ) ;
           } ) ;	// end assertAll

		}	// end testInstantiateStack()


	/**
	 * Test empty stack.
	 */
	@Test
	@Order( 10200 )
	@DisplayName( "Empty Stack" )
	void testEmptyStack()
		{
		assertAll( () -> 
		    {
		assertTimeoutPreemptively( super.testTimeLimit,
           () ->
               {
               this.lastTestInGroupIsRunning =   false ;
               this.currentTestPassed =          false ;
               
               int testSize = DEFAULT_CAPACITY ;

               writeLog( "[%,d] Using DEFAULT_CAPACITY (%,d) stack:%n",
                         this.currentTestGroup,
                         testSize ) ;

               StackInterface<Integer> testStack = new ArrayStack<>( testSize ) ;

               this.currentTestsAttempted++ ;
               writeLog( "[%,d, %,d] Testing: isEmpty()%n",
                         this.currentTestGroup, this.currentTestsAttempted ) ;

               assertTrue( testStack.isEmpty() ) ;
               

               writeLog( "[%,d, %,d] Testing: clear()%n",
                         this.currentTestGroup, this.currentTestsAttempted ) ;

               testStack.clear() ;
               assertTrue( testStack.isEmpty() ) ;

               
               writeLog( "[%,d, %,d] Testing: peek()%n",
                         this.currentTestGroup, this.currentTestsAttempted ) ;

               boolean sawException =               false ;
               try
                   {
                   testStack.peek() ;
                   }
               catch ( @SuppressWarnings( "unused" ) EmptyStackException e )
                   {
                   sawException =           true ;
                   }
               assertTrue( sawException ) ;
                                       // make sure we got the (right) exception


               writeLog( "[%,d, %,d] Testing: pop()%n",
                         this.currentTestGroup, this.currentTestsAttempted ) ;

               sawException =               false ;
               try
                   {
                   testStack.pop() ;
                   }
               catch ( @SuppressWarnings( "unused" ) EmptyStackException e )
                   {
                   sawException =           true ;
                   }
               assertTrue( sawException ) ;
                                       // make sure we got the (right) exception

               this.currentTestPassed = true ;
               testPassed() ;
                   } ) ;
                },
            () ->
                {
                assertTimeoutPreemptively( super.testTimeLimit,
                   () ->
                       {
                       this.lastTestInGroupIsRunning =   false ;
                       this.currentTestPassed =          false ;
                       
               int testSize =                   MEDIUM_CAPACITY ;

               writeLog( "[%,d] Using MEDIUM_CAPACITY (%,d) stack:%n",
                         this.currentTestGroup, 
                         testSize ) ;

               StackInterface<Integer> testStack = new ArrayStack<>( testSize ) ;

               this.currentTestsAttempted++ ;
               writeLog( "[%,d, %,d] Testing: isEmpty()%n",
                         this.currentTestGroup, this.currentTestsAttempted ) ;

               assertTrue( testStack.isEmpty() ) ;


               writeLog( "[%,d, %,d] Testing: clear()%n",
                         this.currentTestGroup, this.currentTestsAttempted ) ;

               testStack.clear() ;
               assertTrue( testStack.isEmpty() ) ;


               writeLog( "[%,d, %,d] Testing: peek()%n",
                         this.currentTestGroup, this.currentTestsAttempted ) ;

               boolean sawException =               false ;
               try
                   {
                   testStack.peek() ;
                   }
               catch ( @SuppressWarnings( "unused" ) EmptyStackException e )
                   {
                   sawException =           true ;
                   }
               assertTrue( sawException ) ;
                                       // make sure we got the (right) exception


               writeLog( "[%,d, %,d] Testing: pop()%n",
                         this.currentTestGroup, this.currentTestsAttempted ) ;

               sawException =               false ;
               try
                   {
                   testStack.pop() ;
                   }
               catch ( @SuppressWarnings( "unused" ) EmptyStackException e )
                   {
                   sawException =           true ;
                   }
               assertTrue( sawException ) ;
                                       // make sure we got the (right) exception

               this.currentTestPassed = true ;
               testPassed() ;
                   } ) ;
                },
            () ->
                {
                assertTimeoutPreemptively( super.testTimeLimit,
                   () ->
                       {
                       this.lastTestInGroupIsRunning =   false ;
                       this.currentTestPassed =          false ;
                       
               int testSize =                   LARGE_CAPACITY ;

               writeLog( "[%,d] Using LARGE_CAPACITY (%,d) stack:%n",
                         this.currentTestGroup,
                         testSize ) ;

               StackInterface<Integer> testStack = new ArrayStack<>( testSize ) ;

               this.currentTestsAttempted++ ;
               writeLog( "[%,d, %,d] Testing: isEmpty()%n",
                         this.currentTestGroup, this.currentTestsAttempted ) ;

               assertTrue( testStack.isEmpty() ) ;


               writeLog( "[%,d, %,d] Testing: clear()%n",
                         this.currentTestGroup, this.currentTestsAttempted ) ;

               testStack.clear() ;
               assertTrue( testStack.isEmpty() ) ;


               writeLog( "[%,d, %,d] Testing: peek()%n",
                         this.currentTestGroup, this.currentTestsAttempted ) ;

               boolean sawException =               false ;
               try
                   {
                   testStack.peek() ;
                   }
               catch ( @SuppressWarnings( "unused" ) EmptyStackException e )
                   {
                   sawException =           true ;
                   }
               assertTrue( sawException ) ;
                                       // make sure we got the (right) exception


               writeLog( "[%,d, %,d] Testing: pop()%n",
                         this.currentTestGroup, this.currentTestsAttempted ) ;

               sawException =               false ;
               try
                   {
                   testStack.pop() ;
                   }
               catch ( @SuppressWarnings( "unused" ) EmptyStackException e )
                   {
                   sawException =           true ;
                   }
               assertTrue( sawException ) ;
                                       // make sure we got the (right) exception

               this.currentTestPassed = true ;
               testPassed() ;
                       } ) ;
                },
            () ->
                {
                assertTimeoutPreemptively( super.testTimeLimit,
                   () ->
                       {
                       this.lastTestInGroupIsRunning =   true ;
                       this.currentTestPassed =          false ;
                       
               int testSize =                   MAX_CAPACITY ;

               writeLog( "[%,d] Using MAX_CAPACITY (%,d) stack:%n",
                         this.currentTestGroup,
                         testSize ) ;

               StackInterface<Integer> testStack = new ArrayStack<>( testSize ) ;

               this.currentTestsAttempted++ ;
               writeLog( "[%,d, %,d] Testing: isEmpty()%n",
                         this.currentTestGroup, this.currentTestsAttempted ) ;

               assertTrue( testStack.isEmpty() ) ;


               writeLog( "[%,d, %,d] Testing: clear()%n",
                         this.currentTestGroup, this.currentTestsAttempted ) ;

               testStack.clear() ;
               assertTrue( testStack.isEmpty() ) ;


               writeLog( "[%,d, %,d] Testing: peek()%n",
                         this.currentTestGroup, this.currentTestsAttempted ) ;

               boolean sawException =               false ;
               try
                   {
                   testStack.peek() ;
                   }
               catch ( @SuppressWarnings( "unused" ) EmptyStackException e )
                   {
                   sawException =           true ;
                   }
               assertTrue( sawException ) ;
                                       // make sure we got the (right) exception


               writeLog( "[%,d, %,d] Testing: pop()%n",
                         this.currentTestGroup, this.currentTestsAttempted ) ;

               sawException =               false ;
               try
                   {
                   testStack.pop() ;
                   }
               catch ( @SuppressWarnings( "unused" ) EmptyStackException e )
                   {
                   sawException =           true ;
                   }
               assertTrue( sawException ) ;
                                       // make sure we got the (right) exception

               this.currentTestPassed = true ;
               } ) ; 
                } ) ;

		}	// end testEmptyStack()


	/**
	 * Test full stack.
	 */
	@Test
	@Order( 10300 )
	@DisplayName( "Full Stack" )
	void testFullStack()
		{
		assertAll( () ->
		    {
		assertTimeoutPreemptively( super.testTimeLimit,
           () ->
               {
               this.lastTestInGroupIsRunning =   false ;
               this.currentTestPassed =          false ;

               int testSize =                   DEFAULT_CAPACITY ;

               this.currentTestsAttempted++ ;
               writeLog( "[%,d, %,d] Testing: DEFAULT_CAPACITY (%,d) stack%n",
                         this.currentTestGroup, this.currentTestsAttempted,
                         testSize ) ;

               StackInterface<Integer> testStack = new ArrayStack<>( testSize ) ;

               Integer[] testArray =        new Integer[ testSize ] ;

               // fill it
               writeLog( "...filling stack%n" ) ;
               for ( int i = 0 ; i < testSize ; i++ )
                   {
                   testStack.push( i ) ;
                   testArray[ testArray.length - 1 - i ] = i ;
                   }

               // verify contents of the stack
               writeLog( "...verifying stack contents%n" ) ;
               assertArrayEquals( testArray,
                                  getContentsOfArrayBackedCollection( testStack,
                                                                      "stack",
                                                                      null,
                                                                      testSize ) ) ;

               // empty it
               writeLog( "...emptying stack%n" ) ;
               for ( int i = testSize - 1 ; i >= 0 ; i-- )
                   {
                   assertEquals( i,
                                 ( int ) testStack.peek() ) ;
                   assertEquals( i,
                                 ( int ) testStack.pop() ) ;
                   }

               assertTrue( testStack.isEmpty() ) ;

               this.currentTestPassed = true ;
               testPassed() ;
                   } ) ;
            },
        () ->
            {
            assertTimeoutPreemptively( super.testTimeLimit,
               () ->
                   {
                   this.lastTestInGroupIsRunning =   false ;
                   this.currentTestPassed =          false ;

               int testSize =                   MEDIUM_CAPACITY ;

               this.currentTestsAttempted++ ;
               writeLog( "[%,d, %,d] Testing: MEDIUM_CAPACITY (%,d) stack%n",
                         this.currentTestGroup, this.currentTestsAttempted,
                         testSize ) ;

               StackInterface<Integer> testStack = new ArrayStack<>( testSize ) ;

               Integer[] testArray =                  new Integer[ testSize ] ;

               // fill it
               writeLog( "...filling stack%n" ) ;
               for ( int i = 0 ; i < testSize ; i++ )
                   {
                   testStack.push( i ) ;
                   testArray[ testArray.length - 1 - i ] = i ;
                   }

               // verify contents of the stack
               writeLog( "...verifying stack contents%n" ) ;
               assertArrayEquals( testArray,
                                  getContentsOfArrayBackedCollection( testStack,
                                                                      "stack",
                                                                      null,
                                                                      testSize ) ) ;

               // empty it
               writeLog( "...emptying stack%n" ) ;
               for ( int i = testSize - 1 ; i >= 0 ; i-- )
                   {
                   assertEquals( i,
                                 ( int ) testStack.peek() ) ;
                   assertEquals( i,
                                 ( int ) testStack.pop() ) ;
                   }

               assertTrue( testStack.isEmpty() ) ;

               this.currentTestPassed = true ;
               testPassed() ;
                   } ) ;
            },
        () ->
            {
            assertTimeoutPreemptively( super.testTimeLimit,
               () ->
                   {
                   this.lastTestInGroupIsRunning =   false ;
                   this.currentTestPassed =          false ;

               int testSize =                   LARGE_CAPACITY ;

               this.currentTestsAttempted++ ;
               writeLog( "[%,d, %,d] Testing: LARGE_CAPACITY (%,d) stack%n",
                         this.currentTestGroup, this.currentTestsAttempted,
                         testSize ) ;

               StackInterface<Integer> testStack = new ArrayStack<>( testSize ) ;

               Integer[] testArray =                  new Integer[ testSize ] ;

               // fill it
               writeLog( "...filling stack%n" ) ;
               for ( int i = 0 ; i < testSize ; i++ )
                   {
                   testStack.push( i ) ;
                   testArray[ testArray.length - 1 - i ] = i ;
                   }

               // verify contents of the stack
               writeLog( "...verifying stack contents%n" ) ;
               assertArrayEquals( testArray,
                                  getContentsOfArrayBackedCollection( testStack,
                                                                      "stack",
                                                                      null,
                                                                      testSize ) ) ;

               // empty it
               writeLog( "...emptying stack%n" ) ;
               for ( int i = testSize - 1 ; i >= 0 ; i-- )
                   {
                   assertEquals( i,
                                 ( int ) testStack.peek() ) ;
                   assertEquals( i,
                                 ( int ) testStack.pop() ) ;
                   }

               assertTrue( testStack.isEmpty() ) ;

               this.currentTestPassed = true ;
               testPassed() ;
                   } ) ;
            },
        () ->
            {
            assertTimeoutPreemptively( super.testTimeLimit,
               () ->
                   {
                   this.lastTestInGroupIsRunning =   true ;
                   this.currentTestPassed =          false ;

               int testSize =                   MAX_CAPACITY ;

               this.currentTestsAttempted++ ;
               writeLog( "[%,d, %,d] Testing: MAX_CAPACITY (%,d) stack%n",
                         this.currentTestGroup, this.currentTestsAttempted,
                         testSize ) ;

               StackInterface<Integer> testStack = new ArrayStack<>( testSize ) ;

               Integer[] testArray = new Integer[ testSize ] ;

               // fill it
               writeLog( "...filling stack%n" ) ;
               for ( int i = 0 ; i < testSize ; i++ )
                   {
                   testStack.push( i ) ;
                   testArray[ testArray.length - 1 - i ] = i ;
                   }

               // verify contents of the stack
               writeLog( "...verifying stack contents%n" ) ;
               assertArrayEquals( testArray,
                                  getContentsOfArrayBackedCollection( testStack,
                                                                      "stack",
                                                                      null,
                                                                      testSize ) ) ;

               // empty it
               writeLog( "...emptying stack%n" ) ;
               for ( int i = testSize - 1 ; i >= 0 ; i-- )
                   {
                   assertEquals( i,
                                 ( int ) testStack.peek() ) ;
                   assertEquals( i,
                                 ( int ) testStack.pop() ) ;
                   }

               assertTrue( testStack.isEmpty() ) ;

               this.currentTestPassed = true ;
               } ) ; } ) ;

		}	// end testFullStack()


	/**
	 * Test stack growth.
	 */
	@Test
	@Order( 10400 )
	@DisplayName( "Stack Growth" )
	void testStackGrowth()
		{
		assertAll( () ->
		    {
		assertTimeoutPreemptively( super.testTimeLimit,
           () ->
               {
               this.lastTestInGroupIsRunning =   false ;
               this.currentTestPassed =          false ;

               int testSize = DEFAULT_CAPACITY ;

               this.currentTestsAttempted++ ;
               writeLog( "[%,d, %,d] Testing: DEFAULT_CAPACITY (%,d) stack%n",
                         this.currentTestGroup, this.currentTestsAttempted,
                         testSize ) ;

               StackInterface<Integer> testStack = new ArrayStack<>( testSize ) ;

               Integer[] testArray =        new Integer[ LARGE_CAPACITY + 1 ] ;

               // fill it
               for ( int i = 0 ; i <= LARGE_CAPACITY ; i++ )
                   {
                   testStack.push( i ) ;
                   testArray[ testArray.length - 1 - i ] = i ;
                   }

               // verify contents of the stack
               writeLog( "\tverifying stack contents:%n" ) ;
               Object[] testStackStack = (Object[]) getReferenceField( testStack, "stack" ) ;
               int testTopIndex = getIntField( testStack, "topIndex" ) ;
               int testStackStackIndex = testTopIndex ;
               
               // check the in-use portion of the array
               writeLog( "\t\t...in-use portion%n" );
               for ( int i = 0; i < testArray.length; i++ )
                   {
                   assertTrue( testStackStackIndex >= 0 ) ;
                   assertTrue( testStackStackIndex < testStackStack.length ) ;
                   assertEquals( testArray[ i ],
                                 testStackStack[ testStackStackIndex++ ] ) ;
                   }
               
               // make sure the rest of the array is null-filled
               writeLog( "\t\t...unused portion%n" ) ;
               for ( int i = 0; i < testTopIndex; i++ )
                   {
                   assertNull( testStackStack[ i ] ) ;
                   }

               // empty it
               for ( int i = LARGE_CAPACITY ; i >= 0 ; i-- )
                   {
                   assertEquals( i,
                                 ( int ) testStack.pop() ) ;
                   }

               assertTrue( testStack.isEmpty() ) ;

               this.currentTestPassed = true ;
               testPassed() ;
                       } ) ;
                },
            () ->
                {
                assertTimeoutPreemptively( super.testTimeLimit,
                   () ->
                       {
                       this.lastTestInGroupIsRunning =   false ;
                       this.currentTestPassed =          false ;

               int testSize =                   MEDIUM_CAPACITY ;

               this.currentTestsAttempted++ ;
               writeLog( "[%,d, %,d] Testing: MEDIUM_CAPACITY (%,d) stack%n",
                         this.currentTestGroup, this.currentTestsAttempted,
                         testSize ) ;

               StackInterface<Integer> testStack = new ArrayStack<>( testSize ) ;

               Integer[] testArray = new Integer[ LARGE_CAPACITY + 1 ] ;

               // fill it
               for ( int i = 0 ; i <= LARGE_CAPACITY ; i++ )
                   {
                   testStack.push( i ) ;
                   testArray[ testArray.length - 1 - i ] = i ;
                   }

               // verify contents of the stack
               writeLog( "\tverifying stack contents:%n" ) ;
               Object[] testStackStack = (Object[]) getReferenceField( testStack, "stack" ) ;
               int testTopIndex = getIntField( testStack, "topIndex" ) ;
               int testStackStackIndex = testTopIndex ;
               
               // check the in-use portion of the array
               writeLog( "\t\t...in-use portion%n" );
               for ( int i = 0; i < testArray.length; i++ )
                   {
                   assertTrue( testStackStackIndex >= 0 ) ;
                   assertTrue( testStackStackIndex < testStackStack.length ) ;
                   assertEquals( testArray[ i ],
                                 testStackStack[ testStackStackIndex++ ] ) ;
                   }
               
               // make sure the rest of the array is null-filled
               writeLog( "\t\t...unused portion%n" ) ;
               for ( int i = 0; i < testTopIndex; i++ )
                   {
                   assertNull( testStackStack[ i ] ) ;
                   }

               // empty it
               for ( int i = LARGE_CAPACITY ; i >= 0 ; i-- )
                   {
                   assertEquals( i,
                                 ( int ) testStack.pop() ) ;
                   }

               assertTrue( testStack.isEmpty() ) ;

               this.currentTestPassed = true ;
               testPassed() ;
                       } ) ;
                },
            () ->
                {
                assertTimeoutPreemptively( super.testTimeLimit,
                   () ->
                       {
                       this.lastTestInGroupIsRunning =   false ;
                       this.currentTestPassed =          false ;

               int testSize =                   LARGE_CAPACITY ;

               this.currentTestsAttempted++ ;
               writeLog( "[%,d, %,d] Testing: LARGE_CAPACITY (%,d) stack%n",
                         this.currentTestGroup, this.currentTestsAttempted,
                         testSize ) ;

               StackInterface<Integer> testStack = new ArrayStack<>( testSize ) ;

               Integer[] testArray =                  new Integer[ MAX_CAPACITY ] ;

               // fill it
               for ( int i = 0 ; i < MAX_CAPACITY ; i++ )
                   {
                   testStack.push( i ) ;
                   testArray[ testArray.length - 1 - i ] = i ;
                   }

               // verify contents of the stack
               writeLog( "\tverifying stack contents:%n" ) ;
               Object[] testStackStack = (Object[]) getReferenceField( testStack, "stack" ) ;
               int testTopIndex = getIntField( testStack, "topIndex" ) ;
               int testStackStackIndex = testTopIndex ;
               
               // check the in-use portion of the array
               writeLog( "\t\t...in-use portion%n" );
               for ( int i = 0; i < testArray.length; i++ )
                   {
                   assertTrue( testStackStackIndex >= 0 ) ;
                   assertTrue( testStackStackIndex < testStackStack.length ) ;
                   assertEquals( testArray[ i ],
                                 testStackStack[ testStackStackIndex++ ] ) ;
                   }
               
               // make sure the rest of the array is null-filled
               writeLog( "\t\t...unused portion%n" ) ;
               for ( int i = 0; i < testTopIndex; i++ )
                   {
                   assertNull( testStackStack[ i ] ) ;
                   }

               // empty it
               for ( int i = MAX_CAPACITY - 1 ; i >= 0 ; i-- )
                   {
                   assertEquals( i,
                                 ( int ) testStack.pop() ) ;
                   }

               assertTrue( testStack.isEmpty() ) ;

               this.currentTestPassed = true ;
               testPassed() ;
                       } ) ;
                },
            () ->
                {
                assertTimeoutPreemptively( super.testTimeLimit,
                   () ->
                       {
                       this.lastTestInGroupIsRunning =   false ;
                       this.currentTestPassed =          false ;

               int testSize =                   MAX_CAPACITY ;

               this.currentTestsAttempted++ ;
               writeLog( "[%,d, %,d] Testing: MAX_CAPACITY (%,d) stack%n",
                         this.currentTestGroup, this.currentTestsAttempted,
                         testSize ) ;

               StackInterface<Integer> testStack = new ArrayStack<>( testSize ) ;

               Integer[] testArray =                  new Integer[ MAX_CAPACITY ] ;

               // fill it
               for ( int i = 0 ; i < testSize ; i++ )
                   {
                   testStack.push( i ) ;
                   testArray[ testArray.length - 1 - i ] = i ;
                   }

               // verify contents of the stack
               writeLog( "\tverifying stack contents:%n" ) ;
               Object[] testStackStack = (Object[]) getReferenceField( testStack, "stack" ) ;
               int testTopIndex = getIntField( testStack, "topIndex" ) ;
               int testStackStackIndex = testTopIndex ;
               
               // check the in-use portion of the array
               writeLog( "\t\t...in-use portion%n" );
               for ( int i = 0; i < testArray.length; i++ )
                   {
                   assertTrue( testStackStackIndex >= 0 ) ;
                   assertTrue( testStackStackIndex < testStackStack.length ) ;
                   assertEquals( testArray[ i ],
                                 testStackStack[ testStackStackIndex++ ] ) ;
                   }
               
               // make sure the rest of the array is null-filled
               writeLog( "\t\t...unused portion%n" ) ;
               for ( int i = 0; i < testTopIndex; i++ )
                   {
                   assertNull( testStackStack[ i ] ) ;
                   }

               // empty it
               for ( int i = testSize - 1 ; i >= 0 ; i-- )
                   {
                   assertEquals( i,
                                 ( int ) testStack.pop() ) ;
                   }

               assertTrue( testStack.isEmpty() ) ;

               this.currentTestPassed = true ;
               testPassed() ;
                       } ) ;
                },
            () ->
                {
                assertTimeoutPreemptively( super.testTimeLimit,
                   () ->
                       {
                       this.lastTestInGroupIsRunning =   false ;
                       this.currentTestPassed =          false ;

               int testSize =                   LARGE_CAPACITY ;

               this.currentTestsAttempted++ ;
               writeLog( "[%,d, %,d] Testing: over-fill a LARGE_CAPACITY (%,d) stack%n",
                         this.currentTestGroup, this.currentTestsAttempted,
                         testSize ) ;

               StackInterface<Integer> testStack = new ArrayStack<>( testSize ) ;

               Integer[] testArray =                  new Integer[ MAX_CAPACITY ] ;

               // fill it
               for ( int i = 0 ; i < MAX_CAPACITY ; i++ )
                   {
                   testStack.push( i ) ;
                   testArray[ testArray.length - 1 - i ] = i ;
                   }

               // verify contents of the stack
               writeLog( "\tverifying stack contents:%n" ) ;
               Object[] testStackStack = (Object[]) getReferenceField( testStack, "stack" ) ;
               int testTopIndex = getIntField( testStack, "topIndex" ) ;
               int testStackStackIndex = testTopIndex ;
               
               // check the in-use portion of the array
               writeLog( "\t\t...in-use portion%n" );
               for ( int i = 0; i < testArray.length; i++ )
                   {
                   assertTrue( testStackStackIndex >= 0 ) ;
                   assertTrue( testStackStackIndex < testStackStack.length ) ;
                   assertEquals( testArray[ i ],
                                 testStackStack[ testStackStackIndex++ ] ) ;
                   }
               
               // make sure the rest of the array is null-filled
               writeLog( "\t\t...unused portion%n" ) ;
               for ( int i = 0; i < testTopIndex; i++ )
                   {
                   assertNull( testStackStack[ i ] ) ;
                   }

               // try to over-fill it
               boolean sawException =               false ;
               try
                   {
                   testStack.push( MAX_CAPACITY ) ;
                   }
               catch ( @SuppressWarnings( "unused" ) IllegalStateException e )
                   {
                   sawException =           true ;
                   }
               assertTrue( sawException ) ;
                                       // make sure we got the (right) exception

               // verify contents of the stack
               writeLog( "\tverifying stack contents:%n" ) ;
               testStackStack = (Object[]) getReferenceField( testStack, "stack" ) ;
               testTopIndex = getIntField( testStack, "topIndex" ) ;
               testStackStackIndex = testTopIndex ;
               
               // check the in-use portion of the array
               writeLog( "\t\t...in-use portion%n" );
               for ( int i = 0; i < testArray.length; i++ )
                   {
                   assertTrue( testStackStackIndex >= 0 ) ;
                   assertTrue( testStackStackIndex < testStackStack.length ) ;
                   assertEquals( testArray[ i ],
                                 testStackStack[ testStackStackIndex++ ] ) ;
                   }
               
               // make sure the rest of the array is null-filled
               writeLog( "\t\t...unused portion%n" ) ;
               for ( int i = 0; i < testTopIndex; i++ )
                   {
                   assertNull( testStackStack[ i ] ) ;
                   }

               // empty it
               for ( int i = MAX_CAPACITY - 1 ; i >= 0 ; i-- )
                   {
                   assertEquals( i,
                                 ( int ) testStack.pop() ) ;
                   }

               assertTrue( testStack.isEmpty() ) ;

               this.currentTestPassed = true ;
               testPassed() ;
                       } ) ;
                },
            () ->
                {
                assertTimeoutPreemptively( super.testTimeLimit,
                   () ->
                       {
                       this.lastTestInGroupIsRunning =   true ;
                       this.currentTestPassed =          false ;

               int testSize =                   MAX_CAPACITY ;

               this.currentTestsAttempted++ ;
               writeLog( "[%,d, %,d] Testing: over-fill a MAX_CAPACITY (%,d) stack%n",
                         this.currentTestGroup, this.currentTestsAttempted,
                         testSize ) ;

               StackInterface<Integer> testStack = new ArrayStack<>( testSize ) ;

               Integer[] testArray = new Integer[ MAX_CAPACITY ] ;

               // fill it
               for ( int i = 0 ; i < testSize ; i++ )
                   {
                   testStack.push( i ) ;
                   testArray[ testArray.length - 1 - i ] = i ;
                   }

               // verify contents of the stack
               writeLog( "\tverifying stack contents:%n" ) ;
               Object[] testStackStack = (Object[]) getReferenceField( testStack, "stack" ) ;
               int testTopIndex = getIntField( testStack, "topIndex" ) ;
               int testStackStackIndex = testTopIndex ;
               
               // check the in-use portion of the array
               writeLog( "\t\t...in-use portion%n" );
               for ( int i = 0; i < testArray.length; i++ )
                   {
                   assertTrue( testStackStackIndex >= 0 ) ;
                   assertTrue( testStackStackIndex < testStackStack.length ) ;
                   assertEquals( testArray[ i ],
                                 testStackStack[ testStackStackIndex++ ] ) ;
                   }
               
               // make sure the rest of the array is null-filled
               writeLog( "\t\t...unused portion%n" ) ;
               for ( int i = 0; i < testTopIndex; i++ )
                   {
                   assertNull( testStackStack[ i ] ) ;
                   }

               // try to over-fill it
               boolean sawException =               false ;
               try
                   {
                   testStack.push( MAX_CAPACITY ) ;
                   }
               catch ( @SuppressWarnings( "unused" ) IllegalStateException e )
                   {
                   sawException =           true ;
                   }
               assertTrue( sawException ) ;
                                       // make sure we got the (right) exception

               // verify contents of the stack
               writeLog( "\tverifying stack contents:%n" ) ;
               testStackStack = (Object[]) getReferenceField( testStack, "stack" ) ;
               testTopIndex = getIntField( testStack, "topIndex" ) ;
               testStackStackIndex = testTopIndex ;
               
               // check the in-use portion of the array
               writeLog( "\t\t...in-use portion%n" );
               for ( int i = 0; i < testArray.length; i++ )
                   {
                   assertTrue( testStackStackIndex >= 0 ) ;
                   assertTrue( testStackStackIndex < testStackStack.length ) ;
                   assertEquals( testArray[ i ],
                                 testStackStack[ testStackStackIndex++ ] ) ;
                   }
               
               // make sure the rest of the array is null-filled
               writeLog( "\t\t...unused portion%n" ) ;
               for ( int i = 0; i < testTopIndex; i++ )
                   {
                   assertNull( testStackStack[ i ] ) ;
                   }

               // empty it
               for ( int i = MAX_CAPACITY - 1 ; i >= 0 ; i-- )
                   {
                   assertEquals( i,
                                 ( int ) testStack.pop() ) ;
                   }

               assertTrue( testStack.isEmpty() ) ;

               this.currentTestPassed = true ;
               } ) ; } ) ;

		}	// end testStackGrowth()


	/**
	 * Test multiple stacks.
	 */
	@Test
	@Order( 10500 )
	@DisplayName( "Multiple Stacks" )
	@Disabled
	void testMultipleStacks()
        {
        Integer testValue37 = Integer.valueOf( 37 ) ;
        Integer testValue42 = Integer.valueOf( 42 ) ;
        
		assertAll( () ->
		    {
		assertTimeoutPreemptively( super.testTimeLimit,
           () ->
               {
               this.lastTestInGroupIsRunning =   false ;
               this.currentTestPassed =          false ;

               this.currentTestsAttempted++ ;
               writeLog( "[%,d, %,d] Testing: multiple stack instances (1)%n",
                         this.currentTestGroup, this.currentTestsAttempted ) ;

               /*
                * - instantiate 2 stacks
                * - add an item to one stack
                * - make sure it contains the item and other is still empty
                * - repeat test with opposite stacks
                * - repeat test with both stacks simultaneously
                * - remove the items and make sure both stacks are empty
                */

               writeLog( "...instantiate 2 stacks" ) ;
               ArrayStack<Integer> testStack1 = new ArrayStack<>( DEFAULT_CAPACITY ) ;

               ArrayStack<Integer> testStack2 = new ArrayStack<>( DEFAULT_CAPACITY ) ;

               // add an item to testStack1
               writeLog( "...push 1 item onto stack 1%n" ) ;
               testStack1.push( testValue42 ) ;

               writeLog( "...test for item on stack 1%n" ) ;
               assertFalse( testStack1.isEmpty() ) ;
               assertEquals( testValue42,
                             testStack1.peek() ) ;

               // testStack2 must still be empty
               writeLog( "...test stack 2 for empty%n" ) ;
               assertTrue( testStack2.isEmpty() ) ;

               // we can remove the item from testStack1
               // and both stacks are now empty
               writeLog( "...pop item from stack 1%n" ) ;
               assertEquals( testValue42,
                             testStack1.pop() ) ;

               writeLog( "...verify both stacks empty%n" ) ;
               assertTrue( testStack1.isEmpty() ) ;
               assertTrue( testStack2.isEmpty() ) ;

               // add an item to testStack2
               writeLog( "...push 1 item onto stack 2%n" ) ;
               testStack2.push( testValue37 ) ;

               writeLog( "...test for item on stack 2%n" ) ;
               assertFalse( testStack2.isEmpty() ) ;
               assertEquals( testValue37,
                             testStack2.peek() ) ;

               // testStack1 must still be empty
               writeLog( "...test stack 1 for empty%n" ) ;
               assertTrue( testStack1.isEmpty() ) ;

               // we can remove the item from testStack2
               // and both stacks are now empty
               writeLog( "...pop item from stack 2%n" ) ;
               assertEquals( testValue37,
                             testStack2.pop() ) ;

               writeLog( "...verify both stacks empty%n" ) ;
               assertTrue( testStack1.isEmpty() ) ;
               assertTrue( testStack2.isEmpty() ) ;

               // add an item to testStack1
               writeLog( "...push 1 item onto each stack%n" ) ;
               testStack1.push( testValue42 ) ;
               testStack2.push( testValue37 ) ;

               writeLog( "...test for correct items on each stack%n" ) ;
               assertFalse( testStack1.isEmpty() ) ;
               assertEquals( testValue42,
                             testStack1.peek() ) ;
               assertFalse( testStack2.isEmpty() ) ;
               assertEquals( testValue37,
                             testStack2.peek() ) ;

               // we can remove the items from each and
               // both stacks are now empty
               writeLog( "...pop items from each stack%n" ) ;
               assertEquals( testValue42,
                             testStack1.pop() ) ;
               assertEquals( testValue37,
                             testStack2.pop() ) ;

               writeLog( "...verify both stacks empty%n" ) ;
               assertTrue( testStack1.isEmpty() ) ;
               assertTrue( testStack2.isEmpty() ) ;

               this.currentTestPassed =          true ;
               testPassed() ;
                   } ) ;
            },
        () ->
            {
            assertTimeoutPreemptively( super.testTimeLimit,
               () ->
                   {
                   this.lastTestInGroupIsRunning =   false ;
                   this.currentTestPassed =          false ;

               this.currentTestsAttempted++ ;
               writeLog( "[%,d, %,d] Testing: multiple stack instances (2)%n",
                         this.currentTestGroup, this.currentTestsAttempted ) ;

               /*
                * - instantiate stack 1
                * - add an item to one stack
                * - instantiate stack 2
                * - make sure stack 1 contains the item and stack 2 is empty
                * - remove the item from stack 1 and make sure both stacks are 
                *       empty
                */

               writeLog( "...instantiate stack 1%n" ) ;
               ArrayStack<Integer> testStack1 = new ArrayStack<>( DEFAULT_CAPACITY ) ;

               // add an item to testStack1
               writeLog( "...push 1 item onto stack 1%n" ) ;
               testStack1.push( testValue42 ) ;

               writeLog( "...test for item on stack 1%n" ) ;
               assertFalse( testStack1.isEmpty() ) ;
               assertEquals( testValue42,
                             testStack1.peek() ) ;

               writeLog( "...instantiate stack 2%n" ) ;
               ArrayStack<Integer> testStack2 = new ArrayStack<>( DEFAULT_CAPACITY ) ;

               // testStack2 must be empty
               writeLog( "...test stack 2 for empty%n" ) ;
               assertTrue( testStack2.isEmpty() ) ;

               // we can remove the item from testStack1
               // and both stacks are now empty
               writeLog( "...pop item from stack 1%n" ) ;
               assertEquals( testValue42,
                             testStack1.pop() ) ;

               writeLog( "...verify both stacks empty%n" ) ;
               assertTrue( testStack1.isEmpty() ) ;
               assertTrue( testStack2.isEmpty() ) ;

               this.currentTestPassed =          true ;
               testPassed() ;
                   } ) ;
            },
        () ->
            {
            assertTimeoutPreemptively( super.testTimeLimit,
               () ->
                   {
                   this.lastTestInGroupIsRunning =   false ;
                   this.currentTestPassed =          false ;

               this.currentTestsAttempted++ ;
               writeLog( "[%,d, %,d] Testing: multiple stack instances (3)%n",
                         this.currentTestGroup, this.currentTestsAttempted ) ;

               /*
                * - instantiate 2 stacks
                * - add items to each stack
                * - make sure each stack contains the correct items
                * - remove the items from both stacks and make sure they are
                *       both empty
                */

               writeLog( "...instantiate 2 stacks%n" ) ;
               ArrayStack<Integer> testStack1 = new ArrayStack<>( SMALL_CAPACITY ) ;
               ArrayStack<Integer> testStack2 = new ArrayStack<>( SMALL_CAPACITY ) ;

               // add items to testStack1
               writeLog( "...push multiple items onto stack 1%n" ) ;
               for ( int i = 0 ; i < MEDIUM_CAPACITY ; i++ )
                   {
                   testStack1.push( STACK_1_BASE + i ) ;
                   }

               // add an items to testStack2
               writeLog( "...push multiple items onto stack 2%n" ) ;
               for ( int i = 0 ; i < MEDIUM_CAPACITY ; i++ )
                   {
                   testStack2.push( STACK_2_BASE + i ) ;
                   }

               // remove items from testStack1
               writeLog( "...test for items on stack 1%n" ) ;
               assertFalse( testStack1.isEmpty() ) ;

               for ( int i = MEDIUM_CAPACITY - 1 ; i >= 0 ; i-- )
                   {
                   assertEquals( Integer.valueOf( STACK_1_BASE + i ),
                                 testStack1.pop() ) ;
                   }

               assertTrue( testStack1.isEmpty() ) ;

               // remove items from testStack2
               writeLog( "...test for items on stack 2%n" ) ;
               assertFalse( testStack2.isEmpty() ) ;

               for ( int i = MEDIUM_CAPACITY - 1 ; i >= 0 ; i-- )
                   {
                   assertEquals( Integer.valueOf( STACK_2_BASE + i ),
                                 testStack2.pop() ) ;
                   }

               assertTrue( testStack2.isEmpty() ) ;

               this.currentTestPassed =          true ;
               testPassed() ;
                   } ) ;
            },
        () ->
            {
            assertTimeoutPreemptively( super.testTimeLimit,
               () ->
                   {
                   this.lastTestInGroupIsRunning =   true ;
                   this.currentTestPassed =          false ;

               this.currentTestsAttempted++ ;
               writeLog( "[%,d, %,d] Testing: multiple stack instances (4)%n",
                         this.currentTestGroup, this.currentTestsAttempted ) ;

               /*
                * - instantiate 2 stacks
                * - add items to each stack
                * - make sure each stack contains the correct items
                * - remove the items from both stacks and make sure they are 
                *       both empty
                */
               writeLog( "...instantiate 2 stacks%n" ) ;
               ArrayStack<Integer> testStack1 = new ArrayStack<>( SMALL_CAPACITY ) ;
               ArrayStack<Integer> testStack2 = new ArrayStack<>( SMALL_CAPACITY ) ;

               // add items to testStack1
               writeLog( "...push multiple items onto stack 1%n" ) ;
               for ( int i = 0 ; i < MEDIUM_CAPACITY ; i++ )
                   {
                   testStack1.push( STACK_1_BASE + i ) ;
                   }

               // add an items to testStack2
               writeLog( "...push multiple items onto stack 2%n" ) ;
               for ( int i = 0 ; i < MEDIUM_CAPACITY ; i++ )
                   {
                   testStack2.push( STACK_2_BASE + i ) ;
                   }

               // remove items from testStack2
               writeLog( "...test for items on stack 2%n" ) ;
               assertFalse( testStack2.isEmpty() ) ;

               for ( int i = MEDIUM_CAPACITY - 1 ; i >= 0 ; i-- )
                   {
                   assertEquals( Integer.valueOf( STACK_2_BASE + i ),
                                 testStack2.pop() ) ;
                   }

               assertTrue( testStack2.isEmpty() ) ;

               // remove items from testStack1
               writeLog( "...test for items on stack 1%n" ) ;
               assertFalse( testStack1.isEmpty() ) ;

               for ( int i = MEDIUM_CAPACITY - 1 ; i >= 0 ; i-- )
                   {
                   assertEquals( ( STACK_1_BASE + i ),
                                 testStack1.pop() ) ;
                   }

               assertTrue( testStack1.isEmpty() ) ;

               this.currentTestPassed =          true ;

               } ) ; } ) ;

		}	// end testMultipleStacks()

	}	// end class ArrayStackDMRTests
