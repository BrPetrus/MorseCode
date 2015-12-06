import java.util.Scanner;
public class MorseCode {

	public static void main(String[] args) {
		/*String s = convertToMorseCode("Ako sa mas");
		System.out.println(s);
		String c = convertFromMorseCode(".- -.- --- | ... .- | -- .- ...");
		System.out.println(c);*/
		
		Scanner input = new Scanner(System.in);
		
		boolean isRunning = true;
		while(isRunning) {
			//Header
			System.out.println("Morse code converter");
			System.out.println("--------------------");
			
			String userInput = "";
			
			//Menu
			switch(getChoice(1, 2, "Please enter desired conversion\n1) Convert English to Morse code\n2) Convert Morse code to English")) {
			case 1:
				//E -> M
				System.out.println("\nEnglish to Morse code");
				System.out.println("-----------------------");
				System.out.println("Separate each word with space. Punctuation will be ignored.");
				System.out.println("Please enter your sentence:");
				userInput = input.nextLine();
				
				//Process
				userInput = convertToMorseCode(userInput);
				
				//Print
				System.out.println(userInput);
				System.out.println();
				System.out.println();
				break;
				
			case 2:
				//M -> E
				System.out.println("\nMorse code to English");
				System.out.println("-----------------------");
				System.out.println("Separate each letter with space and each word with '|'. Punctuation will be ignored.");
				System.out.println("Please enter your sentence:");
				userInput = input.nextLine();
				
				//Process
				userInput = convertFromMorseCode(userInput);
				
				//Print
				System.out.println(userInput);
				System.out.println();
				System.out.println();
				
				break;
			}
		}
	}
	
	/*
	 * String convertToMorseCode(String source)
	 * Converts string of English text to morse code.
	 * !NOTE: We ignore punctuation marks
	 */
	public static String convertToMorseCode(String source) {
		if (source.length() > 0) {
			String[] tableLetters = { 
					".-", "-...", "-.-.", "-..", ".",
					"..-.", "--.", "....", "..", ".---",
					"-.-", ".-..", "--", "-.", "---",
					".--.", "--.-", ".-.", "...", "-",
					"..-", "...-", ".--", "-..-",
					"-.--", "--.."};
			String[] tableNumbers = { //From 0 to 9
					"-----", ".----", "..---", "...--", "....-", 
					".....", "-....", "--...", "---..", "----."};
			
			//final product
			String finalText = "";
			
			//Each letter separated by space and each word separated by '|'
			//First convert string to upper-case
			source = source.toUpperCase();
			
			//Now process every letter ignoring punctuation 
			for (int x = 0; x < source.length(); x++) {
				char letter = source.charAt(x);
				//check letters
				if (letter >= 'A' && letter <= 'Z') {
					finalText += (tableLetters[letter - 'A'] + ' ');
				}
				//check numbers
				else if (letter >= '0' && letter <= '9') {
					finalText += (tableNumbers[letter - '0'] + ' ');
				}
				//check spaces
				else if (letter == ' ') {finalText += "| ";};
			}
			
			return finalText;
		}
		return "The input can't be empty!";
	}
	
	/*
	 * String convertFromMorseCode(String source)
	 * Converts string of morse code to English text
	 * !NOTE: Everything is upper-case
	 * !NOTE: Invalid character will crash the function
	 */
	public static String convertFromMorseCode(String source) {
		int words = howManyWords(source);
		if (!isEmpty(source) && words > 0) { //If the string is empty -> return error message
			//Extract words
			String[] list = getWordsFromText(source);
			
			//Final string
			String convertedString = "";
			
			//Process each word in order to extract each character
			int word = 0;
			
			//Cycle words
			while(word < list.length) {
					int firstSpace = 0;
					int secondSpace = 0;
					
					//Cycle characters
					do {
						secondSpace = list[word].indexOf(" ", firstSpace + 1);
						if (secondSpace < 0) { //If there isn't a next space
							String temp = list[word].substring(firstSpace);
							temp = temp.trim();
							
							
							if (findInTable(temp) == ' ') { //findInTable will return ' ' if something didn't go well
								System.out.println("Fatal error occurred during processing String: \"" + temp + "\"!");
								System.exit(1);
							}
							else {
								convertedString += findInTable(temp);
							}
						}
						else {
							String temp = list[word].substring(firstSpace, secondSpace);
							temp = temp.trim();
							
							if (findInTable(temp) == ' ') { //findInTable will return ' ' if something didn't go well
								System.out.println("Fatal error occurred during processing String: \"" + temp + "\"!");
								System.exit(1);
							}
							else {
								convertedString += findInTable(temp);
							}
							
							firstSpace = secondSpace;
						}
					}while(secondSpace > 0); //If there is no other ' ' -> switch to next word
					
					convertedString += ' '; //Add space between the words
					word++;
				}
			return convertedString;
		}
		return "The input can't be empty!";
	}
	
	/*
	 * boolean findInTable(String findString)
	 * Returns converted value
	 * If there was an error, method returns ' '
	 */
	public static char findInTable(String findString) {
		String[] tableLetters = { 
				".-", "-...", "-.-.", "-..", ".",
				"..-.", "--.", "....", "..", ".---",
				"-.-", ".-..", "--", "-.", "---",
				".--.", "--.-", ".-.", "...", "-",
				"..-", "...-", ".--", "-..-",
				"-.--", "--.."};
		String[] tableNumbers = { //From 0 to 9
				"-----", ".----", "..---", "...--", "....-", 
				".....", "-....", "--...", "---..", "----."};
			
		for (int x = 0; x < (tableLetters.length + tableNumbers.length); x++) {
			if (x < tableLetters.length) {
				//Check in tableLetters
				if (findString.equals(tableLetters[x])) {
					return (char)('A' + x);
					
				}
			}
			else {
				//Check in tableNumbers
				if (findString.equals(tableNumbers[x - tableLetters.length])) {
					return (char)('0' + x - tableLetters.length);
				}
			}
		}
		return ' ';
	}
	
	/*
	 * int howManyCharacters(String s)
	 * Return how many characters are in String s
	 */
	public static int howManyCharacters(String s) {
		int lastSpace = 0;
		int spaceCount = 0;
		for (; s.indexOf(" ", lastSpace + 1) > 0; spaceCount++) {
			lastSpace = s.indexOf(" ", lastSpace + 1);
		}
		
		return s.length() - spaceCount; //If we remove all spaces, we will get the number of characters in string
	}
	
	/*
	 * String[] getWordsFromText(String s)
	 * Method extracts words form String s and place them in an array, which it then returns
	 * !NOTE: It only works for morse code
	 * !NOTE: If you pass a string with 0 words it will cause the method to do undefined "things"
	 */
	public static String[] getWordsFromText(String s) {
		
		int words = howManyWords(s);
		
		//Check if it's one word, so we just pass the string basically
		if (words == 1) {
			String[] listWords = new String[words];
			listWords[0] = s.trim();
			return listWords;
		}
		
		String[] listWords = new String[words];
		
		int index = 0; //Index of word we are processing 
		int lSpace = 0; //Index of last(previous) space we used
		while(index < words) {
			int nSpace = s.indexOf("|", lSpace + 1);
			
			if (nSpace < 0) { //If there are no '|', then we know it is the last word
				String temp = s.substring(lSpace); //Copy everything into a String
				temp = temp.replace("|", " "); //Replace '|' with space
				temp = temp.trim(); //Delete unwanted spaces
				listWords[index] = temp; //Assign
				return listWords;
			}
			
			String temp = s.substring(lSpace, nSpace); //Create a substring starting with previous space (where we left of in previous iteration) ending with next space
			temp = temp.replace("|", " "); //Replace '|' with space
			temp = temp.trim(); //Delete unwanted spaces
			listWords[index] = temp; //Assign
			
			index++; //We just processed next word -> increment index 
			lSpace = nSpace; //The ending space is now the "starting one" in the next iteration
		}
			
		return listWords;
	}
	
	/*
	 * int howManyWords(String text)
	 * Returns how many words the string has
	 * !NOTE: It only works for morse code
	 */
	public static int howManyWords(String text) {
		if (!isEmpty(text)) {
			int count = 1; //By initialising to 1 we don't need to check for the first word 
			for (int x = 0; x < text.length(); x++) {
				if (text.charAt(x) == '|')
					if (isEmpty(text.substring(x)) == false)
						count++;
			}
			return count;
		}
		return 0;
	}
	
	/*
	 * boolean isEmpty(String s)
	 * If there are no '.' and '-' in the string, method returns true(empty)
	 */
	public static boolean isEmpty(String s) {
		s = s.toUpperCase();
		for (int x = 0; x < s.length(); x++) {
			char letter = s.charAt(x);
			if (letter == '.' || letter == '-')
				return false;
		}
		return true;
	}
	
	/* 
	* getChoice(int min, int max, String)
	* Error-proof (nearly :D) way for printing limited options and getting the user's answer
	* from min to max is accepted input
	*/
	public static int getChoice(int min, int max, String text) {
		Scanner input = new Scanner(System.in);
		
		int answer = 0;
		do {
			System.out.print(text + "\nPlease enter a number between " + min + " and " + max + ": ");
			answer = input.nextInt();
			System.out.println();
		}while(answer > max || answer < min); //Repeat until user enters a value between min and max
		
		return answer;
	}
}
