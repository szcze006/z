import java.util.Date;
public class Producer implements Runnable {
    private final Buffer buffer;
    public Producer(Buffer buffer) { this.buffer = buffer; }
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println(Thread.currentThread().getName() + " napping");
            SleepUtilities.nap();
            if (Thread.currentThread().isInterrupted()) return;
            Date message = new Date();
            System.out.println(Thread.currentThread().getName() + " produced " + message);
            buffer.insert(message);
        }
    }
}
