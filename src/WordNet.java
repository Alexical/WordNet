
import java.util.HashMap;
import java.util.Map;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class WordNet {

    private final Map<Integer, String> synsets;
    private final Map<String, MinimalBag<Integer>> nouns;
    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException();

        this.synsets = new HashMap<>();
        this.nouns = new HashMap<>();
        loadSynsets(synsets);
        sap = new SAP(loadHypernyms(hypernyms));
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nouns.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException();

        return nouns.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException();

        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException();

        return sap.length(nouns.get(nounA), nouns.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancesor of
    // nounA and nounB in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException();

        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException();

        return synsets.get(sap.ancestor(nouns.get(nounA), nouns.get(nounB)));
    }

    // load synsets and nouns
    private void loadSynsets(String path) {
        In in = new In(path);
        while (in.hasNextLine()) {
            String[] fields = in.readLine().split(",", 3);
            int id = Integer.parseInt(fields[0]);
            synsets.put(id, fields[1]);
            for (String noun : fields[1].split(" ")) {
                nouns.computeIfAbsent(noun, k -> new MinimalBag<>()).add(id);
            }
        }
    }

    // load hypernyms into digraph
    private Digraph loadHypernyms(String path) {
        Digraph G = new Digraph(synsets.size());
        In in = new In(path);
        while (in.hasNextLine()) {
            String[] fields = in.readLine().split(",");
            int from = Integer.parseInt(fields[0]);
            for (int i = 1; i < fields.length; i++) {
                int to = Integer.parseInt(fields[i]);
                G.addEdge(from, to);
            }
        }
        if (!isRootedDAG(G))
            throw new IllegalArgumentException();
        return G;
    }

    // is the digraph a rooted DAG?
    private boolean isRootedDAG(Digraph G) {
        if (new DirectedCycle(G).hasCycle())
            return false;
        int nRoots = 0;
        for (int v = 0; nRoots < 2 && v < G.V(); v++)
            if (G.outdegree(v) == 0)
                nRoots++;
        return nRoots == 1;
    }

    // do unit testing of this class
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

            long startTime = System.nanoTime();
            int distance = wordNet.distance(nounA, nounB);
            String ancestor = wordNet.sap(nounA, nounB);
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000;

            String format = "distance = %d, ancestor = %s (%d us)\n";
            StdOut.printf(format, distance, ancestor, duration);
        }
    }
}
