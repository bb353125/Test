package com.keeko.second.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class PathClassLoader extends ClassLoader{
    private File dir;

    public PathClassLoader(String path) {
        dir = new File(path);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (dir != null) {
            File clazzFile = new File(dir, name + ".class");
            if (clazzFile.exists()) {
                FileInputStream input = null;
                try {
                    input = new FileInputStream(clazzFile);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = input.read(buffer)) != -1) {
                        baos.write(buffer, 0, len);
                    }

                    return defineClass(name, baos.toByteArray(), 0, baos.size());
                } catch (Exception e) {
                    throw new ClassNotFoundException(name, e);
                } finally {
                    if (input != null) {
                        try {
                            input.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return super.findClass(name);
    }

}
