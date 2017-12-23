
import edu.princeton.cs.algs4.Digraph;

public class Result {

    private final int ancestor, length;

    public Result(Digraph G, Query query) {
        BFSIterator iterV = new BFSIterator(G, query.getV());
        BFSIterator iterW = new BFSIterator(G, query.getW());
        int minAncestor = -1;
        int minLength = Integer.MAX_VALUE;
        while (iterV.hasNext()) {
            int next = iterV.next();
            if (iterV.distTo(next) >= minLength)
                iterV.terminate();
            if (iterW.isVisited(next)) {
                int dist = iterV.distTo(next) + iterW.distTo(next);
                if (dist < minLength) {
                    minLength = dist;
                    minAncestor = next;
                }
            }
            if (iterW.hasNext()) {
                BFSIterator temp = iterV;
                iterV = iterW;
                iterW = temp;
            }
        }
        ancestor = minAncestor;
        length = ancestor != -1 ? minLength : -1;
    }

    public int getAncestor() {
        return ancestor;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        String format = "{ancestor: %d, length: %d }";
        return String.format(format, ancestor, length);
    }

}
