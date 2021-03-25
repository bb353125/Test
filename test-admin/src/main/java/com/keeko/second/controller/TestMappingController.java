package com.keeko.second.controller;

import com.keeko.second.entity.SUser;
import com.keeko.second.service.ITestService;
import com.keeko.utils.SpringMappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class TestMappingController {

    private final RequestMappingHandlerMapping requestMappingHanderMapping;
    private final AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor;
    private final ConfigurableListableBeanFactory factory;

    @Autowired
    public TestMappingController(RequestMappingHandlerMapping requestMappingHanderMapping, AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor, ConfigurableListableBeanFactory factory) {
        this.requestMappingHanderMapping = requestMappingHanderMapping;
        this.autowiredAnnotationBeanPostProcessor = autowiredAnnotationBeanPostProcessor;
        this.factory = factory;
    }

    @ResponseBody
    @GetMapping("/mapping")
    public String test123() throws Exception {
        SpringMappingUtils springMappingUtils = new SpringMappingUtils(requestMappingHanderMapping, autowiredAnnotationBeanPostProcessor, factory);
        //springMappingUtils.registory(A.class, "/test1101", RequestMethod.POST,"a", SUser.class);
        //springMappingUtils.registory(A.class, "/test1102", RequestMethod.GET,"b", null);


        //加载bean
        /*PathClassLoader pathClassLoader = new PathClassLoader("D:\\test\\com\\keeko\\second\\controller");
        Class<?> aClass = pathClassLoader.findClass("Test2ApiController");
        Method[] methods = aClass.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
            //传bean的名称
            DynamicLoadUtils.registerController(method.getName());
        }*/


       /* MyClassLoader loader = new MyClassLoader();
        Class<?> aClass = loader.findClass("com.controller.TestA");
        try {
            Object obj = aClass.newInstance();
            Method method = aClass.getMethod("newLogin");
            method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DynamicLoadUtils.registerController("TestA");*/

       /* ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        // This URL for a directory will be searched *recursively*
        URL classes = new URL("file:\\D:\\test\\");
        ClassLoader custom = new URLClassLoader(new URL[]{classes}, systemClassLoader);
        // this class should be loaded from your directory
        Class<?> clazz = custom.loadClass("com.keeko.second.controller.Test2ApiController");
        DynamicLoadUtils.registerController("Test2ApiController");
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName());

        }*/
        return "test1456";
    }




    @ResponseBody
    @GetMapping("/test1")
    public String test() throws Exception {
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        // This URL for a directory will be searched *recursively*
        URL classes = new URL("file:\\D:\\test\\");
        ClassLoader custom = new URLClassLoader(new URL[]{classes}, systemClassLoader);
        // this class should be loaded from your directory
        Class<?> clazz = custom.loadClass("com.keeko.second.controller.Test2ApiController");

        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }


        //return "123";
        return "test1456";
    }

}
