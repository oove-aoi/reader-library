package com.oovetest.webDemo.series.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.oovetest.webDemo.series.entity.Series;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    public Optional<Series> findByTitle(String title);
    public Page<Series> findByTitleContaining(String keyword, Pageable pageable);
    
    public boolean existsByTitle(String title);
    
} 