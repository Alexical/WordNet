
import java.util.Arrays;
import java.util.Iterator;

public class Query {

    private final int[][] query = new int[2][];
    private final int hashCode;

    public Query(Iterable<Integer> v, Iterable<Integer> w) {
        query[0] = toArray(v);
        query[1] = toArray(w);
        normalizeQuery();
        hashCode = Arrays.deepHashCode(query);
    }

    public int[] getV() {
        return Arrays.copyOf(query[0], query[0].length);
    }

    public int[] getW() {
        return Arrays.copyOf(query[1], query[1].length);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this)
            return true;

        if (other == null)
            return false;

        if (other.getClass() != this.getClass())
            return false;

        Query q = (Query) other;
        return q.hashCode() == hashCode() && Arrays.deepEquals(q.query, query);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(query);
    }

    private void normalizeQuery() {
        Arrays.sort(query[0]);
        Arrays.sort(query[1]);

        if (compare(query[1], query[0]) < 0) {
            int[] temp = query[0];
            query[0] = query[1];
            query[1] = temp;
        }
    }

    private static int compare(int[] a, int[] b) {
        for (int i = 0; i < Math.min(a.length, b.length); i++) {
            int comp = Integer.compare(a[i], b[i]);
            if (comp != 0)
                return comp;
        }
        return Integer.compare(a.length, b.length);
    }

    private static int[] toArray(Iterable<Integer> list) {
        int[] a = new int[sizeOf(list)];
        int i = 0;
        for (int n : list)
            a[i++] = n;
        return a;
    }

    private static int sizeOf(Iterable<Integer> list) {
        int size = 0;
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            it.next();
            size++;
        }
        return size;
    }
}
