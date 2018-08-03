package com.ycgwl.kylin.entity;

import com.ycgwl.kylin.exception.ParameterException;
import org.apache.commons.lang.time.DateUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

public class RequestJsonEntity extends HashMap<String, Object> {

    public static final String[] PATTERNS = {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "yyyy-MM", "yyyy-MM-dd HH:mm",
            "yyyy/MM/dd HH:mm", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd", "yyyy/MM"};

    private static final long serialVersionUID = 1L;


    public String getString(String key) {
        String value = String.valueOf(super.get(key));
        if ("null".equals(value))
            return "";
        return value.trim();
    }

    public Integer getInteger(String key) {
        try {
            return Integer.parseInt(getString(key));
        } catch (Exception e) {
            return 0;
        }
    }

    public Date getDate(String key) throws ParseException {
        Object object = get(key);
        if (object == null) {
            throw new ParameterException("请输入正确的日期时间");
        }
        if (object instanceof Date) {
            return (Date) object;
        }
        return DateUtils.parseDateStrictly(getString(key), PATTERNS);
    }

    public BigDecimal getBigDecimal(String key) {
        try {
            return new BigDecimal(getString(key));
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

}
