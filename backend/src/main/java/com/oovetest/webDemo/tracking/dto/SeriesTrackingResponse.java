package com.oovetest.webDemo.tracking.dto;

import java.time.LocalDateTime;

import com.oovetest.webDemo.tracking.entity.TrackingStatus;


public record SeriesTrackingResponse(
    Long seriesTrackingId,
    Long seriesId,
    TrackingStatus status,
    String seriesTitle,
    String seriesAuthor,
    LocalDateTime createdAt
) {}
