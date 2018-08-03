package com.ycgwl.kylin;

import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SecurityDubboServer {

	/** slf4j logger api */
	protected static final Logger logger = LoggerFactory.getLogger(SecurityDubboServer.class);
    
	private static volatile boolean running = true;

    public static void main(String[] args) {
        try {
            long beginTime = System.currentTimeMillis();
    		String activeProfile = "dev";
    		if (args != null && args.length > 0) {
    			activeProfile = args[0];
    		}
    		logger.info("======profile: {} ======", activeProfile);
    		
    		String currentpath = System.getProperty("user.dir");
    		
    		final GenericXmlApplicationContext applicationContext = new GenericXmlApplicationContext();
    		applicationContext.getEnvironment().setActiveProfiles(activeProfile);
    		applicationContext.load("classpath:spring.xml");
    		
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                	try {
                		applicationContext.stop();
                		applicationContext.close();
                        logger.info("======Dubbo service server stopped======");
                        System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date()) + " Dubbo service server stopped!");
                    } catch (Throwable t) {
                        logger.error(t.getMessage(), t);
                    }
                    synchronized (SecurityDubboServer.class) {
                        running = false;
                        SecurityDubboServer.class.notify();
                    }
                }
            });
    		applicationContext.refresh();
            applicationContext.start();
            logger.info("======Dubbo service server[{}] started in: {} ms======", activeProfile, (System.currentTimeMillis() - beginTime));
            
            String dateString = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
            System.out.println(dateString + " Current Path : "+ currentpath);
            System.out.println(dateString + " Dubbo service server started!");
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            System.exit(1);
        }
        synchronized (SecurityDubboServer.class) {
            while (running) {
                try {
                	SecurityDubboServer.class.wait();
                } catch (Throwable e) { }
            }
        }
    }
}