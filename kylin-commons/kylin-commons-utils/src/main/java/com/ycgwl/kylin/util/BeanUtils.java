package com.ycgwl.kylin.util;


import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * Created by hp001 on 2017/9/12.
 */
public class BeanUtils {
	
	/**
	 * 包含String->Date转化的对象复制
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2017-12-29 14:02:50
	 * @param t
	 * @param y
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static <T,Y> T copyPropertiesContainDate(T t,Y y) throws IllegalAccessException, InvocationTargetException {
		  DateConverter converter = new DateConverter();
	      converter.setPatterns(CommonDateUtil.PATTERNS);
	      ConvertUtils.register(converter,Date.class);
	      org.apache.commons.beanutils.BeanUtils.copyProperties(t, y);
		return t;
	}
	
    /**
     *
     * @param source 被复制的实体类对象
     * @param to 复制完后的实体类对象
     * @throws Exception
     */
    public static void Copy(Object source, Object to) throws Exception {
        // 获取属性
        BeanInfo sourceBean = Introspector.getBeanInfo(source.getClass(),Object.class);
        PropertyDescriptor[] sourceProperty = sourceBean.getPropertyDescriptors();

        BeanInfo destBean = Introspector.getBeanInfo(to.getClass(),Object.class);
        PropertyDescriptor[] destProperty = destBean.getPropertyDescriptors();

        try {
            for (int i = 0; i < sourceProperty.length; i++) {

                for (int j = 0; j < destProperty.length; j++) {

                    if (sourceProperty[i].getName().equals(destProperty[j].getName())) {
                        // 调用source的getter方法和dest的setter方法
                        destProperty[j].getWriteMethod().invoke(to,sourceProperty[i].getReadMethod().invoke(source));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("属性复制失败:" + e.getMessage());
        }
    }
}
