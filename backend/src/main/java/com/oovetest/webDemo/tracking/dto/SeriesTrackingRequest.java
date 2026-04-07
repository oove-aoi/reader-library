package com.oovetest.webDemo.tracking.dto;

import lombok.Data;
import jakarta.validation.constraints.Positive;

import com.oovetest.webDemo.tracking.entity.TrackingStatus;

import jakarta.validation.constraints.NotNull;

@Data
public class SeriesTrackingRequest {
    @Positive(message = "系列ID必須為正數")
    private Long seriesId;

    @NotNull(message = "書籍狀態不能為空")
    private TrackingStatus status;
}
