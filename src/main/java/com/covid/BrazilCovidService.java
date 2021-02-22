package com.covid;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.covid.easyexcel.IndiaDataListener;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

/**
 * @author mukong
 */
@Service
public class BrazilCovidService {

    private static final Logger LOG = LoggerFactory.getLogger(BrazilCovidService.class);

    public static List<ImportData> importDataList;

    @PostConstruct
    public static void init() {
        importDataList = new ArrayList<>();
        initData();
        IndiaDataListener.list.forEach(x -> {
            ImportData importData = new ImportData();
            importData.setDate(x.get(0));
            importData.setState(x.get(1));
            importData.setNumber(Integer.parseInt(x.get(2)));
            importData.setRecovered(Integer.parseInt(x.get(3)));
            importDataList.add(importData);
        });
    }

    public static void main(String[] args) {
        importDataList = new ArrayList<>();
        initData();
        IndiaDataListener.list.forEach(x -> {
            ImportData importData = new ImportData();
            importData.setDate(x.get(0));
            importData.setState(x.get(1));
            importData.setNumber(Integer.parseInt(x.get(2)));
            importData.setRecovered(Integer.parseInt(x.get(3)));
            importDataList.add(importData);
        });
        CountryUtil.initCountry();
        export();
    }

    public static void initData() {
        String indiaFileName = "C:\\Users\\Administrator\\Desktop\\cases-brazil-states.xlsx";
        // 这里 只要，然后读取第一个sheet 同步读取会自动finish
        EasyExcel.read(indiaFileName, new IndiaDataListener()).sheet().doRead();
    }

    private static List<String> getHeader() {

        LocalDate start = LocalDate.of(2020, 2, 24);
        LocalDate end = LocalDate.of(2021, 2, 23);

        List<String> headers = new ArrayList<>();
        headers.add("state");
        headers.add("flag");
        do {
            start = start.plusDays(1);
            headers.add(DateUtil.stringYMD(start));
        } while (start.isBefore(end));
        return headers;
    }

    private static List<Integer> getIntegerHeader() {

        LocalDate start = LocalDate.of(2020, 2, 24);
        LocalDate end = LocalDate.of(2021, 2, 23);

        List<Integer> headers = new ArrayList<>();
        do {
            start = start.plusDays(1);
            headers.add(DateUtil.integerYMD(start));
        } while (start.isBefore(end));
        return headers;
    }

    public static void export(){

        // 文件输出位置
        String outPath = "C:\\Users\\Administrator\\Desktop\\cases-brazil.xlsx";

        try {
            // 所有行的集合
            List<List<Object>> list = new ArrayList<>();

            Map<String, List<ImportData>> groupBy = importDataList
                .stream().collect(groupingBy(ImportData::getState));
            groupBy.forEach((k, v) -> {
                // 第 n 行的数据
                List<Object> row = new ArrayList<>();
                String stateName = BrazilStateUtil.getStateName(k);
                LOG.info("k:{}, stateName:{}", k, stateName);
                if (StringUtils.isEmpty(stateName)) {
                    return;
                }
                row.add(stateName);

                ImmutablePair<String, String> pair = CountryUtil.getTwo("India");
                row.add(pair.getRight());

                Map<String, ImportData> dataMap = v.stream().collect(toMap(ImportData::getDate, Function.identity()));
                getIntegerHeader().forEach(x -> {
                    ImportData importData = dataMap.get(x.toString());
                    if (importData != null && importData.getNumber() != null) {
                        row.add(importData.getNumber());
                    } else {
                        row.add(0);
                    }
                });
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

    public static List<List<Object>> preExport(){

        // 所有行的集合
        List<List<Object>> list = new ArrayList<>();

        Map<String, List<ImportData>> groupBy = importDataList
            .stream().collect(groupingBy(ImportData::getState));
        groupBy.forEach((k, v) -> {
            // 第 n 行的数据
            List<Object> row = new ArrayList<>();
            String stateName = IndiaStateUtil.getStateName(k);
            LOG.info("k:{}, stateName:{}", k, stateName);
            if (StringUtils.isEmpty(stateName)) {
                return;
            }
            row.add(stateName);

            ImmutablePair<String, String> pair = CountryUtil.getTwo("India");
            row.add(pair.getRight());

            Map<String, ImportData> dataMap = v.stream().collect(toMap(ImportData::getDate, Function.identity()));
            getIntegerHeader().forEach(x -> {
                ImportData importData = dataMap.get(x.toString());
                if (importData != null && importData.getNumber() != null) {
                    row.add(importData.getNumber());
                } else {
                    row.add(0);
                }
            });
            list.add(row);
        });

        return list;
    }

}