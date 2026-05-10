package com.oovetest.webDemo.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;

public record SearchPageRequest (
    @PositiveOrZero //整數或為零
    int page,

    @Min(1)
    @Max(100)
    int size,

    String sortBy,
    String direction
) {
    public Pageable toPageable() {
        
        Sort.Direction sortDirection =
            direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        
        return PageRequest.of(page, size, sortDirection, sortBy);
    }
}
