import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Random;

class FastArrayDequeTest extends DequeTest<Integer> {

    private final int n;
    private final Random rand;
    private final List<Integer> data;
    private final ArrayDeque<Integer> expected;
    private final FastArrayDeque<Integer> actual;

    FastArrayDequeTest() {
        n = 1000;
        rand = new Random();
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
    Deque<Integer> getExpected() { return expected; }

    @Override
    Deque<Integer> getActual() { return actual; }

}
