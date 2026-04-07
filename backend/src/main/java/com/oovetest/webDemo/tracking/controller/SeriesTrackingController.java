package com.oovetest.webDemo.tracking.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import com.oovetest.webDemo.tracking.dto.SeriesTrackingResponse;
import com.oovetest.webDemo.tracking.dto.SeriesTrackingRequest;
import com.oovetest.webDemo.tracking.service.SeriesTrackingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Positive;
import jakarta.validation.Valid;

@Validated
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
    @GetMapping("/series-tracking/{id}")
    public ResponseEntity<SeriesTrackingResponse> getSeriesTracking(
        @PathVariable 
        @Positive(message = "追蹤ID必須為正數")
        @Parameter(description = "追蹤ID", example = "1", required = true)
        Long id) {
            return ResponseEntity.ok(seriesTrackingService.getSeriesTrackingById(id)); 
    }


    @Operation(
        summary = "查詢所有追蹤",
        tags = {"追蹤查詢"},
        description = "查詢所有追蹤資料"
    )
    @GetMapping("/series-tracking")
    public ResponseEntity<List<SeriesTrackingResponse>> getAllSeriesTracking() {
            return ResponseEntity.ok(seriesTrackingService.getAllSeriesTracking()); 
    }


    @Operation(
        summary = "創建追蹤",
        tags = {"追蹤管理"},
        description = "創建追蹤資料，需提供系列ID與追蹤狀態"
    )
    @PostMapping("/series-tracking")
    public ResponseEntity<SeriesTrackingResponse> createSeriesTracking(
        @RequestBody
        @Valid
        @Parameter(description = "追蹤請求", required = true)
        SeriesTrackingRequest request) {
            SeriesTrackingResponse seriesTracking = seriesTrackingService.createSeriesTracking(request);
            return ResponseEntity.status(201).body(seriesTracking);   
    }

    @Operation(
        summary = "更新追蹤",
        tags = {"追蹤管理"},
        description = "更新追蹤資料，需提供追蹤ID與更新資訊"
    )
    @PutMapping("series-tracking/{id}")
    public ResponseEntity<SeriesTrackingResponse> updateSeriesTracking(
        @PathVariable 
        @Positive(message = "追蹤ID必須為正數")
        @Parameter(description = "追蹤ID", example = "1",required = true)
        Long id, 

        @RequestBody
        @Valid
        @Parameter(description = "追蹤更新請求", required = true)
        SeriesTrackingRequest request) {
            SeriesTrackingResponse seriesTracking = seriesTrackingService.updateSeriesTracking(id, request);
            return ResponseEntity.ok(seriesTracking);
    }

    @Operation(
        summary = "刪除追蹤",
        tags = {"追蹤管理"},
        description = "刪除追蹤資料，需提供追蹤ID"
    )
    @DeleteMapping("series-tracking/{id}")
    public ResponseEntity<?> deleteSeriesTracking(
        @PathVariable 
        @Positive(message = "追蹤ID必須為正數")
        @Parameter(description = "追蹤ID", example = "1", required = true)
        Long id) {
            seriesTrackingService.deleteSeriesTracking(id);
            return ResponseEntity.noContent().build();
            
    }
}
