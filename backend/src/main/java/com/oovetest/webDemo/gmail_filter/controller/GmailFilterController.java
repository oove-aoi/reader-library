package com.oovetest.webDemo.gmail_filter.controller;

import org.springframework.web.bind.annotation.*;

import com.oovetest.webDemo.gmail_filter.dto.GmailFilterResponse;
import com.oovetest.webDemo.gmail_filter.service.GmailFilterService;
import com.oovetest.webDemo.series.service.SeriesService;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@Validated
@RestController
@RequestMapping("/api")
public class GmailFilterController {
    private final GmailFilterService gmailFilterService; ;

    public GmailFilterController(GmailFilterService gmailFilterService) {
        this.gmailFilterService = gmailFilterService;
    }

    @Operation(
        summary = "Gmail 篩選器字串生成",
        tags = {"Gmail 篩選器生成"},
        description = "獲得追蹤列表的gmail篩選器字串，直接貼上即可"
    )
    @GetMapping("/gmail-filter")
    public ResponseEntity<GmailFilterResponse> getGmailFilter() {
        GmailFilterResponse response = gmailFilterService.getGmailFilter();
        return ResponseEntity.ok(response);
    }
}
