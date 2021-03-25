package com.keeko.master.controller;

import com.keeko.common.ResultResponse;
import com.keeko.utils.DpcCalculatorUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * int相关
 *
 * @author chensq
 */
@Controller
@RequestMapping("/int")
public class IntController {

    /**
     * 增加数组的长度
     */
    @GetMapping("/test")
    @ResponseBody
    public ResultResponse test(HttpServletRequest request) {
        //BigInteger n = new BigInteger(red + ""); 超大整形
        int qlcFsNum = DpcCalculatorUtils.getQlcFsNum(20);

        int[] ary = {1, 2, 3, 4, 5, 6};
        int[] ary1 = {1, 2, 3, 4, 5, 6};
        int[] ary2 = {1, 2, 3, 4};
        System.out.println("Is array 1 equal to array 2?? " + Arrays.equals(ary, ary1));
        System.out.println("Is array 1 equal to array 3?? " + Arrays.equals(ary, ary2));
        int[] intArray = new int[10];
        int[] newIntArray = (int[]) increaseArray(intArray);//增加数组的长度
        return new ResultResponse();
    }

    public static Object increaseArray(Object array) {
        Class<?> clazz = array.getClass();// 获得代表数组的Class对象
        if (clazz.isArray()) {//如果输入是一个数组
            Class<?> componentType = clazz.getComponentType();//获得数组元素的类型
            int length = Array.getLength(array);//获得输入的数组的长度
            Object newArray = Array.newInstance(componentType, length + 1);//新建数组
            System.arraycopy(array, 0, newArray, 0, length);//复制原来数组中的所有数据
            return newArray;//返回新建数组
        }
        return null;//如果输入的不是数组就返回空
    }




}
