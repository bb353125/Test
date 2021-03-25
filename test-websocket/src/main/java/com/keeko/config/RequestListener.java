package com.keeko.config;

import org.springframework.stereotype.Component;

import javax.servlet.AsyncContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.Map;

/**
 * 监听器类:主要任务是用ServletRequest将我们的HttpSession携带过去
 */
@Component //此注解千万千万不要忘记，它的主要作用就是将这个监听器纳入到Spring容器中进行管理,相当于注册监听吧
public class RequestListener implements ServletRequestListener{

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        ServletRequest servletRequest = sre.getServletRequest();
        //1:根据字段获取参数
        String[] openids = servletRequest.getParameterValues("openid");
        //2:根据字段获取参数
        String mac = servletRequest.getParameter("mac");
        //3:获取当前所有请求参数
        Map<String, String[]> parameterMap = servletRequest.getParameterMap();

        new WebSocket().onMessage(mac);

        //将所有request请求都携带上httpSession
        HttpSession httpSession = ((HttpServletRequest) sre.getServletRequest()).getSession();


        System.out.println("将所有request请求都携带上httpSession " + httpSession.getId());
    }

    public RequestListener() {
    }

    @Override
    public void requestDestroyed(ServletRequestEvent arg0) {
    }
}
