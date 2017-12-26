import static org.junit.jupiter.api.Assertions.*;

import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

abstract class DequeTest<E> extends QueueTest<E> {

    private int n;
    private Random rand;
    private List<E> data;
    private Deque<E> expected;
    private Deque<E> actual;

    @BeforeEach
    final void initializeDeque() {
        n = getN();
        rand = getRand();
        data = getData();
        expected = getExpected();
        actual = getActual();
    }

    @Test @DisplayName("deques are equal")
    final void dequesAreEqual() { queuesAreEqual(); }

    @Nested @DisplayName("when new deque")
    final class WhenNewDeque {

        @AfterEach
        void check() { dequesAreEqual(); }

        @Test @DisplayName("addFirst null throws NullPointerException")
        void addFirstNullThrows() {
            for (int i = 0; i < 10; ++i) {
                assertThrows(NullPointerException.class,
                             () -> expected.addFirst(null));
                assertThrows(NullPointerException.class,
                             () -> actual.addFirst(null));
            }
        }

        @Test @DisplayName("addLast null throws NullPointerException")
        void addLastNullThrows() {
            for (int i = 0; i < 10; ++i) {
                assertThrows(NullPointerException.class,
                             () -> expected.addLast(null));
                assertThrows(NullPointerException.class,
                             () -> actual.addLast(null));
            }
        }

        @Test @DisplayName("addFirst each")
        void addFirstEach() {
            for (E e : data) {
                expected.addFirst(e);
                actual.addFirst(e);
                dequesAreEqual();
            }
        }

        @Test @DisplayName("addLast each")
        void addLastEach() {
            for (E e : data) {
                expected.addLast(e);
                actual.addLast(e);
                dequesAreEqual();
            }
        }

        @Test @DisplayName("test addFirst and addLast randomly")
        void testAddFirstAndAddLastRandomly() {
            Iterator<E> iter = data.iterator();
            while (iter.hasNext()) {
                E e = iter.next();
                if (rand.nextBoolean()) {
                    expected.addFirst(e);
                    actual.addFirst(e);
                } else {
                    expected.addLast(e);
                    actual.addLast(e);
                }
                dequesAreEqual();
            }
        }

        @Test @DisplayName("removeFirst throws NoSuchElementException")
        void removeFirstThrows() {
            for (int i = 0; i < 10; ++i) {
                assertThrows(NoSuchElementException.class, expected::removeFirst);
                assertThrows(NoSuchElementException.class, actual::removeFirst);
            }
        }

        @Test @DisplayName("removeLast throws NoSuchElementException")
        void removeLastThrows() {
            for (int i = 0; i < 10; ++i) {
                assertThrows(NoSuchElementException.class, expected::removeLast);
                assertThrows(NoSuchElementException.class, actual::removeLast);
            }
        }

        @Test @DisplayName("getFirst throws NoSuchElementException")
        void getFirstThrows() {
            for (int i = 0; i < 10; ++i) {
                assertThrows(NoSuchElementException.class, expected::getFirst);
                assertThrows(NoSuchElementException.class, actual::getFirst);
            }
        }

        @Test @DisplayName("getLast throws NoSuchElementException")
        void getLastThrows() {
            for (int i = 0; i < 10; ++i) {
                assertThrows(NoSuchElementException.class, expected::getLast);
                assertThrows(NoSuchElementException.class, actual::getLast);
            }
        }

        @Test @DisplayName("offerFirst null throws NullPointerException")
        void offerFirstNullThrows() {
            for (int i = 0; i < 10; ++i) {
                assertThrows(NullPointerException.class,
                             () -> expected.offerFirst(null));
                assertThrows(NullPointerException.class,
                             () -> actual.offerLast(null));
            }
        }

        @Test @DisplayName("offerLast null throws NullPointerException")
        void offerLastNullThrows() {
            for (int i = 0; i < 10; ++i) {
                assertThrows(NullPointerException.class,
                             () -> expected.offerFirst(null));
                assertThrows(NullPointerException.class,
                             () -> actual.offerLast(null));
            }
        }

        @Test @DisplayName("offerFirst each returns true")
        void offerFirstEachReturnsTrue() {
            for (E e : data) {
                assertTrue(expected.offerFirst(e));
                assertTrue(actual.offerFirst(e));
                dequesAreEqual();
            }
        }

        @Test @DisplayName("offerLast each returns true")
        void offerLastEachReturnsTrue() {
            for (E e : data) {
                assertTrue(expected.offerLast(e));
                assertTrue(actual.offerLast(e));
                dequesAreEqual();
            }
        }

        @Test @DisplayName("test offerFirst and offerLast randomly")
        void testOfferFirstAndOfferLastRandomly() {
            Iterator<E> iter = data.iterator();
            while (iter.hasNext()) {
                E e = iter.next();
                if (rand.nextBoolean()) {
                    assertTrue(expected.offerFirst(e));
                    assertTrue(actual.offerFirst(e));
                } else {
                    assertTrue(expected.offerLast(e));
                    assertTrue(actual.offerLast(e));
                }
                dequesAreEqual();
            }
        }

        @Test @DisplayName("pollFirst returns null")
        void pollFirstReturnsNull() {
            for (int i = 0; i < 10; ++i) {
                assertNull(expected.pollFirst());
                assertNull(actual.pollFirst());
            }
        }

        @Test @DisplayName("pollLast returns null")
        void pollLastReturnsNull() {
            for (int i = 0; i < 10; ++i) {
                assertNull(expected.pollLast());
                assertNull(actual.pollLast());
            }
        }

        @Test @DisplayName("peekFirst returns null")
        void peekFirstReturnsNull() {
            for (int i = 0; i < 10; ++i) {
                assertNull(expected.peekFirst());
                assertNull(actual.peekFirst());
            }
        }

        @Test @DisplayName("peekLast returns null")
        void peekLastReturnsNull() {
            for (int i = 0; i < 10; ++i) {
                assertNull(expected.peekLast());
                assertNull(actual.peekLast());
            }
        }

    }

    @Nested @DisplayName("after adding an element to deque")
    final class AfterAddingAnElementToDeque {

        E e;

        @BeforeEach
        void addAnElement() {
            e = data.get(0);
            expected.add(e);
            actual.add(e);
        }

        @AfterEach
        void check() { dequesAreEqual(); }

        @Test @DisplayName("addFirst null throws NullPointerException")
        void addFirstNullThrows() {
            for (int i = 0; i < 10; ++i) {
                assertThrows(NullPointerException.class,
                             () -> expected.addFirst(null));
                assertThrows(NullPointerException.class,
                             () -> actual.addFirst(null));
            }
        }

        @Test @DisplayName("addLast null throws NullPointerException")
        void addLastNullThrows() {
            for (int i = 0; i < 10; ++i) {
                assertThrows(NullPointerException.class,
                             () -> expected.addLast(null));
                assertThrows(NullPointerException.class,
                             () -> actual.addLast(null));
            }
        }

        @Test @DisplayName("removeFirst returns the element")
        void removeFirstReturnsElement() {
            assertEquals(e, expected.removeFirst());
            assertEquals(e, actual.removeFirst());
        }

        @Test @DisplayName("removeLast returns the element")
        void removeLastReturnsElement() {
            assertEquals(e, expected.removeLast());
            assertEquals(e, actual.removeLast());
        }

        @Test @DisplayName("removeFirst twice throws NoSuchElementException")
        void removeFirstTwiceThrows() {
            expected.removeFirst();
            actual.removeFirst();
            for (int i = 0; i < 10; ++i) {
                assertThrows(NoSuchElementException.class, expected::removeFirst);
                assertThrows(NoSuchElementException.class, actual::removeFirst);
            }
        }

        @Test @DisplayName("removeLast twice throws NoSuchElementException")
        void removeLastTwiceThrows() {
            expected.removeLast();
            actual.removeLast();
            for (int i = 0; i < 10; ++i) {
                assertThrows(NoSuchElementException.class, expected::removeLast);
                assertThrows(NoSuchElementException.class, actual::removeLast);
            }
        }

        @Test @DisplayName("getFirst returns the element")
        void getFirstReturnsElement() {
            for (int i = 0; i < 10; ++i) {
                assertEquals(e, expected.getFirst());
                assertEquals(e, actual.getFirst());
            }
        }

        @Test @DisplayName("getLast returns the element")
        void getLastReturnsElement() {
            for (int i = 0; i < 10; ++i) {
                assertEquals(e, expected.getLast());
                assertEquals(e, actual.getLast());
            }
        }

        @Test @DisplayName("offerFirst null throws NullPointerException")
        void offerFirstNullThrows() {
            for (int i = 0; i < 10; ++i) {
                assertThrows(NullPointerException.class,
                             () -> expected.offerFirst(null));
                assertThrows(NullPointerException.class,
                             () -> actual.offerLast(null));
            }
        }

        @Test @DisplayName("offerLast null throws NullPointerException")
        void offerLastNullThrows() {
            for (int i = 0; i < 10; ++i) {
                assertThrows(NullPointerException.class,
                             () -> expected.offerFirst(null));
                assertThrows(NullPointerException.class,
                             () -> actual.offerLast(null));
            }
        }

        @Test @DisplayName("pollFirst returns the element")
        void pollFirstReturnsTheElement() {
            assertEquals(e, expected.pollFirst());
            assertEquals(e, actual.pollFirst());
        }

        @Test @DisplayName("pollLast returns the element")
        void pollLastReturnsTheElement() {
            assertEquals(e, expected.pollLast());
            assertEquals(e, actual.pollLast());
        }

        @Test @DisplayName("pollFirst twice returns null")
        void pollFirstTwiceReturnsNull() {
            expected.pollFirst();
            actual.pollFirst();
            for (int i = 0; i < 10; ++i) {
                assertNull(expected.pollFirst());
                assertNull(actual.pollFirst());
            }
        }

        @Test @DisplayName("pollLast twice returns null")
        void pollLastTwiceReturnsNull() {
            expected.pollLast();
            actual.pollLast();
            for (int i = 0; i < 10; ++i) {
                assertNull(expected.pollLast());
                assertNull(actual.pollLast());
            }
        }

        @Test @DisplayName("peekFirst returns the element")
        void peekFirstReturnsTheElement() {
            for (int i = 0; i < 10; ++i) {
                assertEquals(e, expected.peekFirst());
                assertEquals(e, actual.peekFirst());
            }
        }

        @Test @DisplayName("peekLast returns the element")
        void peekLastReturnsTheElement() {
            for (int i = 0; i < 10; ++i) {
                assertEquals(e, expected.peekLast());
                assertEquals(e, actual.peekLast());
            }
        }

    }

    @Nested @DisplayName("after adding each element to the deque")
    final class AfterAddingEachElementToDeque {

        @BeforeEach
        void addEach() {
            data.forEach(expected::add);
            data.forEach(actual::add);
        }

        @AfterEach
        void check() { dequesAreEqual(); }

        @Test @DisplayName("addFirst null throws NullPointerException")
        void addFirstNullThrows() {
            for (int i = 0; i < 10; ++i) {
                assertThrows(NullPointerException.class,
                             () -> expected.addFirst(null));
                assertThrows(NullPointerException.class,
                             () -> actual.addFirst(null));
            }
        }

        @Test @DisplayName("addLast null throws NullPointerException")
        void addLastNullThrows() {
            for (int i = 0; i < 10; ++i) {
                assertThrows(NullPointerException.class,
                             () -> expected.addLast(null));
                assertThrows(NullPointerException.class,
                             () -> actual.addLast(null));
            }
        }

        @Test @DisplayName("removeFirst returns the elements")
        void removeFirstReturnsElements() {
            for (E e : data) {
                assertEquals(e, expected.removeFirst());
                assertEquals(e, actual.removeFirst());
                queuesAreEqual();
            }
        }

        @Test @DisplayName("removeLast returns the elements in reverse")
        void removeLastReturnsElementsInReverse() {
            ListIterator<E> iter = data.listIterator(data.size());
            while (iter.hasPrevious()) {
                E e = iter.previous();
                assertEquals(e, expected.removeLast());
                assertEquals(e, actual.removeLast());
                queuesAreEqual();
            }
        }

        @Test @DisplayName("removeFirst extra throws NoSuchElementException")
        void removeFirstExtraThrows() {
            while (!expected.isEmpty()) {
                expected.removeFirst();
                actual.removeFirst();
            }
            for (int i = 0; i < 10; ++i) {
                assertThrows(NoSuchElementException.class, expected::removeFirst);
                assertThrows(NoSuchElementException.class, actual::removeFirst);
            }
        }

        @Test @DisplayName("removeLast extra throws NoSuchElementException")
        void removeLastExtraThrows() {
            while (!expected.isEmpty()) {
                expected.removeLast();
                actual.removeLast();
            }
            for (int i = 0; i < 10; ++i) {
                assertThrows(NoSuchElementException.class, expected::removeLast);
                assertThrows(NoSuchElementException.class, actual::removeLast);
            }
        }

        @Test @DisplayName("getFirst returns the first element")
        void getFirstReturnsFirstElement() {
            E e = data.get(0);
            for (int i = 0; i < 10; ++i) {
                assertEquals(e, expected.getFirst());
                assertEquals(e, actual.getFirst());
            }
        }

        @Test @DisplayName("getLast returns the last element")
        void getLastReturnsFirstElement() {
            E e = data.get(data.size() - 1);
            for (int i = 0; i < 10; ++i) {
                assertEquals(e, expected.getLast());
                assertEquals(e, actual.getLast());
            }
        }

        @Test @DisplayName("offerFirst null throws NullPointerException")
        void offerFirstNullThrows() {
            for (int i = 0; i < 10; ++i) {
                assertThrows(NullPointerException.class,
                             () -> expected.offerFirst(null));
                assertThrows(NullPointerException.class,
                             () -> actual.offerLast(null));
            }
        }

        @Test @DisplayName("offerLast null throws NullPointerException")
        void offerLastNullThrows() {
            for (int i = 0; i < 10; ++i) {
                assertThrows(NullPointerException.class,
                             () -> expected.offerFirst(null));
                assertThrows(NullPointerException.class,
                             () -> actual.offerLast(null));
            }
        }

        @Test @DisplayName("pollFirst returns the elements")
        void pollFirstReturnsElements() {
            for (E e : data) {
                assertEquals(e, expected.pollFirst());
                assertEquals(e, actual.pollFirst());
                queuesAreEqual();
            }
        }

        @Test @DisplayName("pollLast returns the elements in reverse")
        void pollLastReturnsElementsInReverse() {
            ListIterator<E> iter = data.listIterator(data.size());
            while (iter.hasPrevious()){
                E e = iter.previous();
                assertEquals(e, expected.pollLast());
                assertEquals(e, actual.pollLast());
                queuesAreEqual();
            }
        }

        @Test @DisplayName("pollFirst extra returns null")
        void pollFirstExtraReturnsNull() {
            while (!expected.isEmpty()) {
                expected.pollFirst();
                actual.pollFirst();
            }
            for (int i = 0; i < 10; ++i) {
                assertNull(expected.pollFirst());
                assertNull(actual.pollFirst());
            }
        }

        @Test @DisplayName("pollLast extra returns null")
        void pollLastExtraReturnsNull() {
            while (!expected.isEmpty()) {
                expected.pollLast();
                actual.pollLast();
            }
            for (int i = 0; i < 10; ++i) {
                assertNull(expected.pollLast());
                assertNull(actual.pollLast());
            }
        }

        @Test @DisplayName("peekFirst returns the first element")
        void peekFirstReturnsFirstElement() {
            E e = data.get(0);
            for (int i = 0; i < 10; ++i) {
                assertEquals(e, expected.peekFirst());
                assertEquals(e, actual.peekFirst());
            }
        }

        @Test @DisplayName("peekLast returns the first element")
        void peekLastReturnsLastElement() {
            E e = data.get(data.size() - 1);
            for (int i = 0; i < 10; ++i) {
                assertEquals(e, expected.peekLast());
                assertEquals(e, actual.peekLast());
            }
        }

        @Test @DisplayName("test randomly")
        void testRandomly() {
            Iterator<E> iter = data.iterator();
            for (int i = 0; i < n; ++i) {
                if (rand.nextBoolean()) {
                    E e = iter.next();
                    switch (rand.nextInt(4)) {
                    case 0:
                        expected.addFirst(e);
                        actual.addFirst(e);
                        break;
                    case 1:
                        expected.addLast(e);
                        actual.addLast(e);
                        break;
                    case 2:
                        expected.offerFirst(e);
                        actual.offerFirst(e);
                        break;
                    case 3:
                        expected.offerLast(e);
                        actual.offerLast(e);
                        break;
                    }
                } else {
                    switch (rand.nextInt(4)) {
                    case 0:
                        assertEquals(expected.removeFirst(),
                                     actual.removeFirst());
                        break;
                    case 1:
                        assertEquals(expected.removeLast(),
                                     actual.removeLast());
                        break;
                    case 2:
                        assertEquals(expected.pollFirst(),
                                     actual.pollFirst());
                        break;
                    case 3:
                        assertEquals(expected.pollLast(),
                                     actual.pollLast());
                        break;
                    }
                }
                assertEquals(expected.getFirst(), actual.getFirst());
                assertEquals(expected.getLast(), actual.getLast());
                assertEquals(expected.peekFirst(), actual.peekFirst());
                assertEquals(expected.peekLast(), actual.peekLast());
                queuesAreEqual();
            }
        }

    }

    @Override
    abstract Deque<E> getExpected();

    @Override
    abstract Deque<E> getActual();

}
