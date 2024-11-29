package osu.cse2123;
/**
 * Word counting exercises.
 * 
 * @author Connor Kovacs
 * @version 10102023
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class WordAnalysis {
	
	/**
	 * Returns the sequence of non letter or digits from the input String
	 * str.
	 * 
	 * @param str String that does not start with a letter or digit
	 * @precond str starts with 1 or more non-letter or digit characters
	 * @return the sequence of non-letter and digits that start str
	 */
	public static String getNextNonTokenSequence(String str) {
	
		String string = "";
		int count = 0;
		boolean valid = true;
		while(valid && count < str.length()){
			char current = str.charAt(count++);
			if(Character.isLetterOrDigit(current)){
				valid = false;
			} else {
				string+=current;
			}
		}
		return string;
	}

	/**
	 * Returns the sequence of letter or digits from the input String
	 * str.
	 * 
	 * @param str String that starts with a letter or digit
	 * @precond str starts with 1 or more letter or digit characters
	 * @return the sequence of letter and digits that start str
	 */
	public static String getNextTokenSequence(String str) {
		String string = "";
		int count = 0;
		boolean valid = true;
		while(valid && count<str.length()){
			char current = str.charAt(count++);
			if(!Character.isLetterOrDigit(current)){
				valid = false;
			} else {
				string += current;
			}
		}
		return string;
	}

	/**
	 * Returns a queue of words from the input String str
	 * 
	 * @param str string to split into words
	 * @return a queue of words from the string str
	 */
	public static Queue<String> splitWords(String str) {
		String string1;
		String string2;
		Queue<String> newWords = new LinkedList<>();
		int index = 0;
		while(index < str.length()) {
			string1 = getNextNonTokenSequence(str.substring(index));
			
		
		if(string1.length() > 0) {
			index = str.indexOf(string1,index) + string1.length();
		}
		string2 = getNextTokenSequence(str.substring(index));
		if(string2.length() > 0) {
			newWords.add(string2);
			index = str.indexOf(string2,index) + string2.length();

		}
		}
		return newWords;
	}

	/**
	 * Returns a set of words from the input file fname.
	 * 
	 * @param fname filename of words file to read
	 * @return a set of words from the input file.
	 */
	public static Set<String> getWordsInFile(String fname) throws FileNotFoundException {
		
		HashSet<String> wordString = new HashSet<String>();
		Scanner scan = new Scanner(new File(fname));
		String set;
		while(scan.hasNextLine()) {
			
			set = scan.nextLine();
			Queue<String> newWords = splitWords(set);
			for(String q: newWords) {
				wordString.add(q);
			}
		}
		
		scan.close();
		return wordString;
	}
	
	/**
	 * Returns a map of word counts from the input file fname.
	 * 
	 * @param fname filename of words file to read
	 * @return a map of words and counts from the input file.
	 */
	public static Map<String, Integer> getWordCounts(String fname) throws FileNotFoundException {
		
       Map<String,Integer> count = new HashMap<>();
      Scanner scan = new Scanner(new File(fname));
       String set;
       while(scan.hasNextLine()) {
			
			set = scan.nextLine();
			Queue<String> newWords = splitWords(set);
			for(String q: newWords) {
				if(count.containsKey(q)) {
					count.put(q, count.get(q)+1);
				}else 
					count.put(q,1);
				}
			}
			scan.close();
		return count;
	}

	public static void main(String[] args) throws FileNotFoundException {

		Scanner keyboard = new Scanner(System.in);
		System.out.print("Enter a filename: ");
		String fname = keyboard.nextLine();
		try {
			File textFile = new File(fname);
			Scanner textScanner = new Scanner(textFile);
			while(textScanner.hasNext()) {
				String exp = textScanner.nextLine();
				
					Map<String, Integer> list = getWordCounts(exp);
					getWordCounts(exp);
			System.out.println(exp);
				
			}
			textScanner.close();
		
		  }catch(FileNotFoundException e) {
			  System.out.print("Error could not open the file " + fname);
		  }
		keyboard.close();
	}
}
/*  Auto-generated method stub
String newStr = getNextNonTokenSequence("?  .-abc123");
System.out.println("Non token sequence is: "+newStr);
newStr = getNextTokenSequence("223abc  ?.");
System.out.println("Token sequence is: "+newStr);
Queue<String> q = splitWords("Over the river, and through the woods.  To grandmother's house.");
System.out.println("Queue of words is: "+q);*/

