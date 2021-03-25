package com.keeko.master.controller;

import com.keeko.common.ResultResponse;
import com.keeko.utils.DpcCalculatorUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * int相关
 *
 * @author chensq
 */
@Controller
@RequestMapping("/double")
public class DoubleController {

    /**
     * 增加数组的长度
     */
    @GetMapping("/test")
    @ResponseBody
    public ResultResponse test(HttpServletRequest request) {
        //向上取整:Math.ceil() //只要有小数都+1
        //向下取整:Math.floor() //不取小数
        //四舍五入:Math.round() //四舍五入



        return new ResultResponse();
    }


    public static void main(String[] args) {
        List<Double> array = new ArrayList();
        array.add(26.1);
        array.add(76.4);
        array.add(46.3);
        array.add(76.3);
        array.add(54.5);
        array.add(94.6);
        array.add(24.7);
        array.add(34.8);

        double targetNum= 74;
        System.out.println("和要查找的数："+targetNum+ "最接近的数："+getNumberThree(array, targetNum));

    }

    public static double getNumberThree(List<Double> intarray, double number){
        double index = Math.abs(number-intarray.get(0));
        double result = intarray.get(0);
        for (double i : intarray) {
            double abs = Math.abs(number-i);
            if(abs <= index){
                index = abs;
                result = i;
            }
        }
        return result;
    }

    public static Integer getNumberThree(Integer[] intarray,Integer number){
        int index = Math.abs(number-intarray[0]);
        int result = intarray[0];
        for (int i : intarray) {
            int abs = Math.abs(number-i);
            if(abs <= index){
                index = abs;
                result = i;
            }
        }
        return result;
    }


}
