package com.cyf.server.hander;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import static io.netty.util.CharsetUtil.UTF_8;

/**
 * 自定义收到消息后处理逻辑
 *
 * @author 陈一锋
 * @date 2021/1/2.
 */
public class HelloServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("message form client:" + in.toString(UTF_8));
        // 发送消息给客户端
        ctx.writeAndFlush(Unpooled.copiedBuffer("你好客服端,我收到了你的消息", UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //当发生错误会触发此方法
        cause.printStackTrace();
        ctx.close();
    }
}
