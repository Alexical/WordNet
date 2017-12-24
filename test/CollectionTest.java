import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

abstract class CollectionTest<E> {

    @Test
    public final void testAdd() {
        E r = data().iterator().next();
        expected().add(r);
        actual().add(r);

        assertIterableEquals(expected(), actual());
        assertEquals(expected().size(), actual().size());
        assertEquals(actual().isEmpty(), actual().isEmpty());
    }

    @Test
    public final void testAddAll() {
        expected().addAll(data());
        actual().addAll(data());

        assertIterableEquals(expected(), actual());
        assertEquals(expected().size(), actual().size());
        assertEquals(actual().isEmpty(), actual().isEmpty());
    }

    @Test
    public final void testClearDefault() {
        expected().clear();
        actual().clear();

        assertIterableEquals(expected(), actual());
        assertEquals(expected().size(), actual().size());
        assertEquals(actual().isEmpty(), actual().isEmpty());
    }

    @Test
    public final void testIteratorDefault() {
        assertIterableEquals(expected(), actual());

        assertFalse(actual().iterator().hasNext());
        assertThrows(NoSuchElementException.class,
                     actual().iterator()::next);

        assertIterableEquals(expected(), actual());
    }

    @Test
    public final void testIsEmptyDefault() {
        assertIterableEquals(expected(), actual());

        assertTrue(actual().isEmpty());

        assertIterableEquals(expected(), actual());
    }

    @Test
    public final void testSizeDefault() {
        assertIterableEquals(expected(), actual());

        assertEquals(0, actual().size());

        assertIterableEquals(expected(), actual());
    }

    protected abstract Collection<E> data();

    protected abstract Collection<E> expected();

    protected abstract Collection<E> actual();

}
