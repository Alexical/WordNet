
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;

import edu.princeton.cs.algs4.Digraph;

public class BFSIterator {

    private final Digraph G;
    private final Queue<Integer> q = new ArrayDeque<>();
    private final Map<Integer, Integer> distTo = new HashMap<>();

    // constructor takes a digraph and source vertices
    public BFSIterator(Digraph G, int[] sources) {
        this.G = G;
        for (int v : sources) {
            q.add(v);
            distTo.put(v, 0);
        }
    }

    // has the vertex been visited?
    public boolean isVisited(int v) {
        return distTo.containsKey(v);
    }

    // the distance to the vertex from any source
    public int distTo(int v) {
        return distTo.get(v);
    }

    // terminate the iterator
    public void terminate() {
        q.clear();
    }

    public boolean hasNext() {
        return !q.isEmpty();
    }

    public int next() {
        if (!hasNext())
            throw new NoSuchElementException();
        int next = q.remove();
        int dist = distTo.get(next) + 1;
        Iterable<Integer> adj;
        try {
            adj = G.adj(next);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(e);
        }
        for (int v : adj) {
            if (!distTo.containsKey(v)) {
                q.add(v);
                distTo.put(v, dist);
            }
        }
        return next;
    }

}
