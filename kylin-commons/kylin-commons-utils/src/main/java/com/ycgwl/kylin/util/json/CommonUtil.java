package com.ycgwl.kylin.util.json;

import com.ycgwl.kylin.exception.BusinessException;

public class CommonUtil {

    private enum EnumObjectMapper {

        SINGLETON_OBJECT_MAPPER;

        private com.fasterxml.jackson.databind.ObjectMapper instance;

        private EnumObjectMapper() {
            instance = new com.fasterxml.jackson.databind.ObjectMapper();
        }

        public com.fasterxml.jackson.databind.ObjectMapper getInstance() {
            return instance;
        }
    }

    /**
     * 获取 {@link com.fasterxml.jackson.databind.ObjectMapper} 实例
     * <p>
     *
     * @return
     * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2017-05-25 12:55:51
     */
    public static com.fasterxml.jackson.databind.ObjectMapper objectMapper() {
        return EnumObjectMapper.SINGLETON_OBJECT_MAPPER.getInstance();
    }

    /**
     * 将java对象转成json字符串
     * <p>
     *
     * @param object java对象
     * @return 转换后的json字符串
     * @throws BusinessException 转换异常时
     * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2017-05-25 13:08:23
     */
    public static String toJsonString(Object object) throws BusinessException {
        try {
            return objectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new BusinessException("Java对象转换Json字符串异常", e);
        }
    }

    /**
     * 将json字符串转换成java对象
     * <p>
     *
     * @param valueType  要转换的java对象类型
     * @param jsonString json字符换
     * @return 转换的java对象
     * @throws BusinessException 转换异常时
     * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2017-05-25 13:09:17
     */
    public static <T extends Object> T toJavaObject(Class<T> valueType, String jsonString) throws BusinessException {
        try {
            return objectMapper().readValue(jsonString, valueType);
        } catch (Exception e) {
            throw new BusinessException("Json字符串转换Java对象异常", e);
        }
    }

    /**
     * 运单号不足12位的首位补0
     *
     * @param transportCode
     * @return
     */
    public static String completeFirstTransportCode(String transportCode, int totalCount) {
        if (transportCode.length() < totalCount) { //如果运单<=11位，就在首位补全0
            int zeroCount = 11 - transportCode.length();
            do {
                transportCode = "0" + transportCode;
                zeroCount -= 1;
            } while (zeroCount > 0);
        }
        return transportCode;
    }
}
