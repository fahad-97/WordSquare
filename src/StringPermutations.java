import java.util.HashSet;
import java.util.Set;

public abstract class StringPermutations {
    private Set<String> permutationsSet = new HashSet<String>();

    // Initial call does not contain a prefix, so will proceed to call the method again containing a blank prefix:
    public void generatePermutations(String str) {
        generatePermutations("", str);
    }

    // We start with a blank prefix and full length string
    // The prefix will "steal" each of the letters from the string (hence the for-loop) and call itself again
    // We then have multiple calls with a one letter prefix and max-1 length string, and the recursion continues
    // When we have a full length prefix and blank string (n==0) the recursion is broken and string is returned
    public void generatePermutations(String prefix, String str) {
        int n = str.length();
        if (n != 0) {
            for (int i = 0; i < n; i++) {
                generatePermutations(
                        prefix + str.charAt(i), // prefix "steals" the character of the iteration
                        str.substring(0, i) + str.substring(i+1, n)); // str has the "stolen" char removed
            }
        }
        else {
            permutationsSet.add(prefix); // n==0 therefore the permutation is complete and can be added to the list
        }
    }

    public Set getPermutationsSet() {
        return permutationsSet;
    }
}
