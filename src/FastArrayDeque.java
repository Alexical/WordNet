import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class FastArrayDeque<E> extends AbstractCollection<E> {

    private static final int DEFAULT_CAPACITY = 16;

    private E[] a;
    private int n;
    private int first;
    private int last;

    public FastArrayDeque() {
        this(DEFAULT_CAPACITY);
    }

    public FastArrayDeque(Collection<? extends E> c) {
        this(c.size());
        c.forEach(this::addLast);
    }

    public FastArrayDeque(int initialCapacity) {
        reset(initialCapacity);
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void reset() {
        reset(DEFAULT_CAPACITY);
    }

    public void reset(int initialCapacity) {
        a = (E[]) new Object[initialCapacity];
        clear();
    }

    public void clear() {
        n = 0;
        first = 0;
        last = a.length;
    }

    public boolean add(E item) {
        addLast(item);
        return true;
    }

    public void addFirst(E item) {
        if (n == a.length)
            resize(2 * a.length);
        if (first == 0)
            first = a.length;
        a[--first] = item;
        n++;
    }

    public void addLast(E item) {
        if (n == a.length)
            resize(2 * a.length);
        if (last == a.length)
            last = 0;
        a[last++] = item;
        n++;
    }

    public E removeFirst() {
        E item = getFirst();
        a[first++] = null;
        n--;
        if (first == a.length)
            first = 0;
        return item;
    }

    public E removeLast() {
        E item = getLast();
        a[--last] = null;
        n--;
        if (last == 0)
            last = a.length;
        return item;
    }

    public E getFirst() {
        if (isEmpty())
            throw new NoSuchElementException("Deque underflow");
        return a[first];
    }

    public E getLast() {
        if (isEmpty())
            throw new NoSuchElementException("Deque underflow");
        return a[last - 1];
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayIterator();
    }

    @Override
    public String toString() {
        return Arrays.toString(a);
    }

    private class ArrayIterator implements Iterator<E> {

        private int i = 0;

        @Override
        public boolean hasNext() {
            return i < n;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            E item = a[i + first < a.length ? i + first
                                               : i + first - a.length];
            i++;
            return item;
        }

    }

    private void resize(int capacity) {
        E[] dest = (E[]) new Object[capacity];
        if (first < last) {
            System.arraycopy(a, first, dest, 0, n);
        } else {
            System.arraycopy(a, first, dest, 0, a.length - first);
            System.arraycopy(a, 0, dest, a.length - first, last);
        }
        a = dest;
        first = 0;
        last = n;
    }

}
