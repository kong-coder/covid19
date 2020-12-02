package com.covid;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author mukong
 */
public class UsStateUtil {

    private static final Map<String, String> stateMap;

    static {
        stateMap = Maps.newHashMap();
        stateMap.put("AL", "阿拉巴马州(美)");
        stateMap.put("AK", "阿拉斯加州(美)");
        stateMap.put("AZ", "亚利桑那州(美)");
        stateMap.put("AR", "阿肯色州(美)");
        stateMap.put("CA", "加利福尼亚州(美)");
        stateMap.put("CO", "科罗拉多州(美)");
        stateMap.put("CT", "康涅狄格州(美)");
        stateMap.put("DE", "特拉华州(美)");
        stateMap.put("FL", "佛罗里达州(美)");
        stateMap.put("GA", "乔治亚州(美)");
        stateMap.put("HI", "夏威夷州(美)");
        stateMap.put("ID", "爱达荷州(美)");
        stateMap.put("IL", "伊利诺斯州(美)");
        stateMap.put("IN", "印第安纳州(美)");
        stateMap.put("IA", "爱荷华州(美)");
        stateMap.put("KS", "堪萨斯州(美)");
        stateMap.put("KY", "肯塔基州(美)");
        stateMap.put("LA", "路易斯安那州(美)");
        stateMap.put("ME", "缅因州(美)");
        stateMap.put("MD", "马里兰州(美)");
        stateMap.put("MA", "马萨诸塞州(美)");
        stateMap.put("MI", "密歇根州(美)");
        stateMap.put("MN", "明尼苏达州(美)");
        stateMap.put("MS", "密西西比州(美)");
        stateMap.put("MO", "密苏里州(美)");
        stateMap.put("MT", "蒙大拿州(美)");
        stateMap.put("NE", "内布拉斯加州(美)");
        stateMap.put("NV", "内华达州(美)");
        stateMap.put("NH", "新罕布什尔州(美)");
        stateMap.put("NJ", "新泽西州(美)");
        stateMap.put("NM", "新墨西哥州(美)");
        stateMap.put("NY", "纽约州(美)");
        stateMap.put("NC", "北卡罗来纳州(美)");
        stateMap.put("ND", "北达科他州(美)");
        stateMap.put("OH", "俄亥俄州(美)");
        stateMap.put("OK", "俄克拉荷马州(美)");
        stateMap.put("OR", "俄勒冈州(美)");
        stateMap.put("PA", "宾夕法尼亚州(美)");
        stateMap.put("RI", "罗得岛州(美)");
        stateMap.put("SC", "南卡罗来纳州(美)");
        stateMap.put("SD", "南达科他州(美)");
        stateMap.put("TN", "田纳西州(美)");
        stateMap.put("TX", "得克萨斯州(美)");
        stateMap.put("UT", "犹他州(美)");
        stateMap.put("VT", "佛蒙特州(美)");
        stateMap.put("VA", "弗吉尼亚州(美)");
        stateMap.put("WA", "华盛顿州(美)");
        stateMap.put("WV", "西弗吉尼亚州(美)");
        stateMap.put("WI", "威斯康辛州(美)");
        stateMap.put("WY", "怀俄明州(美)");
        stateMap.put("PR", "波多黎各");
        stateMap.put("MP", "北马里亚纳群岛");
        stateMap.put("VI", "美属维尔京群岛");
        stateMap.put("AS", "美属萨摩亚");
        stateMap.put("GU", "关岛");
        stateMap.put("DC", "华盛顿特区");
        //stateMap.put("United States", "美国");
    }

    public static String getStateName(String state) {
        return stateMap.get(state);
    }
}