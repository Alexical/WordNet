import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RingBuffer<E> extends AbstractQueue<E> implements Deque<E> {

    private static final int INIT_CAPACITY = 4;

    private E[] buffer;
    private int head;
    private int tail;

    public RingBuffer() { this(INIT_CAPACITY); }

    public RingBuffer(Collection<? extends E> c) {
        this();
        addAll(c);
    }

    public RingBuffer(int initialCapacity) { reset(initialCapacity); }

    public void reset() { reset(INIT_CAPACITY); }

    public void reset(int initialCapacity) {
        buffer = null;
        head = 0;
        tail = ceil2(initialCapacity);
    }

    @Override
    public int size()
    { return buffer != null ? mask(tail - head, buffer.length << 1) : 0; }

    @Override
    public Iterator<E> iterator() { return new RingIterator(); }

    @Override
    public Iterator<E> descendingIterator()
    { throw new UnsupportedOperationException(); }

    @Override
    public boolean isEmpty() { return buffer == null || head == tail; }

    @Override
    public void clear() {
        head = 0;
        if (buffer != null)
            tail = 0;
    }

    @Override
    public boolean add(E e) { return insertLast(e); }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        resize(size() + c.size());
        return super.addAll(c);
    }

    @Override
    public E remove() {
        if (!isEmpty())
            return eraseFirst();
        throw new NoSuchElementException();
    }

    @Override
    public E element() {
        if (!isEmpty())
            return examineFirst();
        throw new NoSuchElementException();
    }

    @Override
    public boolean offer(E e) { return insertLast(e); }

    @Override
    public E poll() {
        if (!isEmpty())
            return eraseFirst();
        return null;
    }

    @Override
    public E peek() {
        if (!isEmpty())
            return examineFirst();
        return null;
    }

    @Override
    public void push(E item) { insertFirst(item); }

    @Override
    public E pop() {
        if (!isEmpty())
            return eraseFirst();
        throw new NoSuchElementException();
    }

    @Override
    public void addFirst(E e) { insertFirst(e); }

    @Override
    public void addLast(E e) { insertLast(e); }

    @Override
    public E removeFirst() {
        if (!isEmpty())
            return eraseFirst();
        throw new NoSuchElementException();
    }

    @Override
    public E removeLast() {
        if (!isEmpty())
            return eraseLast();
        throw new NoSuchElementException();
    }

    @Override
    public E getFirst() {
        if (!isEmpty())
            return examineFirst();
        throw new NoSuchElementException();
    }

    @Override
    public E getLast() {
        if (!isEmpty())
            return examineLast();
        throw new NoSuchElementException();
    }

    @Override
    public boolean offerFirst(E e) { return insertFirst(e); }

    @Override
    public boolean offerLast(E e) { return insertLast(e); }

    @Override
    public E pollFirst() {
        if (!isEmpty())
            return eraseFirst();
        return null;
    }

    @Override
    public E pollLast() {
        if (!isEmpty())
            return eraseLast();
        return null;
    }

    @Override
    public E peekFirst() {
        if (!isEmpty())
            return examineFirst();
        return null;
    }

    @Override
    public E peekLast() {
        if (!isEmpty())
            return examineLast();
        return null;
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
    public String toString() { return Arrays.toString(buffer); }

    private class RingIterator implements Iterator<E> {

        private int r = head;

        @Override
        public boolean hasNext() { return buffer != null && r != tail; }

        @Override
        public void remove()
        { throw new UnsupportedOperationException(); }

        @Override
        public E next() {
            if (hasNext()) {
                E e = buffer[mask(r++, buffer.length)];
                r = mask(r, buffer.length << 1);
                return e;
            }
            throw new NoSuchElementException();
        }

    }

    private boolean insertFirst(E e) {
        if (e != null) {
            if (buffer == null)
                allocate();
            else if (size() == buffer.length)
                resize(buffer.length << 1);
            buffer[mask(--head, buffer.length)] = e;
            head = mask(head, buffer.length << 1);
            return true;
        }
        throw new NullPointerException();
    }

    private boolean insertLast(E e) {
        if (e != null) {
            if (buffer == null)
                allocate();
            else if (size() == buffer.length)
                resize(buffer.length << 1);
            buffer[mask(tail++, buffer.length)] = e;
            tail = mask(tail, buffer.length << 1);
            return true;
        }
        throw new NullPointerException();

    }

    private E eraseFirst() {
        E e = buffer[mask(head++, buffer.length)];
        head = mask(head, buffer.length << 1);
        return e;
    }

    private E eraseLast() {
        E e = buffer[mask(--tail, buffer.length)];
        tail = mask(tail, buffer.length << 1);
        return e;
    }

    private E examineFirst() { return buffer[mask(head, buffer.length)]; }

    private E examineLast() { return buffer[mask(tail - 1, buffer.length)]; }

    private void allocate() {
        buffer = (E[]) new Object[tail];
        tail = 0;
    }

    private void resize(int capacity) {
        capacity = ceil2(capacity);
        if (buffer != null) {
            E[] dest = (E[]) new Object[capacity];
            int x = mask(head, buffer.length);
            int y = mask(tail, buffer.length);
            if (head < tail) {
                System.arraycopy(buffer, x, dest, 0, size());
            } else {
                System.arraycopy(buffer, x, dest, 0, buffer.length - x);
                System.arraycopy(buffer, 0, dest, buffer.length - x, y);
            }
            tail = size();
            head = 0;
            buffer = dest;
        } else if (capacity > tail) {
            tail = capacity;
        }
    }

    private int ceil2(int a)
    { return (a & (a - 1)) == 0 ? a : Integer.highestOneBit(a << 1); }

    private int mask(int x, int length) { return x & (length - 1); }

}
