package com.oovetest.webDemo.gmail_filter.service;

import java.util.stream.Collectors;
import java.util.List;

import org.springframework.stereotype.Service;

import com.oovetest.webDemo.gmail_filter.dto.GmailFilterResponse;
import com.oovetest.webDemo.tracking.entity.SeriesTracking;
import com.oovetest.webDemo.tracking.repository.SeriesTrackingRepository;
import com.oovetest.webDemo.series.entity.Series;

@Service
public class GmailFilterService {
    private SeriesTrackingRepository seriesTrackingRepository;

    public GmailFilterService(SeriesTrackingRepository seriesTrackingRepository) {
        this.seriesTrackingRepository = seriesTrackingRepository;
    }

    public GmailFilterResponse getGmailFilter() {
        GmailFilterResponse response = new GmailFilterResponse();
        // 從seriesTrackingRepository中獲取追蹤列表，然後生成篩選器字串
        List<String> titles = seriesTrackingRepository.findAll().stream()
                .map(SeriesTracking::getSeries)
                .map(Series::getTitle)
                .collect(Collectors.toList());
        String result = titles.stream()
                .filter(s -> s != null && !s.isBlank())
                .collect(Collectors.joining(" OR ", "(", ")"));
        
        response.setGmailFilterString(result);
        return response;
    }

    
}
