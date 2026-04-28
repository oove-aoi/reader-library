package com.oovetest.webDemo.book.entity;

public enum BookType {
    SERIES("系列作品"),
    STANDALONE("單一作品"),
    DOUJIN("同人誌");

    private final String label;

    BookType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
