import org.apache.commons.math3.util.Combinations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class WordPermutations extends StringPermutations {
    private int k;
    private String characters;
    private Set<String> permutationsSet = new HashSet<String>();

    public WordPermutations(int kInput, String charactersInput) {
        k = kInput;
        characters = charactersInput;
        generateWords();
    }

    private void generateWords() {
        // Generate combinations of indices (nCk like in statistical maths e.g. 16C4)
        int n = k * k;
        Iterator<int[]> indexCombinationsIterator = new Combinations(n, k).iterator();
        while (indexCombinationsIterator.hasNext()) {
            int[] indexCombo = indexCombinationsIterator.next();

            // Now translate index combination into a word
            StringBuilder sb = new StringBuilder();
            for (int index : indexCombo) {
                sb.append(characters.charAt(index));
            }
            String str = sb.toString();

            // Generate all permutations of this string. Inherited from StringPermutations
            generatePermutations(str);
            permutationsSet = getPermutationsSet();
        }
    }
}
