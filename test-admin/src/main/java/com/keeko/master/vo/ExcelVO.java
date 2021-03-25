package com.keeko.master.vo;

import lombok.Data;

import java.util.Map;

/**
 * ExcelVO
 *
 * @author chensq
 */
@Data
public class ExcelVO {
    /**
     * Excel名称
     */
    private String fileName;
    /**
     * 模板路径
     */
    private String templateUrl;
    /**
     * 生成的Excel路径
     */
    private String templateNewUrl;
    /**
     * 参数
     */
    private Map<String, Object> map;
}
