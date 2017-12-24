import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FastArrayDequeTest extends CollectionTest<Integer> {

    private static final int N = 1000;
    private static final int BOUND = 10000;

    private Collection<Integer> data;
    private ArrayDeque<Integer> expected;
    private FastArrayDeque<Integer> actual;

    private final Random rand = new Random(0);

    @BeforeEach
    public void runBeforeEach() {
        data = new ArrayList<>();
        for (int i = 0; i < N; ++i)
            data.add(rand.nextInt(BOUND));
        expected = new ArrayDeque<Integer>();
        actual = new FastArrayDeque<Integer>(2);
    }

    @Test
    public void testAddAndClearAtRandom() {
        Iterator<Integer> dataIter = data.iterator();

        for (int i = 0; i < N; i++) {
            if (rand.nextInt(100) > 5) {
                Integer r = dataIter.next();
                if (rand.nextBoolean()) {
                    expected.addFirst(r);
                    actual.addFirst(r);
                } else {
                    expected.addLast(r);
                    actual.addLast(r);
                }

                assertEquals(expected.getFirst(), actual.getFirst());
                assertEquals(expected.getLast(), actual.getLast());
            } else {
                expected.clear();
                actual.clear();
            }

            assertIterableEquals(expected, actual);
            assertEquals(expected.size(), actual.size());
            assertEquals(expected.isEmpty(), actual.isEmpty());
        }
    }

    @Test
    public void testAddAndRemoveAtRandom() {
        expected.addAll(data);
        actual.addAll(data);

        Iterator<Integer> dataIter = data.iterator();

        for (int i = 0; i < N; i++) {
            if (rand.nextBoolean()) {
                Integer r = dataIter.next();
                if (rand.nextBoolean()) {
                    expected.addFirst(r);
                    actual.addFirst(r);
                } else {
                    expected.addLast(r);
                    actual.addLast(r);
                }
            } else {
                if (rand.nextBoolean()) {
                    assertEquals(expected.removeFirst(), actual.removeFirst());
                } else {
                    assertEquals(expected.removeLast(), actual.removeLast());
                }
            }

            assertIterableEquals(expected, actual);
            assertEquals(expected.size(), actual.size());
            assertEquals(expected.isEmpty(), actual.isEmpty());
            assertEquals(expected.getFirst(), actual.getFirst());
            assertEquals(expected.getLast(), actual.getLast());
        }
    }

    @Test
    public void testAddFirst() {
        for (int r : data) {
            expected.addFirst(r);
            actual.addFirst(r);

            assertIterableEquals(expected, actual);
            assertEquals(expected.size(), actual.size());
            assertEquals(expected.isEmpty(), actual.isEmpty());
            assertEquals(expected.getFirst(), actual.getFirst());
            assertEquals(expected.getLast(), actual.getLast());
        }
    }

    @Test
    public void testAddLast() {
        for (int r : data) {
            expected.addLast(r);
            actual.addLast(r);

            assertIterableEquals(expected, actual);
            assertEquals(expected.size(), actual.size());
            assertEquals(expected.isEmpty(), actual.isEmpty());
            assertEquals(expected.getFirst(), actual.getFirst());
            assertEquals(expected.getLast(), actual.getLast());
        }
    }

    @Test
    public void testCopyConstructor() {
        expected = new ArrayDeque<>(data);
        actual = new FastArrayDeque<>(data);

        assertIterableEquals(expected, actual);
        assertEquals(expected.size(), actual.size());
        assertEquals(expected.isEmpty(), actual.isEmpty());
        assertEquals(expected.getFirst(), actual.getFirst());
        assertEquals(expected.getLast(), actual.getLast());
    }

    @Test
    public void testGetFirst() {
        assertThrows(NoSuchElementException.class, actual::getFirst);
    }

    @Test
    public void testGetLast() {
        assertThrows(NoSuchElementException.class, actual::getLast);
    }

    @Test
    public void testRemoveFirst() {
        assertThrows(NoSuchElementException.class, actual::removeFirst);

        expected.addAll(data);
        actual.addAll(data);

        while (!expected.isEmpty()) {
            assertEquals(expected.getFirst(), actual.getFirst());
            assertEquals(expected.getLast(), actual.getLast());

            assertEquals(expected.removeFirst(), actual.removeFirst());

            assertIterableEquals(expected, actual);
            assertEquals(expected.size(), actual.size());
            assertEquals(expected.isEmpty(), actual.isEmpty());
        }

        assertThrows(NoSuchElementException.class, actual::removeFirst);
    }

    @Test
    public void testRemoveLast() {
        assertThrows(NoSuchElementException.class, actual::removeLast);

        expected.addAll(data);
        actual.addAll(data);

        while (!expected.isEmpty()) {
            assertEquals(expected.getFirst(), actual.getFirst());
            assertEquals(expected.getLast(), actual.getLast());

            assertEquals(expected.removeLast(), actual.removeLast());

            assertIterableEquals(expected, actual);
            assertEquals(expected.size(), actual.size());
            assertEquals(expected.isEmpty(), actual.isEmpty());
        }

        assertThrows(NoSuchElementException.class, actual::removeLast);
    }

    @Override
    public String toString() {
        return String.format("%s\n%s", expected, actual);
    }

    @Override
    protected Collection<Integer> getData() {
        return data;
    }

    @Override
    protected Collection<Integer> getExpected() {
        return expected;
    }

    @Override
    protected Collection<Integer> getActual() {
        return actual;
    }
}
