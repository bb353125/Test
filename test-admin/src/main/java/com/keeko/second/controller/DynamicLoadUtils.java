package com.keeko.second.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
//https://blog.csdn.net/qq_38206090/article/details/103101323?utm_medium=distribute.pc_relevant_bbs_down.none-task-blog-baidujs-1.nonecase&depth_1-utm_source=distribute.pc_relevant_bbs_down.none-task-blog-baidujs-1.nonecase
import java.lang.reflect.Method;

public class DynamicLoadUtils {
    //private static SpringContextUtil springContextUtil = ServiceBridgeApplication.springContextUtil;
    private static SpringContextUtil springContextUtil = new SpringContextUtil();

    public static void registerController(String controllerBeanName) throws Exception {
        final RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) springContextUtil.getBean("requestMappingHandlerMapping");

        if (requestMappingHandlerMapping != null) {
            String handler = controllerBeanName;
            Object controller = springContextUtil.getBean(handler);
            if (controller == null) {
                return;
            }
            unregisterController(controllerBeanName);
            //注册Controller
            Method method = requestMappingHandlerMapping.getClass().getSuperclass().getSuperclass().
                    getDeclaredMethod("detectHandlerMethods", Object.class);
            method.setAccessible(true);
            method.invoke(requestMappingHandlerMapping, handler);
        }
    }

    public static void unregisterController(String controllerBeanName) {
        final RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping)
                springContextUtil.getBean("requestMappingHandlerMapping");
        if (requestMappingHandlerMapping != null) {
            String handler = controllerBeanName;
            Object controller = springContextUtil.getBean(handler);
            if (controller == null) {
                return;
            }
            final Class<?> targetClass = controller.getClass();
            ReflectionUtils.doWithMethods(targetClass, new ReflectionUtils.MethodCallback() {
                public void doWith(Method method) {
                    Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
                    try {
                        Method createMappingMethod = RequestMappingHandlerMapping.class.
                                getDeclaredMethod("getMappingForMethod", Method.class, Class.class);
                        createMappingMethod.setAccessible(true);
                        RequestMappingInfo requestMappingInfo = (RequestMappingInfo)
                                createMappingMethod.invoke(requestMappingHandlerMapping, specificMethod, targetClass);
                        if (requestMappingInfo != null) {
                            requestMappingHandlerMapping.unregisterMapping(requestMappingInfo);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, ReflectionUtils.USER_DECLARED_METHODS);
        }
    }

    public static void addBean(String className, String serviceName, ApplicationContext app) {
        try {
            Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
            registerBean(serviceName, beanDefinitionBuilder.getRawBeanDefinition(), app);
        } catch (ClassNotFoundException e) {
            System.out.println(className + ",主动注册失败.");
        }
    }

    public static void addBean(Class clazz, String serviceName, ApplicationContext app) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        registerBean(serviceName, beanDefinitionBuilder.getRawBeanDefinition(), app);
    }

    private static void registerBean(String beanName, BeanDefinition beanDefinition, ApplicationContext context) {
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) context;
        BeanDefinitionRegistry beanDefinitonRegistry = (BeanDefinitionRegistry) configurableApplicationContext
                .getBeanFactory();
        beanDefinitonRegistry.registerBeanDefinition(beanName, beanDefinition);
    }
}
