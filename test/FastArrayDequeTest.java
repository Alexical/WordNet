import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

class FastArrayDequeTest extends QueueTest<Integer> {

    private final int n;
    private final Random rand;
    private final List<Integer> data;
    private final ArrayDeque<Integer> expected;
    private final FastArrayDeque<Integer> actual;

    FastArrayDequeTest() {
        n = 1000;
        rand = new Random(0);
        data = new ArrayList<>();
        for (int i = 0; i < n; ++i)
            data.add(rand.nextInt(n * 2));
        expected = new ArrayDeque<>();
        actual = new FastArrayDeque<>();
    }

    @Override
    int getN() { return n; }

    @Override
    Random getRand() { return rand; }

    @Override
    List<Integer> getData() { return data; }

    @Override
    Queue<Integer> getExpected() { return expected; }

    @Override
    Queue<Integer> getActual() { return actual; }

}
