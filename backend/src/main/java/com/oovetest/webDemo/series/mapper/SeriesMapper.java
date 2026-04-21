package com.oovetest.webDemo.series.mapper;

import org.springframework.stereotype.Component;

import com.oovetest.webDemo.series.dto.SeriesResponse;
import com.oovetest.webDemo.series.entity.Series;

@Component
public class SeriesMapper {
    public SeriesResponse toResponse(Series series, Long bookCount, Long authorId) {
        return new SeriesResponse(
            series.getId(),
            series.getTitle(),
            bookCount,
            series.getStatus(),
            authorId
        );
        
    }
}
