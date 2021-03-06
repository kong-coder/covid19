package com.covid;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.covid.easyexcel.WorldConfirmedDataListener;
import com.google.common.collect.Lists;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author mukong
 */
@Service
public class ChinaGdpService {

    private static final Logger LOG = LoggerFactory.getLogger(ChinaGdpService.class);

    private static List<ImportData> importDataList;

    private static final List<String> filter = Lists.newArrayList("International");


    public static void main(String[] args) {
        importDataList = new ArrayList<>();
        initData();
        CountryUtil.initCountry();
        export();
    }

    public static void initData() {

        String fileName = "/Users/mukong/Desktop/covid/china-gdp-source.xlsx";

        // 这里 只要，然后读取第一个sheet 同步读取会自动finish
        EasyExcel.read(fileName, new WorldConfirmedDataListener()).sheet().doRead();
    }

    private static List<String> getHeader() {
        List<String> headers = new ArrayList<>();
        headers.add("province");
        headers.add("flag");
        int year = 2005;
        do {
            for (int i=1;i <=4;i++) {
                headers.add(year + "年Q" + i);
            }
            year++;
        } while (year <= 2020);
        return headers;
    }

    public static void export(){

        // 文件输出位置
        String outPath = "/Users/mukong/Desktop/covid/china-gdp-target.xlsx";

        try {
            // 所有行的集合
            List<List<Object>> list = new ArrayList<>();
            WorldConfirmedDataListener.list.forEach(x -> {

                List<Object> row = new ArrayList<>();
                row.add(x.get(0));
                row.add("");
                for (int i = 62; i >=1; i--) {
                    row.add(x.get(i));
                }
                list.add(row);
            });

            ExcelWriter excelWriter = EasyExcelFactory.getWriter(new FileOutputStream(outPath));
            // 表单
            Sheet sheet = new Sheet(1,0);
            sheet.setSheetName("第一个Sheet");
            // 创建一个表格
            Table table = new Table(1);
            // 动态添加 表头 headList --> 所有表头行集合
            List<List<String>> headList = new ArrayList<>();
            // 第 n 行 的表头

            List<String> headers = getHeader();
            headers.forEach(x -> {
                List<String> headTitle = new ArrayList<>();
                headTitle.add(x);
                headList.add(headTitle);
            });
            table.setHead(headList);

            excelWriter.write1(list,sheet,table);
            // 记得 释放资源
            excelWriter.finish();
            System.out.println("ok");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}