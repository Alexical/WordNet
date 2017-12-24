import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

abstract class CollectionTest<E> {

    protected Collection<E> expected;
    protected Collection<E> actual;

    @Test
    public void testIteratorDefault() {
        assertFalse(actual.iterator().hasNext());
        assertThrows(NoSuchElementException.class, actual.iterator()::next);
    }

    @Test
    public void testSizeDefault() {
        assertEquals(0, actual.size());
    }

    @Test
    public void testIsEmptyDefault() {
        try {
            assertTrue(actual.isEmpty());
        } catch (UnsupportedOperationException e) {
        }
    }

    protected void setExpected(Collection<E> expected) {
        this.expected = expected;
    }

    protected void setActual(Collection<E> actual) {
        this.actual = actual;
    }

}
