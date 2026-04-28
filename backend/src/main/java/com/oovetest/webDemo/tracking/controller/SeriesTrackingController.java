package com.oovetest.webDemo.tracking.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import com.oovetest.webDemo.tracking.dto.SeriesTrackingResponse;
import com.oovetest.webDemo.tracking.entity.TrackingStatus;
import com.oovetest.webDemo.series.entity.SeriesStatus;
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
        summary = "查詢追蹤狀態選項",
        tags = {"狀態碼查詢(書籍、系列、追蹤清單)"},
        description = "查詢追蹤狀態列表，無需提供參數"
    )   
    @GetMapping("/tracking-status-options")
    public List<Map<String, String>> getTrackingStatus() {
    return Arrays.stream(TrackingStatus.values())
        .map(s -> Map.of(
            "code", s.name(),
            "label", s.getLabel()
        ))
        .toList();
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
        long id) {
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
        description = """
        ### 📌 必填欄位
        - **seriesId** 系列ID
        - **status** 追蹤狀態 

        ### 📌 status 說明
        status 對應以下分類：

        - **TRACKING**：追蹤中
        - **COMPLETED**：已完結
        - **DROPPED**：取消追蹤

        ### 📌 查詢參考
        可透過 `/api/tracking-status-options` 取得完整列表
        """
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
        long id, 

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
        long id) {
            seriesTrackingService.deleteSeriesTracking(id);
            return ResponseEntity.noContent().build();
    }

    //todo:後面可能新增可查看目前各系列買到哪一集、下一本該買哪一集的方法


}
