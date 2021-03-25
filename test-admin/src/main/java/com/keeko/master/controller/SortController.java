package com.keeko.master.controller;

import com.keeko.common.ResultResponse;
import com.keeko.master.entity.User;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 排序 相关
 *
 * @author chensq
 */
@Controller
@RequestMapping("/sort")
public class SortController {

    /**
     * 排序
     */
    @GetMapping("/test")
    @ResponseBody
    public ResultResponse test(HttpServletRequest request) {
        List<User> list = new ArrayList<>();
        //list.add(new User("张三", "1987-05-23 12:34:07"));
        //list.add(new User("李四", "1977-05-23 05:04:07"));
        //list.add(new User("王五", "1987-05-23 09:34:07"));

        // (1)顺序排列
        list.sort(Comparator.comparing(User::getId));
        // (2)倒序排列
        Collections.reverse(list);

        return new ResultResponse();
    }

}
