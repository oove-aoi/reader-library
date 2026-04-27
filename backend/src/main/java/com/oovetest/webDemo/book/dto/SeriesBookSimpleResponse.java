package com.oovetest.webDemo.book.dto;



public record SeriesBookSimpleResponse  (
    Long id,
    String authorName,
    String title,
    String seriesName,
    Integer volume
){} 
