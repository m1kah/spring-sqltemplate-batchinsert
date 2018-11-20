package com.m1kah.batch.source;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class SourceData {
    private String text1;
    private String text2;
    private String text3;
    private String text4;
    private BigDecimal amount1;
    private BigDecimal amount2;
}
