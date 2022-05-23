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
            // Copy and filter validWords to only contain words starting with the letters in currentWord
            List<String> testValidWords = new ArrayList<String>();
            testValidWords.addAll(validWords);
            testValidWords = testValidWords.stream().filter(word -> {
                return ((currentWord.indexOf(word.charAt(0)) >= 0) ? true : false);
            }).collect(Collectors.toList());

            // Start generating test squares with currentWord
            generateTestSquares(currentWord, testValidWords);
            if (!wordSquare.isEmpty()) { break; }
        }
    }

    private void generateTestSquares(String currentWord, List<String> testValidWords) {
        List<String> testWordSquare = new ArrayList<String>();
        generateTestSquares(currentWord, testWordSquare, testValidWords);
    }

    private void generateTestSquares(String currentWord, List<String> testWordSquare, List<String> testValidWords) {
        int squareOrder = currentWord.length();
        if (testWordSquare.size() < squareOrder) {
            for (int i = 0; i < testValidWords.size(); i++) {

                // Make copies of the word square and valid words for modification
                List<String> currentWordSquare = new ArrayList<String>();
                currentWordSquare.addAll(testWordSquare);
                List<String> currentValidWords = new ArrayList<String>();
                currentValidWords.addAll(testValidWords);

                // Put in a new row/column/insertion - currentWordSquare "steals" a word from currentValidWords
                currentWordSquare.add(currentValidWords.get(i));
                currentValidWords.remove(i);

                // Check if it meets the criteria (faster to do it now, than generating all the squares and validating)
                Boolean validInsertion = true;
                int size = currentWordSquare.size();
                int pos = size - 1; // index of position new word was inserted
                for (int j = 0; j < size; j++) {
                    // e.g. 1st words' 3rd character must == 3rd words' 1st character
                    if (currentWordSquare.get(j).charAt(pos) != currentWordSquare.get(pos).charAt(j)) {
                        validInsertion = false;
                        break;
                    }
                }

                // If criteria was met, continue building the square as it is valid
                // Also add condition to stop recursion when a solution is found
                if (validInsertion && wordSquare.isEmpty()) {
                    generateTestSquares(currentWord, currentWordSquare, currentValidWords);
                }
            }
        } else {
            // If we get to this stage a valid square has been created
            wordSquare = testWordSquare;
        }
    }

    public List getWordSquare() {
        return wordSquare;
    }
}
