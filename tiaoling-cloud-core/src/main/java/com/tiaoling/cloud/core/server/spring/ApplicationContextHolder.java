package com.tiaoling.cloud.core.server.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * Created by yhl on 2016/9/29.
 */
public class ApplicationContextHolder {
    private static Logger logger = LoggerFactory.getLogger(ApplicationContextHolder.class);

    //context
    private static ApplicationContext rootContext;
    private static XmlWebApplicationContext mvcContext;

    //路径前缀
    private static final String prePath="";

    public static void init()
    {
        if(mvcContext==null)
        {
            //初始化springmvc起始时间
            long start = System.currentTimeMillis();
            logger.info("开始初始化springmvc context.");
            mvcContext = new XmlWebApplicationContext();
            mvcContext.setConfigLocation("classpath*:"+prePath+"springmvc-context.xml");
            mvcContext.setParent(getRootContext());
            long end = System.currentTimeMillis();
            logger.info("完成springmvc context的初始化用时："+(end-start)+"ms.");
        }
    }

    public static ApplicationContext getRootContext()
    {
        if(rootContext==null)
        {
            //初始化ApplicationContext起始时间点
            long start = System.currentTimeMillis();
            logger.info("开始初始化spring context.");
            rootContext = new ClassPathXmlApplicationContext("classpath*:"+prePath+"applicationContext.xml");
            long end = System.currentTimeMillis();
            logger.info("完成spring context的初始化用时："+(end-start)+"ms.");
        }
        return rootContext;
    }
    public static WebApplicationContext getMvcContext()
    {
        return mvcContext;
    }
}
