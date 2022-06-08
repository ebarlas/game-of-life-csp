package gameoflife;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Channel<T> {
    private final BlockingQueue<T> queue = new LinkedBlockingQueue<>();

    T take() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            return null;
        }
    }

    void put(T value) {
        try {
            queue.put(value);
        } catch (InterruptedException e) {
            // abort
        }
    }
}
