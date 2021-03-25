package com.keeko.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 状态枚举相关
 *
 * @author chensq
 */
@Getter
@AllArgsConstructor
public enum StatusEnum {
    /**
     * 删除标志
     */
    NORMAL("0", "正常"), DELETE("1", "删除"),
    /**
     * 是 否
     */
    YES("0", "是"), NO("1", "否");

    String code;
    String message;
}
