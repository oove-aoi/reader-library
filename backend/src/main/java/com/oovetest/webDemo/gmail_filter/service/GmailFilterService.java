package com.oovetest.webDemo.gmail_filter.service;

import java.util.stream.Collectors;
import java.util.List;

import org.springframework.stereotype.Service;

import com.oovetest.webDemo.gmail_filter.dto.GmailFilterResponse;
import com.oovetest.webDemo.tracking.repository.SeriesTrackingRepository;

@Service
public class GmailFilterService {
    private SeriesTrackingRepository seriesTrackingRepository;

    public GmailFilterService(SeriesTrackingRepository seriesTrackingRepository) {
        this.seriesTrackingRepository = seriesTrackingRepository;
    }

    public GmailFilterResponse getGmailFilter() {
        // 從seriesTrackingRepository中獲取追蹤列表，然後生成篩選器字串
        List<String> titles = seriesTrackingRepository.findAllSeriesTitles();

        String result = titles.stream()
                .filter(s -> s != null && !s.isBlank())
                .collect(Collectors.joining(" OR ", "(", ")"));
        
        return new GmailFilterResponse(result);
    }

    
}
