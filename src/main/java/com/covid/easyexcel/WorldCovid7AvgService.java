package com.covid.easyexcel;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.covid.CountryUtil;
import com.covid.DateUtil;
import com.covid.ImportData;
import com.google.common.collect.Lists;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
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
public class WorldCovid7AvgService {

    private static final Logger LOG = LoggerFactory.getLogger(WorldCovid7AvgService.class);

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
        CountryUtil.initCountry();
        export();
    }

    public static void initData() {

        String fileName = "/Users/yanhom/Desktop/covid/covid-total-11-12.xlsx";

        // 这里 只要，然后读取第一个sheet 同步读取会自动finish
        EasyExcel.read(fileName, new WorldConfirmedDataListener()).sheet().doRead();
    }

    private static List<String> getHeader() {

        LocalDate start = LocalDate.of(2019, 12, 30);
        LocalDate end = LocalDate.of(2020, 11, 13);

        List<String> headers = new ArrayList<>();
        headers.add("state");
        headers.add("flag");
        do {
            headers.add(DateUtil.stringMD(start) + "~" + DateUtil.stringMD(start.plusDays(7)));
            start = start.plusDays(7);
        } while (start.isBefore(end));
        return headers;
    }

    public static void export(){

        // 文件输出位置
        String outPath = "/Users/yanhom/Desktop/covid/export-11-12.xlsx";

        try {
            // 所有行的集合
            List<List<Object>> list = new ArrayList<>();
            WorldConfirmedDataListener.list.forEach(x -> {

                // 第 n 行的数据
                String code = x.get(0);
                if (filter.contains(code)) {
                    return;
                }

                List<Object> row = new ArrayList<>();
                ImmutablePair<String, String> pair = CountryUtil.getTwo(code);
                if (pair == null) {
                    return;
                }

                if (StringUtils.isBlank(pair.left)) {
                    LOG.error("code:{}, countryName:{}", code, pair.left);
                    return;
                }

                if (StringUtils.isBlank(pair.right)) {
                    LOG.error("code:{}, countryName:{}, flag:{}", code, pair.left, pair.right);
                    return;
                }

                x.remove(0);
                row.add(pair.left);
                row.add(pair.right);
                AtomicInteger sum = new AtomicInteger();
                x.forEach((k, v) -> {
                    if (k % 7 == 0) {
                        row.add(sum.get() / 7);
                        sum.set(0);
                    } else {
                        sum.addAndGet(Integer.parseInt(v));
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