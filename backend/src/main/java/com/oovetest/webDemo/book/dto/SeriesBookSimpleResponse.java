package com.oovetest.webDemo.book.dto;

import lombok.Data;


public record SeriesBookSimpleResponse  (
    Long id,
    String title,
    String seriesName,
    Integer volume
){} 
