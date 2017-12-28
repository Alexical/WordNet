import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    private final WordNet wordnet;

    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    public String outcast(String[] nouns) {
        int maxDist = -1;
        String maxNoun = null;
        for (String nounA : nouns) {
            int dist = 0;
            for (String nounB : nouns)
                if (!nounB.equals(nounA))
                    dist += wordnet.distance(nounA, nounB);
            if (dist > maxDist) {
                maxDist = dist;
                maxNoun = nounA;
            }
        }
        return maxNoun;
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.printf("%s: %s\n", args[t], outcast.outcast(nouns));
        }
    }

}
