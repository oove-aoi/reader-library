package com.oovetest.webDemo.series.dto;

import com.oovetest.webDemo.series.entity.SeriesStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class SeriesRequest {
    @NotBlank(message = "系列名稱不能為空")
    @Size(max = 255, message = "系列名稱不能超過255個字元")
    @Size(min = 1, message = "系列名稱至少要有1個字元")
    @Schema(example = "哈利波特")
    private String title;

    @NotNull(message = "系列狀態不能為空")
    @Schema(example = "COMPLETED")
    private SeriesStatus status;

    @NotNull(message = "作者ID不能為空")
    @Positive(message = "作者ID必須為正整數")
    @Schema(example = "1")
    private Long authorId;
}
