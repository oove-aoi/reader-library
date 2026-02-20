package com.oovetest.webDemo.tracking.dto;

import lombok.Data;
import com.oovetest.webDemo.tracking.model.TrackingStatus;

@Data
public class SeriesTrackingRequest {
    private Long seriesId;
    private TrackingStatus status;
}
