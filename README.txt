Instructions for use:
- Ensure you have Java 8+ installed
- Navigate to "out/artifacts/WordSquare_jar"
- To run the application run the following command (arguments are illustrated by being surrounded by square brackets):

java -jar WordSquare.jar [k] [string]

For the application to work correctly, the correct arguments must be given. 2 arguments must be specified, no more or less.
[k] represents the "order" of word square, or alternatively the length of each side of the square
[string] must be a string of length k^2 (e.g. for an order 4 square, 16 characters must be supplied) with which a word square may be calculated. The string should be supplied in lower case to avoid any issues with the word square.

As documented through comments in the main class, the application will first load the accepted list of viable words ("out/artifacts/WordSquare_jar/enable1.txt"). It will then form all possible permutations of k-length words from the characters supplied. These will then be verified against the viable words to narrow down all valid words than can be generated from the [string]. Finally, the application will use each word from the valid words as the first entry and attempt to build a word square.

E.g. if the following command is run
java -jar WordSquare.jar 4 eeeeddoonnnsssrv
4-letter combinations of the string will be formed and validated against the valid words list for genuine 4-letter words that can be generated from the string. From this list of validated words, the application will attempt to build a word square with each word as the first entry, until one is found.

For reference, the logic used to verify if a word square is valid is the transpose property of word squares; the 1st letter of the 2nd word must equal the 2nd letter of the 1st word, and so on. Therefore, if an nth word is being added to a k word square (n must obviously < k) then the nth letter of each preceeding word will be checked against every letter of the new word from 1 to n-1.
For example, if a 3rd word is being added to a k=4 word square, the 3rd letter of word 1 and 2 (already inserted and confirmed into the square) must match the 1st and 2nd letter of the new word respectively.
A successful solution is found when the kth word is entered and each letter up to k-1 matches the kth (or final) letter of all preceeding words.