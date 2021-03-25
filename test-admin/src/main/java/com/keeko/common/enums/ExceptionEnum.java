package com.keeko.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 异常 枚举类
 *
 * @author chensq
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {

    /**
     * 文件上传失败
     */
    UPLOAD_FILE_ERROR("500", "文件上传失败");

    private String code;
    private String msg;
}
