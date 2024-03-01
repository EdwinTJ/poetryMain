import java.io.File;
import java.io.FileNotFoundException;
//import java.util.HashMap;
//import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class WritePoetry {
    private HashTable<String, WordFreqInfo> wordMap;

    public WritePoetry(String fileName) throws FileNotFoundException {
        wordMap = new HashTable<>();
        processFile(fileName);
        System.out.println(wordMap.toString(100));;
    }

    private void processFile(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));
        WordFreqInfo prevWordInfo = null;

        while (scanner.hasNext()) {
            String currentWord = scanner.next().toLowerCase().trim();
//            System.out.println("the current word is: *" + currentWord +"*" );
            if (prevWordInfo != null) {
                prevWordInfo.updateFollows(currentWord);
            }
            WordFreqInfo currnetInfo = wordMap.find(currentWord);
            if(currnetInfo == null){
                 currnetInfo = new WordFreqInfo(currentWord, 1);
                wordMap.insert(currentWord, currnetInfo);
            }else{
                currnetInfo.occurCt++;
            }
            prevWordInfo = currnetInfo;
        }
    }

    public String generateVerse(String startWord, int verseLength) {
        StringBuilder verse = new StringBuilder(startWord);
        Random random = new Random();
        String currentWord = startWord.toLowerCase();

        for (int i = 1; i < verseLength; i++) {
            WordFreqInfo wordInfo = wordMap.find(currentWord);
            if (wordInfo == null || wordInfo.followList.isEmpty()) {
                break;
            }

            int totalOccurrences = wordInfo.followList.stream().mapToInt(f -> f.followCt).sum();
            int randomIndex = random.nextInt(totalOccurrences);
            int cumulativeSum = 0;
            String nextWord = "";

            for (WordFreqInfo.Freq freq : wordInfo.followList) {
                cumulativeSum += freq.followCt;
                if (randomIndex < cumulativeSum) {
                    nextWord = freq.follow;
                    break;
                }
            }

            verse.append(" ").append(nextWord);
            currentWord = nextWord;
        }

        return verse.toString();
    }

    public String pickNextWord(String currentWord) {
        WordFreqInfo wordInfo = wordMap.find(currentWord);
        if (wordInfo == null || wordInfo.followList.isEmpty()) {
            return "";
        }

        Random random = new Random();
        int totalOccurrences = wordInfo.followList.stream().mapToInt(f -> f.followCt).sum();
        int randomIndex = random.nextInt(totalOccurrences);
        int cumulativeSum = 0;
        String nextWord = "";

        for (WordFreqInfo.Freq freq : wordInfo.followList) {
            cumulativeSum += freq.followCt;
            if (randomIndex < cumulativeSum) {
                nextWord = freq.follow;
                break;
            }
        }

        System.out.println("pickNextWord Word: " + currentWord + " (" + wordInfo.occurCt + ")");

        StringBuilder sb = new StringBuilder();
        sb.append("Random number generated: ").append(randomIndex).append("\n");
        sb.append("Word selected: ").append(nextWord);
        System.out.println(sb.toString());

        return nextWord;
    }

}