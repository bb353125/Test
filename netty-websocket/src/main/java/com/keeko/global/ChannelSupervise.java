package com.keeko.global;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 *
 * 4.保存客户端的信息
 * 当有客户端连接时候会被channelActive监听到，当断开时会被channelInactive监听到，一般在这两个方法中去保存/移除客户端的通道信息，而通道信息保存在ChannelSupervise中：
 *
 * 作者：rpf_siwash
 * 链接：https://www.jianshu.com/p/56216d1052d7
 * 来源：简书
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
public class ChannelSupervise {

    private static ChannelGroup GlobalGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static ConcurrentMap<String, ChannelId> ChannelMap = new ConcurrentHashMap();

    public static void addChannel(Channel channel) {
        GlobalGroup.add(channel);
        ChannelMap.put(channel.id().asShortText(), channel.id());
    }

    public static void removeChannel(Channel channel) {
        GlobalGroup.remove(channel);
        ChannelMap.remove(channel.id().asShortText());
    }

    public static Channel findChannel(String id) {
        return GlobalGroup.find(ChannelMap.get(id));
    }

    public static void send2All(TextWebSocketFrame tws) {
        GlobalGroup.writeAndFlush(tws);
    }
}
