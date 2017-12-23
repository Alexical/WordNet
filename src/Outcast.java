
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    private final WordNet wordnet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int maxDist = -1;
        String maxNoun = null;
        for (String nounA : nouns) {
            int dist = 0;
            for (String nounB : nouns)
                dist += wordnet.distance(nounA, nounB);
            if (dist > maxDist) {
                maxDist = dist;
                maxNoun = nounA;
            }
        }
        return maxNoun;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        while (true) {
            try {
            StdOut.println(outcast.outcast(StdIn.readLine().split(" ")));
            } catch (IllegalArgumentException e) {
            }
        }
    }

}
