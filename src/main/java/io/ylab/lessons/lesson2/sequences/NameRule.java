package io.ylab.lessons.lesson2.sequences;

public enum NameRule {
    A("a"), B("b"), C("c"), D("d"), E("e"), F("f"), G("g"), H("h"), I("i"), J("j");
    private String name;
    NameRule(String name) {
        this.name = name;
    }
    public String getName() {return name;}
}
