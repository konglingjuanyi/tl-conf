package com.tiaoling.cloud.conf.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by yhl on 2016/10/9.
 */
public class CommonPropertiesUtils {
    private static Properties properties = new Properties();

    static {
        try {
            properties.load(CommonPropertiesUtils.class.getClassLoader().getResourceAsStream("common.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
