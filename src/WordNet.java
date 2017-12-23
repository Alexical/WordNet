import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class WordNet {

    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException();
    }

    public Iterable<String> nouns() {
        return null;
    }

    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException();

        return false;
    }

    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException();

        return 0;
    }

    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException();

        return null;
    }

    public static void main(String[] args) {
        WordNet wordNet = new WordNet(args[0], args[1]);

        while (!StdIn.isEmpty()) {
            String nounA = StdIn.readString();
            String nounB = StdIn.readString();

            if (!wordNet.isNoun(nounA)) {
                StdOut.printf("%s is not a noun!\n", nounA);
                continue;
            }

            if (!wordNet.isNoun(nounB)) {
                StdOut.printf("%s is not a noun!\n", nounB);
                continue;
            }

            int distance = wordNet.distance(nounA, nounB);
            String ancestor = wordNet.sap(nounA, nounB);
            String format = "distance = %d, ancestor = %s\n";
            StdOut.printf(format, distance, ancestor);
        }
    }
}
