package com.covid;

import com.excel.poi.annotation.ImportField;
import lombok.Data;

/**
 * @author mukong
 */
@Data
public class ImportData {

    @ImportField(required = true)
    private String date;

    @ImportField(required = true)
    private String state;

    @ImportField
    private Integer number;
}