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
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
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
public class RecoveredCovidService {

    private static final Logger LOG = LoggerFactory.getLogger(RecoveredCovidService.class);

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

        String fileName = "/Users/mukong/Desktop/covid/recovered.xlsx";

        // 这里 只要，然后读取第一个sheet 同步读取会自动finish
        EasyExcel.read(fileName, new RecoveredDataListener()).sheet().doRead();
    }

    private static List<String> getHeader() {

        LocalDate start = LocalDate.of(2020, 1, 21);
        LocalDate end = LocalDate.of(2020, 10, 12);

        List<String> headers = new ArrayList<>();
        headers.add("state");
        headers.add("t");
        headers.add("flag");
        do {
            start = start.plusDays(1);
            headers.add(DateUtil.stringYMD(start));
        } while (start.isBefore(end));
        return headers;
    }

    public static void export(){

        // 文件输出位置
        String outPath = "/Users/mukong/Desktop/covid/recovered-export.xlsx";

        try {
            // 所有行的集合
            List<List<Object>> list = new ArrayList<>();
            Map<String, List<Map<Integer, String>>> map = RecoveredDataListener.list.stream().collect(Collectors.groupingBy(x -> x.get(0)));
            map.forEach((k, v) -> {
                if (filter.contains(k)) {
                    return;
                }


                List<Object> row = new ArrayList<>();
                ImmutablePair<String, String> pair = CountryUtil.getTwo(k);
                if (pair == null) {
                    log.error("no country, code:{}", k);
                    return;
                }

                if (StringUtils.isBlank(pair.left)) {
                     LOG.error("no chinese name, code:{}, countryName:{}", k, pair.left);
                    return;
                }

                if (StringUtils.isBlank(pair.right)) {
                    LOG.error("no flag, code:{}, countryName:{}, flag:{}", k, pair.left, pair.right);
                    return;
                }

                row.add(pair.left);
                row.add("");
                row.add(pair.right);

                int size = v.get(0).size();
                for (int i=1; i<=size; i++) {
                    int finalI = i;
                    AtomicInteger sum = new AtomicInteger();
                    v.forEach(y -> {
                        y.remove(0);
                        if (y.get(finalI) != null) {
                            int tmp = Integer.parseInt(y.get(finalI));
                            sum.addAndGet(tmp);
                        }
                    });
                    row.add(sum.get());
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