package com.ycgwl.kylin.util.spring;

import com.ycgwl.kylin.util.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * @author <a href="mailto:108252@ycgwl.com">wyj</a>
 * @version 需求对应版本号
 * @Description: 自定义配置文件加载过程
 * @date 2017年9月25日 下午3:01:59
 */
public class PropertiesFactoryBean extends org.springframework.beans.factory.config.PropertiesFactoryBean {

    private final Logger logger = LoggerFactory.getLogger(PropertiesFactoryBean.class);

    @Override
    protected void loadProperties(Properties props) throws IOException {
        super.loadProperties(props);
        logger.info("系统配置信息如下:");
        logger.info("=============================================================================================");
        if (props.isEmpty()) {
            logger.info("没有任何配置信息");
        } else {
            for (Map.Entry<Object, Object> entry : props.entrySet()) {
                SystemUtils.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
                logger.info("{} = {}", entry.getKey(), entry.getValue());
            }
        }
        logger.info("=============================================================================================");
    }

}
