package com.oovetest.webDemo.series.service;

import org.springframework.stereotype.Service;

import com.oovetest.webDemo.author.model.Author;
import com.oovetest.webDemo.series.dto.SeriesRequest;
import com.oovetest.webDemo.series.dto.SeriesResponse;
import com.oovetest.webDemo.series.model.Series;
import com.oovetest.webDemo.series.repository.SeriesRepository;
import com.oovetest.webDemo.series.mapper.SeriesMapper;
import com.oovetest.webDemo.author.service.AuthorService;

@Service
public class SeriesService {
    private final SeriesRepository seriesRepository;
    private final SeriesMapper seriesMapper;
    private final AuthorService authorService;

    public SeriesService(SeriesRepository seriesRepository, SeriesMapper seriesMapper, AuthorService authorService) {
        this.seriesRepository = seriesRepository;
        this.seriesMapper = seriesMapper;
        this.authorService = authorService;
    }

    public Series getEntityById(Long id) {
        return seriesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Series not found"));
    }

    public Series getEntityByTitle(String name) {
        return seriesRepository.findByTitle(name);
    }

    public SeriesResponse getSeriesById(Long id) {
            Series series = getEntityById(id);
        
        return seriesMapper.toResponse(series);
    }

    public SeriesResponse createSeries(SeriesRequest seriesRequest) {
        Series series = new Series();
        Author author = authorService.getEntityById(seriesRequest.getAuthorId());
        
        series.setTitle(seriesRequest.getTitle());
        series.setStatus(seriesRequest.getStatus());
        series.setAuthor(author);

        return seriesMapper.toResponse(seriesRepository.save(series));
    }

    public SeriesResponse updateSeries(Long id, SeriesRequest seriesRequest) {
        Series series = getEntityById(id);
        Author author = authorService.getEntityById(seriesRequest.getAuthorId());

        series.setTitle(seriesRequest.getTitle());
        series.setStatus(seriesRequest.getStatus());
        series.setAuthor(author);

        return seriesMapper.toResponse(seriesRepository.save(series));
    }

    public void deleteSeries(Long id) {
        Series series = getEntityById(id);
        seriesRepository.delete(series);
    }
}
