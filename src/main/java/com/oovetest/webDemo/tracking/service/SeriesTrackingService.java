package com.oovetest.webDemo.tracking.service;

import org.springframework.stereotype.Service;
import com.oovetest.webDemo.tracking.repository.SeriesTrackingRepository;
import com.oovetest.webDemo.series.service.SeriesService;
import com.oovetest.webDemo.tracking.dto.SeriesTrackingRequest;
import com.oovetest.webDemo.tracking.dto.SeriesTrackingResponse;
import com.oovetest.webDemo.tracking.mapper.SeriesTrackingMapper;
import com.oovetest.webDemo.tracking.model.SeriesTracking;
import com.oovetest.webDemo.series.model.Series;
import java.time.LocalDateTime;

@Service
public class SeriesTrackingService {

    private final SeriesService seriesService;
    private final SeriesTrackingRepository seriesTrackingRepository;
    private final SeriesTrackingMapper seriesTrackingMapper;

    public SeriesTrackingService(SeriesTrackingRepository seriesTrackingRepository, SeriesTrackingMapper seriesTrackingMapper, SeriesService seriesService) {
        this.seriesService = seriesService;
        this.seriesTrackingRepository = seriesTrackingRepository;
        this.seriesTrackingMapper = seriesTrackingMapper;
    }

    public SeriesTracking getEntityById(Long id) {
        return seriesTrackingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SeriesTracking not found"));
    }


    public SeriesTrackingResponse createSeriesTracking(SeriesTrackingRequest request) {
        SeriesTracking seriesTracking = new SeriesTracking();
        Series series = seriesService.getEntityById(request.getSeriesId());

        seriesTracking.setSeries(series);
        seriesTracking.setStatus(request.getStatus());
        seriesTracking.setCreatedAt(LocalDateTime.now());

        return seriesTrackingMapper.toResponse(seriesTrackingRepository.save(seriesTracking));

    }

    public SeriesTrackingResponse getSeriesTracking(Long id) {
        SeriesTracking seriesTracking = getEntityById(id);

        return seriesTrackingMapper.toResponse(seriesTracking);
    }

    public SeriesTrackingResponse updateSeriesTracking(Long id, SeriesTrackingRequest request) {
        SeriesTracking seriesTracking = getEntityById(id);
        Series series = seriesService.getEntityById(request.getSeriesId());

        seriesTracking.setSeries(series);
        seriesTracking.setStatus(request.getStatus());

        return seriesTrackingMapper.toResponse(seriesTrackingRepository.save(seriesTracking));
    }

    public void deleteSeriesTracking(Long id) {
        SeriesTracking seriesTracking = getEntityById(id);
        seriesTrackingRepository.delete(seriesTracking);
    }
}
