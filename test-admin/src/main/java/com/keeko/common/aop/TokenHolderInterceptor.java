package com.keeko.common.aop;

import com.keeko.common.ResultResponse;
import com.keeko.common.annotation.TokenAnnotation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 测试token拦截
 *
 * @author chensq
 */
@Aspect
@Component
public class TokenHolderInterceptor {

    /**
     * 测试拦截
     *
     * @param point           切入点
     * @param tokenAnnotation token自定义注解
     */
    @Around("@annotation(tokenAnnotation)")
    public Object login(ProceedingJoinPoint point, TokenAnnotation tokenAnnotation) throws Throwable {
        Object ret = point.proceed();
        //获取传来的token @SessionAnnotation(value = "#token") 必须带#号
        String token = getToken(tokenAnnotation.value(), point);
        //比较真实类型
        if (ret instanceof ResultResponse) {
            ResultResponse result = (ResultResponse) ret;
            //比如改变返回的msg
            String msg = result.getMsg();
            System.out.println(msg);
            result.setMsg("你需要返回的信息");
            return result;
        }
        String value = tokenAnnotation.value();
        System.out.println(token);
        System.out.println(value);
        return ret;
    }

    public String getToken(String token, ProceedingJoinPoint point) {
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        String[] parameterNames = new LocalVariableTableParameterNameDiscoverer().getParameterNames(method);
        return getToken(token, parameterNames, point.getArgs());
    }

    private static ExpressionParser parser = new SpelExpressionParser();

    /**
     * @param key         el表达式字符串，占位符以#开头
     * @param paramsNames 形参名称，可以理解为占位符名称
     * @param args        参数值,可以理解为占位符真实的值
     * @return 返回el表达式经过参数替换后的字符串值
     */
    public static String getToken(String key, String[] paramsNames, Object[] args) {
        Expression exp = parser.parseExpression(key); //将key字符串解析为el表达式
        EvaluationContext context = new StandardEvaluationContext();//初始化赋值上下文
        if (args.length <= 0) {
            return null;
        }
        //将形参和形参值以配对的方式配置到赋值上下文中
        for (int i = 0; i < args.length; i++) {
            context.setVariable(paramsNames[i], args[i]);
        }
        //根据赋值上下文运算el表达式
        return exp.getValue(context, String.class);
    }
}
