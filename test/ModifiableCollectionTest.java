import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

import org.junit.jupiter.api.Test;

abstract class ModifiableCollectionTest<Item> extends CollectionTest<Item> {

    @Test
    public void testAddSingle() {
        Item r = data().iterator().next();
        expected.add(r);
        actual.add(r);
        assertIterableEquals(expected, actual);
    }

    @Test
    public void testAddMultiple() {
        expected.addAll(data());
        actual.addAll(data());
        assertIterableEquals(expected, actual);
    }

    @Test
    public void testClearDefault() {
        try {
            actual.clear();
            assertEquals(0, actual.size());
            assertTrue(actual.isEmpty());
        } catch (UnsupportedOperationException e) {
        }
    }

    protected abstract Collection<Item> data();

}
