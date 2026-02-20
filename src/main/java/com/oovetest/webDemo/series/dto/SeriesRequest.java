package com.oovetest.webDemo.series.dto;

import com.oovetest.webDemo.series.model.SeriesStatus;

import lombok.Data;

@Data
public class SeriesRequest {
    private String title;
    private SeriesStatus status;
    private Long authorId;
}
