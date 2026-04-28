package com.oovetest.webDemo.tracking.entity;

public enum TrackingStatus {
    TRACKING("追蹤中"), 
    COMPLETED("已完結"),
    DROPPED("取消追蹤");

    private final String label;

    TrackingStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
