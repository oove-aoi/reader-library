package com.oovetest.webDemo.tracking.dto;

import java.time.LocalDateTime;

import com.oovetest.webDemo.tracking.entity.TrackingStatus;

import lombok.Data;

@Data
public class SeriesTrackingResponse {
    private Long id;
    private Long seriesId;
    private TrackingStatus status;
    private String seriesTitle;
    private String seriesAuthor;
    private LocalDateTime createdAt;
}
