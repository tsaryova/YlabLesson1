package io.ylab.lessons.lesson1;

public class MultTable {

    public static void main(String[] args) throws Exception{
        short start = 1;
        short end = 9;
        for (short i = start; i <= end; i++) {
            for (short j = start; j <= end; j++) {
                System.out.printf("%d x %d = %d%n", i, j, i * j);
            }
            System.out.println();
        }
    }

}
