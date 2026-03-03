package com.oovetest.webDemo.series.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.oovetest.webDemo.series.model.Series;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    public Series findByTitle(String title);
    
} 