package com.m1kah.batch;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Accessors(chain = true)
@Data
public class BatchResponse {
    private int rows;
    private long duration;
    private BigDecimal msPerRow;
    private BigDecimal rowsPerSecond;
}
