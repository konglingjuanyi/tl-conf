package com.tiaoling.cloud.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by yhl on 2016/9/29.
 */
public class PropertyConfigurer extends PropertyPlaceholderConfigurer {
    private static Logger logger = LoggerFactory.getLogger(PropertyConfigurer.class);
    private static Map<String,Object> ctxPropertiesMap;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        ctxPropertiesMap = new HashMap<String,Object>();
        for (Object key : props.keySet())
        {
            String keyStr = key.toString();
            String keyValue = props.getProperty(keyStr);
            ctxPropertiesMap.put(keyStr,keyValue);
        }
    }
    public static String getContextProperty(String name)
    {
        return ctxPropertiesMap.get(name).toString();
    }

}
