import org.apache.commons.math3.util.Combinations;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
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
        // We want k word combinations from validWords, so n will be the length of validWords
        int n = validWords.size();
        Iterator<int[]> indexCombinationsIterator = new Combinations(n, k).iterator();
        while (indexCombinationsIterator.hasNext()) {
            int[] indexCombo = indexCombinationsIterator.next();

            // Now translate index combination into a word combination
            // Streams can only be operated on once, so creating a streamSupplier will allow reuse
            Supplier<Stream<String>> wordComboSupplier = () -> Arrays.stream(indexCombo).mapToObj(index -> validWords.get(index));

            // Test if current combination can fit into a square.

            // FLAWED METHOD - I left it here if you would like to see
//
//            // Assume combination fits (success=true) until disproven.
//            Boolean success = true;
//            Map wordGrid = new HashMap();
//
//            // Loop through the indices of each word (e.g. i=0 is first letter of each word)
//            for (int i=0; i<k; i++) {
//                int index = i;
//                String indexLetters = wordComboSupplier.get().map(word -> word.charAt(index)).map(c -> c.toString()).collect(Collectors.joining());
//
//                // Use these letters to output all possible word permutations into permutationsSet
//                generatePermutations(indexLetters);
//                permutationsSet = getPermutationsSet();
//
//                // See if any of these permutations match a word in the word combo
//                Boolean matchFound = false;
//                for (String testWord : permutationsSet) {
//                    if (wordComboSupplier.get().anyMatch(word -> word.equals(testWord))) {
//                        matchFound = true;
//                        wordGrid.put(index, testWord);
//                        break;
//                    }
//                }
//
//                // Clear permutationsSet for future use
//                permutationsSet.clear();
//
//                // If a match was found we proceed to the next index in the for-loop
//                // If a match was not found the word square has been disproven for this word combo:
//                if (!matchFound) {
//                    success = false;
//                    break;
//                }
//            }
//
//            // If the word square for this word combo was disproven we should move onto the next combo
//            // If it wasn't disproven then this combo forms a successful word square, so no more iteration needed
//            if (success) {
//                wordSquare = wordGrid;
//                break;
//            }

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
