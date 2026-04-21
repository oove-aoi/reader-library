package com.oovetest.webDemo.series.dto;

import com.oovetest.webDemo.series.entity.SeriesStatus;

import lombok.Data;

public record SeriesResponse(
    Long id,
    String title,
    Long bookCount,
    SeriesStatus status,
    Long authorId
) {} 