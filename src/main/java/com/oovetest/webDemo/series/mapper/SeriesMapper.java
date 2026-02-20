package com.oovetest.webDemo.series.mapper;

import org.springframework.stereotype.Component;

import com.oovetest.webDemo.series.dto.SeriesResponse;
import com.oovetest.webDemo.series.model.Series;

@Component
public class SeriesMapper {
    public SeriesResponse toResponse(Series series) {
        SeriesResponse response = new SeriesResponse();
        
        response.setId(series.getId());
        response.setTitle(series.getTitle());
        response.setStatus(series.getStatus());
        response.setBookCount(series.getBooks().size());
        response.setAuthorId(series.getAuthor().getId());

        return response;
    }
}
