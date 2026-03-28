package com.oovetest.webDemo.series.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oovetest.webDemo.series.entity.Series;

public interface SeriesRepositoryTest extends JpaRepository<Series, Long> {
    
    
} 