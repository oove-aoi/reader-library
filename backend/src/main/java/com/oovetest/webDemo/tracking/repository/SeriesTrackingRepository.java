package com.oovetest.webDemo.tracking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oovetest.webDemo.tracking.entity.SeriesTracking;

public interface SeriesTrackingRepository extends JpaRepository<SeriesTracking, Long> {
    public boolean existsBySeriesId(Long seriesId); // 檢查是否已追蹤該系列
}

