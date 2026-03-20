package com.oovetest.webDemo.series.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import com.oovetest.webDemo.series.dto.SeriesRequest;
import com.oovetest.webDemo.series.dto.SeriesResponse;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.val;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import com.oovetest.webDemo.series.service.SeriesService;

@Validated
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
    public ResponseEntity<SeriesResponse> getSeriesById(
        @PathVariable 
        @Positive(message = "系列ID必須為正整數")
        @Parameter(description = "系列ID", example = "152") 
        Long id) {

            SeriesResponse series = seriesService.getSeriesById(id);
            return ResponseEntity.ok(series);
            
    }

    @Operation(
        summary = "以系列名稱查詢",
        tags = {"系列查詢"},
        description = "查詢系列資料，需提供系列名稱"
    )
    @GetMapping("series/name/{name}")
    public ResponseEntity<SeriesResponse> getSeriesByName(
        @PathVariable 
        @NotBlank(message = "系列名稱不能為空")
        @Size(max = 255, message = "系列名稱不能超過255個字")
        @Size(min = 1, message = "系列名稱至少要有1個字元")
        @Parameter(description = "系列名稱", example = "哈利波特系列") 
        String name) {
           
            SeriesResponse series = seriesService.getSeriesByName(name);
            return ResponseEntity.ok(series);
            
    }

    @Operation(
        summary = "創建系列",
        tags = {"系列管理"},
        description = "創建系列資料，需提供系列名稱"
    )
    @PostMapping("series")
    public ResponseEntity<SeriesResponse> createSeries(
        @RequestBody 
        @Valid
        SeriesRequest seriesRequest) {
        
            SeriesResponse series = seriesService.createSeries(seriesRequest);
            return ResponseEntity.status(201).body(series);
           
    }


    @Operation(
        summary = "更新系列",
        tags = {"系列管理"},
        description = "更新系列資料，需提供系列ID與更新資訊"
    )
    @PutMapping("series/{id}")
    public ResponseEntity<SeriesResponse> updateSeries(
        @PathVariable 
        @Positive(message = "系列ID必須為正整數")
        @Parameter(description = "系列ID", example = "152")
        Long id,

        @RequestBody 
        @Valid
        SeriesRequest seriesRequest) {
            SeriesResponse series = seriesService.updateSeries(id, seriesRequest);
            return ResponseEntity.ok(series);
    }

    @Operation(
        summary = "刪除系列",
        tags = {"系列管理"},
        description = "刪除系列資料，需提供系列ID"
    )
    @DeleteMapping("series/{id}")
    public ResponseEntity<?> deleteSeries(
        @PathVariable 
        @Positive(message = "系列ID必須為正整數")
        @Parameter(description = "系列ID", example = "152")
        Long id) {
            seriesService.deleteSeries(id);
            return ResponseEntity.noContent().build();
            
    }
}