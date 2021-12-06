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

package edu.wit.scds.comp2000.stack.app ;

import edu.wit.scds.comp2000.stack.StackInterface ;
import edu.wit.scds.comp2000.stack.VectorStack ;
import edu.wit.scds.dmr.testing.framework.StubMethodException ;

import java.util.NoSuchElementException ;
import java.util.Scanner ;

/**
 * A class to evaluate infix arithmetic expressions:
 * <ul>
 * <li>all values are represented as {@code long}s
 * <li>all arithmetic operations are performed with integer arithmetic (no fractional
 * values)
 * <li>all arithmetic operations are performed with Java binary operators
 * <li>no Java Class Library (JCL) classes or methods are used to perform the
 * expression evaluation
 * </ul>
 * <p>
 * Standard (minimum): Support for valid expressions containing:
 * <ul>
 * <li>single-digit, unsigned, decimal operands
 * <li>operators: {@code +, -, *, /, %}
 * <li>parenthesized subexpressions, including nested parentheses
 * <li>spaces, which are ignored
 * </ul>
 * and invalid expressions containing:
 * <ul>
 * <li>invalid characters
 * </ul>
 * <p>
 * Optional: Support for expressions containing all of the above plus:
 * <ul>
 * <li>multi-digit, unsigned, decimal operands
 * </ul>
 * <p>
 * Optional: Support for valid expressions containing all of the above plus invalid
 * expressions containing:
 * <ul>
 * <li>unbalanced parentheses
 * <li>multiple consecutive operators
 * <li>division by zero
 * <li>consecutive operands
 * </ul>
 *
 * @author Dave Rosenberg
 * @version 1.0.0 2019-02-08 initial implementation
 * 
 * @author Luis Gjuraj      // TODO
 * @version 1.1.0 2021-10-15 implement per assignment
 */
public final class InfixExpressionEvaluator
    {

    /**
     * prevent instantiation
     */
    private InfixExpressionEvaluator()
        {
        // can't instantiate an InfixExpressionEvaluator
        }	// end constructor


    /**
     * Evaluate an infix arithmetic expression.
     *
     * @param expression
     *     an infix expression composed of unsigned decimal operands and a
     *     combination of operators ({@code +, -, *, /, %}) including parenthesized
     *     (sub-)expressions; may include spaces
     * @return the result of evaluating {@code expression}
     * @throws ArithmeticException
     *     if {@code expression} is syntactically invalid, is null/0-length,
     *     attempts to divide by zero, or contains unrecognized characters
     */
    public static long evaluate( final String expression ) throws ArithmeticException
        {
        
        // we'll use 2 stacks to remember what's left to evaluate
        final StackInterface<Character> operatorStack = new VectorStack<>() ;
        final StackInterface<Long> valuesStack = new VectorStack<>() ;

        
        for(int i =0 ; i<expression.length();i++) {
        	if(expression.charAt(i)=='(') {
        	pushNext(expression,valuesStack,operatorStack,i); // pushes next value into correct stack
        	}
        	else if( expression.charAt(i)==')') {	// in case we run into a closing bracket
        		if(operatorStack.peek()== '(') { // remove ( when we run into it
        			operatorStack.pop();
        		}
        		else {
        			while(operatorStack.peek()!='(') { // while searching for '(', do the other operations 
        				long n2 = valuesStack.pop();
        				long n1 = valuesStack.pop();
        				char operation = operatorStack.pop();
        				valuesStack.push(doOperation(operation,n1,n2));
        			}
        			operatorStack.pop();
        			}
        	}//if operator, add to stack
        	else if (expression.charAt(i)=='*' || expression.charAt(i)=='/' || expression.charAt(i)=='%' || expression.charAt(i)=='+' || expression.charAt(i)=='-') {
        		if(operatorStack.isEmpty()) {
            		pushNext(expression,valuesStack,operatorStack,i);
            		}
        		// if previous operator has bigger precedence, execute it first . Dont know how to make it loop so that it checks the next operation after it.
        		else if(precedenceOf(expression.charAt(i)) <= precedenceOf(operatorStack.peek()) && !operatorStack.isEmpty()) {
        			long n2 = valuesStack.pop();
    				long n1 = valuesStack.pop();
    				char operation = operatorStack.pop();
    				valuesStack.push(doOperation(operation,n1,n2));
    				pushNext(expression,valuesStack,operatorStack,i);
        		}
        		// continue if not
        		else if(precedenceOf(expression.charAt(i)) >= precedenceOf(operatorStack.peek()) && !operatorStack.isEmpty()) {
        			pushNext(expression,valuesStack,operatorStack,i);
        		}
        	}
        	// get the value of the char, if its equal to a digit, put it in the value stack
        	else if(Value(expression.charAt(i)) >= 0 && Value(expression.charAt(i)) <= 9) {// when we get digits
        		
				pushNext(expression,valuesStack,operatorStack,i);

        	}
        	else if(Character.isWhitespace(expression.charAt(i))) { // continue on whitespaces
        	continue;
        	}
        	else
        		throw new ArithmeticException("unrecognized character: '"+expression.charAt(i)+"'");// alien symbols
        	
        }
        while(!operatorStack.isEmpty()) {
			long n2 = valuesStack.pop();
			long n1 = valuesStack.pop();
			char operation = operatorStack.pop();
			valuesStack.push(doOperation(operation,n1,n2)); // do the rest of the operations when we reach end of loop
		}													// which is also end of the expression
        

        
        // the result is on top of the values/operand stack
        return valuesStack.pop() ;        
        
        }	// end evaluate()

    private static void pushNext(String expression, StackInterface<Long> valuesStack, StackInterface <Character> operatorStack, int i) {
    	if(expression.charAt(i)=='+' || expression.charAt(i)=='(' || expression.charAt(i)==')' || expression.charAt(i)=='-' || expression.charAt(i)=='/' || expression.charAt(i)=='*'|| expression.charAt(i)=='%')
    		operatorStack.push(expression.charAt(i));
    	else if(Value(expression.charAt(i)) >= 0 && Value(expression.charAt(i)) <= 9 ) {
    		valuesStack.push(Value(expression.charAt(i)));// if operator, put in operator stack. if char is a num, put it in the value stack
    	}												  // great if you want to copy it over and over, not really if you are typing it with different stacks every time.
    	
    }
    
    private static int precedenceOf(char operator) { // assigning precedence to operators
    	if(operator=='+' || operator == '-') {
    		return 1;
    	}
    	else if (operator=='/' || operator == '*' || operator == '%') {
    		return 2;
    	}
    	else if(operator == ')') {
    		return 3;
    	}
    	return 0;
    }
    
    private static long doOperation(char operator, long n1, long n2) { // looks at what operation we have and executes it
    	if(operator == '+') {
    		return (n1 + n2);
    	}
    	else if(operator == '-') {
    		return (n1 - n2);
    	}
    	else if(operator == '/') {
    		return (n1 / n2);
    	}
    	else if(operator == '*') {
    		return (n1 * n2);
    	}
    	else if(operator == '%') {
    		return n1%n2;
    	}
    	return 0;
    }
    
    private static long Value(char number) {// gets us the digit we want
    	return (long) (number -'0');
    }
   
    /**
     * (optional) Driver for testing/debugging.
     * <p>
     * Facilitates interactive testing of {@code evaluate()}.
     *
     * @param args
     *     -unused-
     */
    public static void main( final String[] args )
        {
        // interactive via console
        try ( Scanner input = new Scanner( System.in ) )
            {
            while ( true )
                {
                System.out.printf( "%nEnter expression (or quit): " ) ;
                String expression = input.nextLine() ;
                
                // continue or exit?
                String trimmedExpression = expression.trim() ;
                if ( ( trimmedExpression.length() <= 4 ) &&
                     !trimmedExpression.equals( "" ) &&
                     trimmedExpression.equalsIgnoreCase( "quit".substring( 0, trimmedExpression.length() ) ) )
                    {
                    break ; // exit
                    }

                System.out.print( "Result: " ) ;
                try
                    {
                    System.out.printf( "%,d%n", evaluate( expression ) ) ;
                    }
                catch ( final Throwable e )
                    {
                    e.printStackTrace( System.out ) ;
                    }   // end try/catch
                
                }   // end while
            }
        catch ( @SuppressWarnings( "unused" ) NoSuchElementException e )
            {
            // ignore end-of-file/input - done processing
            }
        catch ( final Throwable e )
            {
            e.printStackTrace( System.out ) ;
            }   // end try/catch

        System.out.printf( "%ndone%n" ) ;
        
        }   // end main()

    }	// end class InfixExpressionEvaluator
