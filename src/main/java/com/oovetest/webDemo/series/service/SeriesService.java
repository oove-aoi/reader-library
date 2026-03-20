package com.oovetest.webDemo.series.service;

import org.springframework.stereotype.Service;

import com.oovetest.webDemo.author.model.Author;
import com.oovetest.webDemo.series.dto.SeriesRequest;
import com.oovetest.webDemo.series.dto.SeriesResponse;
import com.oovetest.webDemo.series.model.Series;
import com.oovetest.webDemo.series.repository.SeriesRepository;
import com.oovetest.webDemo.series.mapper.SeriesMapper;
import com.oovetest.webDemo.author.service.AuthorService;
import com.oovetest.webDemo.book.repository.BookRepository;
import com.oovetest.webDemo.exception.NotFoundException;
import com.oovetest.webDemo.exception.ValidationException;


@Service
public class SeriesService {
    private final SeriesRepository seriesRepository;
    private final SeriesMapper seriesMapper;
    private final AuthorService authorService;
    private final BookRepository bookRepository;

    public SeriesService(SeriesRepository seriesRepository, 
        SeriesMapper seriesMapper, 
        AuthorService authorService, 
        BookRepository bookRepository) {
            this.seriesRepository = seriesRepository;
            this.seriesMapper = seriesMapper;
            this.authorService = authorService;
            this.bookRepository = bookRepository;
    }

    public Series getEntityById(Long id) {
        return seriesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("查無此系列ID"));
    }

    public Series getEntityByTitle(String name) {
        return seriesRepository.findByTitle(name)
                .orElseThrow(() -> new NotFoundException("查無此系列名稱"));
    }

    public SeriesResponse getSeriesById(Long id) {
        Series series = getEntityById(id);
        
        return seriesMapper.toResponse(
            series, 
            bookRepository.countBySeriesId(series.getId()), 
            series.getAuthor().getId());
    }

    public SeriesResponse getSeriesByName(String name) {
        Series series = getEntityByTitle(name);
        
        return seriesMapper.toResponse(
            series, 
            bookRepository.countBySeriesId(series.getId()), 
            series.getAuthor().getId());
    }

    public SeriesResponse createSeries(SeriesRequest seriesRequest) {
        Author author = authorService.getEntityById(seriesRequest.getAuthorId());
        if (seriesRepository.existsByTitle(seriesRequest.getTitle())) {
            throw new ValidationException("已存在相同的系列名稱");
        }

        Series series = new Series();
        
        series.setTitle(seriesRequest.getTitle());
        series.setStatus(seriesRequest.getStatus());
        series.setAuthor(author);

        Series saved = seriesRepository.save(series);

        return seriesMapper.toResponse(saved, 0L, author.getId());
    }

    public SeriesResponse updateSeries(Long id, SeriesRequest seriesRequest) {
        Series series = getEntityById(id);
        Author author = authorService.getEntityById(seriesRequest.getAuthorId());
        long count = bookRepository.countBySeriesId(series.getId());

        series.setTitle(seriesRequest.getTitle());
        series.setStatus(seriesRequest.getStatus());
        series.setAuthor(author);

        return seriesMapper.toResponse(seriesRepository.save(series), count, author.getId());
    }

    public void deleteSeries(Long id) {
        Series series = getEntityById(id);
        seriesRepository.delete(series);
    }
}
