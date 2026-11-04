import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Run VoteLab with program argument 1, 2, 3, 4, 5 or 6.
 * Each mode corresponds to the corresponding Vote.java lab test.
 * Run duration is 10 seconds by default; optional second argument sets milliseconds.
 */
public class VoteLab {
    private static final int THREADS = 5;
    private static volatile boolean done = false;

    public static void main(String[] args) throws Exception {
        int test = args.length > 0 ? Integer.parseInt(args[0]) : 1;
        long duration = args.length > 1 ? Long.parseLong(args[1]) : 10000;
        if (test < 1 || test > 6) throw new IllegalArgumentException("Test must be 1-6");
        int permits = test == 3 ? 2 : 1;
        Semaphore sem = new Semaphore(permits);
        CyclicBarrier barrier = new CyclicBarrier(THREADS);
        AtomicInteger inBooth = new AtomicInteger();
        AtomicInteger maxInBooth = new AtomicInteger();
        Thread[] workers = new Thread[THREADS];
        System.out.println("=== Vote Lab Test " + test + " ===");
        System.out.println("Semaphore permits=" + permits + ", threads=" + THREADS);
        for (int i = 0; i < THREADS; i++) {
            final int number = i;
            workers[i] = new Thread(() -> {
                Random rand = new Random();
                int myVotes = 0;
                try {
                    while (!done && !Thread.currentThread().isInterrupted()) {
                        // Tests 1 and 4 skip acquire().
                        if (test != 1 && test != 4) sem.acquire();
                        int count = inBooth.incrementAndGet();
                        maxInBooth.accumulateAndGet(count, Math::max);
                        System.out.printf("Enter vote box Thread%d Count=%d%n", number, count);
                        Thread.sleep(10);
                        int choice = rand.nextInt(2);
                        System.out.printf("Depart voting box Thread%d Vote=%d%n", number, choice);
                        inBooth.decrementAndGet();
                        // Tests 1 and 5 skip release().
                        if (test != 1 && test != 5) sem.release();
                        myVotes++;
                        // Test 6 waits until all five workers complete this voting round.
                        if (test == 6) barrier.await();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    System.out.println(Thread.currentThread().getName() + ": " + e);
                } finally {
                    System.out.printf("Completed Poll Station: Thread%d with votes=%d%n",
                            number, myVotes);
                }
            }, "Thread" + i);
            workers[i].start();
        }
        Thread.sleep(duration);
        done = true;
        // In test 5 four workers remain blocked forever without interruption.
        if (test == 5 || test == 6) {
            for (Thread worker : workers) worker.interrupt();
            barrier.reset();
        }
        for (Thread worker : workers) worker.join(2000);
        System.out.println("Maximum simultaneous voters observed: " + maxInBooth.get());
        System.out.println("=== End Test " + test + " ===");
    }
}
