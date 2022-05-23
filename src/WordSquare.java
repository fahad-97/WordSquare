import org.apache.commons.math3.util.Combinations;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class WordSquare {
    private List<String> validWords = new ArrayList<String>();
    private List<String> wordSquare = new ArrayList<String>();
    private int k;


    public WordSquare(List validWordsInput) {
        validWords = validWordsInput;
        k = validWords.get(0).length();
        generateSquare();
    }

    private void generateSquare() {
        for (String currentWord : validWords) {

            // Copy and filter validWords to only contain words starting with the letters in word
            List<String> currentValidWords = new ArrayList<String>();
            currentValidWords.addAll(validWords);
            currentValidWords = currentValidWords.stream().filter(word -> {
                return ((currentWord.indexOf(word.charAt(0)) >= 0) ? true : false);
            }).collect(Collectors.toList());
            
        }

        while (indexCombinationsIterator.hasNext()) {
            int[] indexCombo = indexCombinationsIterator.next();

            // Now translate index combination into a word combination
            // Streams can only be operated on once, so creating a streamSupplier will allow reuse
            Supplier<Stream<String>> wordComboSupplier = () -> Arrays.stream(indexCombo).mapToObj(index -> validWords.get(index));

            // Test if current combination can fit into a square.

            // New method: start with each word in the combination, then test if words fit into row 2, then row 3 etc
            List<String> wordCombo = wordComboSupplier.get().collect(Collectors.toList());
            generateTestSquares(wordCombo);
        }
    }

    private void generateTestSquares(List<String> wordCombo) {
        List<String> testWordSquare = new ArrayList<String>();
        generateTestSquares(testWordSquare, wordCombo);
    }

    private void generateTestSquares(List<String> testWordSquare, List<String> wordCombo) {
        if (!wordCombo.isEmpty()) {
            for (int i=0; i<wordCombo.size(); i++) {

                // Make copies of the word square and word combo for modification
                List<String> currentWordSquare = new ArrayList<String>();
                currentWordSquare.addAll(testWordSquare);
                List<String> currentWordCombo = new ArrayList<String>();
                currentWordCombo.addAll(wordCombo);
                
                // Put in a new row/column/insertion
                currentWordSquare.add(currentWordCombo.get(i));
                currentWordCombo.remove(i);
                
                // Check if it meets the criteria (faster to do it now, than generating all the squares and validating)
                Boolean validInsertion = true;
                int size = currentWordSquare.size();
                int pos = size - 1; // positionInserted
                for (int j=0; j<size; j++) {
                    // e.g. 1st words' 3rd character must == 3rd words' 1st character
                    if (currentWordSquare.get(j).charAt(pos) != currentWordSquare.get(pos).charAt(j)) {
                        validInsertion = false;
                    }
                }

                // If criteria was met, continue building the square as it is valid
                // Also add condition to stop recursion when a solution is found
                if (validInsertion && wordSquare.isEmpty()) {
                    generateTestSquares(currentWordSquare, currentWordCombo);
                }
            }
        }
        else {
            // If we get to this stage a valid square has been created
            wordSquare = testWordSquare;
        }
    }

    public List getWordSquare() {
        return wordSquare;
    }
}
