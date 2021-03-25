package com.keeko.common.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.keeko.common.annotation.BussinessLog;
import com.keeko.master.entity.User;
import com.keeko.utils.HttpUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 日志记录
 *
 * @author Yelq
 */
@Aspect
@Component
public class LogAop {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut(value = "@annotation(com.keeko.common.annotation.BussinessLog)")
    public void cutService() {}

    @Around("cutService()")
    public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {

        //先执行业务
        Object result = point.proceed();

        try {
            handle(point);
        } catch (Exception e) {
            log.error("日志记录出错!", e);
        }

        return result;
    }

    private void handle(ProceedingJoinPoint point) throws NoSuchMethodException, SecurityException, IllegalAccessException, InstantiationException  {

        //获取拦截的方法名
        Signature sig = point.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        String methodName = currentMethod.getName();

        Object[] args = point.getArgs();
        String string = args.toString();
        System.out.println(string);
        JSONObject argsJson = (JSONObject) JSON.toJSON(args[0]);
        System.out.println(argsJson);
        //如果当前用户未登录，不做日志
        /*ShiroUser user = ShiroKit.getUser();
        if (null == user) {
            return;
        }*/

        //获取拦截方法的参数
        String className = point.getTarget().getClass().getName();
        Object[] params = point.getArgs();

        //获取操作名称
        BussinessLog annotation = currentMethod.getAnnotation(BussinessLog.class);
        String bussinessName = annotation.value();
        String key = annotation.key();
        Class<?> dictClass = annotation.dict();
        String param = annotation.param();

        //1: 直接传参数进来 优点是无论什么样的请求都可以获取出参数
        Object object = getUser(param, point);
        JSONObject json = (JSONObject) JSON.toJSON(object);
        System.out.println(json);
        System.out.println(json.toString());
        System.out.println(object.toString());
        User user = (User)getUser(param,point);
        System.out.println(user.getId());
        System.out.println(user.getName());
        System.out.println(user.getUsetList().toString());

        StringBuilder sb = new StringBuilder();
        for (int i = 0,length = params.length; i < length; i++){
        	 sb.append(params[i]);
             sb.append(" & ");
        }

        //2: 从Request中获取出参数
        Map<String, String> map = HttpUtil.getRequestParameters();
        System.out.println(map.toString());
    }

    public Object getUser(String param,ProceedingJoinPoint point){
        Method method = ((MethodSignature)point.getSignature()).getMethod();
        String[] parameterNames = new LocalVariableTableParameterNameDiscoverer().getParameterNames(method);
        return SpelParser.getUser(param, parameterNames, point.getArgs());
    }
}