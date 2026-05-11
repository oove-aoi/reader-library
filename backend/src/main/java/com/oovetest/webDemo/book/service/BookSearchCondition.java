package com.oovetest.webDemo.book.service;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Valid
public record BookSearchCondition (
    @Max(255)
    @Positive
    @Schema(example = "5")
    @Parameter(description = "作者ID(可選)", example = "5", required = false)
    Long authorId,

    @Max(255)
    @Positive
    @Schema(example = "")
    @Parameter(description = "tag ID(可選)", example = "null", required = false)
    Long tagId,

    @Size(max = 255, message = "關鍵字不得超過100個字元")
    @Schema(example = "")
    @Parameter(description = "關鍵字(可選)", example = "null", required = false)
    String keyword,


    @Size(max = 255, message = "標籤名稱長度不能超過255字元")
    @Schema(example = "")
    @Parameter(description = "tag 名稱(可選)", example = "null", required = false)
    String tagName,

    @Size(max = 255, message = "作者名稱長度不能超過255字元")
    @Schema(example = "")
    @Parameter(description = "作者名稱(可選)", example = "null", required = false)
    String authorName,

    @Size(max = 255, message = "書籍名稱長度不能超過255字元")
    @Schema(example = "")
    @Parameter(description = "書籍名稱(可選)", example = "null", required = false)
    String bookTitle
) {}
