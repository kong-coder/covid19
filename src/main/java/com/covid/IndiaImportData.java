package com.covid;

import com.excel.poi.annotation.ImportField;
import lombok.Data;

/**
 * @author mukong
 */
@Data
public class IndiaImportData {

    @ImportField(required = true)
    private Integer date;

    @ImportField(required = true)
    private String state;

    @ImportField
    private Integer number;

    @ImportField
    private Integer recovered;
}