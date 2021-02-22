package com.covid;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author mukong
 */
public class BrazilStateUtil {

    private static final Map<String, String> stateMap;

    static {
        stateMap = Maps.newHashMap();
        stateMap.put("AC", "阿拉巴马州(巴)");
        stateMap.put("AL", "阿拉戈斯州(巴)");
        stateMap.put("AP", "阿马帕州(巴)");
        stateMap.put("AM", "亚马孙州(巴)");
        stateMap.put("BA", "巴伊亚州(巴)");
        stateMap.put("CE", "塞阿拉州(巴)");
        stateMap.put("ES", "圣埃斯皮里图州(巴)");
        stateMap.put("GO", "戈亚斯州(巴)");
        stateMap.put("MA", "马拉尼昂州(巴)");
        stateMap.put("MT", "马托格罗索州(巴)");
        stateMap.put("MS", "南马托格罗索州(巴)");
        stateMap.put("MG", "米纳斯吉拉斯州(巴)");
        stateMap.put("PA", "帕拉州(巴)");
        stateMap.put("PB", "帕拉伊巴州(巴)");
        stateMap.put("PR", "巴拉那州(巴)");
        stateMap.put("PE", "伯南布哥州(巴)");
        stateMap.put("PI", "皮奥伊州(巴)");
        stateMap.put("RJ", "里约热内卢州(巴)");
        stateMap.put("RN", "北里奥格兰德州(巴)");
        stateMap.put("RS", "南里奥格兰德州(巴)");
        stateMap.put("RO", "朗多尼亚州(巴)");
        stateMap.put("RR", "罗赖马州(巴)");
        stateMap.put("SC", "圣卡塔琳娜州(巴)");
        stateMap.put("SP", "圣保罗州(巴)");
        stateMap.put("SE", "塞尔希培州(巴)");
        stateMap.put("TO", "托坎廷斯州(巴)");
        stateMap.put("DF", "联邦区(巴)");
        stateMap.put("TOTAL", "巴西");
        //stateMap.put("United States", "美国");
    }

    public static String getStateName(String state) {
        return stateMap.get(state);
    }

    public static String getFlag() {
        return "https://flagpedia.net/data/flags/h80/us.png";
    }
}