import java.util.concurrent.Semaphore;

/** Semaphore-protected bounded buffer with adjustable capacity. */
public class BoundedBuffer implements Buffer {
    private final int capacity;
    private final Object[] buffer;
    private final Semaphore mutex = new Semaphore(1);
    private final Semaphore empty;
    private final Semaphore full = new Semaphore(0);
    private int count = 0;
    private int in = 0;
    private int out = 0;

    public BoundedBuffer() { this(2); }
    public BoundedBuffer(int capacity) {
        this.capacity = capacity;
        this.buffer = new Object[capacity];
        this.empty = new Semaphore(capacity);
    }
    public void insert(Object item) {
        try {
            empty.acquire();
            mutex.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        count++;
        buffer[in] = item;
        in = (in + 1) % capacity;
        System.out.println("Producer Entered " + item +
                (count == capacity ? " Buffer FULL" : " Buffer Size = " + count));
        mutex.release();
        full.release();
    }
    public Object remove() {
        try {
            full.acquire();
            mutex.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
        count--;
        Object item = buffer[out];
        out = (out + 1) % capacity;
        System.out.println("Consumer Consumed " + item +
                (count == 0 ? " Buffer EMPTY" : " Buffer Size = " + count));
        mutex.release();
        empty.release();
        return item;
    }
}
