package io.ylab.lessons.lesson3.transliterator;

public class TransliteratorTest {
    public static void main(String[] args) {
        Transliterator transliterator = new TransliteratorImpl();
        String res = transliterator.transliterate("HELLO! ПРИВЕТ! Go, boy!");
        System.out.println(res);
    }
}
