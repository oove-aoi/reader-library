package com.oovetest.webDemo.tracking.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.oovetest.webDemo.tracking.dto.SeriesTrackingResponse;
import com.oovetest.webDemo.tracking.entity.SeriesTracking;

@Component
public class SeriesTrackingMapper {
    public SeriesTrackingResponse toResponse(SeriesTracking seriesTracking) {
        return new SeriesTrackingResponse(
            seriesTracking.getId(),
            seriesTracking.getSeries().getId(),
            seriesTracking.getStatus(),
            seriesTracking.getSeries().getTitle(),
            seriesTracking.getSeries().getAuthor().getName(),
            seriesTracking.getCreatedAt()

        );
    }

    public List<SeriesTrackingResponse> toResponse(List<SeriesTracking> seriesTrackings) {
        return seriesTrackings.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
