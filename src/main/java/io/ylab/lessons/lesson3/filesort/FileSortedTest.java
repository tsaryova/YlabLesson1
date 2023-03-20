package io.ylab.lessons.lesson3.filesort;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class FileSortedTest {
    public static void main(String[] args) throws IOException {
        File dataFile = new Generator().generate("data.txt", 500);
        System.out.println(new Validator(dataFile).isSorted()); // false

        long startTime = System.currentTimeMillis();

        Sorter sorter = new Sorter();
        File sortedFile = sorter.sortFile(dataFile);
        System.out.println(new Validator(sortedFile).isSorted()); // true

        long endTime = System.currentTimeMillis();
        System.out.println(TimeUnit.MILLISECONDS.toSeconds(endTime - startTime));

        System.out.println(new Validator(sortedFile).isEqualsContentFiles(dataFile)); // true

    }
}
