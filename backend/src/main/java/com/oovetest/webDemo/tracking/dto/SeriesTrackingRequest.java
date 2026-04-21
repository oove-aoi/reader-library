package com.oovetest.webDemo.tracking.dto;

import jakarta.validation.constraints.Positive;

import com.oovetest.webDemo.tracking.entity.TrackingStatus;

import jakarta.validation.constraints.NotNull;


public record SeriesTrackingRequest (
    @Positive(message = "系列ID必須為正數")
    Long seriesId,

    @NotNull(message = "書籍狀態不能為空")
    TrackingStatus status
){}
