package io.ylab.intensive.lesson05.messagefilter.parsing.file;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class ParsingWordsFileImpl implements ParsingWordsFile {

    private static final String NEW_FILE_PATH_NAME = "src/main/resources/lessons/lesson5/filter_words.txt";

    @Override
    public File createNewParsedFile(File originalFile, String delimiter) throws IOException {
        List<String> wordList = getWordList(originalFile, delimiter);
        File file = new File(NEW_FILE_PATH_NAME);

        if (!wordList.isEmpty()) {
            try (PrintWriter fileWriter = new PrintWriter(file)) {
                wordList.forEach(fileWriter::println);
            }
        }

        return file;
    }

    @Override
    public List<String> getWordList(File file, String delimiter) throws IOException {
        List<String> wordList = new ArrayList<>();
        try (Scanner scanner = new Scanner(new FileInputStream(file))) {
            scanner.useDelimiter(delimiter);
            while (scanner.hasNext()) {
                wordList.add(scanner.next().replace(" ", "").trim());
            }
        }

        return wordList;
    }
}
