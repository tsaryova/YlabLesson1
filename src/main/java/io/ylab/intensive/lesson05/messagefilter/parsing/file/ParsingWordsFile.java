package io.ylab.intensive.lesson05.messagefilter.parsing.file;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ParsingWordsFile {

    File createNewParsedFile(File originalFile, String delimiter) throws IOException;

    List<String> getWordList(File file, String delimiter) throws IOException;
}
