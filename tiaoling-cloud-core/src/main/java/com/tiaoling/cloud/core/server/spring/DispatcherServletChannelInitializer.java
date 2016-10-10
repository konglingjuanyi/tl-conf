package com.tiaoling.cloud.core.server.spring;

import com.tiaoling.cloud.core.utils.PropertyConfigurer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yhl on 2016/9/29.
 */
public class DispatcherServletChannelInitializer extends ChannelInitializer<SocketChannel> {
    private static Logger logger = LoggerFactory.getLogger(DispatcherServletChannelInitializer.class);
    private final Map<String,DispatcherServlet> dispatcherServlet;
    private final MockServletContext servletContext;
    private Map<String,String> servletMappings;
    public DispatcherServletChannelInitializer() throws Exception
    {
        logger.info("开始初始化spring和springmvc！");
        dispatcherServlet = new ConcurrentHashMap<String, DispatcherServlet>();
        servletContext = new MockServletContext();
        //读取配置的servlet属性
        String[] properties = PropertyConfigurer.getContextProperty("system.servlets").split(",");
        for (String property:properties)
        {
            MockServletConfig servletConfig = new MockServletConfig(servletContext,property);
            DispatcherServlet itemDispatcherServlet =  new DispatcherServlet(ApplicationContextHolder.getMvcContext());
            itemDispatcherServlet.init(servletConfig);
            dispatcherServlet.put(property,itemDispatcherServlet);
        }
        servletMappings = new ConcurrentHashMap<String,String>();
        String[] sufStrs = PropertyConfigurer.getContextProperty("system.suffix").split(",");
        for (String str:sufStrs)
        {
            String[] sletsuf = str.split(":");
            servletMappings.put(sletsuf[1],sletsuf[0]);
        }
        logger.info("spring和springmvc的初始化已完成");
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast("decoder", new HttpRequestDecoder());
        pipeline.addLast("aggregator", new HttpObjectAggregator(3048576)); // 消息最大3M
        pipeline.addLast("encoder", new HttpResponseEncoder());
        pipeline.addLast("chunkedWriter", new ChunkedWriteHandler());
        pipeline.addLast("handler",new DispatcherServletHandler(this.dispatcherServlet,
                this.servletContext,this.servletMappings));
    }
    public Map<String,DispatcherServlet> getDispatcherServlet()
    {
        return dispatcherServlet;
    }

}
