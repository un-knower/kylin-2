package com.ycgwl.kylin.web.transport.util;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 读取Properties综合类,默认绑定到classpath下的config.properties文件。
 */
public class PropertiesUtil {
    // 配置文件的路径
    private String configPath = null;

    /**
     * 配置文件对象
     */
    private static Properties props = null;

    // 初始化配置
    static {
        // InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream("config.properties");
        //
        // try {
        // props = new Properties();
        // props.load(in);
        // in.close();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
    }

    /**
     * 默认构造函数，用于sh运行，自动找到classpath下的config.properties。
     */
    public PropertiesUtil() throws IOException {
        if (null == props) {
            InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream("conf/application.properties");
            props = new Properties();
            props.load(in);
            // 关闭资源
            in.close();
        }
    }

    /**
     * 获取配置文件
     */
    public static void checkConfig() {
        if (null == props) {
            try {
                InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream("conf/application.properties");
                props = new Properties();
                props.load(in);
                // 关闭资源
                in.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * 根据key值读取配置的值
     * @param key key值
     * @return key 键对应的值
     * @throws IOException iOException
     */
    public static String readValue(String key) {
        checkConfig();
        return props.getProperty(key);
    }

    /**
     * 读取properties的全部信息
     * @throws FileNotFoundException 配置文件没有找到
     * @throws IOException 关闭资源文件，或者加载配置文件错误
     */
    public Map<String, String> readAllProperties() throws FileNotFoundException, IOException {
        // 保存所有的键值
        Map<String, String> map = new HashMap<String, String>();
        @SuppressWarnings("rawtypes")
        Enumeration en = props.propertyNames();
        while (en.hasMoreElements()) {
            String key = (String) en.nextElement();
            String property = props.getProperty(key);
            map.put(key, property);
        }
        return map;
    }

    /**
     * 设置某个key的值,并保存至文件。
     * @param key key值
     * @param value 键对应的值
     * @throws IOException iOException
     */
    public void setValue(String key, String value) throws IOException {
        Properties prop = new Properties();
        InputStream fis = new FileInputStream(this.configPath);
        // 从输入流中读取属性列表（键和元素对）
        prop.load(fis);
        // 调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
        // 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
        OutputStream fos = new FileOutputStream(this.configPath);
        prop.setProperty(key, value);
        // 以适合使用 load 方法加载到 Properties 表中的格式，
        // 将此 Properties 表中的属性列表（键和元素对）写入输出流
        prop.store(fos, "last update");
        // 关闭文件
        fis.close();
        fos.close();
    }

    /**
     * @param fileName 路径名称.
     * @return Properties
     * @throws IOException iOException
     */
    public static Properties loadPropertiesFromSrc(String fileName) throws IOException {
        InputStream in = null;
        try {
            Properties properties = null;
            in = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName);
            if (null != in) {
                BufferedReader bf = new BufferedReader(new InputStreamReader(in, "utf-8"));
                properties = new Properties();
                try {
                    properties.load(bf);
                } catch (IOException ioException) {
                    throw ioException;
                }
            }

            return properties;
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

}
