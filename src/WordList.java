import java.util.ArrayList;
import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class WordList {

    private String filename;
    private ArrayList<String> wordList = new ArrayList<String>();

    public WordList(String filenameArgument, int k) {
        filename = filenameArgument;
        try {
            populate();
            filterLength(k);
        } catch (FileNotFoundException ex) {
            System.out.println("Invalid file path");
        }
    }

    private void populate() throws FileNotFoundException {
        File file = new File(filename);
        Scanner sc = null;
        sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            wordList.add(line);
        }
    }

    private void filterLength(int k) {
        List<String> filteredWordList = wordList.stream().filter(word -> word.length() == k).collect(Collectors.toList());
        ArrayList<String> filteredArrayList = new ArrayList<String>(filteredWordList);
        wordList = filteredArrayList;
    }

    public ArrayList getWordList() {
        return wordList;
    }
}
