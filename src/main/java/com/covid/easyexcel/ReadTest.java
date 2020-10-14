package com.covid.easyexcel;

import com.alibaba.excel.EasyExcel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 读的常见写法
 *
 * @author Jiaju Zhuang
 */
public class ReadTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadTest.class);

    public static void main(String[] args) {
        String fileName = "/Users/yanhom/Desktop/world-covid-data.xlsx";
        // 这里 只要，然后读取第一个sheet 同步读取会自动finish
        EasyExcel.read(fileName, new WorldConfirmedDataListener()).sheet().doRead();
    }
}