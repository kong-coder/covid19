package com.covid;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.covid.easyexcel.FlagDataListener;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CountryUtil {

    private static final Map<String, String> countryMap = new HashMap<>();
    private static final Map<String, String> flagMap = new HashMap<>();

    static {
        countryMap.put("United States Virgin Islands", "美属维京群岛");
        countryMap.put("Turks and Caicos Islands", "特克斯与凯科斯群岛");
        countryMap.put("Timor", "东帝汶");
        countryMap.put("Sao Tome and Principe", "圣多美与普林西比");
        countryMap.put("Saint Vincent and the Grenadines", "圣文森特和格林纳丁斯");
        countryMap.put("Saint Kitts and Nevis", "圣基茨和尼维斯联邦");
        countryMap.put("Kosovo", "科索沃");
        countryMap.put("Isle of Man", "英国属地曼岛");
        countryMap.put("Democratic Republic of Congo", "刚果民主共和国");
        countryMap.put("Syrian Arab Republic", "叙利亚");
        countryMap.put("Venezuela, RB", "委内瑞拉");
        countryMap.put("Yemen, Rep.", "也门");
        countryMap.put("Eswatini", "斯威士兰");
        countryMap.put("Slovak Republic", "斯洛伐克");
        countryMap.put("Bahamas, The", "巴拿马");
        countryMap.put("Brunei Darussalam", "文莱");
        countryMap.put("Cote d'Ivoire", "科特迪瓦");
        countryMap.put("Congo, Dem. Rep", "刚果民主共和国");
        countryMap.put("Congo, Rep.", "刚果共和国");
        countryMap.put("Cabo Verde", "佛得角");
        countryMap.put("Egypt, Arab Rep.", "埃及");
        countryMap.put("Gambia, The", "冈比亚");
        countryMap.put("Iran, Islamic Rep.", "伊朗");
        countryMap.put("Kyrgyz Republic", "吉尔吉斯坦");
        countryMap.put("Korea, Rep.", "韩国");
        countryMap.put("Lao PDR", "老挝");
        countryMap.put("Korea, Dem. People’s Rep.", "朝鲜");
        countryMap.put("US", "美国");
        countryMap.put("us", "美国");
        countryMap.put("Korea, South", "韩国");
        countryMap.put("Czechia", "捷克");
        countryMap.put("Congo (Brazzaville)", "刚果共和国");
        countryMap.put("North Macedonia", "北马其顿共和国");
        countryMap.put("Burma", "缅甸");

        flagMap.put("US", "https://www.countryflags.io/US/shiny/64.png");
        flagMap.put("Korea, South", "https://www.countryflags.io/kr/flat/64.png");
        flagMap.put("Czechia", "https://www.countryflags.io/CZ/flat/64.png");
        flagMap.put("Czech Republic", "https://www.countryflags.io/CZ/flat/64.png");
        flagMap.put("Burma", "https://www.countryflags.io/MM/shiny/64.png");
    }

    public static void initCountry() {

        String[] locales = Locale.getISOCountries();

        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
            countryMap.put(obj.getDisplayCountry(Locale.ENGLISH), obj.getDisplayCountry(Locale.CHINA));
        }
        initFlag();
        System.out.println(JSONObject.toJSONString(countryMap));

    }

    public static void main(String[] args) {
        //
        initCountry();
    }

    public static void initFlag() {

        String fileName = "/Users/mukong/Desktop/covid/flags.xlsx";

        // 这里 只要，然后读取第一个sheet 同步读取会自动finish
        EasyExcel.read(fileName, new FlagDataListener()).sheet().doRead();
        FlagDataListener.list.forEach(x -> flagMap.put(x.get(0), x.get(1)));
    }

    public static String get(String name) {
        if (name.toLowerCase().contains("taiwan") || name.contains("台湾")) {
            return null;
        }
        countryMap.remove("Taiwan");
        return countryMap.get(name);
    }

    public static ImmutablePair<String, String> getTwo(String name) {
        if (name.toLowerCase().contains("taiwan") || name.contains("台湾")) {
            return null;
        }
        countryMap.remove("Taiwan");
        String countryName = countryMap.get(name);
        if ("阿拉伯联合酋长国".equals(countryName)) {
            countryName = "阿联酋";
        }
        return new ImmutablePair<>(countryName, flagMap.get(name));
    }

}