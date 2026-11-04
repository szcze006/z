/** Producer-consumer tests: optional arguments are capacity, producers, consumers. */
public class Factory {
    public static void main(String[] args) {
        int size = args.length > 0 ? Integer.parseInt(args[0]) : 2;
        int producers = args.length > 1 ? Integer.parseInt(args[1]) : 1;
        int consumers = args.length > 2 ? Integer.parseInt(args[2]) : 3;
        Buffer server = new BoundedBuffer(size);
        System.out.printf("Buffer size=%d Producers=%d Consumers=%d%n", size, producers, consumers);
        for (int i = 0; i < producers; i++) {
            Thread t = new Thread(new Producer(server), "Producer" + i);
            t.start();
        }
        for (int i = 0; i < consumers; i++) {
            Thread t = new Thread(new Consumer(server), "Consumer" + i);
            t.start();
        }
    }
}
