import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class WordNet {

    private Map<Integer, List<String>> synsets;
    private Map<String, List<Integer>> nouns;
    private SAP sap;

    public WordNet(String synsets, String hypernyms) {
        if (synsets != null && hypernyms != null) {
            loadSynsets(synsets);
            loadHypernyms(hypernyms);
        } else throw new IllegalArgumentException();
    }

    public Iterable<String> nouns() { return nouns.keySet(); }

    public boolean isNoun(String word) {
        if (word != null)
            return nouns.containsKey(word);
        else throw new IllegalArgumentException();
    }

    public int distance(String nounA, String nounB) {
        if (nounA != null && nounB != null)
            return sap.length(nouns.get(nounA), nouns.get(nounB));
        else throw new IllegalArgumentException();
    }

    public String sap(String nounA, String nounB) {
        if (nounA != null && nounB != null) {
            int ancestor = sap.ancestor(nouns.get(nounA), nouns.get(nounB));
            return String.join(" ", synsets.get(ancestor));
        } else throw new IllegalArgumentException();
    }

    private void loadSynsets(String file) {
        int initialCapacity = Integer.parseInt("16");
        synsets = new HashMap<>(initialCapacity, Float.parseFloat("0.5"));
        nouns = new HashMap<>(initialCapacity, Float.parseFloat("0.5"));
        In in = new In(file);
        while (in.hasNextLine()) {
            String[] fields = in.readLine().split(",", 3);
            int id = Integer.parseInt(fields[0]);
            List<String> words = Arrays.asList(fields[1].split(" "));
            synsets.put(id, words);
            for (String word : words)
                nouns.computeIfAbsent(word, k -> new ArrayList<>(2)).add(id);
        }
    }

    private void loadHypernyms(String file) {
        Digraph digraph = new Digraph(synsets.size());
        In in = new In(file);
        while (in.hasNextLine()) {
            List<String> fields = Arrays.asList(in.readLine().split(","));
            Iterator<String> iter = fields.iterator();
            int src = Integer.parseInt(iter.next());
            while (iter.hasNext())
                digraph.addEdge(src, Integer.parseInt(iter.next()));
        }
        if (isRootedDAG(digraph))
            sap = new SAP(digraph);
        else throw new IllegalArgumentException();
    }

    private boolean isRootedDAG(Digraph G) {
        if (new DirectedCycle(G).hasCycle())
            return false;
        int nRoots = 0;
        for (int v = 0; nRoots < 2 && v < G.V(); v++)
            if (G.outdegree(v) == 0)
                nRoots++;
        return nRoots == 1;
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
