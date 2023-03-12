package io.ylab.lessons.lesson2.stats;

public class StatsAccumulatorImpl implements StatsAccumulator {
    private int min;
    private int max;
    private int count;
    private Double avg;
    private Integer sum;

    public StatsAccumulatorImpl() {
        this.min = 0;
        this.max = 0;
        this.count = 0;
        this.avg = 0.0;
        this.sum = 0;
    }

    @Override
    public void add(int value) {
        setMin(value);
        setMax(value);
        setSum(value);
        setCount();
        setAvg();
    }

    @Override
    public int getMin() {
        return min;
    }

    private void setMin(int value) {
        min = Integer.min(min, value);
    }

    @Override
    public int getMax() {
        return max;
    }

    private void setMax(int value) {
        max = Integer.max(max, value);
    }

    @Override
    public int getCount() {
        return count;
    }

    private void setCount() {
        count++;
    }

    @Override
    public Double getAvg() {
        return avg;
    }

    private void setSum(int value) {
        sum += value;
    }

    private void setAvg() {
        avg = (double) sum / (double) count;
    }
}
