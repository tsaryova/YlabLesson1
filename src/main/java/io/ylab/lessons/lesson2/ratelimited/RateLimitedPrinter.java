package io.ylab.lessons.lesson2.ratelimited;

public class RateLimitedPrinter {

    private long lastTimeToPrint;
    private int interval;

    public RateLimitedPrinter(int interval) {
        this.interval = interval;
        this.lastTimeToPrint = 0;

    }

    public void print(String message) {
        if (canPrinting()) {
            System.out.println(message);
            this.lastTimeToPrint = System.currentTimeMillis();
        }
    }

    private boolean canPrinting() {
        long currentInterval = System.currentTimeMillis() - this.lastTimeToPrint;
        if (currentInterval > this.interval) {
            return true;
        }
        return false;
    }

}
