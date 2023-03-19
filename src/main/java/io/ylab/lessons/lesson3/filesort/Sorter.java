package io.ylab.lessons.lesson3.filesort;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Sorter {
    private static final int COUNT_ELEMENTS_TO_READ = 1_000_000;
    private static final String START_NAME_TEMP_FILE = "temp-file-";
    private int countElementsInFile;
    private int countFiles;
    private static final String NEW_FILE_PATHNAME = "external-sorted.txt";

    public File sortFile(File dataFile) throws IOException {

        createTempFullyFiles(dataFile); // здесь присваются актуальные значения для countFiles и countElementsInFile

        if (countFiles > 0 && countElementsInFile > 0) {

            long[] topElements = new long[countFiles];
            BufferedReader[] arrBufferedReader = new BufferedReader[countFiles];

            //получаем первые значения из каждого файла
            for (int i = 0; i < countFiles; i++) {
                arrBufferedReader[i] = new BufferedReader(new FileReader(START_NAME_TEMP_FILE + i + ".txt"));
                String FirstElementStr = arrBufferedReader[i].readLine();
                if (FirstElementStr != null){
                    topElements[i] = Long.parseLong(FirstElementStr);
                }
            }

            File sortedFile = createAndWriteSortedFile(arrBufferedReader, topElements);

            for (int i = 0; i < countFiles; i++){
                arrBufferedReader[i].close();
                Files.deleteIfExists(Paths.get(START_NAME_TEMP_FILE + i + ".txt"));
            }

            return sortedFile;
        }

        return null;
    }

    private void writeSortedElementToTempFile(List<Long> sortedElems, int numTempFile) throws IOException {
        try (
                FileWriter fileWriter = new FileWriter(START_NAME_TEMP_FILE + numTempFile + ".txt");
                PrintWriter printWriter = new PrintWriter(fileWriter);
        ) {
            for (int i = 0; i < sortedElems.size(); i++) {
                printWriter.println(sortedElems.get(i));
            }

        }
    }

    private void createTempFullyFiles(File file) throws IOException {
        try (
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
        ) {
            List<Long> bufferList = new ArrayList<>();

            String currentElem = "";
            int curCountInChunk = 0;
            int numTempFile = 0;

            while ((currentElem = bufferedReader.readLine()) != null) {
                if (curCountInChunk < COUNT_ELEMENTS_TO_READ - 1) {
                    bufferList.add(Long.parseLong(currentElem));
                    curCountInChunk++;
                } else {
                    bufferList.add(Long.parseLong(currentElem));
                    Collections.sort(bufferList);
                    writeSortedElementToTempFile(bufferList, numTempFile);
                    bufferList.clear();
                    curCountInChunk = 0;
                    numTempFile++;
                }
                countElementsInFile++;
            }

            countFiles = numTempFile;

        }
    }

    private File createAndWriteSortedFile(BufferedReader[] arrBufferedReader, long[] topElements) throws IOException {
        File newFile = new File(NEW_FILE_PATHNAME);
        PrintWriter printWriter = new PrintWriter(newFile);

        for (int i = 0; i < countElementsInFile; i++) {
            long min = topElements[0];
            int fileWithMin = 0;

            for (int j = 0; j < countFiles; j++) {
                if (min > topElements[j]) {
                    min = topElements[j];
                    fileWithMin = j;
                }
            }

            printWriter.println(min);
            String nextElem = arrBufferedReader[fileWithMin].readLine();
            if (nextElem != null){
                topElements[fileWithMin] = Long.parseLong(nextElem);
            } else {
                topElements[fileWithMin] = Long.MAX_VALUE;
            }
        }

        printWriter.close();
        return newFile;
    }

}

