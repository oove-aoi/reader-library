package com.oovetest.webDemo.series.entity;

public enum SeriesStatus {
    ONGOING("進行中"), 
    COMPLETED("已完結"), 
    AXED("斷尾");   

    private final String label;

    SeriesStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
