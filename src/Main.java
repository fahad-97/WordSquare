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

        long startTime = System.currentTimeMillis();

        // Generate list of viable k-length words to compare generated words against
        System.out.println("Loading word list...");
        WordList wordList = new WordList("enable1.txt", k);
        ArrayList viableWords = wordList.getWordList();
        System.out.println("Done.");

        long wordListLoadTime = System.currentTimeMillis();
        long wordListLoadTimeElapsed = wordListLoadTime - startTime;
        System.out.println("Time elapsed: " + wordListLoadTimeElapsed + "ms");
        System.out.println();

        System.out.println("Generating word permutations...");
        WordPermutations wordPermutations = new WordPermutations(k, characters);
        Set<String> generatedWords = wordPermutations.getPermutationsSet();
        System.out.println("Done.");

        long generationTime = System.currentTimeMillis();
        long generationTimeElapsed = generationTime - wordListLoadTime;
        System.out.println("Time elapsed: " + generationTimeElapsed + "ms");
        System.out.println();

        // Filtering generated words to leave a List of valid words from the input string
        System.out.println("Verifying permutations are valid...");
        List<String> validWords = generatedWords.stream().filter(word -> viableWords.contains(word)).collect(Collectors.toList());
        System.out.println("Done.");

        long verificationTime = System.currentTimeMillis();
        long verificationTimeElapsed = verificationTime - generationTime;
        System.out.println("Time elapsed: " + verificationTimeElapsed + "ms");
        System.out.println();

        // Calculate a viable Word Square from the valid words
        System.out.println("Calculating viable word square...");
        WordSquare wordSquare = new WordSquare(validWords);
        List<String> solution = wordSquare.getWordSquare();

        if (solution.isEmpty()) {
            System.out.println("No word square solution was found for the given inputs.");
        }
        else {
            System.out.println("Word square generated!");

            long calculationTime = System.currentTimeMillis();
            long calculationTimeElapsed = calculationTime - verificationTime;
            System.out.println("Time elapsed: " + calculationTimeElapsed + "ms");
            System.out.println();

            for (String word : solution) {
                System.out.println(Arrays.stream(word.split("")).collect(Collectors.joining(" ")));
            }
        }

        long endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;
        System.out.println();
        System.out.println("Total time elapsed: " + timeElapsed + "ms");
    }
}