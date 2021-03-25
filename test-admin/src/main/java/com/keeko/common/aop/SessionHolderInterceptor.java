package com.keeko.common.aop;

import com.keeko.common.exception.AdminException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 静态调用session的拦截器
 *
 * @author Yelq
 */
@Aspect
@Component
public class SessionHolderInterceptor {

    @Pointcut("execution(* com.keeko.*..controller.*.*(..))")
    public void getSsession() {
    }

    /**
     * 两种方式获取session中的参数
     *
     * @author Yelq
     */
    @Around("getSsession()")
    public Object sessionKit(ProceedingJoinPoint point) throws Throwable {
        //1:
        Object[] args = point.getArgs();
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                System.out.println("userId======");
                HttpServletRequest request = (HttpServletRequest) arg;
                HttpSession session = request.getSession();
                Object userId = session.getAttribute("userId");
                System.out.println(userId);
            }
        }

        //2:
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object userId = session.getAttribute("userId");
        if (userId != null) {
            System.out.println(userId.toString());
        }
        //验证token若是已经过期或者不是本平台的 抛异常
        //throw new AdminException("自定义异常=======请先登录");
        return point.proceed();
    }


}
