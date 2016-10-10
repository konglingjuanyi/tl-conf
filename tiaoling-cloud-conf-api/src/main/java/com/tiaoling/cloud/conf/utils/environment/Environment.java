package com.tiaoling.cloud.conf.utils.environment;

import com.tiaoling.cloud.core.utils.PropertyConfigurer;

import java.util.Properties;

/**
 * 环境基类
 * Created by yhl on 2016/10/10.
 */
public class Environment {
    /**
     * conf data path in zk
     */
    public static final String CONF_DATA_PATH = PropertyConfigurer.getContextProperty("conf_data_path");


    /**
     * zk address
     */
    public static final String ZK_ADDRESS = PropertyConfigurer.getContextProperty("zk_server");		// zk地址：格式	ip1:port,ip2:port,ip3:port

}
