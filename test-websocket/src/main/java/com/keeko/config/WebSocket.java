package com.keeko.config;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

//configurator = WebsocketConfig.class 该属性就是我上面配置的信息
@ServerEndpoint(value = "/websocket", configurator = WebSocketConfig.class)
@Component    //监听器纳入到Spring容器中进行管理
public class WebSocket {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<WebSocket>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     * <p>
     * config用来获取WebsocketConfig中的配置信息
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {

        //获取WebsocketConfig.java中配置的“sessionId”信息值
        String httpSessionId = (String) config.getUserProperties().get("sessionId");

        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
        try {
            //sendMessage("Hello world");
            sendMessage("已连接");
        } catch (IOException e) {
            System.out.println("IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * 1:可以对所有用户发消息
     * 2:可以对单个用户发消息
     * @param message 这个传json字符串比较合适 在这里解析出mac
     *
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message/*, Session session*/) {
        if (StringUtils.isEmpty(message)){
            return;
        }
        System.out.println("来自客户端的消息:" + message);
        String requestMac = message.substring(0, 3);
        //群发消息
        for (WebSocket item : webSocketSet) {
            //初始化链接最开始时的时候传mac过来 如send.ftl中 -> ws://localhost:8080/websocket?mac=456
            String queryString = item.session.getQueryString();
            String linkMac = "";
            if (queryString != null && queryString != ""){
                //链接最开始时的mac
                linkMac = queryString.substring(queryString.indexOf("=") + 1);
            }
            if (linkMac.equals(requestMac)) {
                //在这个时候再执行推送当前对应设备消息
                System.out.println("在这个时候再执行推送当前对应设备消息" + linkMac + requestMac);
            }
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }


    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message) throws IOException {
        for (WebSocket item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocket.onlineCount--;
    }
}
