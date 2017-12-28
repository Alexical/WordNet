import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

import edu.princeton.cs.algs4.Digraph;

public class BFS {

    private final Digraph digraph;
    private final Queue<Integer> all;
    private Queue<Integer> prev, curr;
    private int[] dist;
    private int maxDist;

    public BFS(Digraph G) {
        this.digraph = G;
        this.all = new ArrayDeque<>();
        this.prev = new ArrayDeque<>();
        this.curr = new ArrayDeque<>();
        this.dist = new int[G.V()];
        this.maxDist = -1;
        Arrays.fill(dist, Integer.MIN_VALUE);
    }

    public void seed(Iterable<Integer> iterV) {
        clear();
        for (Integer v : iterV) {
            validate(v);
            all.add(v);
            curr.add(v);
            dist[v] = 0;
        }
    }

    public boolean hasNext() { return !prev.isEmpty() || !curr.isEmpty(); }

    public Iterable<Integer> next() {
        ++maxDist;
        while (!prev.isEmpty()) {
            for (Integer v : digraph.adj(prev.remove())) {
                if (!isVisited(v)) {
                    all.add(v);
                    curr.add(v);
                    dist[v] = maxDist;
                }
            }
        }
        Queue<Integer> temp = curr;
        curr = prev;
        prev = temp;
        return prev;
    }

    public boolean isVisited(int v) { return dist[v] != Integer.MIN_VALUE; }

    public int dist(int v) { return dist[v]; }

    public int maxDist() { return maxDist; }

    private void clear() {
        while (!all.isEmpty())
            dist[all.remove()] = Integer.MIN_VALUE;
        prev.clear();
        curr.clear();
        maxDist = -1;
    }

    private void validate(int v) {
        if (v < 0 || v >= digraph.V())
            throw new IllegalArgumentException();
    }
}
