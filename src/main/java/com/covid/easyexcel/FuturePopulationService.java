package com.covid.easyexcel;


import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.covid.CountryUtil;
import com.excel.poi.ExcelBoot;
import com.excel.poi.entity.ErrorEntity;
import com.excel.poi.function.ImportFunction;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
public class FuturePopulationService {

    private static final Logger LOG = LoggerFactory.getLogger(FuturePopulationService.class);

    private static List<Population> populations;

    @PostConstruct
    public static void init() {
        populations = new ArrayList<>();
        initData();
        export();
    }

    public static void main(String[] args) {
        populations = new ArrayList<>();
        initData();
        CountryUtil.initCountry();
        export();
    }

    public static void initData() {
        try{
            ExcelBoot
                    .ImportBuilder(new FileInputStream(new File("/Users/yanhom/Desktop/population.xlsx")), Population.class)
                    .importExcel(new ImportFunction<Population>() {

                        @Override
                        public void onProcess(int sheetIndex,  int rowIndex, Population population) {
                            populations.add(population);
                        }

                        /**
                         * @param errorEntity 错误信息实体
                         */
                        @Override
                        public void onError(ErrorEntity errorEntity) {
                            System.out.println(errorEntity);
                        }
                    });
        } catch (Exception e) {
            LOG.error("load error", e);
        }

        System.out.println(populations.size());
    }

    private static List<String> getHeader() {
        List<String> headers = new ArrayList<>();
        headers.add("state");
        int year = 1950;
        do {
            headers.add(String.valueOf(year));
            year++;
        } while (year <= 2100);
        return headers;
    }

    public static void export(){

        // 文件输出位置
        String outPath = "/Users/yanhom/Desktop/population-export.xlsx";

        try {
            // 所有行的集合
            List<List<Object>> list = new ArrayList<>();

            Map<String, List<Population>> groupBy = populations.stream().collect(groupingBy(Population::getEntity));
            groupBy.forEach((k, v) -> {
                // 第 n 行的数据
                List<Object> row = new ArrayList<>();
                String countryName = CountryUtil.get(k);
                if (StringUtils.isBlank(countryName)) {
                    LOG.error("no country mapping, k:{}", k);
                    return;
                }


                row.add(countryName);

                Map<Integer, Population> dataMap = v.stream().collect(toMap(Population::getYear, Function.identity()));
                getHeader().forEach(x -> {
                    if (x.equals("state")) {
                        return;
                    }
                    Population population = dataMap.get(Integer.parseInt(x));
                    if (population == null) {
                        LOG.error("population null, k:{}, x:{}", k, x);
                    }
                    if (population != null && population.getProjection() != null && !population.getProjection().equals("0")) {
                        row.add(population.getProjection());
                    } else if (population != null && population.getEstimates() != null && !population.getEstimates().equals("0")) {
                        row.add(population.getEstimates());
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

}