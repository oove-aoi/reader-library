package com.oovetest.webDemo.tracking.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.oovetest.webDemo.tracking.dto.SeriesTrackingResponse;
import com.oovetest.webDemo.tracking.dto.SeriesTrackingRequest;
import com.oovetest.webDemo.tracking.service.SeriesTrackingService;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/api")
public class SeriesTrackingController {
    private final SeriesTrackingService seriesTrackingService;

    public SeriesTrackingController(SeriesTrackingService seriesTrackingService) {
        this.seriesTrackingService = seriesTrackingService;
    }

    @Operation(
        summary = "以追蹤ID查詢",
        tags = {"追蹤查詢"},
        description = "查詢追蹤資料，需提供追蹤ID"
    )
    @GetMapping("series-tracking/{id}")
    public SeriesTrackingResponse getSeriesTracking(Long id) {
        return seriesTrackingService.getSeriesTracking(id);
    }

    @Operation(
        summary = "創建追蹤",
        tags = {"追蹤管理"},
        description = "創建追蹤資料，需提供系列ID與追蹤狀態"
    )
    @PostMapping("series-tracking")
    public ResponseEntity<SeriesTrackingResponse> createSeriesTracking(SeriesTrackingRequest request) {
        try {
            SeriesTrackingResponse seriesTracking = seriesTrackingService.createSeriesTracking(request);
            return ResponseEntity.status(201).body(seriesTracking);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "更新追蹤",
        tags = {"追蹤管理"},
        description = "更新追蹤資料，需提供追蹤ID與更新資訊"
    )
    @PutMapping("series-tracking/{id}")
    public ResponseEntity<SeriesTrackingResponse> updateSeriesTracking(@PathVariable Long id, SeriesTrackingRequest request) {
        try {
            SeriesTrackingResponse seriesTracking = seriesTrackingService.updateSeriesTracking(id, request);
            return ResponseEntity.ok(seriesTracking);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "刪除追蹤",
        tags = {"追蹤管理"},
        description = "刪除追蹤資料，需提供追蹤ID"
    )
    @DeleteMapping("series-tracking/{id}")
    public ResponseEntity<?> deleteSeriesTracking(@PathVariable Long id) {
        try {
            seriesTrackingService.deleteSeriesTracking(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
