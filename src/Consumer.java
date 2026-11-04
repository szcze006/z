import java.util.Date;
public class Consumer implements Runnable {
    private final Buffer buffer;
    public Consumer(Buffer buffer) { this.buffer = buffer; }
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println(Thread.currentThread().getName() + " napping");
            SleepUtilities.nap();
            if (Thread.currentThread().isInterrupted()) return;
            System.out.println(Thread.currentThread().getName() + " wants to consume");
            Date message = (Date) buffer.remove();
            if (message != null) System.out.println(Thread.currentThread().getName() + " received " + message);
        }
    }
}
