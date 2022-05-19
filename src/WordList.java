import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class WordList {

    private String filename;
    private ArrayList<String> wordList = new ArrayList<String>();
    private int k;
    String characters;

    public WordList(String filenameArgument) {
        filename = filenameArgument;
        populate();
    }

    private void populate() {
        File file = new File(filename);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                wordList.add(line);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Invalid file path");;
        }
    }

    public ArrayList getWordList() {
        return wordList;
    }
}
