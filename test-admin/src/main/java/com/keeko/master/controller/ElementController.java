package com.keeko.master.controller;

import com.keeko.common.ResultResponse;
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

/**
 * Jsoup 解析网页相关
 *
 * @author chensq
 */
@Controller
@RequestMapping("/element")
public class ElementController {

    /**
     * 解析网页
     */
    @GetMapping("/test")
    @ResponseBody
    public ResultResponse test(HttpServletRequest request) {
        String requestUrl = "www.baidu.com";
        Document doc = null;
        try {
            doc = Jsoup.connect(requestUrl).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (doc == null){
            return new ResultResponse(false, "解析异常");
        }
        //div 中的文章内容   1: artibody = div标签Id 用来指定当前div
        Element div = doc.getElementById("artibody");
        Elements contentList = div.getElementsByTag("p");
        System.out.println(contentList.toString());
        //a标签id
        Element a = doc.getElementById("keywords");
        Elements labels = a.getElementsByTag("a");
        System.out.println(labels.toString());
        return new ResultResponse();
    }

}
