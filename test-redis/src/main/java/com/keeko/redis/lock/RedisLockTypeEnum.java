package com.keeko.redis.lock;

/**
 * 设置必要参数
 *
 * @author https://github.com/Vip-Augus/springboot-note/blob/master/src/main/java/cn/sevenyuan/demo/aop/lock/RedisLockAnnotation.java
 */
public enum RedisLockTypeEnum {

    /**
     * 自定义 key 前缀
     */
    ONE("Business1", "Test1"),

    TWO("Business2", "Test2");

    private String code;

    private String desc;

    RedisLockTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getUniqueKey(String key) {
        return String.format("%s:%s", this.getCode(), key);
    }

}
