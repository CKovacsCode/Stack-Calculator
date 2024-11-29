package osu.cse2123;
/**
 * A series of methods to implement a calculator that evaluates postfix expressions
 * (also known as RPN or Reverse Polish Notation) using stacks and queues.
 * 
 * @author Connor Kovacs	
 * @version 10032023
 */



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;



public class StackCalculator {


	/**
	 * Returns the sequence of digit characters from the front of a String
	 * that make up an integer value.
	 * @param str String that starts with an integer value
	 * @precond str starts with 1 or more digit characters
	 * @return the sequence of digits that start the String str
	 */
	public static String getNextDigitSequence(String str) {
		String str2 = "";//String our new string

		for(int idx = 0;idx < str.length();idx++) {
			char i = str.charAt(idx);//set our variable 
			if(Character.isDigit(i)) {//Determines if the specified character is a digit
				str2 = str2 + i;// add our new string with our integers in the string
			}
		}
		return str2;//return our new string

	}

	/**
	 * Returns the sequence of whitespace characters from the front of a
	 * String.
	 * @param str String that starts with whitespace
	 * @precond str starts with 1 or more whitespace characters
	 * @return the 
	 */
	public static String getNextWhitespaceSequence(String str) {
		String str2 = "";//set new string

		for(int idx = 0;idx < str.length();idx++) {
			char i = str.charAt(idx);//set our variable
			if(Character.isWhitespace(i)){//Determines if the specified character is white space  
				str2 = str2 + i;// add our new string with our whitespaces in the string
			}
		}
		return str2;//return our new string

	}



	/**
	 * Converts a string of mathematical operations into a Queue of 
	 * Strings.  See the assignment write-up for details.
	 * @param str String to convert
	 * @precond str contains only digits, whitespace and operators + - / * % ( )
	 * @return a queue with each digit sequence (number) and operator as a separate entry in the same
	 *         order they appeared in the string str
	 */
	public static Queue<String> parseSymbols(String str) {
		Queue<String> q = new LinkedList<>();//add the new list we add our variables
		String word = "";//String our separator
		for(int idx = 0; idx < str.length(); idx++) { 
			//set i to the character that the loop will look use
			char i = str.charAt(idx);
			//check for our operators without the negative sign cause we run into error make seperate statment 
			if(i == '('  || i == ')' || i == '+' || i == '/' || i == '*' || i == '%') {
				//If one of our operators hits then we need to add our separator 
				if(word.length() > 0) {
					q.add(word);
					word = "";
				}
				//then we can add our variable into q
				q.add(String.valueOf(i));
			} 
			//if we dont have one of the operators above then we have a minus sign that could be just a reuglar minus sign or correlate to a negative number 
			if(i == '-') {
				if(word.length()> 0) {
					q.add(word);	
				}//set to idx because thats our increment 
				else if(Character.isDigit(str.charAt(idx+1))) {//If character is a digit in str at idx + 1 we add our separator to it then to the list this makes sure that we check the character after the '-' sign to make sure we do/don't have a negative number 
					word = word + i;//add the values together
				}
				else {
					q.add(String.valueOf(i));//add our value to our list if it is '-' or a negative integer 
				}
			}
			else if(Character.isWhitespace(i)) {//Check for whitespaces 
				if(word.length()> 0) {
					q.add(word);
					word = "";//make sure we have our separator so we don't get dupilcate digits in our list
				}
			}
			else if(Character.isDigit(i)) {//if our character is a digit 
				word = word + i; //add our seperator to the digits that we find in the list
			}
		}
		if(word.length()>0) {
			q.add(word);

		}
		return q;//return our q
	}
	/**
	 * Combines operands num1 and num2 with the operator op and returns the result
	 * of (num1 op num2)
	 * @param op - operator + - * % or /
	 * @param num1 first operand to combine
	 * @param num2 second operand to combine
	 * @precond op is restricted to be one of "+" "-" "*" "%" "/"
	 * @return num1 op num2
	 */
	public static int evaluate(String op, int num1, int num2) {
		int result = 0;//Variablize result so we dont need to use multiple return statements
		if(op.equals("+")) {//iff equal result equals the op with the two nums
			result = num1 + num2;
		}
		else if(op.equals("-")) {//subtraction expression
			result = num1 - num2;
		}
		else if(op.equals("*")) {//multiply expression
			result = num1 * num2;
		}
		else if(op.equals("%")) {//modulo expression
			result = num1 % num2;
		}
		else if(op.equals("/")) {
			result = num1 / num2;//we know this is our last so we can just set else
		}

		return result;//return result
	}
	/*  1) read String and turn it into a Queue<String>
	2) create empty Stack<Integer> operands
	3) check if the head of the queue can be interpreted as a digit
	3.1) YES: add to the stack
	3.2) NO: if a special character, put result back on the stack
	4) if the queue is empty, pop the last result from the stack*/
	/**
	 * Evaluates postfix algebraic expressions
	 * @param input queue to be evaluated
	 * @precond input is formatted as a correct postfix algebraic expression
	 * @return the evaluation of input as an postfix algebraic expression
	 */
	public static int postfixEvaluate(Queue<String> input) {
		Stack<Integer> operands = new Stack<>();//create a stack
		List<String> operatorChars = new ArrayList<>();// add our operators to a new list 
		operatorChars.add("+");
		operatorChars.add("-");
		operatorChars.add("*");
		operatorChars.add("%");
		operatorChars.add("/");	
		int result = 0;//int result
		while(!input.isEmpty()) {//if input is not empty run through
			String element = input.poll();//We won't need to use remove because poll retrieves and removes the head of this queue,or returns null if this queue is empty
			//bad practice to use exceptions in control flow
			try {
				int val = Integer.parseInt(element); //Checking to see if our element at the head is a number
				operands.push(val);// if its a number push it to the stack 
			} catch (Exception e) { 
				//now we know it's not a number check if its one of the operand characters instead
				if(operatorChars.contains(element)) {//if one of our operator characters contains a operator we pop our two numbers 
					int num1 = operands.pop();//Pop our two numbers
					int num2 = operands.pop();
					result = evaluate(element, num2, num1);// Evaluate the element with the two number
					operands.push(result);//push the result
				} 
			}
		}
		return operands.pop();//return the pop
	}

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);//scanner in 
		System.out.print("Enter a postfix expression: ");//prompt
		String exp = scan.nextLine();//scan the users input on the next line
		Queue<String>inputs = parseSymbols(exp);//Make our new Queue of strings 
		if(!inputs.isEmpty()) {//If input user entered is not empty execute the loop
			parseSymbols(exp);//Parse the user input through our parseSymbols method to find if it symbols
			int eval = postfixEvaluate(inputs);//variablize our inputs and call our postfix method to deal with the inputs by the user
			System.out.println("The answer is: " + eval);//print out answer
		}

		else if(inputs.isEmpty()) {//once empty print out goodbye
			System.out.print("Goodbye!");
		}
	}
}





