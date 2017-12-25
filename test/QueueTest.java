import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

abstract class QueueTest<E> extends CollectionTest<E> {

    private int n;
    private Random rand;
    private List<E> data;
    private Queue<E> expected;
    private Queue<E> actual;

    @BeforeEach
    final void initializeQueue() {
        n = getN();
        rand = getRand();
        data = getData();
        expected = getExpected();
        actual = getActual();
    }

    @Test @DisplayName("queues are equal")
    final void queuesAreEqual() {
        assertIterableEquals(expected, actual);
        assertEquals(expected.size(), actual.size());
        assertEquals(expected.isEmpty(), actual.isEmpty());
    }

    @Nested @DisplayName("when new queue")
    final class WhenNewQueue {

        @AfterEach
        void check() { queuesAreEqual(); }

        @Test @DisplayName("add null throws")
        void addNullThrows() {
            for (int i = 0; i < 10; ++i) {
                assertThrows(NullPointerException.class,
                             () -> expected.add(null));
                assertThrows(NullPointerException.class,
                             () -> actual.add(null));
            }
        }

        @Test @DisplayName("addAll returns true")
        void addAllReturnsTrue() {
            assertTrue(expected.addAll(data));
            assertTrue(actual.addAll(data));
        }

        @Test @DisplayName("remove throws")
        void removeThrows() {
            for (int i = 0; i < 10; ++i)
                assertThrows(NoSuchElementException.class, actual::remove);
        }

        @Test @DisplayName("element throws")
        void elementThrows() {
            for (int i = 0; i < 10; ++i)
                assertThrows(NoSuchElementException.class, actual::element);
        }

        @Test @DisplayName("offer returns true")
        void offerReturnsTrue() {
            for (E e : data) {
                assertTrue(expected.offer(e));
                assertTrue(actual.offer(e));
                queuesAreEqual();
            }
        }

        @Test @DisplayName("offer null throws")
        void offerNullThrows() {
            for (int i = 0; i < 10; ++i) {
                assertThrows(NullPointerException.class,
                             () -> expected.offer(null));
                assertThrows(NullPointerException.class,
                             () -> actual.offer(null));
            }
        }

        @Test @DisplayName("poll returns null")
        void pollReturnsNull() {
            for (int i = 0; i < 10; ++i)
                assertNull(actual.poll());
        }

        @Test @DisplayName("peek returns null")
        void peekReturnsNull() {
            for (int i = 0; i < 10; ++i)
                assertNull(actual.peek());
        }

    }

    @Nested @DisplayName("after adding an element to queue")
    final class AfterAddingAnElementToQueue {

        E e;

        @BeforeEach
        void addAnElement() {
            e = data.get(0);
            expected.add(e);
            actual.add(e);
        }

        @AfterEach
        void check() { queuesAreEqual(); }

        @Test @DisplayName("add null throws")
        void addNullThrows() {
            for (int i = 0; i < 10; ++i) {
                assertThrows(NullPointerException.class,
                             () -> expected.add(null));
                assertThrows(NullPointerException.class,
                             () -> actual.add(null));
            }
        }

        @Test @DisplayName("remove returns the element")
        void removeReturnsElement() {
            assertEquals(e, expected.remove());
            assertEquals(e, actual.remove());
        }

        @Test @DisplayName("remove twice throws")
        void removeTwiceThrows() {
            expected.remove();
            actual.remove();
            for (int i = 0; i < 10; ++i) {
                assertThrows(NoSuchElementException.class, expected::remove);
                assertThrows(NoSuchElementException.class, actual::remove);
            }
        }

        @Test @DisplayName("element returns the element")
        void elementReturnsElement() {
            for (int i = 0; i < 10; ++i) {
                assertEquals(e, expected.element());
                assertEquals(e, actual.element());
            }
        }

        @Test @DisplayName("offer null throws")
        void offerNullThrows() {
            for (int i = 0; i < 10; ++i) {
                assertThrows(NullPointerException.class,
                             () -> expected.offer(null));
                assertThrows(NullPointerException.class,
                             () -> actual.offer(null));
            }
        }

        @Test @DisplayName("poll returns the element")
        void pollReturnsTheElement() {
            assertEquals(e, expected.poll());
            assertEquals(e, actual.poll());
        }

        @Test @DisplayName("poll twice returns null")
        void pollTwiceReturnsNull() {
            expected.poll();
            actual.poll();
            for (int i = 0; i < 10; ++i) {
                assertNull(expected.poll());
                assertNull(actual.poll());
            }
        }

        @Test @DisplayName("peek returns the element")
        void peekReturnsTheElement() {
            for (int i = 0; i < 10; ++i) {
                assertEquals(e, expected.peek());
                assertEquals(e, actual.peek());
            }
        }

    }

    @Nested @DisplayName("after adding each element to queue")
    final class AfterAddingEachElementToQueue {

        @BeforeEach
        void addEach() {
            data.forEach(expected::add);
            data.forEach(actual::add);
        }

        @AfterEach
        void check() { queuesAreEqual(); }

        @Test @DisplayName("add null throws")
        void addNullThrows() {
            for (int i = 0; i < 10; ++i) {
                assertThrows(NullPointerException.class,
                             () -> expected.add(null));
                assertThrows(NullPointerException.class,
                             () -> actual.add(null));
            }
        }

        @Test @DisplayName("remove returns the elements")
        void removeReturnsElements() {
            for (E e : data) {
                assertEquals(e, expected.remove());
                assertEquals(e, actual.remove());
                queuesAreEqual();
            }
        }

        @Test @DisplayName("remove extra throws")
        void removeExtraThrows() {
            while (!expected.isEmpty()) {
                expected.remove();
                actual.remove();
            }
            for (int i = 0; i < 10; ++i) {
                assertThrows(NoSuchElementException.class, expected::remove);
                assertThrows(NoSuchElementException.class, actual::remove);
            }
        }

        @Test @DisplayName("element returns the first element")
        void elementReturnsElement() {
            E e = data.get(0);
            for (int i = 0; i < 10; ++i) {
                assertEquals(e, expected.element());
                assertEquals(e, actual.element());
            }
        }

        @Test @DisplayName("offer null throws")
        void offerNullThrows() {
            for (int i = 0; i < 10; ++i) {
                assertThrows(NullPointerException.class,
                             () -> expected.offer(null));
                assertThrows(NullPointerException.class,
                             () -> actual.offer(null));
            }
        }

        @Test @DisplayName("poll returns the elements")
        void pollReturnsTheElement() {
            for (E e : data) {
                assertEquals(e, expected.poll());
                assertEquals(e, actual.poll());
                queuesAreEqual();
            }
        }

        @Test @DisplayName("poll extra returns null")
        void pollTwiceReturnsNull() {
            while (!expected.isEmpty()) {
                expected.poll();
                actual.poll();
            }
            for (int i = 0; i < 10; ++i) {
                assertNull(expected.poll());
                assertNull(actual.poll());
            }
        }

        @Test @DisplayName("peek returns the first element")
        void peekReturnsTheElement() {
            E e = data.get(0);
            for (int i = 0; i < 10; ++i) {
                assertEquals(e, expected.peek());
                assertEquals(e, actual.peek());
            }
        }

        @Test @DisplayName("test randomly")
        void testRandomly() {
            Iterator<E> iter = data.iterator();
            for (int i = 0; i < n; ++i) {
                if (rand.nextBoolean()) {
                    E e = iter.next();
                    if (rand.nextBoolean()) {
                        assertTrue(expected.add(e));
                        assertTrue(actual.add(e));
                    } else {
                        expected.offer(e);
                        actual.offer(e);
                    }
                } else {
                    if (rand.nextBoolean()) {
                        assertEquals(expected.remove(), actual.remove());
                    } else {
                        assertEquals(expected.poll(), actual.poll());
                    }
                }
                assertEquals(expected.element(), actual.element());
                assertEquals(expected.peek(), actual.peek());
                queuesAreEqual();
            }
        }

    }

    @Override
    abstract Queue<E> getExpected();

    @Override
    abstract Queue<E> getActual();

}
