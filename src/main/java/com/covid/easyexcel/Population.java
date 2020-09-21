package com.covid.easyexcel;

import com.excel.poi.annotation.ImportField;
import lombok.Data;

/**
 * @author mukong
 */
@Data
public class Population {

    @ImportField(required = true)
    private String entity;

    @ImportField(required = true)
    private Integer year;

    @ImportField
    private String projection;

    @ImportField
    private String estimates;
}