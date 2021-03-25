package com.keeko.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.condition.ConsumesRequestCondition;
import org.springframework.web.servlet.mvc.condition.HeadersRequestCondition;
import org.springframework.web.servlet.mvc.condition.MediaTypeExpression;
import org.springframework.web.servlet.mvc.condition.ParamsRequestCondition;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.ProducesRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 新增mapping至spring容器
 *
 * @author chensq
 */
@Component
public class SpringMappingUtils {

   /* @PostConstruct
    public void add() throws Exception {
        registoryInit();
    }*/
   private final RequestMappingHandlerMapping requestMappingHanderMapping;
   private final AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor;
   private final ConfigurableListableBeanFactory factory;

    @Autowired
    public SpringMappingUtils(RequestMappingHandlerMapping requestMappingHanderMapping,
                              AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor,
                              ConfigurableListableBeanFactory factory) {
        this.requestMappingHanderMapping = requestMappingHanderMapping;
        this.autowiredAnnotationBeanPostProcessor = autowiredAnnotationBeanPostProcessor;
        this.factory = factory;
    }

    /**
     * 新增mapping至spring容器
     * https://blog.csdn.net/x369201170/article/details/85054217   //参考
     *
     * @param clazz 泛型类: 需要注入容器的类 如: A.class
     * @param mappingUrl 路径: 如: GetMapping("/test")中的 "/test"
     * @param requestMethod 请求方法类型: 如: RequestMethod.GET RequestMethod.POST
     * @param methodName 方法名: 如: getInfo
     * @param object 参数对象: 1:无参数方法传null 2:有参数传对象类
     */
    public <T> void registory(Class<T> clazz, String mappingUrl, RequestMethod requestMethod, String methodName, Class... object) throws IllegalAccessException, InstantiationException {
        T c = clazz.newInstance();
        Class[] paramClazz;//指定需要带什么类型的参数
        if (object != null){
            paramClazz = object;
        } else {
            paramClazz = new Class[]{};
        }
        PatternsRequestCondition patternsRequestCondition = new PatternsRequestCondition(mappingUrl);
        //请求类型
        RequestMethodsRequestCondition requestMethodsRequestCondition = new RequestMethodsRequestCondition(requestMethod);
        ParamsRequestCondition paramsRequestCondition = new ParamsRequestCondition();
        HeadersRequestCondition headersRequestCondition = new HeadersRequestCondition();
        ConsumesRequestCondition consumesRequestCondition = new ConsumesRequestCondition();
        ProducesRequestCondition producesRequestCondition = new ProducesRequestCondition();
        MediaTypeExpression mediaTypeExpression = new MediaTypeExpression() {
            @Override
            public MediaType getMediaType() {
                return MediaType.APPLICATION_JSON_UTF8;
            }

            @Override
            public boolean isNegated() {
                return false;
            }
        };
        producesRequestCondition.getExpressions().add(mediaTypeExpression);
        RequestMappingInfo requestMappingInfo = new RequestMappingInfo("test",
                patternsRequestCondition,
                requestMethodsRequestCondition,
                paramsRequestCondition,
                headersRequestCondition,
                consumesRequestCondition,
                producesRequestCondition,
                null);
        Method targetMethod = ReflectionUtils.findMethod(clazz, methodName, paramClazz); // 找到处理该路由的方法
        requestMappingHanderMapping.registerMapping(requestMappingInfo, c, targetMethod);
    }


    /**
     * 新增mapping至spring容器
     * https://blog.csdn.net/hhj13978064496/article/details/92794095   //参考
     *
     * @param clazz 泛型类: 需要注入容器的类 如: A.class
     */
    public <T> void test(Class<T> clazz) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        //A test = new A();
        T c = clazz.newInstance();
        PatternsRequestCondition patternsRequestCondition = new PatternsRequestCondition("/test");
        RequestMethodsRequestCondition requestMethodsRequestCondition = new RequestMethodsRequestCondition();
        ParamsRequestCondition paramsRequestCondition = new ParamsRequestCondition();
        HeadersRequestCondition headersRequestCondition = new HeadersRequestCondition();
        ConsumesRequestCondition consumesRequestCondition = new ConsumesRequestCondition();
        ProducesRequestCondition producesRequestCondition = new ProducesRequestCondition();
        MediaTypeExpression mediaTypeExpression = new MediaTypeExpression() {
            @Override
            public MediaType getMediaType() {
                return MediaType.APPLICATION_JSON_UTF8;
            }

            @Override
            public boolean isNegated() {
                return false;
            }
        };
        producesRequestCondition.getExpressions().add(mediaTypeExpression);
        RequestMappingInfo requestMappingInfo = new RequestMappingInfo("test", patternsRequestCondition, requestMethodsRequestCondition, paramsRequestCondition, headersRequestCondition, consumesRequestCondition, producesRequestCondition, null);
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) factory;
        autowiredAnnotationBeanPostProcessor.processInjection(c);
//        ((DefaultListableBeanFactory) factory).registerBeanDefinition("test", test);

        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        defaultListableBeanFactory.registerBeanDefinition(clazz.getName(), beanDefinitionBuilder.getBeanDefinition());
//        Test jobCaller = factory.getBean("test", Test.class);
//        defaultListableBeanFactory.destroySingleton("test");

        Class<? extends RequestMappingHandlerMapping> aClass = requestMappingHanderMapping.getClass();
        Class<?> superclass = aClass.getSuperclass().getSuperclass();
        Method detectHandlerMethods = superclass.getDeclaredMethod("detectHandlerMethods", Object.class);
        detectHandlerMethods.setAccessible(true);
        detectHandlerMethods.invoke(requestMappingHanderMapping, clazz.getName());
        requestMappingHanderMapping.registerMapping(requestMappingInfo, c, clazz.getMethod("a"));
    }

}
