package com.oovetest.webDemo.book.dto;

import lombok.Data;

@Data
public class SeriesBookSimpleResponse {
    private Long id;
    private String title;
    private String seriesName;
    private Integer volume;
}
