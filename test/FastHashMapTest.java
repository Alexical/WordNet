import static org.junit.jupiter.api.Assertions.*;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FastHashMapTest extends MapTest<Integer, Integer> {

    private static final boolean DUMMY = false;
    private static final int N = 1000;
    private static final int BOUND = 10000;

    private List<Map.Entry<Integer, Integer>> data1, data2;
    private HashMap<Integer, Integer> expected;
    private Map<Integer, Integer> actual;

    private final Random rand = new Random(0);

    @BeforeEach
    public void runBeforeEach() {
        data1 = new ArrayList<>();
        for (int i = 0; i < N; ++i)
            data1.add(entry(rand.nextInt(BOUND), rand.nextInt(BOUND)));

        data2 = new ArrayList<>();
        for (int i = 0; i < N; ++i)
            data2.add(entry(rand.nextInt(BOUND) - BOUND,
                            rand.nextInt(BOUND) - BOUND));

        expected = new HashMap<>();
        actual = DUMMY ? new TreeMap<>() : new FastHashMap<>();
    }

    @Test
    public void testCopyConstructor() {
        data1.forEach(entry -> expected.put(entry.getKey(), entry.getValue()));
        actual = new FastHashMap<>(expected);
        actual = DUMMY ? new TreeMap<>(expected) : new FastHashMap<>(expected);

        assertEquals(expected, actual);
        assertEquals(expected.size(), actual.size());
        assertEquals(expected.isEmpty(), actual.isEmpty());
    }

    @Override
    protected int N() {
        return N;
    }

    @Override
    protected Random rand() {
        return rand;
    }

    @Override
    protected List<Map.Entry<Integer, Integer>> data1() {
        return data1;
    }

    @Override
    protected List<Map.Entry<Integer, Integer>> data2() {
        return data2;
    }

    @Override
    protected Map<Integer, Integer> expected() {
        return expected;
    }

    @Override
    protected Map<Integer, Integer> actual() {
        return actual;
    }

    private <K, V> Map.Entry<K, V> entry(K key, V value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

}
