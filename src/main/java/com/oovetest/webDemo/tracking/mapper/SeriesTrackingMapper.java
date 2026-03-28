package com.oovetest.webDemo.tracking.mapper;

import org.springframework.stereotype.Component;

import com.oovetest.webDemo.tracking.dto.SeriesTrackingResponse;
import com.oovetest.webDemo.tracking.entity.SeriesTracking;

@Component
public class SeriesTrackingMapper {
    public SeriesTrackingResponse toResponse(SeriesTracking seriesTracking) {
        SeriesTrackingResponse response = new SeriesTrackingResponse();
        
        response.setSeriesId(seriesTracking.getSeries().getId());
        response.setStatus(seriesTracking.getStatus());
        response.setCreatedAt(seriesTracking.getCreatedAt());
        response.setSeriesTitle(seriesTracking.getSeries().getTitle());
        response.setSeriesAuthor(seriesTracking.getSeries().getAuthor().getName());
        
        return response;
    }
}
