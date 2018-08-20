package com.ycgwl.kylin.util;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author <a href="mailto:108252@ycgwl.com">万玉杰</a>
 * @version 需求对应版本号
 * @Description: 统一整理日期转化格式
 * @date 2017年11月7日 上午8:34:21
 */
public class CommonDateUtil {

    public static final String PATTERN1 = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN2 = "yyyyMMddHHmmss";

    public static final String[] PATTERNS = {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "yyyy-MM", "yyyy-MM-dd HH:mm",
            "yyyy/MM/dd HH:mm", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd", "yyyy/MM"};


    /**
     * @param date    要转换的日期
     * @param pattern 格式
     * @return
     * @throws
     * @Description: 日期转字符串  .注意:使用者自己捕获异常
     */
    public static String DateToString(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    /**
     * @param date 日期字符串
     * @return
     * @throws ParseException
     * @throws
     * @Description: 对字符串严格规范的 字符->日期 转化
     */
    public static Date StrictlyStringToDate(String date) throws ParseException {
        return DateUtils.parseDateStrictly(date, PATTERNS);
    }

    /**
     * @param date
     * @return
     * @throws ParseException
     * @throws
     * @Description: 不严格规范字符串的 字符->日期 之间的转化
     */
    public static Date StringToDate(String date) throws ParseException {
        return DateUtils.parseDate(date, PATTERNS);
    }

    /**
     * 判断当前日期是否在 gap 天数之内
     *
     * @param date 校验的日期
     * @param gap  多少天之内
     * @return
     * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2017-12-19 10:29:56
     */
    public static Boolean HalfMonthWithIn(Date date, Long gap) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate plusDays = localDate.plusDays(gap);
        return plusDays.compareTo(LocalDate.now()) > 0;
    }

    public static String getTodayByFormat(String timeFormat) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(timeFormat));
    }


    public static HashMap<String, Object> FormatDate(HashMap<String, Object> map) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        Set<Map.Entry<String, Object>> entrySet = map.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            if (entry.getValue().getClass().isAssignableFrom(Date.class)) {
                result.put(entry.getKey(), DateToString((Date) entry.getValue(), CommonDateUtil.PATTERN1));
            } else {
              result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    public static List<HashMap<String, Object>> FormatDate(Collection<HashMap<String, Object>> list) {
        List<HashMap<String, Object>> result = new ArrayList<>();
        for (HashMap<String, Object> map : list) {
            HashMap<String, Object> formatDate = FormatDate(map);
            result.add(formatDate);
        }
        return result;
    }


    public static void main(String[] args) throws ParseException {
        System.out.println(getTodayByFormat(CommonDateUtil.PATTERN2));
    }
}
