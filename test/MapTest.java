import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;

import org.junit.jupiter.api.Test;

abstract class MapTest<K, V> {

    @Test
    public final void testContainsKey() {
        data1().forEach(entry -> {
            expected().put(entry.getKey(), entry.getValue());
            actual().put(entry.getKey(), entry.getValue());
        });

        Iterator<Map.Entry<K, V>> data1 = data1().iterator();
        Iterator<Map.Entry<K, V>> data2 = data2().iterator();

        for (int i = 0; i < N(); ++i) {
            K key = rand().nextBoolean() ? data1.next().getKey()
                                         : data2.next().getKey();
            assertEquals(expected().containsKey(key),
                         actual().containsKey(key));
        }
    }

    @Test
    public final void testContainsValue() {
        data1().forEach(entry -> {
            expected().put(entry.getKey(), entry.getValue());
            actual().put(entry.getKey(), entry.getValue());
        });

        Iterator<Map.Entry<K, V>> data1 = data1().iterator();
        Iterator<Map.Entry<K, V>> data2 = data2().iterator();

        for (int i = 0; i < N(); ++i) {
            V value = rand().nextBoolean() ? data1.next().getValue()
                                           : data2.next().getValue();
            assertEquals(expected().containsValue(value),
                         actual().containsValue(value));
        }
    }

    @Test
    public final void testClearDefault() {
        expected().clear();
        actual().clear();

        assertEquals(expected(), actual());
        assertEquals(expected().size(), actual().size());
        assertEquals(expected().isEmpty(), actual().isEmpty());
    }

    @Test
    public final void testDefaultMap() {
        assertEquals(expected(), actual());

        assertFalse(actual().entrySet().iterator().hasNext());
        assertFalse(actual().keySet().iterator().hasNext());
        assertFalse(actual().values().iterator().hasNext());

        assertThrows(NoSuchElementException.class,
                     actual().entrySet().iterator()::next);
        assertThrows(NoSuchElementException.class,
                     actual().keySet().iterator()::next);
        assertThrows(NoSuchElementException.class,
                     actual().values().iterator()::next);
    }

    @Test
    public final void testEqualMaps() {
        data1().forEach(entry -> {
            expected().put(entry.getKey(), entry.getValue());
            actual().put(entry.getKey(), entry.getValue());
        });

        assertEquals(expected().hashCode(), actual().hashCode());
        assertEquals(expected(), actual());
        assertEquals(expected().size(), actual().size());
        assertEquals(expected().entrySet(), actual().entrySet());
        assertEquals(expected().keySet(), actual().keySet());

        assertEquals(expected().values().size(), actual().values().size());
        assertTrue(expected().values().containsAll(actual().values()));
        assertTrue(actual().values().containsAll(expected().values()));
    }

    @Test
    public final void testGet() {
        data1().forEach(entry -> {
            expected().put(entry.getKey(), entry.getValue());
            actual().put(entry.getKey(), entry.getValue());
        });

        Iterator<Map.Entry<K, V>> data1 = data1().iterator();
        Iterator<Map.Entry<K, V>> data2 = data2().iterator();

        for (int i = 0; i < N(); ++i) {
            K key = rand().nextBoolean() ? data1.next().getKey()
                                         : data2.next().getKey();
            assertEquals(expected().get(key), actual().get(key));
        }
    }

    @Test
    public final void testIsEmptyDefault() {
        assertEquals(expected(), actual());

        assertTrue(actual().isEmpty());

        assertEquals(expected(), actual());
    }

    @Test
    public final void testPut() {
        Map.Entry<K, V> entry = data1().get(0);
        expected().put(entry.getKey(), entry.getValue());
        actual().put(entry.getKey(), entry.getValue());

        assertEquals(expected(), actual());
        assertEquals(expected().size(), actual().size());
        assertEquals(expected().isEmpty(), actual().isEmpty());
    }

    @Test
    public final void testPutAll() {
        data1().forEach(entry -> expected().put(entry.getKey(),
                                                entry.getValue()));
        actual().putAll(expected());

        assertEquals(expected(), actual());
        assertEquals(expected().size(), actual().size());
        assertEquals(expected().isEmpty(), actual().isEmpty());
    }

    @Test
    public final void testPutAndClearAtRandom() {
        Iterator<Map.Entry<K, V>> data = data1().iterator();

        for (int i = 0; i < N(); i++) {
            if (rand().nextInt(100) > 5) {
                Map.Entry<K, V> r = data.next();
                K key = r.getKey();
                V value = r.getValue();
                expected().put(key, value);
                actual().put(key, value);

                assertEquals(expected().get(key), actual().get(key));
            } else {
                expected().clear();
                actual().clear();
            }

            assertEquals(expected(), actual());
            assertEquals(expected().size(), actual().size());
            assertEquals(expected().isEmpty(), actual().isEmpty());
        }
    }

    @Test
    public final void testRemove() {
        data1().forEach(entry -> {
            expected().put(entry.getKey(), entry.getValue());
            actual().put(entry.getKey(), entry.getValue());
        });

        Collections.shuffle(data1());
        Iterator<Map.Entry<K, V>> data = data1().iterator();

        while (data.hasNext()) {
            K key = data.next().getKey();
            assertEquals(expected().remove(key), actual().remove(key));

            assertEquals(expected(), actual());
            assertEquals(expected().size(), actual().size());
            assertEquals(expected().isEmpty(), actual().isEmpty());
        }

        K key = data1().get(0).getKey();
        assertEquals(expected().remove(key), actual().remove(key));
        assertEquals(expected(), actual());
        assertEquals(expected().size(), actual().size());
        assertEquals(expected().isEmpty(), actual().isEmpty());
    }

    @Test
    public final void testSizeDefault() {
        assertEquals(expected(), actual());

        assertEquals(0, actual().size());

        assertEquals(expected(), actual());
    }

    @Test
    public final void testUnqualMaps() {
        data1().forEach(entry -> {
            expected().put(entry.getKey(), entry.getValue());
            actual().put(entry.getKey(), entry.getValue());
        });

        Map.Entry<K, V> r = data2().iterator().next();
        actual().put(r.getKey(), r.getValue());

        assertNotEquals(expected().hashCode(), actual().hashCode());
        assertNotEquals(expected(), actual());
        assertNotEquals(expected().size(), actual().size());
        assertNotEquals(expected().entrySet(), actual().entrySet());
        assertNotEquals(expected().keySet(), actual().keySet());

        assertNotEquals(expected().values().size(), actual().values().size());
        assertFalse(expected().values().containsAll(actual().values()));
    }

    protected abstract int N();

    protected abstract Random rand();

    protected abstract List<Map.Entry<K, V>> data1();

    protected abstract List<Map.Entry<K, V>> data2();

    protected abstract Map<K, V> expected();

    protected abstract Map<K, V> actual();

}
