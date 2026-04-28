package com.oovetest.webDemo.experience.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oovetest.webDemo.exception.ValidationException;
import com.oovetest.webDemo.experience.dto.ExperienceRequest;
import com.oovetest.webDemo.experience.dto.ExperienceResponse;
import com.oovetest.webDemo.experience.service.ExperienceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@Validated
@RestController
@RequestMapping("/api")
public class ExperienceController {
    private final ExperienceService experienceService;

    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @Operation(
        summary = "以書籍ID查詢心得",
        tags = {"心得查詢"},
        description = "查詢心得資料，需提供書籍ID"
    )
    @GetMapping("/books/{bookId}/experience")
    public ResponseEntity<ExperienceResponse> getExperienceByBookId(
        @PathVariable 
        @Positive(message = "作者ID必須為正整數")
        @Parameter(description = "書籍ID", example = "1", required = true)
        long bookId) {
            ExperienceResponse experienceResponse = experienceService.getExperienceByBookId(bookId);
            return ResponseEntity.ok(experienceResponse);
    }
    
    @Operation(
        summary = "以書籍ID新增心得",
        tags = {"心得管理"},
        description = """
        ### 📌 必填參數 
        - **bookId**

        ### 📌 必填欄位
        - **bookId** 需與路徑參數一致
        - **content** 心得內容
        - **rating** 評分，範圍1-10

        """
    )
    @PostMapping("/books/{bookId}/experience")
    public ResponseEntity<ExperienceResponse> createExperience(
        @PathVariable 
        @Positive(message = "書籍ID必須為正整數")
        @Parameter(description = "書籍ID", example = "1", required = true)
        long bookId,

        @RequestBody 
        @Valid
        @Parameter(description = "心得資料")
        ExperienceRequest experienceRequest) {

            if (experienceRequest.bookId() != null &&
                !experienceRequest.bookId().equals(bookId)) {
                    throw new ValidationException("RequestBody 中 bookId 與路徑不一致");
            }
            
            ExperienceResponse experienceResponse = experienceService.saveExperience(experienceRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(experienceResponse);
        }

    @Operation(
        summary = "以書籍ID更新心得",
        tags = {"心得管理"},
        description = "更新心得資料，需提供書籍ID"
    )
    @PutMapping("/books/{bookId}/experience")
    public ResponseEntity<ExperienceResponse> updateExperience(
        @PathVariable 
        @Positive(message = "書籍ID必須為正整數")
        @Parameter(description = "書籍ID", example = "1", required = true)
        long bookId, 

        @RequestBody 
        @Valid
        @Parameter(description = "心得資料")
        ExperienceRequest experienceRequest) {
            if (experienceRequest.bookId() != null && 
                !experienceRequest.bookId().equals(bookId)) {
                throw new ValidationException("ExperienceRequest 的 bookId" +
                                                    "與路徑不一致，請確認請求內容");
            }

            ExperienceResponse experienceResponse = experienceService.updateExperience(bookId, experienceRequest);
            return ResponseEntity.ok(experienceResponse);
    }

    @Operation(
        summary = "以書籍ID刪除心得",
        tags = {"心得管理"},
        description = "刪除心得資料，需提供書籍ID"
    )
    @DeleteMapping("/books/{bookId}/experience")
    public ResponseEntity<?> deleteExperience(@PathVariable 
        @Positive(message = "書籍ID必須為正整數")
        @Parameter(description = "書籍ID", example = "1", required = true)
        long bookId) {
            experienceService.deleteExperience(bookId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}