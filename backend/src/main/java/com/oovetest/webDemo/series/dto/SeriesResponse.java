package com.oovetest.webDemo.series.dto;

import org.springframework.data.domain.Page;

import com.oovetest.webDemo.series.entity.SeriesStatus;


public record SeriesResponse(
    Long id,
    String title,
    Long bookCount,
    SeriesStatus status,
    Long authorId
) {
} 