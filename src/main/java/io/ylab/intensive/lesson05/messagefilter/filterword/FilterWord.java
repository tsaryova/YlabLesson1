package io.ylab.intensive.lesson05.messagefilter.filterword;

public class FilterWord {

    private Long id;
    private String value;

    public FilterWord(){ };

    public FilterWord(Long id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
