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

    protected abstract Collection<Item> data();

}
