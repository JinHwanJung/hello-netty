package com.jinhwan.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // Discard the received data silently
        ByteBuf in = (ByteBuf) msg;
        try {
            while (in.isReadable()) {
                System.out.println((char)in.readByte());
                System.out.flush();
            }

        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

// Usually, channelRead() handler method is implemented like the flowing:
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        try {
//            // Do something with msg
//        } finally {
//            ReferenceCountUtil.release(msg);
//        }
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
