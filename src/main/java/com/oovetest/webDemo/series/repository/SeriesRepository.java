package com.oovetest.webDemo.series.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.oovetest.webDemo.series.model.Series;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    public Optional<Series> findByTitle(String title);
    public boolean existsByTitle(String title);
    
} 