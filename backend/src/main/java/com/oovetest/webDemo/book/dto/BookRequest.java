package com.oovetest.webDemo.book.dto;

import lombok.Data;

import java.util.Set;

import com.oovetest.webDemo.book.entity.BookStatus;

import java.time.LocalDateTime;
import java.util.HashSet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;


public record BookRequest (
    @NotBlank(message = "書名不能為空")
    @Size(max = 100, message = "書名長度不能超過100字元")
    @Schema(example = "哈利波特與魔法石")
    String title,

    @NotBlank(message = "作者名稱不能為空")
    @Size(max = 100, message = "作者名稱長度不能超過100字元")
    @Schema(example = "J.K. Rowling")
    String authorName,

    @NotNull(message = "書籍狀態不能為空")
    @Schema(example = "BUYED")
    BookStatus status,

    @Schema(
        description = "ISBN，可為 null",
        example = "978-0134685991",
        nullable = true,
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    String isbn,

    LocalDateTime buyTime,

    @NotBlank(message = "系列名稱不可為空")
    @Size(max = 100, message = "系列名稱長度不能超過100字元")
    @Schema(example = "哈利波特系列")
    String seriesName,
    
    @Positive(message = "卷數必須為正整數")
    @Schema(example = "1")
    Integer volume,

    // 用 Set<String> 代替 Tag 實體
    Set<String> tagNames
) {}

