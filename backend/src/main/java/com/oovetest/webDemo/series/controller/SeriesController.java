package com.oovetest.webDemo.series.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import com.oovetest.webDemo.series.dto.SeriesRequest;
import com.oovetest.webDemo.series.dto.SeriesResponse;
import com.oovetest.webDemo.series.entity.SeriesStatus;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import com.oovetest.webDemo.series.service.SeriesService;
import com.oovetest.webDemo.util.PageResponse;

@Validated
@RestController
@RequestMapping("/api")
public class SeriesController {
    private final SeriesService seriesService;

    public SeriesController(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    @Operation(
        summary = "查詢系列狀態選項",
        tags = {"狀態碼查詢(書籍、系列、追蹤清單)"},
        description = "查詢系列狀態列表，無需提供參數"
    )   
    @GetMapping("/series-status-options")
    public List<Map<String, String>> getSeriesStatus() {
    return Arrays.stream(SeriesStatus.values())
        .map(s -> Map.of(
            "code", s.name(),
            "label", s.getLabel()
        ))
        .toList();
    }

    @Operation(
        summary = "以系列ID查詢",
        tags = {"系列查詢"},
        description = "查詢系列資料，需提供系列ID"
    )
    @GetMapping("/series/{seriesId}")
    public ResponseEntity<SeriesResponse> getSeriesById(
        @PathVariable 
        @Positive(message = "系列ID必須為正整數")
        @Parameter(description = "系列ID", example = "152", required = true) 
        long seriesId) {

            SeriesResponse series = seriesService.getSeriesById(seriesId);
            return ResponseEntity.ok(series);
            
    }

    @Operation(
        summary = "以系列名稱查詢",
        tags = {"系列查詢"},
        description = "查詢系列資料，需提供系列名稱"
    )
    @GetMapping("/series")
    public ResponseEntity<PageResponse<SeriesResponse>> getSeriesByKeyword(
        @RequestParam 
        @NotBlank(message = "系列名稱不能為空")
        @Size(max = 255, message = "系列名稱不能超過255個字")
        @Size(min = 1, message = "系列名稱至少要有1個字元")
        @Parameter(description = "系列名稱", example = "哈利波特系列") 
        String keyword,

        @RequestParam(defaultValue = "0") 
        @Parameter(description = "頁碼（從0開始）", example = "0", required = false)
        int page,
        
        @RequestParam(defaultValue = "5") 
        @Parameter(description = "每頁筆數", example = "5", required = false)
        int size,

        @RequestParam(defaultValue = "id") 
        @Parameter(description = "排序欄位", example = "id", required = false)
        String sortBy,

        @RequestParam(defaultValue = "asc") 
        @Parameter(description = "排序方向", example = "asc", required = false)
        String direction
    ) {
        Sort.Direction sortDirection = 
            direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        
        Pageable pageable = PageRequest.of(page, size, sortDirection, sortBy);
        PageResponse<SeriesResponse> pageResponse = seriesService.findByTitleContaining(keyword, pageable);
        return ResponseEntity.ok(pageResponse);
            
    }

    @Operation(
        summary = "創建系列",
        tags = {"系列管理"},
        description = """
        ### 📌 必填欄位
        - **title** 系列名稱
        - **author_id** 作者ID

        ### 📌 series status 說明
        series status 對應以下分類：(預設值為 **ONGOING**)

        - **ONGOING**：進行中
        - **COMPLETED**：已完結
        - **AXED**：斷尾

        ### 📌 查詢參考
        可透過 `/api/series-status-options` 取得完整列表
        """
    )
    @PostMapping("/series")
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
    @PutMapping("/series/{seriesId}")
    public ResponseEntity<SeriesResponse> updateSeries(
        @PathVariable 
        @Positive(message = "系列ID必須為正整數")
        @Parameter(description = "系列ID", example = "152", required = true)
        long seriesId,

        @RequestBody 
        @Valid
        SeriesRequest seriesRequest) {
            SeriesResponse series = seriesService.updateSeries(seriesId, seriesRequest);
            return ResponseEntity.ok(series);
    }

    @Operation(
        summary = "刪除系列",
        tags = {"系列管理"},
        description = "刪除系列資料，需提供系列ID"
    )
    @DeleteMapping("/series/{seriesId}")
    public ResponseEntity<?> deleteSeries(
        @PathVariable 
        @Positive(message = "系列ID必須為正整數")
        @Parameter(description = "系列ID", example = "152", required = true)
        long seriesId) {
            seriesService.deleteSeries(seriesId);
            return ResponseEntity.noContent().build();
            
    }
}