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

package edu.wit.scds.comp2000.stack.adt ;

import static org.junit.jupiter.api.Assertions.assertFalse ;
import static org.junit.jupiter.api.Assertions.assertNotNull ;
import static org.junit.jupiter.api.Assertions.assertTrue ;
import static org.junit.jupiter.api.Assertions.fail ;

import edu.wit.scds.comp2000.stack.StackInterface ;

import org.junit.jupiter.api.DisplayName ;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation ;
import org.junit.jupiter.api.Order ;
import org.junit.jupiter.api.Test ;
import org.junit.jupiter.api.TestInstance ;
import org.junit.jupiter.api.TestInstance.Lifecycle ;
import org.junit.jupiter.api.TestMethodOrder ;

/**
 * @author Luis Gjuraj   // TODO
 * @version 1.0.0 initial implementation
 */
@DisplayName( "Testing ArrayStack" )
@TestInstance( Lifecycle.PER_CLASS )
@TestMethodOrder( OrderAnnotation.class )
class ArrayStackStudentTests
    {

    /*
     * test constructors
     */

    /**
     * Test method for
     * {@link edu.wit.scds.comp2000.stack.adt.ArrayStack#ArrayStack()}.
     */
    @Test
    @Order( 100 )
    void testArrayStack()
        {
        System.out.println( "\nTesting: no-arg constructor" ) ;

        System.out.println( "...instantiate a stack with default initial capacity" ) ;
        final StackInterface<Integer> testStack = new ArrayStack<>() ;
        
        assertTrue(testStack instanceof ArrayStack); // see if its an instance of ArrayStack
        

        System.out.println( "Test passed." );
        
        }	// end testArrayStack()


    /**
     * Test method for
     * {@link edu.wit.scds.comp2000.stack.adt.ArrayStack#ArrayStack(int)}.
     */
    @Test
    @Order( 200 )
    void testArrayStackInt()
        {
        System.out.println( "\nTesting: 1-arg constructor" ) ;

        System.out.println( "...instantiate a stack with default initial capacity" ) ;
        final StackInterface<Integer> testStack = new ArrayStack<>(5) ;
        
        assertTrue(testStack instanceof ArrayStack); // see if its an instance of ArrayStack
        
        System.out.println( "Test passed." ) ;
        

        }	// end testArrayStackInt()


    /*
     * test API methods
     */

    /**
     * Test method for {@link edu.wit.scds.comp2000.stack.adt.ArrayStack#clear()}.
     */
    @Test
    @Order( 300 )
    void testClear()
        {
        System.out.println( "\nTesting: clear()" ) ;
        
        System.out.println( "...instantiate a stack with default initial capacity" ) ;
        final StackInterface<Integer> testStack = new ArrayStack<>() ;
        for(int i = 0; i < 100; i++) {
        testStack.push(i);
        assertFalse( testStack.isEmpty() ) ;

        testStack.clear();
        
        assertTrue(testStack.isEmpty());
        }

        System.out.println( "Test passed." ) ;
        
        }	// end testClear()


    /**
     * Test method for {@link edu.wit.scds.comp2000.stack.adt.ArrayStack#isEmpty()}.
     */
    @Test
    @Order( 400 )
    void testIsEmpty()
        {
        System.out.println( "\nTesting isEmpty()" ) ;

        // DONE
        
        System.out.println( "...instantiate a stack with default initial capacity" ) ;
        final StackInterface<Integer> testStack = new ArrayStack<>() ;

        assertNotNull( testStack ) ;    // unnecessary - for illustration

        System.out.println( "...must be empty" ) ;
        assertTrue( testStack.isEmpty() ) ;

        System.out.println( "...push() onto the stack" ) ;
        testStack.push( 3 ) ;
//        testStack.push( 3 ) ;   // if you uncomment this, this isEmpty() test
//                                // succeeds but the next isEmpty() test fails
        System.out.println( "...must not be empty" ) ;
        assertFalse( testStack.isEmpty() ) ;

        System.out.println( "...pop() from the stack" ) ;
        testStack.pop() ;
        System.out.println( "...must be empty again" ) ;
        assertTrue( testStack.isEmpty() ) ;

        System.out.println( "Test passed." ) ;

        }   // end testIsEmpty()


    /**
     * Test method for {@link edu.wit.scds.comp2000.stack.adt.ArrayStack#peek()}.
     */
    @Test
    @Order( 500 )
    void testPeek()
        {
        System.out.println( "\nTesting peek()" ) ;
        System.out.println( "...instantiate a stack with default initial capacity" ) ;
        final StackInterface<Integer> testStack = new ArrayStack<>() ;

        for (int i=0;i < 100; i++) { //to make sure that it is able to read 
        							 // each number when we add it
            testStack.push(i);
            
            assertTrue(testStack.peek()==i);
            	
            
            
            }

        System.out.println( "Test passed." ) ;

        }	// end testPeek()


    /**
     * Test method for {@link edu.wit.scds.comp2000.stack.adt.ArrayStack#pop()}.
     */
    @Test
    @Order( 600 )
    void testPop()
        {
        System.out.println( "\nTesting pop()" ) ;

        System.out.println( "...instantiate a stack with default initial capacity" ) ;
        final StackInterface<Integer> testStack = new ArrayStack<>() ;
        
        for (int i=0;i < 100; i++) { //to make sure that it is able to read 
			 // each number when we add it
        	testStack.push(i); // last number will be 99
        	}
        for (int i=99;i >=0;i--) {
        	assertTrue(testStack.pop()==i);
        }

        System.out.println( "Test passed." ) ;

        }	// end testPop()


    /**
     * Test method for
     * {@link edu.wit.scds.comp2000.stack.adt.ArrayStack#push(java.lang.Object)}.
     */
    @Test
    @Order( 700 )
    void testPush()
        {
        System.out.println( "\nTesting push()" ) ;

        System.out.println( "...instantiate a stack with default initial capacity" ) ;
        final StackInterface<Integer> testStack = new ArrayStack<>() ;
        
        for (int i=0;i < 100; i++) { //to make sure that it is able to read 
			 // each number when we add it
        	testStack.push(i);
        	assertTrue(testStack.peek()==i);
        	}

        System.out.println( "Test passed." ) ;

        }	// end testPush()

    }	// end class ArrayStackStudentTests
