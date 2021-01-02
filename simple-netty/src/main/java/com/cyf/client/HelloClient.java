package com.cyf.client;

import com.cyf.client.handler.HelloClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author 陈一锋
 * @date 2021/1/2.
 */
public class HelloClient {

    private final String host;
    private final int port;
    private final String messages;

    public HelloClient(String host, int port, String messages) {
        this.host = host;
        this.port = port;
        this.messages = messages;
    }

    private void start() throws InterruptedException {
        //创建线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //创建客户端启动指引类
            Bootstrap b = new Bootstrap();
            //指定线程组
            b.group(group)
                    //指定io模型
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new HelloClientHandler(messages));
                        }
                    });
            //创建连接
            ChannelFuture future = b.connect(host, port).addListener(future1 -> {
                        if (future1.isSuccess()) {
                            System.out.println("连接成功");
                        } else {
                            System.out.println("连接失败");
                        }
                    }
            ).sync();
            //等待关闭连接 阻塞直至Channel关闭
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new HelloClient("127.0.0.1", 8888, "你好,我第一次使用netty").start();
    }
}
