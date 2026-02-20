package com.oovetest.webDemo.series.dto;

import com.oovetest.webDemo.series.model.SeriesStatus;

import lombok.Data;

@Data
public class SeriesResponse {
    private Long id;
    private String title;
    private Integer bookCount; 
    private SeriesStatus status;
    private Long authorId;
}
