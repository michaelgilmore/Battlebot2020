package cc.gilmore.athenahealth;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//################################################################################################
//# Hidden Message - Morse Code
//################################################################################################
//
//################################################################################################
//# Step One
//# Given a string that has been converted to Morse code, find all of the possible and unique sequences of
//# remaining tokens after removing the second message from the first. There are 3 different types of
//# tokens in the Morse code message.
//# • Dot (*)
//# • Dash (-)
//# • Blank (_)
//# Every letter in the message is separated by a single blank character and every word is separated by 3
//# blank characters.
//# Example:
//# • Given: AB *-_-***
//# • Remove: R *-*
//# • This can be done 6 different ways.
//# o X X _ - X * * o X X _ - * X * o X X _ - * * X
//# o X - _ X X * * o X - _ X * X * o X - _ X * * X
//# • But this results in only 2 possible unique sequences of remaining tokens.
//# o _ - * * o - _ * *
//# • So the final result for this example would be 2.
//# 
//# Write a program that can calculate all of the deletion paths for removing one Morse code message
//# from another. This program should be able to calculate all of the paths in the example below in under
//# 10 seconds and return the total number of paths found. This is a preliminary step for your program, for
//# testing.
//# • Given: Hello World
//# • Remove: Help
//# o ****_*_*-**_*- **_-- -___*- -_- -- _*-*_*- **_-**
//# o ****_*_*-**_*- -*
//# • ANSWER: 1311
//# 
//################################################################################################
//# Step Two
//# Find the possible deletion paths where you must remove a second message from the remaining tokens
//# in the original message after removing the first phrase, including blank tokens. All remaining tokens
//# would be kept in the same order to find a second phrase. Return all of the possible and unique
//# sequences of remaining tokens after removing both phrases.
//# In the example above where R is removed from AB notice that there are 6 delete paths but only 2
//# unique sequences of remaining tokens.
//# Example:
//# • Given: ABCD
//# • Remove: ST
//# • Then Remove: ZN
//# • One solution path would look like:
//# o *-_- ***_-*- *_-**
//# o ***_-
//# o -- **_-*
//# o Start:
//# o Remove ST:
//# o Then Remove ZN:
//# o The set of remaining characters
//# ? * - _ - * * * _ - * - * _ - * *
//# ? x - _ - x x * x x * - * _ - * *
//# ? x x _ x x x x x x x - * x x x *
//# ? _ - * *
//# • There are 5 sequences of remaining characters for this example:
//# o _-** o _*-* o -_** o *_-* o *-_*
//# Expand your program to find all of the possible sequences of remaining characters after removing 2
//# hidden Morse code messages from an original message. This program should be able to calculate all of
//# the sequences in the example below in less than 60 seconds and return the total number of distinct and
//# valid sequences found. This will be the final expected output from the program.
//# • Given: The Star Wars Saga o -_****_*___***_- _*-_*- *___*-- _*-_*- *_***___***_*-_- -*_*-
//# • Remove: Yoda o -*- -_- -- _-**_*-
//# • And Remove: Leia o *-**_*_**_*-
//# • Expected Answer: 11474
//# 
//# Program Specifications
//# • Input: Three command-line arguments denoting the original message, the first hidden message,
//# and the second hidden message. The three messages will be in Morse code using the
//# representation described here.
//# • Expected Output: Total number of distinct and valid remaining token sequences in the original
//# message.
//# • Bounds: Original Message less than 100 Morse code characters.
//
//################################################################################################
//# Test cases for Step 1
//# 1.1. No parameters
//# 1.2. Only one parameter which is valid
//# 1.3. Only one parameter which is invalid
//# 1.4. Two parameters, neither is valid
//# 1.5. Two parameters, first is valid, second is invalid
//# 1.6. Two parameters, first is invalid, second is valid
//# 1.7. Two parameters, both valid
//#
//
//################################################################################################
//# Additional test cases for Step 2
//# 2.1. A third parameter that is invalid
//# 2.2. A third parameter that is valid
//# ...
//#


class AthenahealthCodingTest {

	private String source;
	private String hiddenMsg1;
	private int[] msg1Indices;
	private String hiddenMsg2 = null;
	private Set<String> results = new HashSet<String>();
	private StringBuilder sbLeftover = null;
	private boolean inTestMode = false;
	
    public static void main(String[] args) {
        AthenahealthCodingTest app = new AthenahealthCodingTest();
        app.processArgs(args);
        app.run();
    }
    
    public void processArgs(String[] args) {
        
        if(args == null || args.length <= 0) {
            printUsage();
            System.exit(0);
        }
        
        source = args[0];
        
        handleTestRuns();
        
        if(!inTestMode) {
	        hiddenMsg1 = args[1];
        }

        System.out.println("Running with:\nsource = '" + source
        				+ "'\nhiddenMsg1 = '" + hiddenMsg1 + "'");

        if(args.length > 2) {
        	hiddenMsg2 = args[2];
        }
        
        if(hiddenMsg2 != null && hiddenMsg2.length() > 0) {
        	System.out.println("hiddenMsg2 = '"+hiddenMsg2+"'");
        }
        
        //blank line between run parameters and results
        System.out.println();
    }
    
    public void handleTestRuns() {
        if(source.equals("-testA")) {
            source = "*-_-***"; //AB
            hiddenMsg1 = "*-*"; //R
            inTestMode = true;
            System.out.println("TestA - Expected Answer: 2");
        }
        else if(source.equals("-testB")) {
            source = "****_*_*-**_*-**_---____*--_---_*-*_*-**_-**"; //Hello World
            hiddenMsg1 = "****_*_*-**_*--*"; //Help
            inTestMode = true;
            System.out.println("TestB - Expected Answer: 1311");
        }
        else if(source.equals("-testC")) {
            source = "*-_-***_-*-*_-**"; //ABCD
            hiddenMsg1 = "***_-"; //ST
            hiddenMsg2 = "--**_-*"; //ZN
            inTestMode = true;
            System.out.println("TestC - Expected Answer: 5");
        }
        else if(source.equals("-testD")) {
        	//This is one from Step Two of TestC.
            source = "-_-**-*_-**"; //
            hiddenMsg1 = "--**_-*"; //ZN
            inTestMode = true;
            System.out.println("TestD - Expected Answer: 2");
        }
        else if(source.equals("-testE")) {
            source = "-_****_*___***_-_*-_*-*___*--_*-_*-*_***___***_*-_--*_*-"; //The Star Wars Saga
            hiddenMsg1 = "-*--_---_-**_*-"; //Yoda
            hiddenMsg2 = "*-**_*_**_*-"; //Leia
            inTestMode = true;
            System.out.println("TestE - Expected Answer: 11474");
        }
        else if(source.equals("-testF")) {
            source = "-_****_*___***_-_*-_*-*___*--_*-_*-*_***___***_*-_--*_*-"; //The Star Wars Saga
            hiddenMsg1 = "-*--_---_-**_*-"; //Yoda
            inTestMode = true;
            System.out.println("TestE - Expected Answer: ?");
        }
    }
    
    public void run() {
    	
    	long start = System.currentTimeMillis();
    	
    	processHiddenMessage1();

		if(hiddenMsg2 != null)
			processHiddenMessage2();

		System.out.println("\nANSWER: " + results.size());
		
//		for(String s : results) {
//			System.out.println(s);
//		}

		long finish = System.currentTimeMillis();
		System.out.println("runtime = " + ((finish - start)/1000) + " seconds");
    }

	private void processHiddenMessage1() {
//		System.out.println("Calling processHiddenMessage1");

        msg1Indices = new int[hiddenMsg1.length()];

		if(findFirstIndices()) {
			do {
//		    	System.out.print("Indices: ");
//		    	for (int i : msg1Indices) {System.out.print(i + ",");}
//		    	System.out.println();
		
		    	String leftover = getLeftover();
		    	//HashSet doesn't allow duplicates
	    		if(results.add(leftover)) {
	    			System.out.println("Adding pattern("+(results.size())+") '" + leftover + "'");
	    		}
			} while(getNextIndices());
		}
	}

	private void processHiddenMessage2() {
//		System.out.println("Calling processHiddenMessage2");

		//re-initialize global variables
		hiddenMsg1 = hiddenMsg2;
		
		//make copy so existing code can use results list
		List<String> process1Results = new ArrayList<String>();
		for(String result : results) {
			process1Results.add(result);
		}
		results.clear();

		for(String pattern : process1Results) {
			source = pattern;
			processHiddenMessage1();
		}
	}
	
	private boolean findFirstIndices() {
//		System.out.println("Calling findFirstIndices");
		
    	int i1 = 0;
    	int startIndex = 0;
		for (char ch: hiddenMsg1.toCharArray()) {
    		msg1Indices[i1] = source.indexOf(ch, startIndex);
    		if(msg1Indices[i1] == -1) {
				return false;
    		}
    		startIndex = msg1Indices[i1] + 1;
    		i1++;
    	}
		
		return true;
    }
    
    private boolean getNextIndices() {
//    	System.out.println("Calling getNextIndices()");
    	
    	int lastHidMsgIndex = msg1Indices.length-1;
    	while(msg1Indices[lastHidMsgIndex]+1 >= source.length()) {
    		lastHidMsgIndex--;
    		if(lastHidMsgIndex < 0) {
    			return false;
    		}
    	}
    	
    	int startIndex = msg1Indices[lastHidMsgIndex] + 1;
    	for(int i = lastHidMsgIndex; i < msg1Indices.length; i++) {
    		msg1Indices[i] = source.indexOf(hiddenMsg1.charAt(i), startIndex);
//    		System.out.println("Found '"+hiddenMsg1.charAt(i)+"' for '"+i+"' at '"+msg1Indices[i]+"' starting from '"+startIndex+"'");
    		if(msg1Indices[i] == -1) {
    			if(i == 0) {
    				return false;
    			}
    			i--;//back up to previous index
    			startIndex = msg1Indices[i] + 1;
    			i--;//offset the i++
    		}
    		else {
    			startIndex = msg1Indices[i] + 1;
    		}
    	}
    	
    	return true;
    }
    
    private String getLeftover() {
    	sbLeftover = new StringBuilder(source);
    	for(int i = msg1Indices.length-1; i >= 0;  i--) {
    		sbLeftover.deleteCharAt(msg1Indices[i]);
    	}
    	return sbLeftover.toString();
    }
    
    public void printUsage() {
    	System.out.println(
    	"Step One Usage\n"
    	+ "----------------------------------------------------------\n"
    	+ "this_script.pl [source] [hidden_msg]\n"
    	+ "\n"
		+ "This script removes the hidden message from the source string in as many ways as possible and gives a count of the number of unique Morse Code sequences that result.\n"
    	+ "\n"
    	+ "source = a string of Morse Code made up only of dits, dahs, and spaces\n"
    	+ "hidden_msg = a string of Morse Code made up only of dits, dahs, and spaces that should be found within the source string\n"
    	+ "dit = the '*' character\n"
    	+ "dah = the '-' character\n"
    	+ "space = the '_' character\n"
    	+ "\n"
    	+ "There should be a single space character between each Morse Code letter and three space characters between each Morse Code word.\n"
    	+ "\n"
    	+ "Example:\n"
    	+ "this_script.pl ****_*_*-**_*-**_---___*-_-_****_*_-*_*-_****_*_*-_*-**_-_**** **-*_**_-*_-**___--_*\n"
    	+ "source = hello athenahealth\n"
    	+ "hidden_msg = find me\n"
    	+ "\n"
    	+ "\n"
    	+ "----------------------------------------------------------\n"
    	+ "Step Two Usage\n"
    	+ "----------------------------------------------------------\n"
    	+ "this_script.pl [source] [hidden_msg] [second_hidden_msg]\n"
    	+ "\n"
    	+ "This script removes the first hidden message from the source string in as many ways as possible. Then removes the second hidden message from each of those unique results in the same manner giving a combined count of all unique Morse Code sequences that result.");
    }
}
