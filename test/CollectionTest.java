import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

abstract class CollectionTest<E> {

    @Test
    public void testIteratorDefault() {
        assertIterableEquals(getExpected(), getActual());

        assertFalse(getActual().iterator().hasNext());
        assertThrows(NoSuchElementException.class,
                     getActual().iterator()::next);

        assertIterableEquals(getExpected(), getActual());
    }

    @Test
    public void testSizeDefault() {
        assertIterableEquals(getExpected(), getActual());

        assertEquals(0, getActual().size());

        assertIterableEquals(getExpected(), getActual());
    }

    @Test
    public void testIsEmptyDefault() {
        assertIterableEquals(getExpected(), getActual());

        assertTrue(getActual().isEmpty());

        assertIterableEquals(getExpected(), getActual());
    }

    @Test
    public void testAddSingle() {
        E r = getData().iterator().next();
        getExpected().add(r);
        getActual().add(r);

        assertIterableEquals(getExpected(), getActual());
        assertEquals(getExpected().size(), getActual().size());
        assertEquals(getActual().isEmpty(), getActual().isEmpty());
    }

    @Test
    public void testAddMultiple() {
        getExpected().addAll(getData());
        getActual().addAll(getData());

        assertIterableEquals(getExpected(), getActual());
        assertEquals(getExpected().size(), getActual().size());
        assertEquals(getActual().isEmpty(), getActual().isEmpty());
    }

    @Test
    public void testClearDefault() {
        getExpected().clear();
        getActual().clear();

        assertIterableEquals(getExpected(), getActual());
        assertEquals(getExpected().size(), getActual().size());
        assertEquals(getActual().isEmpty(), getActual().isEmpty());
    }

    @Test
    public abstract void testCopyConstructor();

    protected abstract Collection<E> getData();

    protected abstract Collection<E> getExpected();

    protected abstract Collection<E> getActual();

}
