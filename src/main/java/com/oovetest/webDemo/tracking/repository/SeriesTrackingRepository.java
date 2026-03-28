package com.oovetest.webDemo.tracking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oovetest.webDemo.tracking.entity.SeriesTracking;

public interface SeriesTrackingRepository extends JpaRepository<SeriesTracking, Long> {
    
}
