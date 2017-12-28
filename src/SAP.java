import java.util.Collections;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

    private Query query;

    public SAP(Digraph G) {
        if (G != null)
            this.query = new Query(new Digraph(G));
        else throw new IllegalArgumentException();
    }

    public int length(int v, int w) {
        return length(Collections.singletonList(v),
                      Collections.singletonList(w));
    }

    public int ancestor(int v, int w) {
        return ancestor(Collections.singletonList(v),
                        Collections.singletonList(w));
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (!isNull(v) && !isNull(w))
            return query.compute(v, w).getLength();
        else throw new IllegalArgumentException();
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (!isNull(v) && !isNull(w))
            return query.compute(v, w).getAncestor();
        else throw new IllegalArgumentException();
    }

    private boolean isNull(Object x) { return x == null; }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

}
