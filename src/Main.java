/*
a. Samson Suen
b. Jan 10 2024
c. Assignment 1 (Hangman)
d. Ms Nahar, websites (research as cited in Doc)
e. This is my Hangman project. Enjoy :)
*/
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import java.io.*;
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String[] hangManWordList = new String[0];
        String word = "";
        String difficulty = "";
        int lives = 7;
        String dashes = "";
        String wrong = "";
        StringBuilder wrongGuess = new StringBuilder(wrong); //for wrong guesses
        int hint = 1;
        System.out.println("Hello, welcome to hangman! You have " + lives + " lives. A random word will be generated for you to guess, with each dash representing a character. You can guess characters \nin the word or the word. If you guess the wrong character/word, you lose a life. Good luck! \nNote: You have one hint per game. For a hint, press '3'");
        word = selectDifficultyAndGetWord(hangManWordList, word, difficulty) ;
        System.out.println(word); //for testing
        dashes = printDashes(dashes, word);
        StringBuilder dash = new StringBuilder(dashes);
        System.out.println("Here is the hanging area:");
        printHangMan(lives, dash, wrongGuess); //hanging area display
        System.out.println("Guess a character:");
        System.out.println(dash);
        while (lives > 0) {
            Scanner scan = new Scanner(System.in);
            String sGuess = scan.nextLine();
            String sGuessUp = sGuess.toUpperCase();
            if (sGuess.isEmpty()) { //if no input
                System.out.println("Please input something:");
            } else if (sGuess.equals("3")) { //for hint
                if(hint == 1) {
                    String charForHint = ""; // This variable should be assigned a specific character for the hint
                    System.out.println("You have requested a hint! A letter will be given to you for free. ");
                    for (int i = 0; i < dash.length(); i++) {
                        if (dash.charAt(i) == '-') {
                            for (int x = 0; x < word.length(); x++) {
                                if (word.charAt(x) == word.charAt(i)) {
                                    dash.replace(x, x + 1, String.valueOf(word.charAt(i)));
                                }
                            } hint--;
                            break;
                        }
                    }
                    if (dash.indexOf("-") != -1) { // Game continues
                        System.out.println("Your word now looks like this: \n" + dash + "\nEnter your next guess:");
                    } else { // Win
                        System.out.println("Since you used your hint on the last character, you win the game with " + lives + " lives left. The word was " + word + ". GG!");
                        System.exit(0);
                    }
                } else {
                    System.out.println("You already used your hint. Enter a guess: \n" + dash);
                }
            } else if (sGuess.length() > 1) { //is not one character
                if (sGuessUp.contains(word)) { //if user types the correct word
                    System.out.println("Good job! You guessed the word " + word + ". You win!");
                    System.exit(0);
                } else if (!checkAlphabet(sGuessUp)) { //input is not all character
                    System.out.println("Enter characters only: \n" + dash);
                } else { //valid input
                    lives--;
                    System.out.println("Wrong word. You have " + lives + " lives left.");
                    System.out.println("Guess another character or word: \n" + dash + "\n");
                }
            } else { //input length = 1
                char guess = sGuess.charAt(0);
                char guessUp = Character.toUpperCase(guess);
                int iGuess = (char) guessUp;
                if (iGuess > 64 && iGuess < 91) {  //is a character
                    if ((dash.indexOf(String.valueOf(guessUp)) != -1) || (wrongGuess.indexOf(String.valueOf(guessUp)) != -1)) { //repeat character
                        System.out.println("You already guessed this character. Please guess a different one:");
                        System.out.println(dash);
                    } else if (!word.contains(String.valueOf(guessUp))) { //wrong guess
                        if (wrongGuess.isEmpty()) {
                            wrongGuess.append(guessUp);
                        } else {
                            wrongGuess.append("," + guessUp);
                        } lives--;
                        printHangMan(lives, dash, wrongGuess);
                    } else { //correct guess
                        for (int i = 0; i < word.length(); i++) {
                            if (word.charAt(i) == guessUp) {
                                dash.replace(i, i + 1, String.valueOf(guessUp));
                            }
                        } if (dash.indexOf("-") != -1) { //game continues
                            System.out.println("Good job! Your word now looks like this: \n" + dash + "\nEnter another character:");
                        } else { //win
                            System.out.println("Good job! You guessed the word " + word + " with " + lives + " lives left. You win!");
                            System.exit(0);
                        }
                    }
                } else { //if not char
                    System.out.println("Enter characters only:");
                    System.out.println(dash);
                }
            }
        }
        System.out.println("The word was " + word + ". Better luck next time!"); //lose
        System.exit(0);
    }
    public static void printHangMan(int lives, StringBuilder sb, StringBuilder wrongGuess){
        if (lives != 7) {
            System.out.print("Character not included, you have " + lives + " lives left.");
        } if (lives == 0) {
            System.out.println(" Hangman is complete... RIP");
            System.out.println("|________");
            System.out.println("|    |");
            System.out.println("|    o");
            System.out.println("|   -|-");
            System.out.println("|   / \\ ");
            System.out.println("|");
        } if (lives == 1) {
            System.out.println(" Oh no! Only one leg left");
            System.out.println("|________");
            System.out.println("|    |") ;
            System.out.println("|    o" );
            System.out.println("|   -|-");
            System.out.println("|   / ");
            System.out.println("|");
        } if (lives == 2) {
            System.out.println(" Oh no! The upper body is complete");
            System.out.println("|________");
            System.out.println("|    |") ;
            System.out.println("|    o" );
            System.out.println("|   -|-");
            System.out.println("|");
            System.out.println("|");
        } if(lives == 3) {
            System.out.println(" Uh oh... First arm");
            System.out.println("|________");
            System.out.println("|    |") ;
            System.out.println("|    o" );
            System.out.println("|   -|");
            System.out.println("|");
            System.out.println("|");
        } if (lives == 4) {
            System.out.println(" The body has appeared...");
            System.out.println("|________");
            System.out.println("|    |");
            System.out.println("|    o");
            System.out.println("|    |");
            System.out.println("|");
            System.out.println("|");
        } if (lives == 5) {
            System.out.println(" Oh no! It's a head");
            System.out.println("|________");
            System.out.println("|    |");
            System.out.println("|    o");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
        } if (lives == 6) {
            System.out.println(" The noose has been set up...");
            System.out.println("|________");
            System.out.println("|    |");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
        } if (lives == 7) {
            System.out.println("|________");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
        } if(lives != 0 && lives != 7) {
            System.out.println("Your wrong guesses: " +wrongGuess+"\nTry again:\n"+sb);
        }
    }
    public static int countLinesInFile(String HangmanWordList) throws FileNotFoundException {
        File file = new File(HangmanWordList);
        Scanner scanner = new Scanner(file);
        int lineCount = 0;
        while (scanner.hasNextLine()) {
            lineCount++;
            scanner.nextLine();
        } scanner.close();
        return lineCount;
    }
    public static String[] readString(String HangmanWordList) throws FileNotFoundException {
        File file = new File(HangmanWordList);
        Scanner scanner = new Scanner(file);
        int numberOfLinesInFile = countLinesInFile(HangmanWordList);
        String[] array = new String[numberOfLinesInFile];
        int index = 0;
        while (scanner.hasNextLine()) {
            array[index++]=scanner.nextLine();
        } scanner.close();
        return array;
    }
    public static String getRandomWord(String[] hangManWordList){
        Random random = new Random();
        int i = random.nextInt(hangManWordList.length);
        return hangManWordList[i];
    }
    public static boolean checkAlphabet(String sGuessUp){
        char[] chars = sGuessUp.toCharArray();
        for (char c:chars) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }return true;
    }
    public static String printDashes(String dashes, String word) {
        for (int i = 0; i < word.length(); i++) {
            dashes += ("-");
        } return dashes;
    }
    public static String selectDifficultyAndGetWord(String[] hangManWordList, String word, String difficulty) throws FileNotFoundException {
        while (true) {
            System.out.println("Choose your difficulty (1 = easy, 2 = medium, 3 = hard):");
            Scanner scanner = new Scanner(System.in);
            difficulty = scanner.nextLine();
            if (difficulty.equals("1") || difficulty.equals("2") || difficulty.equals("3")) {
                break;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        } if (difficulty.equals("1")) {
            hangManWordList = readString("HangmanEasy.txt");
            word = getRandomWord(hangManWordList);
            System.out.println("You have chosen easy difficulty.");
        } else if (difficulty.equals("2")) {
            hangManWordList = readString("HangmanMedium.txt");
            word = getRandomWord(hangManWordList);
            System.out.println("You have chosen medium difficulty.");
        } else if (difficulty.equals("3")) {
            hangManWordList = readString("HangmanWordList.txt");
            word = getRandomWord(hangManWordList);
            System.out.println("You have chosen hard difficulty.");
        } return word;
    }
}