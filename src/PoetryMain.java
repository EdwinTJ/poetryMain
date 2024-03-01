import java.io.FileNotFoundException;

public class PoetryMain {
    public static void main(String[] args) {
        try {
//            String files[] = {"Lester.txt", "green.txt", "Nose.txt", "Zebra.txt"};
//            String files[] = {"small.txt"};
            String files[] = {"Lester.txt"};
//            String startWords[] = {"lester", "sam", "nose", "are"};
            String startWords[] = {"lester"};
            int poemLength[] = {20};
            boolean printTable[] = {true};
            for (int i = 0; i < files.length; i++) {
                System.out.println("Generating poem using generateVerse:" + files[i]);
                WritePoetry poem = new WritePoetry(files[i]);
                String verse = poem.generateVerse(startWords[i], poemLength[i]);
                if (printTable[i]) {
                    System.out.println(verse);
                }
                // Generate poem using pickNextWord method
                System.out.println("Generating poem using pickNextWord:");
                generatePoemWithPickNextWord(poem, startWords[i], poemLength[i]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void generatePoemWithPickNextWord(WritePoetry writePoetry, String startWord, int poemLength) {
        StringBuilder poem = new StringBuilder(startWord);
        String currentWord = startWord;
        for (int i = 1; i < poemLength; i++) {
            String nextWord = writePoetry.pickNextWord(currentWord);
            if (nextWord.isEmpty()) {
                break;
            }
            poem.append(" ").append(nextWord);
            currentWord = nextWord;
        }
        System.out.println(poem.toString() + '\n');
    }
}
