package com.oovetest.webDemo.book.entity;

public enum BookStatus {
    BUYED("已購買"),  
    TO_READ("預定要讀"), 
    READING("閱讀中"), 
    READ("閱畢"), 
    DROP_OUT("棄坑");

    private final String label;

    BookStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
