package com.oovetest.webDemo.tracking.service;

import org.springframework.stereotype.Service;
import com.oovetest.webDemo.tracking.repository.SeriesTrackingRepository;

import jakarta.validation.constraints.NotNull;

import com.oovetest.webDemo.series.entity.Series;
import com.oovetest.webDemo.series.service.SeriesService;
import com.oovetest.webDemo.tracking.dto.SeriesTrackingRequest;
import com.oovetest.webDemo.tracking.dto.SeriesTrackingResponse;
import com.oovetest.webDemo.tracking.entity.SeriesTracking;
import com.oovetest.webDemo.tracking.mapper.SeriesTrackingMapper;
import com.oovetest.webDemo.book.repository.BookRepository;
import com.oovetest.webDemo.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SeriesTrackingService {

    private final SeriesService seriesService;
    private final SeriesTrackingRepository seriesTrackingRepository;
    private final SeriesTrackingMapper seriesTrackingMapper;
    private final BookRepository bookRepository;

    public SeriesTrackingService(SeriesTrackingRepository seriesTrackingRepository,
                                SeriesTrackingMapper seriesTrackingMapper,
                                SeriesService seriesService,
                                BookRepository bookRepository) {
            this.seriesService = seriesService;
            this.seriesTrackingRepository = seriesTrackingRepository;
            this.seriesTrackingMapper = seriesTrackingMapper;
            this.bookRepository = bookRepository;
        }

    public SeriesTracking getEntityById(Long id) {
        return seriesTrackingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("查無此系列追蹤ID"));
    }

    public int getOwnedVolume(Long seriesId) {
        return bookRepository.findMaxVolumeBySeriesId(seriesId) == null ? 0 : bookRepository.findMaxVolumeBySeriesId(seriesId);
    }

    public int getNextVolume(Long seriesId) {
        return getOwnedVolume(seriesId) == 0 ? 1 : getOwnedVolume(seriesId) + 1;
    }

    public List<SeriesTrackingResponse> getAllSeriesTracking() {
    return seriesTrackingRepository.findAll()
        .stream()
        .map(t -> {
            int owned = getOwnedVolume(t.getSeries().getId()); 
            return seriesTrackingMapper.toResponse(t, owned, owned + 1);
        })
        .toList();
    }

    public SeriesTrackingResponse getSeriesTrackingById(Long id) {
        SeriesTracking seriesTracking = getEntityById(id);

        int ownedVolume = getOwnedVolume(seriesTracking.getSeries().getId());
        int nextVolume = getNextVolume(seriesTracking.getSeries().getId());

        return seriesTrackingMapper.toResponse(seriesTracking, ownedVolume, nextVolume);
    }

    public SeriesTrackingResponse createSeriesTracking(SeriesTrackingRequest request) {
        SeriesTracking seriesTracking = new SeriesTracking();
        Series series = seriesService.getEntityById(request.seriesId());

        seriesTracking.setSeries(series);
        seriesTracking.setStatus(request.status());
        seriesTracking.setCreatedAt(LocalDateTime.now());

        return seriesTrackingMapper.toResponse(seriesTrackingRepository.save(seriesTracking), getOwnedVolume(series.getId()), getNextVolume(series.getId()));

    }

    public SeriesTrackingResponse updateSeriesTracking(Long id, SeriesTrackingRequest request) {
        SeriesTracking seriesTracking = getEntityById(id);
        Series series = seriesService.getEntityById(request.seriesId());

        seriesTracking.setSeries(series);
        seriesTracking.setStatus(request.status());

        return seriesTrackingMapper.toResponse(seriesTrackingRepository.save(seriesTracking), getOwnedVolume(series.getId()), getNextVolume(series.getId()));
    }

    public void deleteSeriesTracking(Long id) {
        SeriesTracking seriesTracking = getEntityById(id);
        seriesTrackingRepository.delete(seriesTracking);
    }
}
