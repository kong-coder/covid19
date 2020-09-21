package com.covid;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author mukong
 */
public class IndiaStateUtil {

    private static final Map<String, String> stateMap;

    static {
        stateMap = Maps.newHashMap();

        stateMap.put("Andhra Pradesh", "安德拉邦(印)");
        stateMap.put("AP", "安德拉邦(印)");
        stateMap.put("Assam", "阿萨姆邦(印)");
        stateMap.put("AS", "阿萨姆邦(印)");
        stateMap.put("Bihar", "比哈尔邦(印)");
        stateMap.put("BR", "比哈尔邦(印)");
        stateMap.put("Goa", "果阿邦(印)");
        stateMap.put("GA", "果阿(邦)");
        stateMap.put("Gujarat", "古吉拉特邦(印)");
        stateMap.put("GJ", "古吉拉特邦(印)");
        stateMap.put("Haryana", "哈里亚那邦(印)");
        stateMap.put("HR", "哈里亚那邦(印)");
        stateMap.put("Himachal Pradesh", "喜马偕尔邦(印)");
        stateMap.put("HP", "喜马偕尔邦(印)");
        stateMap.put("Kanatak", "卡纳塔克邦(印)");
        stateMap.put("KA", "卡纳塔克邦(印)");
        stateMap.put("Kerala", "喀拉拉邦(印)");
        stateMap.put("KL", "喀拉拉邦(印)");
        stateMap.put("Madhya Pradesh", "中央邦(印)");
        stateMap.put("MP", "中央邦(印)");
        stateMap.put("Maharashtra", "马哈拉斯特拉邦(印)");
        stateMap.put("MH", "马哈拉斯特拉邦(印)");
        stateMap.put("Manpur", "曼普尔邦(印)");
        stateMap.put("MN", "曼普尔邦(印)");
        stateMap.put("Meghalaya", "梅加拉亚邦(印)");
        stateMap.put("ML", "梅加拉亚邦(印)");
        stateMap.put("Mizoram", "米佐拉姆邦(印)");
        stateMap.put("MZ", "米佐拉姆邦(印)");
        stateMap.put("Nagaland", "那加兰邦(印)");
        stateMap.put("NL", "那加兰邦(印)");
        stateMap.put("Orissa", "奥里萨邦(印)");
        stateMap.put("OR", "奥里萨邦(印)");
        stateMap.put("Punjab", "旁遮普邦(印)");
        stateMap.put("PB", "旁遮普邦(印)");
        stateMap.put("Rajasthan", "拉贾斯坦邦(印)");
        stateMap.put("RJ", "拉贾斯坦邦(印)");
        stateMap.put("Tamil Nadu", "泰米尔纳德邦(印)");
        stateMap.put("YN", "泰米尔纳德邦(印)");
        stateMap.put("Tripura", "特里普拉邦(印)");
        stateMap.put("TR", "特里普拉邦(印)");
        stateMap.put("Utar Pradesh", "北方邦(印)");
        stateMap.put("Uttar Pradesh", "北方邦(印)");
        stateMap.put("UP", "北方邦(印)");
        stateMap.put("West Bengal", "西孟加拉邦(印)");
        stateMap.put("WB", "西孟加拉邦(印)");
        stateMap.put("Uttarakhand", "北阿坎德邦(印)");
        stateMap.put("UT", "北阿坎德邦(印)");
        stateMap.put("Telangana", "特伦甘纳邦(印)");
        stateMap.put("TG", "特伦甘纳邦(印)");
        stateMap.put("Sikkim", "锡金邦(印侵占)");
        stateMap.put("SK", "锡金邦(印侵占)");
        stateMap.put("Puducherry", "本地治里邦(印)");
        stateMap.put("PY", "本地治里邦(印)");
        stateMap.put("Odisha", "奥里萨邦(印)");
        stateMap.put("OR", "奥里萨邦(印)");
        stateMap.put("Ladakh", "拉达克(印侵占)");
        stateMap.put("LA", "拉达克(印侵占)");
        stateMap.put("Jharkhand", "贾坎德邦(印)");
        stateMap.put("JH", "贾坎德邦(印)");
        stateMap.put("Jammu and Kashmir", "查谟和克什米尔(印)");
        stateMap.put("JK", "查谟和克什米尔(印)");
        stateMap.put("Delhi", "德里(印)");
        stateMap.put("DL", "德里(印)");
        stateMap.put("Chhattisgarh", "恰蒂斯加尔邦(印)");
        stateMap.put("CT", "恰蒂斯加尔邦(印)");
        stateMap.put("Chandigarh", "昌迪加尔(印)");
        stateMap.put("CH", "昌迪加尔(印)");
        stateMap.put("Arunachal Pradesh", "藏南(印侵占)");
        stateMap.put("AR", "藏南(印侵占)");

        stateMap.put("Karnataka", "卡纳塔克邦(印)");
        stateMap.put("Manipur", "曼尼普尔邦(印)");

        stateMap.put("Andaman and Nicobar Islands", "安达曼和尼科巴群岛(印)");
        stateMap.put("AN", "安达曼和尼科巴群岛(印)");

        stateMap.put("State Unassigned", "其他地区");
        stateMap.put("UN", "其他地区");

        stateMap.put("India", "印度");

    }

    public static String getStateName(String state) {
        return stateMap.get(state);
    }

}