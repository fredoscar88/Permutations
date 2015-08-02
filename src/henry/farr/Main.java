package henry.farr;

//import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {

	static String ConsoleInput;
	static String ConsoleCmd;
	static Scanner ConsoleReader;
	static int Iteration = 0;
	static boolean doSpellCheck = true;
	static boolean toNull = false;
	static boolean running = true;
	static boolean debug = false;
	static int culled = 0;
	static int repeats = 0;
	
	
	static Set<String> list;
	
	public static void main(String[] args) {
		System.out.println("Debug"); 
		ConsoleRun("Permutation lister v1.0");	//Accepted parameter of ConsoleRun is the startup message
	}
	
	
	
	static void ConsoleRun(String ConsoleStartMessage) {
		ConsoleReader = new Scanner(System.in);
		
		System.out.println(ConsoleStartMessage);
		System.out.println("Type \"?\" or \"help\" for help.");
		//System.out.println("Debug (should say 'null'): " + ConsoleInput);
		
		while (running) {
			
			if (ConsoleInput == null) {
				
				System.out.print(": ");
				ConsoleInput = ConsoleReader.nextLine();
				//ConsoleInput = ConsoleInput.toLowerCase();
					
				ConsoleCmd = ConsoleParse(ConsoleInput);
					
			} else {
				ConsoleCmd = ConsoleParse(ConsoleInput);
			}
			
			//Here, we'd send ConsoleCmd to be checked against a list of command words to perform console actions.
			//This is dependent on the command or action word preceding it. Some action words incorporate the succeeding 
			//words so whatever method reads these and performs actions will account for that. I think I should save this
			//console stuff somewhere :I
			//Action words that don't require succeeding words should wipe ConsoleInput to null, and invalid input should
			//inform the user of the invalid command.
			
			//For the purposes of this program though we dont need such an interactive console.
			
			ConsoleAction(ConsoleCmd);
			
			//System.out.println(ConsoleCmd + Iteration); 	//Action word that just performed
			//System.out.println(ConsoleInput + Iteration);	//Remaining command to execute
			Iteration++;
		}
	}
	
	//Theoretically, we can just run this in the Action performer, too. I might try that.
	static String ConsoleParse(String ConsoleInput) {
		String ParseNext = ConsoleInput;
		
		for (int i = 0; i < ParseNext.length(); i++) {
				
			if (ParseNext.substring(i, i+1).equals(" ")) {
				
				ConsoleInput = ParseNext.substring(i+1);
				ParseNext = ParseNext.substring(0,i);
				
			}
			else {
				ConsoleInput = null;
			}
			
		}
		
		if (ParseNext.equals("")) ParseNext = null;
		
		Main.ConsoleInput = ConsoleInput;
		return ParseNext;
	}
	
	static String ConsoleParse(String ConsoleInput, boolean toLowerCase) {
		String ParseNext;
		
		if (toLowerCase) {
			ParseNext = ConsoleInput.toLowerCase();
		}
		else {
			ParseNext = ConsoleInput;
		}
		
		for (int i = 0; i < ParseNext.length(); i++) {
				
			if (ParseNext.substring(i, i+1).equals(" ")) {
				
				ConsoleInput = ParseNext.substring(i+1);
				ParseNext = ParseNext.substring(0,i);
				
			}
			else {
				ConsoleInput = null;
			}
			
		}
		
		Main.ConsoleInput = ConsoleInput;
		return ParseNext;
	}
	
	static void ConsoleAction(String ConsoleCmd) {
		
		try {
			switch (ConsoleCmd) {
			case "?":
			case "help": System.out.println("Input any variety of characters to create a permutation list"); ListCommands(); ConsoleInputNull(); break;
			case "permute": Permute(ConsoleParse(Main.ConsoleInput)); ConsoleInputNull(); break;
			case "print": System.out.println(ConsoleParse(Main.ConsoleInput)); ConsoleInputNull(); break;
			case "easteregg": System.out.println("You've found easter!"); break;
			case "spellcheck": SpellCheckToggle(); break;
			case "hashclear": if (list != null) {list.clear();} else {System.out.println("The list is not declared");} break;
			case "factorial": System.out.println(factorial(Integer.parseInt(ConsoleParse(Main.ConsoleInput)))); ConsoleInputNull(); break;
			case "settings": 
			
			case "": System.out.println("Oh dear :c (The string is null. This can happen if the ConsoleParse is called more than once during an iteration of ConsoleRun, or if the input string is a space)"); ConsoleInputNull(); break;	//This case will (should) never be reached
			case "stop": Main.running = false; System.out.println("Program terminated."); break;
			default: ConsoleInputNull(); System.out.println("Command not found, type \"help\" or \"?\" for a list of commands");
			}
		} catch (Exception e) {
			System.out.println("Bad command! (Did you remember all of the syntax?)");
			ConsoleInputNull();
		}
	}
	
	static void ListCommands() {
		System.out.println("List of commands:"
				+ "\npermute <string>: Returns a list of all possible permutations of the input string (limit in size to 16 chars)"
				+ "\nprint {<string>|'<string>'}: Returns input"//right now single quotes aren't supported
				+ "\nspellcheck <true/false>: Disables spell-check on the \"permute\" command"
				+ "\nstop: Stops the program"
				+ "\nhashclear: Clears the hashset");
	}
	
	static void SpellCheckToggle() {
		String temp = ConsoleParse(ConsoleInput);
		
		if (temp.equalsIgnoreCase("true")) {
			Main.doSpellCheck = true;
			System.out.println("Spell check enabled");
		}
		else if (temp.equalsIgnoreCase("false")) {
			Main.doSpellCheck = false;
			System.out.println("Spell check disabled");
		}
		else {
			System.out.println("Must be \"true\" or \"false\"");
		}
	}
	
	
	
	
	
	//This permutation method is only really good for if you want a way to determine the permutation at the Nth position.
	//Consider it a function, done iteratively as opposed to what we mention just below, recursively.
	//The only other good thing about it is that it does not user recursion
	
	//I should probably make a thingy to write this stuff to a file, and also a thingy where you can input an integer
	//between 0 and n! where n is the input length and that would give you the permutation at that spot
	
	//note to self: add a thingy to write this stuff to a file. pls. include as console command maybe.
	static void Permute(String Input) {
		
		list = new HashSet<String>();	//the hashset stores all of the permutations, and new ones are checked against it so they don't repeat
		culled = 0;
		
		String output;
		
		int inputLength = Input.length(); //length of the input
		int inputFactorial = factorial(inputLength);
		System.out.println("Listing permutations of: " + Input);
		
		char[] ArrayChar = new char[inputLength];	
		char[] AdaptiveArrayChar = new char[inputLength];
		char[][] PermuteList;
		int AdaptiveArrayCharPos = 0;
		
		//--------------------------------------------------------------------------------
		for (int i = 0; i < inputLength ; i++) {
			ArrayChar[i] = Input.charAt(i);
		}	
		
		AdaptiveArrayChar = ArrayChar.clone();	//ArrayChar is used later to reset AAC. AAC is changed throughout the
												//execution of the program to exclude letters from selection.
		
		PermuteList = new char[inputFactorial][inputLength];
		//----------------------------------------------------------------------------------
	
		for (int i = 0; i < inputFactorial; i++) {
			
			
			AdaptiveArrayChar = ArrayChar.clone();
			int AALeftShift = 0;
			
			for (int j = 0; j < inputLength; j++) {
				
				AdaptiveArrayCharPos = (((i % factorial(inputLength-j))/factorial((inputLength-1)-j)));
					
				//DEBUG System.out.println("i:" + i + " j:" + j + " AACP:" + AdaptiveArrayCharPos + " AACPC:" + AdaptiveArrayChar[AdaptiveArrayCharPos]);
				PermuteList[i][j] = AdaptiveArrayChar[AdaptiveArrayCharPos];
					
				//left shift (Gets rid of the letter just used from the adaptive character array)
				try {
						
					for (AALeftShift = AdaptiveArrayCharPos; AALeftShift < inputLength; AALeftShift++) {
						//DEBUG System.out.println("AAC: " + AALeftShift + " " + AdaptiveArrayChar[AALeftShift] + " " + AdaptiveArrayChar[AALeftShift+1]);
							
						AdaptiveArrayChar[AALeftShift] = AdaptiveArrayChar[AALeftShift+1];
							
							
					}
						
				}	catch (Exception e) {
					AdaptiveArrayChar[AALeftShift] = '?';
						
				}
				
			}
		}
		
		//Here, we list the permutations
		
		System.out.println(inputFactorial);
		
		for (int i=0; i < inputFactorial; i++) {
			
			output = new String(PermuteList[i]);
			if (list.add(output)) {	//Checks to see if this word is a repeat and runs spell check
				
				if (SpellCheck(output) && Main.doSpellCheck) {
					System.out.println(output);
				}	else if (!Main.doSpellCheck) {
					System.out.println(output);
				}
			}
			else {
				culled++;
				repeats++;
			}
			
		}
		System.out.println("Words culled: " + culled + " of " + inputFactorial
				+ "\nWords printed: " + (inputFactorial-culled) + " (" + round((float)(inputFactorial - culled)/(float)inputFactorial) + "%)"
				+ "\nRepeats: " + repeats);
		
	}
	
	static boolean SpellCheck(String input) {
		if (Main.doSpellCheck	//We can likely remove the doSpellCheck condition
				&& input.contains("bbb")
				|| input.contains("ccc")
				/*|| input.contains("ccc")
				|| input.contains("ccc")
				|| input.contains("ccc")
				|| input.contains("ccc")
				|| input.contains("ccc")
				|| input.contains("ccc")
				|| input.contains("ccc")
				|| input.contains("ccc")
				|| input.contains("ccc")*/
				
				|| input.contains("ryr")
				|| input.contains("nyr")
				|| input.contains("rrn")
				|| input.contains("rryn")
				|| input.contains("rnye")
				|| input.contains("ynr")
				|| input.contains("nrr")
				|| input.contains("yrr")
				|| input.contains("rnr")
				|| input.contains("ryno")
				|| input.contains("yorr")
				|| input.contains("renr")
				|| input.contains("yron")
				|| input.contains("rern")
				|| input.contains("rory")
				|| input.contains("rnyo")
				|| input.contains("rrye")
				|| input.contains("norr")
				|| input.contains("nre")
				|| input.contains("nror")
				|| input.contains("yero")
				|| input.contains("nerr")
				|| input.contains("rery")
				|| input.contains("ryo")
				|| input.contains("eyr")
				|| input.contains("rorn")
				|| input.contains("nye")
				|| input.contains("oerr")
				|| input.contains("noer")
				|| input.contains("noyr")
				|| input.contains("onry")
				
				|| check(input, 2, "start")
				|| check(input, 3, "start")
				|| check(input, 2, "end")
				|| check(input, 3, "end")
				) {
			culled++;
			return false;
		}
		else {
			return true;
		
		}
	}
	
	static boolean check(String word, int amt, String toCheck) {
		String temp;
		
		if ((word.length() >= amt) && toCheck.equals("start")) {
			
			temp = word.substring(0, amt);
			
			
			//checks first 2 letters
			if (amt == 2 &&
					temp.equals("yr")
					|| temp.equals("nr")
					|| temp.equals("rr")
					|| temp.equals("rn")
					|| temp.equals("yn")) {
				
				
				return true;
			} else if (amt == 2) {
				return false;
			}
			//checks first 3 letters
			if (amt == 3 &&
					temp.equals("ryn")
					|| temp.equals("nyo")
					|| temp.equals("orr")
					|| temp.equals("ory")
					|| temp.equals("oyr")) {
				
				return true;
			} else if (amt == 3) {
				return false;
			}
			
			return false;
			
		}
		else if ((word.length() >= amt) && toCheck.equals("end")) {
			
			temp = word.substring(word.length()-amt, word.length());
			
			//checks last two letters
			if (amt == 2 && 
					temp.equals("nr")
					|| temp.equals("")) {
			
			return true;
			} else if (amt == 2) {
				return false;
			}
			
			//checks last 3 letters (later)
			if (amt == 3 &&
					temp.equals("oyr")
					|| temp.equals("nro")
					|| temp.equals("yrn")) {
				
				return true;
			} else if (amt == 3) {
				return false;
			}
			
			
			
		}
		else {
			return false;
		}
		
		return false;
	}
	
	static int factorial(int input) {
		int finalAnswer = input;
		
		
		
		for (input = (input-1); input > 0; input--) {
			finalAnswer *= input;
		}
		
		if (finalAnswer == 0) finalAnswer++;
		
		return finalAnswer;
	}
	
	static void ConsoleInputNull() {
		Main.ConsoleInput = null;
	}

	static void Debug(String message) {
		
		if (debug) System.out.println(message);
		
	}
	
	static int round(float t) {
		
		t = (t*100);
		return (int)t;
	}
}
