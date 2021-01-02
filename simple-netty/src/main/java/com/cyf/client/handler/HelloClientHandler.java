package com.cyf.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import static io.netty.util.CharsetUtil.UTF_8;

/**
 * @author 陈一锋
 * @date 2021/1/2.
 */
public class HelloClientHandler extends ChannelInboundHandlerAdapter {
    private final String messages;

    public HelloClientHandler(String messages) {
        this.messages = messages;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client send msg is"+messages);
        //发送消息
        ctx.writeAndFlush(Unpooled.copiedBuffer(messages,UTF_8));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf)msg;
        //接受到消息
        try {
            System.out.println("client receive msg from server :"+in.toString(UTF_8));
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //自定义异常
        cause.printStackTrace();
        ctx.close();
    }
}
