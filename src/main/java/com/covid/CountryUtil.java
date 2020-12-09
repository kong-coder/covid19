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
    private static Map<String, Object> flagMap = new HashMap<>();
    private static final String fJson = "{\"Afghanistan\": \"https://flagpedia.net/data/flags/h80/af.png\", \"\\u00c5land Islands\": \"https://flagpedia.net/data/flags/h80/ax.png\", \"Albania\": \"https://flagpedia.net/data/flags/h80/al.png\", \"Algeria\": \"https://flagpedia.net/data/flags/h80/dz.png\", \"American Samoa\": \"https://flagpedia.net/data/flags/h80/as.png\", \"Andorra\": \"https://flagpedia.net/data/flags/h80/ad.png\", \"Angola\": \"https://flagpedia.net/data/flags/h80/ao.png\", \"Anguilla\": \"https://flagpedia.net/data/flags/h80/ai.png\", \"Antarctica\": \"https://flagpedia.net/data/flags/h80/aq.png\", \"Antigua and Barbuda\": \"https://flagpedia.net/data/flags/h80/ag.png\", \"Argentina\": \"https://flagpedia.net/data/flags/h80/ar.png\", \"Armenia\": \"https://flagpedia.net/data/flags/h80/am.png\", \"Aruba\": \"https://flagpedia.net/data/flags/h80/aw.png\", \"Australia\": \"https://flagpedia.net/data/flags/h80/au.png\", \"Austria\": \"https://flagpedia.net/data/flags/h80/at.png\", \"Azerbaijan\": \"https://flagpedia.net/data/flags/h80/az.png\", \"Bahamas\": \"https://flagpedia.net/data/flags/h80/bs.png\", \"Bahrain\": \"https://flagpedia.net/data/flags/h80/bh.png\", \"Bangladesh\": \"https://flagpedia.net/data/flags/h80/bd.png\", \"Barbados\": \"https://flagpedia.net/data/flags/h80/bb.png\", \"Belarus\": \"https://flagpedia.net/data/flags/h80/by.png\", \"Belgium\": \"https://flagpedia.net/data/flags/h80/be.png\", \"Belize\": \"https://flagpedia.net/data/flags/h80/bz.png\", \"Benin\": \"https://flagpedia.net/data/flags/h80/bj.png\", \"Bermuda\": \"https://flagpedia.net/data/flags/h80/bm.png\", \"Bhutan\": \"https://flagpedia.net/data/flags/h80/bt.png\", \"Bolivia\": \"https://flagpedia.net/data/flags/h80/bo.png\", \"Bosnia and Herzegovina\": \"https://flagpedia.net/data/flags/h80/ba.png\", \"Botswana\": \"https://flagpedia.net/data/flags/h80/bw.png\", \"Bouvet Island\": \"https://flagpedia.net/data/flags/h80/bv.png\", \"Brazil\": \"https://flagpedia.net/data/flags/h80/br.png\", \"British Indian Ocean Territory\": \"https://flagpedia.net/data/flags/h80/io.png\", \"Brunei\": \"https://flagpedia.net/data/flags/h80/bn.png\", \"Bulgaria\": \"https://flagpedia.net/data/flags/h80/bg.png\", \"Burkina Faso\": \"https://flagpedia.net/data/flags/h80/bf.png\", \"Burundi\": \"https://flagpedia.net/data/flags/h80/bi.png\", \"Cambodia\": \"https://flagpedia.net/data/flags/h80/kh.png\", \"Cameroon\": \"https://flagpedia.net/data/flags/h80/cm.png\", \"Canada\": \"https://flagpedia.net/data/flags/h80/ca.png\", \"Cape Verde\": \"https://flagpedia.net/data/flags/h80/cv.png\", \"Caribbean Netherlands\": \"https://flagpedia.net/data/flags/h80/bq.png\", \"Cayman Islands\": \"https://flagpedia.net/data/flags/h80/ky.png\", \"Central African Republic\": \"https://flagpedia.net/data/flags/h80/cf.png\", \"Chad\": \"https://flagpedia.net/data/flags/h80/td.png\", \"Chile\": \"https://flagpedia.net/data/flags/h80/cl.png\", \"China\": \"https://flagpedia.net/data/flags/h80/cn.png\", \"Christmas Island\": \"https://flagpedia.net/data/flags/h80/cx.png\", \"Cocos Islands\": \"https://flagpedia.net/data/flags/h80/cc.png\", \"Colombia\": \"https://flagpedia.net/data/flags/h80/co.png\", \"Comoros\": \"https://flagpedia.net/data/flags/h80/km.png\", \"Republic of the Congo\": \"https://flagpedia.net/data/flags/h80/cg.png\", \"DR Congo\": \"https://flagpedia.net/data/flags/h80/cd.png\", \"Cook Islands\": \"https://flagpedia.net/data/flags/h80/ck.png\", \"Costa Rica\": \"https://flagpedia.net/data/flags/h80/cr.png\", \"C\\u00f4te d'Ivoire\": \"https://flagpedia.net/data/flags/h80/ci.png\", \"Croatia\": \"https://flagpedia.net/data/flags/h80/hr.png\", \"Cuba\": \"https://flagpedia.net/data/flags/h80/cu.png\", \"Cura\\u00e7ao\": \"https://flagpedia.net/data/flags/h80/cw.png\", \"Cyprus\": \"https://flagpedia.net/data/flags/h80/cy.png\", \"Czechia\": \"https://flagpedia.net/data/flags/h80/cz.png\", \"Denmark\": \"https://flagpedia.net/data/flags/h80/dk.png\", \"Djibouti\": \"https://flagpedia.net/data/flags/h80/dj.png\", \"Dominica\": \"https://flagpedia.net/data/flags/h80/dm.png\", \"Dominican Republic\": \"https://flagpedia.net/data/flags/h80/do.png\", \"Ecuador\": \"https://flagpedia.net/data/flags/h80/ec.png\", \"Egypt\": \"https://flagpedia.net/data/flags/h80/eg.png\", \"El Salvador\": \"https://flagpedia.net/data/flags/h80/sv.png\", \"England\": \"https://flagpedia.net/data/flags/h80/gb-eng.png\", \"Equatorial Guinea\": \"https://flagpedia.net/data/flags/h80/gq.png\", \"Eritrea\": \"https://flagpedia.net/data/flags/h80/er.png\", \"Estonia\": \"https://flagpedia.net/data/flags/h80/ee.png\", \"Eswatini\": \"https://flagpedia.net/data/flags/h80/sz.png\", \"Ethiopia\": \"https://flagpedia.net/data/flags/h80/et.png\", \"Falkland Islands\": \"https://flagpedia.net/data/flags/h80/fk.png\", \"Faroe Islands\": \"https://flagpedia.net/data/flags/h80/fo.png\", \"Fiji\": \"https://flagpedia.net/data/flags/h80/fj.png\", \"Finland\": \"https://flagpedia.net/data/flags/h80/fi.png\", \"France\": \"https://flagpedia.net/data/flags/h80/fr.png\", \"French Guiana\": \"https://flagpedia.net/data/flags/h80/gf.png\", \"French Polynesia\": \"https://flagpedia.net/data/flags/h80/pf.png\", \"French Southern and Antarctic Lands\": \"https://flagpedia.net/data/flags/h80/tf.png\", \"Gabon\": \"https://flagpedia.net/data/flags/h80/ga.png\", \"Gambia\": \"https://flagpedia.net/data/flags/h80/gm.png\", \"Georgia\": \"https://flagpedia.net/data/flags/h80/ge.png\", \"Germany\": \"https://flagpedia.net/data/flags/h80/de.png\", \"Ghana\": \"https://flagpedia.net/data/flags/h80/gh.png\", \"Gibraltar\": \"https://flagpedia.net/data/flags/h80/gi.png\", \"Greece\": \"https://flagpedia.net/data/flags/h80/gr.png\", \"Greenland\": \"https://flagpedia.net/data/flags/h80/gl.png\", \"Grenada\": \"https://flagpedia.net/data/flags/h80/gd.png\", \"Guadeloupe\": \"https://flagpedia.net/data/flags/h80/gp.png\", \"Guam\": \"https://flagpedia.net/data/flags/h80/gu.png\", \"Guatemala\": \"https://flagpedia.net/data/flags/h80/gt.png\", \"Guernsey\": \"https://flagpedia.net/data/flags/h80/gg.png\", \"Guinea\": \"https://flagpedia.net/data/flags/h80/gn.png\", \"Guinea-Bissau\": \"https://flagpedia.net/data/flags/h80/gw.png\", \"Guyana\": \"https://flagpedia.net/data/flags/h80/gy.png\", \"Haiti\": \"https://flagpedia.net/data/flags/h80/ht.png\", \"Heard Island and McDonald Islands\": \"https://flagpedia.net/data/flags/h80/hm.png\", \"Honduras\": \"https://flagpedia.net/data/flags/h80/hn.png\", \"Hong Kong\": \"https://flagpedia.net/data/flags/h80/hk.png\", \"Hungary\": \"https://flagpedia.net/data/flags/h80/hu.png\", \"Iceland\": \"https://flagpedia.net/data/flags/h80/is.png\", \"India\": \"https://flagpedia.net/data/flags/h80/in.png\", \"Indonesia\": \"https://flagpedia.net/data/flags/h80/id.png\", \"Iran\": \"https://flagpedia.net/data/flags/h80/ir.png\", \"Iraq\": \"https://flagpedia.net/data/flags/h80/iq.png\", \"Ireland\": \"https://flagpedia.net/data/flags/h80/ie.png\", \"Isle of Man\": \"https://flagpedia.net/data/flags/h80/im.png\", \"Israel\": \"https://flagpedia.net/data/flags/h80/il.png\", \"Italy\": \"https://flagpedia.net/data/flags/h80/it.png\", \"Jamaica\": \"https://flagpedia.net/data/flags/h80/jm.png\", \"Japan\": \"https://flagpedia.net/data/flags/h80/jp.png\", \"Jersey\": \"https://flagpedia.net/data/flags/h80/je.png\", \"Jordan\": \"https://flagpedia.net/data/flags/h80/jo.png\", \"Kazakhstan\": \"https://flagpedia.net/data/flags/h80/kz.png\", \"Kenya\": \"https://flagpedia.net/data/flags/h80/ke.png\", \"Kiribati\": \"https://flagpedia.net/data/flags/h80/ki.png\", \"North Korea\": \"https://flagpedia.net/data/flags/h80/kp.png\", \"South Korea\": \"https://flagpedia.net/data/flags/h80/kr.png\", \"Kosovo\": \"https://flagpedia.net/data/flags/h80/xk.png\", \"Kuwait\": \"https://flagpedia.net/data/flags/h80/kw.png\", \"Kyrgyzstan\": \"https://flagpedia.net/data/flags/h80/kg.png\", \"Laos\": \"https://flagpedia.net/data/flags/h80/la.png\", \"Latvia\": \"https://flagpedia.net/data/flags/h80/lv.png\", \"Lebanon\": \"https://flagpedia.net/data/flags/h80/lb.png\", \"Lesotho\": \"https://flagpedia.net/data/flags/h80/ls.png\", \"Liberia\": \"https://flagpedia.net/data/flags/h80/lr.png\", \"Libya\": \"https://flagpedia.net/data/flags/h80/ly.png\", \"Liechtenstein\": \"https://flagpedia.net/data/flags/h80/li.png\", \"Lithuania\": \"https://flagpedia.net/data/flags/h80/lt.png\", \"Luxembourg\": \"https://flagpedia.net/data/flags/h80/lu.png\", \"Macau\": \"https://flagpedia.net/data/flags/h80/mo.png\", \"Madagascar\": \"https://flagpedia.net/data/flags/h80/mg.png\", \"Malawi\": \"https://flagpedia.net/data/flags/h80/mw.png\", \"Malaysia\": \"https://flagpedia.net/data/flags/h80/my.png\", \"Maldives\": \"https://flagpedia.net/data/flags/h80/mv.png\", \"Mali\": \"https://flagpedia.net/data/flags/h80/ml.png\", \"Malta\": \"https://flagpedia.net/data/flags/h80/mt.png\", \"Marshall Islands\": \"https://flagpedia.net/data/flags/h80/mh.png\", \"Martinique\": \"https://flagpedia.net/data/flags/h80/mq.png\", \"Mauritania\": \"https://flagpedia.net/data/flags/h80/mr.png\", \"Mauritius\": \"https://flagpedia.net/data/flags/h80/mu.png\", \"Mayotte\": \"https://flagpedia.net/data/flags/h80/yt.png\", \"Mexico\": \"https://flagpedia.net/data/flags/h80/mx.png\", \"Micronesia\": \"https://flagpedia.net/data/flags/h80/fm.png\", \"Moldova\": \"https://flagpedia.net/data/flags/h80/md.png\", \"Monaco\": \"https://flagpedia.net/data/flags/h80/mc.png\", \"Mongolia\": \"https://flagpedia.net/data/flags/h80/mn.png\", \"Montenegro\": \"https://flagpedia.net/data/flags/h80/me.png\", \"Montserrat\": \"https://flagpedia.net/data/flags/h80/ms.png\", \"Morocco\": \"https://flagpedia.net/data/flags/h80/ma.png\", \"Mozambique\": \"https://flagpedia.net/data/flags/h80/mz.png\", \"Myanmar\": \"https://flagpedia.net/data/flags/h80/mm.png\", \"Namibia\": \"https://flagpedia.net/data/flags/h80/na.png\", \"Nauru\": \"https://flagpedia.net/data/flags/h80/nr.png\", \"Nepal\": \"https://flagpedia.net/data/flags/h80/np.png\", \"Netherlands\": \"https://flagpedia.net/data/flags/h80/nl.png\", \"New Caledonia\": \"https://flagpedia.net/data/flags/h80/nc.png\", \"New Zealand\": \"https://flagpedia.net/data/flags/h80/nz.png\", \"Nicaragua\": \"https://flagpedia.net/data/flags/h80/ni.png\", \"Niger\": \"https://flagpedia.net/data/flags/h80/ne.png\", \"Nigeria\": \"https://flagpedia.net/data/flags/h80/ng.png\", \"Niue\": \"https://flagpedia.net/data/flags/h80/nu.png\", \"Norfolk Island\": \"https://flagpedia.net/data/flags/h80/nf.png\", \"North Macedonia\": \"https://flagpedia.net/data/flags/h80/mk.png\", \"Northern Ireland\": \"https://flagpedia.net/data/flags/h80/gb-nir.png\", \"Northern Mariana Islands\": \"https://flagpedia.net/data/flags/h80/mp.png\", \"Norway\": \"https://flagpedia.net/data/flags/h80/no.png\", \"Oman\": \"https://flagpedia.net/data/flags/h80/om.png\", \"Pakistan\": \"https://flagpedia.net/data/flags/h80/pk.png\", \"Palau\": \"https://flagpedia.net/data/flags/h80/pw.png\", \"Palestine\": \"https://flagpedia.net/data/flags/h80/ps.png\", \"Panama\": \"https://flagpedia.net/data/flags/h80/pa.png\", \"Papua New Guinea\": \"https://flagpedia.net/data/flags/h80/pg.png\", \"Paraguay\": \"https://flagpedia.net/data/flags/h80/py.png\", \"Peru\": \"https://flagpedia.net/data/flags/h80/pe.png\", \"Philippines\": \"https://flagpedia.net/data/flags/h80/ph.png\", \"Pitcairn Islands\": \"https://flagpedia.net/data/flags/h80/pn.png\", \"Poland\": \"https://flagpedia.net/data/flags/h80/pl.png\", \"Portugal\": \"https://flagpedia.net/data/flags/h80/pt.png\", \"Puerto Rico\": \"https://flagpedia.net/data/flags/h80/pr.png\", \"Qatar\": \"https://flagpedia.net/data/flags/h80/qa.png\", \"R\\u00e9union\": \"https://flagpedia.net/data/flags/h80/re.png\", \"Romania\": \"https://flagpedia.net/data/flags/h80/ro.png\", \"Russia\": \"https://flagpedia.net/data/flags/h80/ru.png\", \"Rwanda\": \"https://flagpedia.net/data/flags/h80/rw.png\", \"Saint Barth\\u00e9lemy\": \"https://flagpedia.net/data/flags/h80/bl.png\", \"Saint Helena, Ascension and Tristan da Cunha\": \"https://flagpedia.net/data/flags/h80/sh.png\", \"Saint Kitts and Nevis\": \"https://flagpedia.net/data/flags/h80/kn.png\", \"Saint Lucia\": \"https://flagpedia.net/data/flags/h80/lc.png\", \"Saint Martin\": \"https://flagpedia.net/data/flags/h80/mf.png\", \"Saint Pierre and Miquelon\": \"https://flagpedia.net/data/flags/h80/pm.png\", \"Saint Vincent and the Grenadines\": \"https://flagpedia.net/data/flags/h80/vc.png\", \"Samoa\": \"https://flagpedia.net/data/flags/h80/ws.png\", \"San Marino\": \"https://flagpedia.net/data/flags/h80/sm.png\", \"S\\u00e3o Tom\\u00e9 and Pr\\u00edncipe\": \"https://flagpedia.net/data/flags/h80/st.png\", \"Saudi Arabia\": \"https://flagpedia.net/data/flags/h80/sa.png\", \"Scotland\": \"https://flagpedia.net/data/flags/h80/gb-sct.png\", \"Senegal\": \"https://flagpedia.net/data/flags/h80/sn.png\", \"Serbia\": \"https://flagpedia.net/data/flags/h80/rs.png\", \"Seychelles\": \"https://flagpedia.net/data/flags/h80/sc.png\", \"Sierra Leone\": \"https://flagpedia.net/data/flags/h80/sl.png\", \"Singapore\": \"https://flagpedia.net/data/flags/h80/sg.png\", \"Sint Maarten\": \"https://flagpedia.net/data/flags/h80/sx.png\", \"Slovakia\": \"https://flagpedia.net/data/flags/h80/sk.png\", \"Slovenia\": \"https://flagpedia.net/data/flags/h80/si.png\", \"Solomon Islands\": \"https://flagpedia.net/data/flags/h80/sb.png\", \"Somalia\": \"https://flagpedia.net/data/flags/h80/so.png\", \"South Africa\": \"https://flagpedia.net/data/flags/h80/za.png\", \"South Georgia\": \"https://flagpedia.net/data/flags/h80/gs.png\", \"South Sudan\": \"https://flagpedia.net/data/flags/h80/ss.png\", \"Spain\": \"https://flagpedia.net/data/flags/h80/es.png\", \"Sri Lanka\": \"https://flagpedia.net/data/flags/h80/lk.png\", \"Sudan\": \"https://flagpedia.net/data/flags/h80/sd.png\", \"Suriname\": \"https://flagpedia.net/data/flags/h80/sr.png\", \"Svalbard and Jan Mayen\": \"https://flagpedia.net/data/flags/h80/sj.png\", \"Sweden\": \"https://flagpedia.net/data/flags/h80/se.png\", \"Switzerland\": \"https://flagpedia.net/data/flags/h80/ch.png\", \"Syria\": \"https://flagpedia.net/data/flags/h80/sy.png\", \"Taiwan\": \"https://flagpedia.net/data/flags/h80/tw.png\", \"Tajikistan\": \"https://flagpedia.net/data/flags/h80/tj.png\", \"Tanzania\": \"https://flagpedia.net/data/flags/h80/tz.png\", \"Thailand\": \"https://flagpedia.net/data/flags/h80/th.png\", \"Timor-Leste\": \"https://flagpedia.net/data/flags/h80/tl.png\", \"Togo\": \"https://flagpedia.net/data/flags/h80/tg.png\", \"Tokelau\": \"https://flagpedia.net/data/flags/h80/tk.png\", \"Tonga\": \"https://flagpedia.net/data/flags/h80/to.png\", \"Trinidad and Tobago\": \"https://flagpedia.net/data/flags/h80/tt.png\", \"Tunisia\": \"https://flagpedia.net/data/flags/h80/tn.png\", \"Turkey\": \"https://flagpedia.net/data/flags/h80/tr.png\", \"Turkmenistan\": \"https://flagpedia.net/data/flags/h80/tm.png\", \"Turks and Caicos Islands\": \"https://flagpedia.net/data/flags/h80/tc.png\", \"Tuvalu\": \"https://flagpedia.net/data/flags/h80/tv.png\", \"Uganda\": \"https://flagpedia.net/data/flags/h80/ug.png\", \"Ukraine\": \"https://flagpedia.net/data/flags/h80/ua.png\", \"United Arab Emirates\": \"https://flagpedia.net/data/flags/h80/ae.png\", \"United Kingdom\": \"https://flagpedia.net/data/flags/h80/gb.png\", \"United States\": \"https://flagpedia.net/data/flags/h80/us.png\", \"United States Minor Outlying Islands\": \"https://flagpedia.net/data/flags/h80/um.png\", \"Uruguay\": \"https://flagpedia.net/data/flags/h80/uy.png\", \"Uzbekistan\": \"https://flagpedia.net/data/flags/h80/uz.png\", \"Vanuatu\": \"https://flagpedia.net/data/flags/h80/vu.png\", \"Vatican City\": \"https://flagpedia.net/data/flags/h80/va.png\", \"Venezuela\": \"https://flagpedia.net/data/flags/h80/ve.png\", \"Vietnam\": \"https://flagpedia.net/data/flags/h80/vn.png\", \"British Virgin Islands\": \"https://flagpedia.net/data/flags/h80/vg.png\", \"United States Virgin Islands\": \"https://flagpedia.net/data/flags/h80/vi.png\", \"Wales\": \"https://flagpedia.net/data/flags/h80/gb-wls.png\", \"Wallis and Futuna\": \"https://flagpedia.net/data/flags/h80/wf.png\", \"Western Sahara\": \"https://flagpedia.net/data/flags/h80/eh.png\", \"Yemen\": \"https://flagpedia.net/data/flags/h80/ye.png\", \"Zambia\": \"https://flagpedia.net/data/flags/h80/zm.png\", \"Zimbabwe\": \"https://flagpedia.net/data/flags/h80/zw.png\"}";

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

        flagMap =  JSONObject.parseObject(fJson);
        flagMap.put("US", "https://flagpedia.net/data/flags/h160/us.webp");
        flagMap.put("Korea, South", "https://flagpedia.net/data/flags/h160/kr.webp");
        flagMap.put("Czechia", "https://flagpedia.net/data/flags/h160/cz.webp");
    }

    public static void initCountry() {

        String[] locales = Locale.getISOCountries();

        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
            countryMap.put(obj.getDisplayCountry(Locale.ENGLISH), obj.getDisplayCountry(Locale.CHINA));
        }
        System.out.println(JSONObject.toJSONString(countryMap));

    }

    public static void main(String[] args) {
        //
        initCountry();
    }

//    public static void initFlag() {
//
//        String fileName = "/Users/mukong/Desktop/covid/flags.xlsx";
//
//        // 这里 只要，然后读取第一个sheet 同步读取会自动finish
//        EasyExcel.read(fileName, new FlagDataListener()).sheet().doRead();
//        FlagDataListener.list.forEach(x -> flagMap.put(x.get(0), x.get(1)));
//    }

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
        Object flag = flagMap.get(name);
        if (flag != null) {
            return new ImmutablePair<>(countryName, flagMap.get(name).toString());
        } else {
            return new ImmutablePair<>(countryName, null);
        }
    }

}