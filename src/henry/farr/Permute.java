package henry.farr;

import java.util.HashSet;
import java.util.Set;

public class Permute {
	
	 int culled;
	 int repeats;
	 Set<String> list;
	
	 char[] ArrayChar;
	 char[] AdaptiveArrayChar;
	 int AdaptiveArrayCharPos;
	 char[][] PermuteList;
	 
	 int inputLength;
	 int inputFactorial;
	 
	 String output;
	 
	public Permute(String stringToPermute) {
		
		toPermute(stringToPermute);
		
	}
	
	//This permutation method is only really good for if you want a way to determine the permutation at the Nth position.
		//Consider it a function, done iteratively as opposed to what we mention just below, recursively.
		//The only other good thing about it is that it does not user recursion
		
		//I should probably make a thingy to write this stuff to a file, and also a thingy where you can input an integer
		//between 0 and n! where n is the input length and that would give you the permutation at that spot
		
		//note to self: add a thingy to write this stuff to a file. pls. include as console command maybe.
	 void toPermute(String Input) {
		
		list = new HashSet<String>();	//the hashset stores all of the permutations, and new ones are checked against it so they don't repeat
		
		inputLength = Input.length(); 				//length of the input
		inputFactorial = factorial(inputLength);	//Amount of permutations (repeats included)
		
		ArrayChar = new char[inputLength];	
		//AdaptiveArrayChar = new char[inputLength];	//This array is declared later
		AdaptiveArrayCharPos = 0;
		
		//--------------------------------------------------------------------------------
		for (int i = 0; i < inputLength ; i++) {	//converts the input into an array of chars
			ArrayChar[i] = Input.charAt(i);
		}	
		
		AdaptiveArrayChar = ArrayChar.clone();	//ArrayChar is used later to reset AAC. AAC is changed throughout the
												//execution of the program to exclude letters from selection.
		
		PermuteList = new char[inputFactorial][inputLength];	//Creates the list where all of the permutations will go
		//----------------------------------------------------------------------------------
		//This fills out the PermuteList array
		for (int i = 0; i < inputFactorial; i++) {
			
			//At the start of every permutation (every i value), the AdaptiveArrayChar is reset, and the
			//AALeftShift is reset.
			AdaptiveArrayChar = ArrayChar.clone();
			int AALeftShift = 0;
			
			for (int j = 0; j < inputLength; j++) {
				//The algorithm below finds a value 0-inputLength-j, and grabs its corresponding char from the adaptive array.
				//The char is then set into the PermuteList and removed from the AdaptiveArray by shifting all values to
				//the right of it one spot left
				//eg [a][b][c][d][e], AdaptiveArrayCharPos = 2 (value 'c')
				//adaptive array is changed to [a][b][d][e][?]
				//For the rest of this permutation, the value of AdaptiveArrayCharPos, mathematically speaking, cannot
				//ever reference any spot with a '?' just by how the math works (which I might detail later).
				
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
					AdaptiveArrayChar[AALeftShift] = '?';	//When the array runs out of bounds during the left shift.
						
				}
				
			}
		}
		
		//Here, we list the permutations
		
		/*System.out.println(inputFactorial);
		
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
				+ "\nRepeats: " + repeats);*/
		
	}
	
	 boolean SpellCheck(String input) {
		if (	   input.contains("bbb")
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
	
	 boolean check(String word, int amt, String toCheck) {
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
	

	 int factorial(int input) {
		int finalAnswer = input;
		
		
		
		for (input = (input-1); input > 0; input--) {
			finalAnswer *= input;
		}
		
		if (finalAnswer == 0) finalAnswer++;
		
		return finalAnswer;
	}
	
	 int round(float t) {
		
		t = (t*100);
		return (int)t;
	}

	char[][] getList() {
		
		return PermuteList;
	}
	
	void listPermute(boolean doSpellCheck, boolean doCull) {
		System.out.println("Spellcheck? " + doSpellCheck
				+ "\nCulling? " + doCull);
		
		list.clear();
		culled = 0;
		repeats = 0;
		
		System.out.println(inputFactorial);
		
		for (int i=0; i < inputFactorial; i++) {
			
			output = new String(PermuteList[i]);
			if (list.add(output) || !doCull) {	//Checks to see if this word is a repeat, or if we aren't culling
												//!doCull can be on either side here, it shouldn't matter. This is because the !doCull is only there to indicate to the program that if we aren't culling, and the hashset check returns false, then we still need to output.
				if (doSpellCheck && SpellCheck(output)) {	//If the output clears the spellcheck, and spellcheck is enabled. //Note bien, the doSpellCheck must be on the left, if it's false then the spellcheck won't increment the "culled" value
					System.out.println(output);
				}	else if (!doSpellCheck) {				//If spellcheck is NOT enabled
					System.out.println(output);
				}
			}
			else {
				culled++;
				repeats++;
			}
			
		}
		if (doSpellCheck || doCull) {
			System.out.println("Words culled: " + culled + " of " + inputFactorial
					+ "\nWords printed: " + (inputFactorial-culled) + " (" + round((float)(inputFactorial - culled)/(float)inputFactorial) + "%)"
					+ "\nRepeats: " + repeats);
		}
	}
	
	void listPermute(int i) {
		
		output = new String(PermuteList[i]);
		System.out.println(output);
		
	}
}
