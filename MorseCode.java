import java.util.Scanner;
public class MorseCode {

	public static void main(String[] args) {
		//This does work
		String s = convertToMorseCode("Mozme ist hrat");
		System.out.println(howManyCharacters("Mozme ist hrat"));
		
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
	
	public static String convertfromMorseCode(String source) {
		int words = howManyWords(source);
		if (!isEmpty(source) && words > 0) {
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
			
			//Extract words
			String[] list = getWordsFromText(source);
			
			//Cycle through words
			for (int x = 0; x < list.length; x++) {
				//Process each word in order to extract each character
				int word = 0;
				int firstSpace = 0;
				int secondSpace = 0;
				while(word < list.length) {
					int characters = howManyCharacters(list[word]);
					for (int y = 0; y < list[word].length(); y++) {
						secondSpace = list[word].indexOf(" ", firstSpace + 1);
						String temp = list[word].substring(firstSpace, secondSpace);
						
						firstSpace = secondSpace;
					}
					
					word++;
				}
				
			}
			
			return finalText;
		}
		return "The input can't be empty!";
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
		
		return s.length() - spaceCount;
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
			System.out.print(text + "\nPlease enter a number between " + min + " and " + max + ": 9");
			answer = input.nextInt();
		}while(answer > max || answer < min); //Repeat until user enters a value between min and max
		
		return answer;
	}
}
