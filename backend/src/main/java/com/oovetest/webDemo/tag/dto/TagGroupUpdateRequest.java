package com.oovetest.webDemo.tag.dto;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TagGroupUpdateRequest {

    //廢棄；未來將僅保留TagGroup不允許自定義

    @NotBlank(message = "顯示名稱不能為空")
    @Size(max = 100, message = "顯示名稱長度不能超過100字元")
    private String displayName;
}
