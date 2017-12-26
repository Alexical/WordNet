import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Random;

class RingBufferTest extends DequeTest<Integer> {

    private final int n;
    private final Random rand;
    private final List<Integer> data;
    private final ArrayDeque<Integer> expected;
    private final RingBuffer<Integer> actual;

    RingBufferTest() {
        n = 1000;
        rand = new Random(3);
        data = new ArrayList<>();
        for (int i = 0; i < n; ++i)
            data.add(rand.nextInt(n * 10));
        expected = new ArrayDeque<>();
        actual = new RingBuffer<>();
    }

    @Override
    int getN() { return n; }

    @Override
    Random getRand() { return rand; }

    @Override
    List<Integer> getData() { return data; }

    @Override
    Deque<Integer> getExpected() { return expected; }

    @Override
    Deque<Integer> getActual() { return actual; }

}
