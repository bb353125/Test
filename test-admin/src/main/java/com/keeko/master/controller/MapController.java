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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

/**
 * int相关
 *
 * @author chensq
 */
@Controller
@RequestMapping("/map")
public class MapController {

    /**
     * 增加数组的长度
     */
    @GetMapping("/test")
    @ResponseBody
    public ResultResponse test(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        //1: 遍历map
        map.forEach((key, value) -> {
            System.out.println(key + value.toString());
        });
        //2: 分组
        //Map<String, List<RegionCity>> map = regionCities.stream().collect(Collectors.groupingBy(RegionCity::getProvinceId));

        //2: 分组
        //Map<Long, List<Product>> map = productList.stream().collect(groupingBy(Product::getShopProductCategoryId));

        return new ResultResponse(true, "");
    }






}
