package com.keeko.second.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class MyClassLoader extends ClassLoader  {

    @Override
    public Class<?> findClass(String name) {
        String myPath = "file:///D:/test/" + name.replace(".","/") + ".class";
        System.out.println(myPath);
        byte[] cLassBytes = null;
        Path path = null;
        try {
            path = Paths.get(new URI(myPath));
            cLassBytes = Files.readAllBytes(path);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        Class clazz = defineClass(name, cLassBytes, 0, cLassBytes.length);
        return clazz;
    }


    public static void main(String[] args) throws ClassNotFoundException {
        MyClassLoader loader = new MyClassLoader();
        Class<?> aClass = loader.findClass("com.controller.TestB");
        try {
            Object obj = aClass.newInstance();
            Method method = aClass.getMethod("test");
            method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
