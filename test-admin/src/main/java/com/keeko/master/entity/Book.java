package com.keeko.master.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 书籍相关
 *
 * @author chensq
 */
@Getter
@Setter
public class Book extends PageParam {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 书本名称
     */
    private String name;
    /**
     * 图片地址
     */
    private String imgUrl;
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 设备mac
     */
    private String mac;
    /**
     * 小程序用户id
     */
    private Long xcxUserId;
    /**
     * 类型
     */
    private String type;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 作者
     */
    private String author;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;
    /**
     * 删除标识
     */
    private String delFlag;

    /**
     * 小程序用户姓名
     */
    private String nickname;
    /**
     * 小程序用户id
     */
    private String openId;

    /**
     * 书籍isbn
     */
    private String isbn;
    /**
     * 父标题
     */
    private String subTitle;
    /**
     * 图片地址
     */
    private String pic;
    /**
     * 总结，概要
     */
    private String summary;
    /**
     * 出版商
     */
    private String publisher;
    /**
     * 出版地址
     */
    private String pubplace;
    /**
     * 出版时间
     */
    private String pubdate;
    /**
     * 价格
     */
    private String price;
    /**
     * 装订
     */
    private String binding;
    /**
     * isbn10
     */
    private String isbn10;
    /**
     * 关键字
     */
    private String keyword;
    /**
     * 第几版
     */
    private String edition;
    /**
     * 效果
     */
    private String impression;
    /**
     * 语言
     */
    private String language;
    /**
     * 版式: 25×19
     */
    private String format;
    /**
     * json 哪里卖/价格信息
     */
    private String sellerList;


}