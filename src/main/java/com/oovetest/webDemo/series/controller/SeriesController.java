package com.oovetest.webDemo.series.controller;

import org.springframework.web.bind.annotation.*;

import com.oovetest.webDemo.series.service.SeriesService;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;

import com.oovetest.webDemo.series.dto.SeriesRequest;
import com.oovetest.webDemo.series.dto.SeriesResponse;

import jakarta.validation.constraints.NotNull;


@RestController
@RequestMapping("/api")
public class SeriesController {
    private final SeriesService seriesService;

    public SeriesController(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    @Operation(
        summary = "以系列ID查詢",
        tags = {"系列查詢"},
        description = "查詢系列資料，需提供系列ID"
    )
    @GetMapping("series/{id}")
    public ResponseEntity<SeriesResponse> getSeriesById(@PathVariable Long id) {
        try {
            SeriesResponse series = seriesService.getSeriesById(id);
            return ResponseEntity.ok(series);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "創建系列",
        tags = {"系列管理"},
        description = "創建系列資料，需提供系列名稱"
    )
    @PostMapping("series")
    public ResponseEntity<SeriesResponse> createSeries(@RequestBody SeriesRequest seriesRequest) {
        try {    
            SeriesResponse series = seriesService.createSeries(seriesRequest);
            return ResponseEntity.status(201).body(series);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @Operation(
        summary = "更新系列",
        tags = {"系列管理"},
        description = "更新系列資料，需提供系列ID與更新資訊"
    )
    @PutMapping("series/{id}")
    public ResponseEntity<SeriesResponse> updateSeries(@PathVariable Long id, @RequestBody SeriesRequest seriesRequest) {
        try {
            SeriesResponse series = seriesService.updateSeries(id, seriesRequest);
            return ResponseEntity.ok(series);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "刪除系列",
        tags = {"系列管理"},
        description = "刪除系列資料，需提供系列ID"
    )
    @DeleteMapping("series/{id}")
    public ResponseEntity<?> deleteSeries(@PathVariable Long id) {
        try {
            seriesService.deleteSeries(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}