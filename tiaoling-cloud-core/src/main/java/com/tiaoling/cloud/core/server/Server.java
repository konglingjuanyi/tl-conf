package com.tiaoling.cloud.core.server;

import com.tiaoling.cloud.core.server.spring.ApplicationContextHolder;
import com.tiaoling.cloud.core.server.spring.DispatcherServletChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yhl on 2016/9/29.
 */
public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private int port;
    private ServerBootstrap serverBootstrap;
    private DispatcherServletChannelInitializer childHandler;
    public Server(int port)
    {
        this.port=port;
    }
    public void run() throws Exception
    {
        if(serverBootstrap!=null) throw new Exception("服务器已启动，请勿重复启动！");
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(new NioEventLoopGroup(),new NioEventLoopGroup()).channel(NioServerSocketChannel.class);
        ApplicationContextHolder.init();
        childHandler =new DispatcherServletChannelInitializer();
        serverBootstrap.childHandler(childHandler);
        ChannelFuture future = serverBootstrap.localAddress(port).bind().sync();
        future.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                logger.info("服务器启动成功");
            }
        });
        future.channel().closeFuture().sync();
    }

    public DispatcherServletChannelInitializer getDispatcherServletChannelInitializer()
    {
        return childHandler;
    }
    public ServerBootstrap getBootstrap()
    {
        return serverBootstrap;
    }

}
