package io.ylab.lessons.lesson3.filesort;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Validator {

    private File file;
    public Validator(File file) {
        this.file = file;
    }
    public boolean isSorted() {
        try (Scanner scanner = new Scanner(new FileInputStream(file))) {
            long prev = Long.MIN_VALUE;
            while (scanner.hasNextLong()) {
                long current = scanner.nextLong();
                if (current < prev) {
                    return false;
                } else {
                    prev = current;
                }
            }
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean isEqualsContentFiles(File otherFile) throws IOException{
        long hashFile = getHashCodeContentFile(file);
        long hashOtherFile = getHashCodeContentFile(otherFile);
        return (hashFile == hashOtherFile);
    }

    private long getHashCodeContentFile(File file) throws IOException{
        long hashCode = 0;
        try (
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
        ) {
            String currentElem = "";
            while ((currentElem = bufferedReader.readLine()) != null) {
                hashCode += currentElem.hashCode();
            }
        }
        return hashCode;
    }

}
