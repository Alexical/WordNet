
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

    private final Digraph G;
    private final Map<Query, Result> cache;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null)
            throw new IllegalArgumentException();

        this.G = new Digraph(G);
        this.cache = new Cache(G.V());
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        return length(Collections.singletonList(v),
                      Collections.singletonList(w));
    }

    // a common ancestor of v and w that participates in a shortest ancestral
    // path; -1 if no such path
    public int ancestor(int v, int w) {
        return ancestor(Collections.singletonList(v),
                        Collections.singletonList(w));
    }

    // length of shortest ancestral path between any vertex in v and any vertex
    // in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException();
        return cache.computeIfAbsent(new Query(v, w), q -> new Result(G, q))
                    .getLength();
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no
    // such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException();
        return cache.computeIfAbsent(new Query(v, w), q -> new Result(G, q))
                    .getAncestor();
    }

    private static class Cache extends LinkedHashMap<Query, Result> {

        private static final long serialVersionUID = 1L;
        private final int maxSize;

        private Cache(int maxSize) {
            super(Integer.parseInt("16"), Float.parseFloat("0.618"), true);
            this.maxSize = maxSize;
        }

        @Override
        public boolean removeEldestEntry(Map.Entry<Query, Result> eldest) {
            return size() > maxSize;
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        int V;
        SAP sap;
        {
            Digraph G = new Digraph(in);
            V = G.V();
            sap = new SAP(G);
        }
        int nQueries = 1000000;
        int[] arrayV = new int[nQueries];
        int[] arrayW = new int[nQueries];
        Random rand = new Random();
        for (int i = 0; i < nQueries; i++) {
            arrayV[i] = rand.nextInt(V);
            arrayW[i] = rand.nextInt(V);
        }
        int[] lengths = new int[nQueries];
        int[] ancestors = new int[nQueries];
        StdOut.print("Ready...");
        StdIn.readLine();
        for (int i = 0; i < nQueries; i++) {
            int v = arrayV[i];
            int w = arrayW[i];
            lengths[i] = sap.length(v, w);
            ancestors[i] = sap.ancestor(v, w);
        }
    }

}
