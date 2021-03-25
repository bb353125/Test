package com.keeko.config;

import org.apache.catalina.session.StandardSessionFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

/**
 * 开启WebSocket支持
 */
@Configuration
public class WebSocketConfig extends Configurator{

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        String mac = request.getQueryString();
        System.out.println("请求参数"+mac);
        //
        //sec.getUserProperties().put("mac", mac);
        /*如果没有监听器,那么这里获取到的HttpSession是null*/
        Object ssf = request.getHttpSession();
        if (ssf != null) {
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        //关键操作
        sec.getUserProperties().put("sessionId", httpSession.getId());
        System.out.println("获取到的SessionID：" + httpSession.getId());
        }
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
