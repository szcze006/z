public class SleepUtilities {
    private static final int NAP_TIME = 5;
    public static void nap() {
        int units = (int) (NAP_TIME * Math.random());
        try {
            Thread.sleep(units * 100L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
