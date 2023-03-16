package io.ylab.lessons.lesson2.stats;

public class StatsAccumulatorTest {
    public static void main(String[] args) {
        StatsAccumulator stats = new StatsAccumulatorImpl();
        System.out.println(stats.getAvg());
        stats.add(1);
        stats.add(2);
        System.out.println(stats.getAvg()); //1.5

        stats.add(0);
        System.out.println(stats.getMin()); //0

        stats.add(3);
        stats.add(8);
        System.out.println(stats.getMax()); //8
        System.out.println(stats.getCount()); // 5
    }
}
