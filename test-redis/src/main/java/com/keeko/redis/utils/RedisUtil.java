package com.keeko.redis.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis Tools
 *
 * @author Chensq
 */
@Component
public class RedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * @param name
     * @return
     * @Description 获取String类型的value
     */
    public String findName(String name) {
        if (name == null) {
        }
        return stringRedisTemplate.opsForValue().get(name);
    }

    /**
     * @param name
     * @param value
     * @return
     * @Description 添加String类型的key-value
     */
    public String setNameValue(String name, String value) {
        stringRedisTemplate.opsForValue().set(name, value);
        return name;
    }

    /**
     * @param name
     * @return
     * @Description 根据key删除redis的数据
     */
    public String delNameValue(String name) {
        stringRedisTemplate.delete(name);
        return name;
    }

    /**
     * @param key
     * @return
     * @Description 根据key获取list类型的value(范围)
     */
    public List<String> findList(String key, int start, int end) {
        return stringRedisTemplate.opsForList().range(key, start, end);
    }

    /**
     * @param key
     * @param value
     * @return
     * @Description 插入多条数据
     */
    public long setList(String key, List<String> value) {
        return stringRedisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * @param key
     * @return
     * @Description 获取list最新记录（右侧）
     */
    public String findLatest(String key) {
        return stringRedisTemplate.opsForList().index(key, stringRedisTemplate.opsForList().size(key) - 1);
    }

    /**
     * @param key
     * @return
     * @Description 查询hash
     */
    public Map<Object, Object> findHash(String key) {
        return stringRedisTemplate.opsForHash().entries(key);
    }

    /**
     * @param key
     * @return
     * @Description 查询hash中所有的key
     */
    public Set<Object> findHashKeys(String key) {
        return stringRedisTemplate.opsForHash().keys(key);
    }

    /**
     * @param key
     * @return
     * @Description 查询hash中所有的value
     */
    public List<Object> findHashValues(String key) {
        return stringRedisTemplate.opsForHash().values(key);
    }

    /**
     * @param key
     * @param map
     * @return
     * @Desscription 插入hash数据
     */
    public long insertHash(String key, Map<String, Object> map) {
        stringRedisTemplate.opsForHash().putAll(key, map);
        return map.size();
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return 是否设置成功
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key   缓存key
     * @param value 缓存value
     * @param time  失效时间
     * @return boolean
     * @Description 失效时间单位： 秒
     * @Author Jason
     * @Date 2019/12/25
     **/
    public boolean expire(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                redisTemplate.opsForValue().set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
