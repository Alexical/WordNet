import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class FastArrayDeque<E> extends AbstractCollection<E> {

    private static final int INIT_CAPACITY = 16;

    private E[] a;
    private int n;
    private int j;

    public FastArrayDeque() {
        this(INIT_CAPACITY);
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
        reset(INIT_CAPACITY);
    }

    public void reset(int initialCapacity) {
        a = (E[]) new Object[initialCapacity];
        clear();
    }

    public void clear() {
        n = 0;
        j = a.length;
    }

    public boolean add(E item) {
        addLast(item);
        return true;
    }

    public void push(E item) {
        addFirst(item);
    }

    public void addFirst(E item) {
        if (n == a.length)
            resize(2 * a.length);
        int i = i();
        if (i == 0)
            i = a.length;
        a[--i] = item;
        ++n;
    }

    public void addLast(E item) {
        if (n == a.length)
            resize(2 * a.length);
        if (j == a.length)
            j = 0;
        a[j++] = item;
        ++n;
    }

    public E remove() {
        return removeFirst();
    }

    public E pop() {
        return removeFirst();
    }

    public E removeFirst() {
        E item = getFirst();
        a[i()] = null;
        --n;
        return item;
    }

    public E removeLast() {
        E item = getLast();
        a[--j] = null;
        --n;
        if (j == 0)
            j = a.length;
        return item;
    }

    public E getFirst() {
        if (isEmpty())
            throw new NoSuchElementException();

        return a[i()];
    }

    public E getLast() {
        if (isEmpty())
            throw new NoSuchElementException();

        return a[j - 1];
    }

    @Override
    public Iterator<E> iterator() {
        return new RingIterator();
    }

    @Override
    public String toString() {
        return Arrays.toString(a);
    }

    private class RingIterator implements Iterator<E> {

        private int r = n;

        @Override
        public boolean hasNext() {
            return r > 0;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            int curr = j - r;
            if (curr < 0)
                curr += a.length;
            E item = a[curr];
            --r;
            return item;
        }

    }

    private void resize(int capacity) {
        E[] dest = (E[]) new Object[capacity];
        int i = i();
        if (i < j) {
            System.arraycopy(a, i, dest, 0, n);
        } else {
            System.arraycopy(a, i, dest, 0, a.length - i);
            System.arraycopy(a, 0, dest, a.length - i, j);
        }
        a = dest;
        j = n;
    }

    private int i() {
        int i = j - n;
        if (i < 0)
            i += a.length;
        return i;
    }

}
