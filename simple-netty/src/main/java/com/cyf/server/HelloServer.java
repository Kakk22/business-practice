package com.cyf.server;

import com.cyf.server.hander.HelloServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;

import static io.netty.handler.logging.LogLevel.INFO;

/**
 * @author 陈一锋
 * @date 2021/1/2.
 */
public class HelloServer {
    /**
     * 绑定端口
     */
    private final int port;

    public HelloServer(int port) {
        this.port = port;
    }

    private void start() throws InterruptedException {
        //1. bossGroup 用于处理连接请求  workGroup用于处理具体请求
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            //2.创建服务端启动引导辅助类 ServerBootstrap
            ServerBootstrap b = new ServerBootstrap();
            //3.给引导类配置两大线程组 确定线程模型
            b.group(bossGroup, workGroup)
                    // 打印日志
                    .handler(new LoggingHandler(INFO))
                    // 4.指定IO模型
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            //5.这里可以自定义客户端消息处理逻辑
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new HelloServerHandler());
                        }
                    });
            //6.绑定端口 调用sync方法阻塞直到绑定完成
            ChannelFuture future = b.bind(port).sync();
            //7.阻塞等待直到服务器Channel关闭(closeFuture()方法获取Channel 的CloseFuture对象,然后调用sync()方法)
            future.channel().closeFuture().sync();
        } finally {
            //8 优雅关闭线程资源
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new HelloServer(8888).start();
    }
}
