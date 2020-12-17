package com.covid.easyexcel;


import cn.hutool.core.util.NumberUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.covid.CountryUtil;
import com.covid.DateUtil;
import com.covid.EsayIndiaCovidService;
import com.covid.ImportData;
import com.covid.IndiaCovidService;
import com.covid.IndiaImportData;
import com.covid.IndiaStateUtil;
import com.covid.UsCovidService;
import com.covid.UsStateUtil;
import com.google.common.collect.Lists;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author mukong
 */
@Service
@Slf4j
public class UsIndiaStateV2CovidService {

    private static final Logger LOG = LoggerFactory.getLogger(UsIndiaStateV2CovidService.class);

    private static List<ImportData> importDataList;

    private static final List<String> filter = Lists.newArrayList("International");

    @PostConstruct
    public void init() {
        importDataList = new ArrayList<>();
        initData();
        CountryUtil.initCountry();
        export();
    }

    public static void main(String[] args) {
        importDataList = new ArrayList<>();
        initData();
        UsCovidService.init();
        EsayIndiaCovidService.init();
        CountryUtil.initCountry();
        export();
    }

    public static void initData() {

        String fileName = "/Users/mukong/Desktop/covid/us-total-12-17.xlsx";

        // 这里 只要，然后读取第一个sheet 同步读取会自动finish
        EasyExcel.read(fileName, new RecoveredDataListener()).sheet().doRead();

        importDataList = new ArrayList<>();
        RecoveredDataListener.list.forEach(x -> {
            ImportData importData = new ImportData();
            importData.setDate(x.get(0));
            importData.setState(x.get(1));
            importData.setNumber(Integer.parseInt(x.get(2)));
            importData.setRecovered(Integer.parseInt(x.get(3)));
            importDataList.add(importData);
        });
    }

    private static List<String> getHeader() {
        List<String> headers = new ArrayList<>();
        headers.add("name");
        headers.add("flag");
        headers.add("value");
        headers.add("rate");
        headers.add("date");
        return headers;
    }

    public static void export(){

        // 文件输出位置
        String outPath = "/Users/mukong/Desktop/covid/us-india-12-17.xlsx";

        try {
            // 所有行的集合
            List<List<Object>> list = new ArrayList<>();
            Map<String, Map<Integer, String>> confirmMap = WorldConfirmedDataListener.list.stream()
                .collect(Collectors.toMap(x -> x.get(0), Function.identity()));
            Map<String, List<Map<Integer, String>>> recoveredMap = RecoveredDataListener.list.stream()
//                .filter(x -> Integer.parseInt(x.get(260)) >70000)
                .collect(Collectors.groupingBy(x -> x.get(0)));

            List<ImportData> indiaList = EsayIndiaCovidService.importDataList;
            List<ImportData> usList = UsCovidService.importDataList;
            importDataList.forEach(x -> x.setState("US"));
            //usList.addAll(importDataList);

            indiaList.forEach(x -> {
                List<Object> row = new ArrayList<>();
                String name = IndiaStateUtil.getStateName(x.getState());
                if (StringUtils.isEmpty(name)) {
                    LOG.error("name empty, code:{}, name:{}", x.getState(), name);
                    return;
                }
                row.add(name);
                row.add(IndiaStateUtil.getFlag());

                    if (x.getNumber() != null && x.getNumber() != 0) {
                        try{
                            BigDecimal div = NumberUtil.div(BigDecimal.valueOf(x.getRecovered()), BigDecimal.valueOf(x.getNumber()), 3);
                            row.add(x.getNumber());
                            row.add(div.doubleValue() * 100);
                        } catch (Exception e) {
                            LOG.error("error:{}", x);
                        }

                    } else {
                        row.add(0);
                        row.add(0);
                    }

                row.add(DateUtil.strToDateFormat(x.getDate()));
                list.add(row);
            });

            Collections.reverse(usList);
            usList.forEach(x -> {
                List<Object> row = new ArrayList<>();
                String name = UsStateUtil.getStateName(x.getState());
                if (StringUtils.isEmpty(name)) {
                    LOG.error("name empty, code:{}, name:{}", x.getState(), name);
                    return;
                }
                row.add(name);
                row.add(UsStateUtil.getFlag());

                if (x.getNumber() != 0) {
                    BigDecimal div = NumberUtil.div(BigDecimal.valueOf(x.getRecovered()), BigDecimal.valueOf(x.getNumber()), 3);
                    row.add(x.getNumber());
                    row.add(div.doubleValue() * 100);
                } else {
                    row.add(0);
                    row.add(0);
                }

                row.add(DateUtil.strToDateFormat(x.getDate()));
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