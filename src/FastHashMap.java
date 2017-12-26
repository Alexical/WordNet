import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

public class FastHashMap<K, V> extends AbstractMap<K, V> {

    private static final int INIT_CAPACITY = 4;

    private int n;
    private int m;
    private K[] keys;
    private V[] vals;

    public FastHashMap() {
        this(INIT_CAPACITY);
    }

    public FastHashMap(Map<? extends K, ? extends V> map) {
    }

    public FastHashMap(int capacity) {
        n = 0;
        m = capacity;
        keys = (K[]) new Object[m];
        vals = (V[]) new Object[m];
    }

    @Override
    public int size() {
        return n;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

}
