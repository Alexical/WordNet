import java.util.Iterator;

import edu.princeton.cs.algs4.Digraph;

public class Query {

    private BFS bfsV, bfsW;
    private int length, ancestor;
    private Iterable<Integer> prevIterV, prevIterW;

    public Query(Digraph G) {
        bfsV = new BFS(G);
        bfsW = new BFS(G);
    }

    public Query compute(Iterable<Integer> iterV, Iterable<Integer> iterW) {
        if (!equals(iterV, prevIterV) || !equals(iterW, prevIterW)) {
            bfsV.seed(iterV);
            bfsW.seed(iterW);
            prevIterV = iterV;
            prevIterW = iterW;
            ancestor = -1;
            length = Integer.MAX_VALUE;

            while (bfsV.hasNext() && bfsV.maxDist() < length) {
                for (Integer v : bfsV.next()) {
                    if (bfsW.isVisited(v)) {
                        int dist = bfsV.dist(v) + bfsW.dist(v);
                        if (dist < length) {
                            length = dist;
                            ancestor = v;
                        }
                    }
                }
                if (bfsW.hasNext()) {
                    BFS temp = bfsV;
                    bfsV = bfsW;
                    bfsW = temp;
                }
            }

            if (length == Integer.MAX_VALUE)
                length = -1;
        }
        return this;
    }

    public int getLength() { return length; }

    public int getAncestor() { return ancestor; }

    private boolean equals(Iterable<Integer> a, Iterable<Integer> b) {
        if (a == null || b == null)
            return false;
        if (a == b)
            return true;
        Iterator<Integer> iterA = a.iterator();
        Iterator<Integer> iterB = b.iterator();
        while (iterA.hasNext())
            if (!iterB.hasNext() || iterB.next() != iterA.next())
                return false;
        return !iterB.hasNext();
    }

}
