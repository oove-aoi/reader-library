package com.oovetest.webDemo.tracking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oovetest.webDemo.tracking.entity.SeriesTracking;

public interface SeriesTrackingRepositoryTest extends JpaRepository<SeriesTracking, Long> {
    
}
