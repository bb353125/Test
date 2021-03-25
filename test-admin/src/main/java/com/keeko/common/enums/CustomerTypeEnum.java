package com.keeko.common.enums;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * 客户类型
 *
 * @author chensq
 */
@Getter
public enum CustomerTypeEnum {

    /**
     * 客户类型
     */
    PUBLIC_SCHOOL("0", "公立园"),
    PRIVATE_SCHOOL("1", "私立园"),
    TRAINING_AGENCY("2", "培训机构"),
    AGENT("3", "代理商"),
    DISTRIBUTOR("4", "分销商"),
    DEALER("5", "经销商"),
    PERSONAGE("6", "个人"),
    AGENCY("7", "机构"),
    PROVINCE_AGENT("8","省代"),
    CITY_AGENT("9","市代"),
    DISTRICT_AGENT("10","区代")
    ;

    String code;
    String message;

    CustomerTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
    //获取当前枚举列表
    public static List<Map<String, Object>> getAllCustomerType() {
        List<Map<String, Object>> list = Lists.newArrayList();
        CustomerTypeEnum[] customerTypeEnums = values();
        for (CustomerTypeEnum customerTypeEnum : customerTypeEnums) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("code", customerTypeEnum.getCode());
            map.put("message", customerTypeEnum.getMessage());
            list.add(map);
        }
        return list;
    }

    //传code获取中文类型名称
    public static String getEnumMessage(String code) {
        CustomerTypeEnum[] enums = values();
        for (CustomerTypeEnum e : enums) {
            if (e.getCode().equals(code)) {
                return e.getMessage();
            }
        }
        return "";
    }
}
