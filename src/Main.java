import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    String filepath;

    public static void main(String[] args) {
        int k;
        String characters;

        // Generate list of valid words to compare generated words against
        System.out.println("Loading word list...");
        WordList wordList = new WordList("enable1.txt");
        ArrayList viableWords = wordList.getWordList();
        System.out.println("Done.");

        // Check inputs to confirm valid, then process to generate word permutations
        try {
            k = Integer.parseInt(args[0]);
            characters = args[1];
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Not enough arguments given");
            System.out.println("See documentation for more information");
            return;
        } catch (NumberFormatException ex) {
            System.out.println("Incorrect argument: first argument must be an integer");
            System.out.println("See documentation for more information");
            return;
        }
        System.out.println("Inputs validated.");
        System.out.println("Generating word permutations...");
        WordPermutations wordPermutations = new WordPermutations(k, characters);
        Set<String> generatedWords = wordPermutations.getPermutationsSet();
        System.out.println("Done.");

        // Filtering generated words to leave a List of valid Word Square combinations
        System.out.println("Verifying permutations are valid...");
        List<String> validWords = generatedWords.stream().filter(word -> viableWords.contains(word)).collect(Collectors.toList());
        System.out.println("Done.");

        // Calculate a viable Word Square from the valid words
        System.out.println("Calculating viable word square...");
        WordSquare wordSquare = new WordSquare(validWords);
        List<String> solution = wordSquare.getWordSquare();

        if (solution.isEmpty()) {
            System.out.println("No word square solution was found for the given inputs.");
        }
        else {
            System.out.println("Word square generated!");
            for (String word : solution) {
                System.out.println(Arrays.stream(word.split("")).collect(Collectors.joining(" ")));
            }
        }
    }
}