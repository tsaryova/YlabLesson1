package io.ylab.lessons.lesson3.transliterator;

import java.util.Map;

public class TransliteratorImpl implements Transliterator{
    private final Map<Character, String> rulesTransliterate;

    public TransliteratorImpl() {
        rulesTransliterate = Map.ofEntries(
                Map.entry('А', "A"),
                Map.entry('Б', "B"),
                Map.entry('В', "V"),
                Map.entry('Г', "G"),
                Map.entry('Д', "D"),
                Map.entry('Е', "E"),
                Map.entry('Ё', "E"),
                Map.entry('Ж', "ZH"),
                Map.entry('З', "Z"),
                Map.entry('И', "I"),
                Map.entry('Й', "I"),
                Map.entry('К', "K"),
                Map.entry('Л', "L"),
                Map.entry('М', "M"),
                Map.entry('Н', "N"),
                Map.entry('О', "O"),
                Map.entry('П', "P"),
                Map.entry('Р', "R"),
                Map.entry('С', "S"),
                Map.entry('Т', "T"),
                Map.entry('У', "U"),
                Map.entry('Ф', "F"),
                Map.entry('Х', "KH"),
                Map.entry('Ц', "TS"),
                Map.entry('Ч', "CH"),
                Map.entry('Ш', "SH"),
                Map.entry('Щ', "SHCH"),
                Map.entry('Ь', ""),
                Map.entry('Ы', "Y"),
                Map.entry('Ъ', "IE"),
                Map.entry('Э', "E"),
                Map.entry('Ю', "IU"),
                Map.entry('Я', "IA")
        );
    }

    @Override
    public String transliterate(String source) {
        if (source.trim().isEmpty()) {
            throw new IllegalArgumentException("String is empty!");
        }
        String result = "";
        for (char symbol : source.toCharArray()) {
            if (rulesTransliterate.containsKey(symbol)) {
                result += rulesTransliterate.get(symbol);
            } else {
                result += symbol;
            }
        }

        return result;
    }
}
