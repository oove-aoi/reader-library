package com.oovetest.webDemo.tracking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import com.oovetest.webDemo.tracking.dto.SeriesTrackingResponse;
import com.oovetest.webDemo.tracking.entity.SeriesTracking;

public interface SeriesTrackingRepository extends JpaRepository<SeriesTracking, Long> {
    public boolean existsBySeriesId(Long seriesId); // 檢查是否已追蹤該系列
    
    @Query("""
        SELECT s.title
        FROM SeriesTracking st
        JOIN st.series s
    """)
    List<String> findAllSeriesTitles();

    //暫不使用也暫不修改，等未來有優化想法再來改輸出DTO的方式
    @Query("""
    SELECT b.series.id, MAX(b.volume)
    FROM Book b
    GROUP BY b.series.id
    """)
    public List<SeriesTracking> findSeriesProgress();
}

