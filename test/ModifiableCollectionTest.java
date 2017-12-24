import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

import org.junit.jupiter.api.Test;

abstract class ModifiableCollectionTest<E> extends CollectionTest<E> {

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

    protected abstract Collection<E> getData();

}
