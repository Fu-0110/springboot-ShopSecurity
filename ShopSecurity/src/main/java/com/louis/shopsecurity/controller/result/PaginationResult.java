package com.louis.shopsecurity.controller.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResult<T> extends DataResult<T> {
    private long total;
}