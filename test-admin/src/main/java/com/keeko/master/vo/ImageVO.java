package com.keeko.master.vo;

import lombok.Data;

import java.util.List;

/**
 * 向Excel导入图片vo
 *
 * @author chensq
 */
@Data
public class ImageVO {

    /**
     * 图片路径1
     */
    private String imgPath1;
    /**
     * 图片路径2
     */
    private String imgPath2;
    /**
     * 图片路径3
     */
    private String imgPath3;
    /**
     * 图片名称1
     */
    private String imgName1;
    /**
     * 图片名称2
     */
    private String imgName2;
    /**
     * 图片名称3
     */
    private String imgName3;

    /**
     * 自己
     */
    private List<ImageVO> imageList;

}
