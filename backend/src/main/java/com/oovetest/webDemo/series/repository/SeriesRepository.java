package com.oovetest.webDemo.series.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.oovetest.webDemo.series.entity.Series;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    public Optional<Series> findByTitle(String title);
    
    public boolean existsByTitle(String title);
    
} 