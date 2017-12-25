import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

abstract class CollectionTest<E> {

    private List<E> data;
    private Collection<E> expected;
    private Collection<E> actual;

    @BeforeEach
    final void initializeCollection() {
        data = getData();
        expected = getExpected();
        actual = getActual();
    }

    @Test @DisplayName("collections are equal")
    final void collectionsAreEqual() {
        assertTrue(expected.containsAll(actual));
        assertTrue(actual.containsAll(expected));
        assertEquals(expected.size(), actual.size());
        assertEquals(expected.isEmpty(), actual.isEmpty());
    }

    @Nested @DisplayName("when new collection")
    final class WhenNewCollection {

        @AfterEach
        void check() { collectionsAreEqual(); }

        @Test @DisplayName("is size 0")
        void isSize0() {
            for (int i = 0; i < 10; ++i)
                assertEquals(0, actual.size());
        }

        @Test @DisplayName("is empty")
        void isEmpty() {
            for (int i = 0; i < 10; ++i)
                assertTrue(actual.isEmpty());
        }

        @Test @DisplayName("clears")
        void clears() { expected.clear(); actual.clear(); }

        @Test @DisplayName("iterator hasNext returns false")
        void iteratorHasNextReturnsFalse() {
            Iterator<E> iter = actual.iterator();
            for (int i = 0; i < 10; ++i)
                assertFalse(iter.hasNext());
        }

        @Test @DisplayName("iterator next throws NoSuchElementException")
        void iteratorNextThrows() {
            Iterator<E> iter = actual.iterator();
            for (int i = 0; i < 10; ++i)
                assertThrows(NoSuchElementException.class, iter::next);
        }

        @Test @DisplayName("add returns true")
        void addReturnsTrue() {
            for (E e : data) {
                assertTrue(expected.add(e));
                assertTrue(actual.add(e));
            }
        }

        @Test @DisplayName("addAll returns true")
        void addAllReturnsTrue() {
            assertTrue(expected.addAll(data));
            assertTrue(actual.addAll(data));
        }

    }

    @Nested @DisplayName("after adding an element to collection")
    final class AfterAddingAnElementToCollection {

        E e;

        @BeforeEach
        void addAnElement() {
            e = data.get(0);
            expected.add(e);
            actual.add(e);
        }

        @AfterEach
        void check() { collectionsAreEqual(); }

        @Test @DisplayName("is size 1")
        void isSize1() {
            for (int i = 0; i < 10; ++i)
                assertEquals(1, actual.size());
        }

        @Test @DisplayName("is not empty")
        void isNotEmpty() {
            for (int i = 0; i < 10; ++i)
                assertFalse(actual.isEmpty());
        }

        @Test @DisplayName("clears")
        void clears() {
            expected.clear();
            actual.clear();
        }

        @Test @DisplayName("iterator hasNext returns true")
        void iteratorHasNextReturnsTrue() {
            Iterator<E> iter = actual.iterator();
            for (int i = 0; i < 10; ++i)
                assertTrue(iter.hasNext());
        }

        @Test @DisplayName("iterator next returns the element")
        void iteratorNextReturnsElement() {
            assertEquals(e, actual.iterator().next());
        }

        @Test @DisplayName("iteroator next twice throws")
        void iteratorNextTwiceThrows() {
            Iterator<E> iter = actual.iterator();
            iter.next();
            for (int i = 0; i < 10; ++i)
                assertThrows(NoSuchElementException.class, iter::next);
        }

    }

    @Nested @DisplayName("after adding each element to collection")
    final class AfterAddingEachElementToCollection {

        @BeforeEach
        void addEach() {
            data.forEach(expected::add);
            data.forEach(actual::add);
        }

        @AfterEach
        void check() { collectionsAreEqual(); }

        @Test @DisplayName("is size of data")
        void isSizeOfData() { assertEquals(data.size(), actual.size()); }

        @Test @DisplayName("is not empty")
        void isNotEmpty() { assertFalse(actual.isEmpty()); }

        @Test @DisplayName("clears")
        void clears() {
            expected.clear();
            actual.clear();
        }

        @Test @DisplayName("iterator hasNext returns true")
        void iteratorHasNextReturnsTrue() {
            Iterator<E> iter = actual.iterator();
            for (int i = 0; i < 10; ++i)
                assertTrue(iter.hasNext());
        }

    }

    @Override
    public String toString()
    { return String.format("%s\n%s", expected, actual); }

    abstract int getN();
    abstract Random getRand();
    abstract List<E> getData();
    abstract Collection<E> getExpected();
    abstract Collection<E> getActual();

}
