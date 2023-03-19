package io.ylab.lessons.lesson3.filesort;

import java.io.File;
import java.io.IOException;

public class FileSortedTest {
    public static void main(String[] args) throws IOException {
        File dataFile = new Generator().generate("data.txt", 370_000_000);
        System.out.println(new Validator(dataFile).isSorted()); // false
        File sortedFile = new Sorter().sortFile(dataFile);
        System.out.println(new Validator(sortedFile).isSorted()); // true
    }
}
