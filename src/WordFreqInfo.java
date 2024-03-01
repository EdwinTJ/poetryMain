import java.util.ArrayList;

public class WordFreqInfo {
    public String word;
    public int occurCt;
    ArrayList<Freq> followList;

    public WordFreqInfo(String word, int count) {
        this.word = word;
        this.occurCt = count;
        this.followList = new ArrayList<>();
    }

    public void updateFollows(String follow) {
//        System.out.println("Updating follows for " + word + " with " + follow);
        for (Freq f : followList) {
            // If the follow word is already in the list, increment the count
//            System.out.println("Comparing " + f.follow + " with " + follow);
            if (follow.compareTo(f.follow) == 0) {
//                System.out.println("Incrementing count for " + f.follow);
                f.followCt++;
                return;
            }
        }
        followList.add(new Freq(follow, 1));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Word :" + word + ":");
        sb.append(" (" + occurCt + ") : ");
        for (Freq f : followList)
            sb.append(f.toString());
        return sb.toString();
    }
    public static class Freq {
        String follow;
        int followCt;

        public Freq(String follow, int ct) {
            this.follow = follow;
            this.followCt = ct;
        }

        public String toString(){
            return follow + "[" + followCt + "]";
        }
        public boolean equals(Freq f2) {
            return this.follow.equals(f2.follow);
        }
    }
}
