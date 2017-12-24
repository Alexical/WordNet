import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class FastArrayDeque<E> extends AbstractQueue<E> implements Deque<E> {

    private static final int INIT_CAPACITY = 4;

    private E[] a;
    private int n;
    private int j;

    public FastArrayDeque() {
        this(INIT_CAPACITY);
    }

    public FastArrayDeque(Collection<? extends E> c) {
        this(c.size());
        c.forEach(this::add);
    }

    public FastArrayDeque(int initialCapacity) {
        reset(initialCapacity);
    }

    public void reset() {
        reset(INIT_CAPACITY);
    }

    public void reset(int initialCapacity) {
        a = null;
        n = 0;
        j = initialCapacity;
    }

    @Override
    public void push(E item) {
        addFirst(item);
    }

    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    @Override
    public void addFirst(E e) {
        offerFirst(e);
    }

    @Override
    public void addLast(E e) {
        offerLast(e);
    }

    @Override
    public boolean offer(E e) {
        return offerFirst(e);
    }

    @Override
    public boolean offerFirst(E e) {
        if (e == null)
            throw new NullPointerException();
        if (a == null)
            a = (E[]) new Object[j];
        if (n == a.length)
            resize(2 * a.length);
        int i = i();
        if (i == 0)
            i = a.length;
        a[--i] = e;
        ++n;
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        if (e == null)
            throw new NullPointerException();
        if (a == null)
            a = (E[]) new Object[j];
        if (n == a.length)
            resize(2 * a.length);
        if (j == a.length)
            j = 0;
        a[j++] = e;
        ++n;
        return true;
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public E remove() {
        return removeFirst();
    }

    @Override
    public E removeFirst() {
        E e = pollFirst();
        if (e != null)
            return e;
        throw new NoSuchElementException();
    }

    @Override
    public E removeLast() {
        E e = pollLast();
        if (e != null)
            return e;
        throw new NoSuchElementException();
    }

    @Override
    public boolean removeFirstOccurrence(Object arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeLastOccurrence(Object arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E pollFirst() {
        if (isEmpty())
            return null;
        int i = i();
        E item = a[i];
        a[i] = null;
        --n;
        return item;
    }

    @Override
    public E pollLast() {
        if (isEmpty())
            return null;
        E item = getLast();
        a[--j] = null;
        --n;
        if (j == 0)
            j = a.length;
        return item;
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E getFirst() {
        E e = peekFirst();
        if (e != null)
            return e;
        throw new NoSuchElementException();
    }

    @Override
    public E getLast() {
        E e = peekLast();
        if (e != null)
            return e;
        throw new NoSuchElementException();
    }

    @Override
    public E peek() {
        return peekFirst();
    }

    @Override
    public E peekFirst() {
        if (isEmpty())
            return null;
        return a[i()];
    }

    @Override
    public E peekLast() {
        if (isEmpty())
            return null;
        return a[j - 1];
    }

    @Override
    public int size() {
        return n;
    }

    @Override
    public boolean isEmpty() {
        return n == 0;
    }

    @Override
    public void clear() {
        n = 0;
        if (a != null)
            j = a.length;
    }

    @Override
    public Iterator<E> iterator() {
        return new RingIterator();
    }

    @Override
    public Iterator<E> descendingIterator() {
        throw new UnsupportedOperationException();
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
