package com.keeko.redis.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.keeko.redis.config.ResultResponse;
import com.keeko.redis.entity.RUser;
import com.keeko.redis.lock.RedisLockAnnotation;
import com.keeko.redis.utils.RedisUtil;
import com.sun.xml.internal.messaging.saaj.soap.Envelope;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Controller
public class TestRedisController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 测试
     */
    @GetMapping("/info")
    @ResponseBody
    public ResultResponse appInfo(String name, String i) {
        //2: values 取值方式
        List<Object> oUserList = redisTemplate.opsForHash().values("key");
        for (Object o : oUserList) {
            System.out.println("查看obj真实类型:" + o.getClass().getName());
            RUser user1 = (RUser) o;
            System.out.println(user1.toString());
        }
        return new ResultResponse(true, "");
    }

    /**
     * Redis HashMap数据结构 存取对象例子
     * 根据id为map的Key即可区分数据
     */
    @GetMapping("/redisHashMap")
    @ResponseBody
    public ResultResponse redisHashMap() {
        //根据key删除redis的数据
        redisTemplate.delete("key");
        Map<String, Object> map = new HashMap<>(16);
        RUser user = new RUser();
        user.setId("001");
        user.setLoginName("铃铃");
        user.setHeadPortrait("头像地址:url");
        map.put("hashKey01", user);
        RUser user2 = new RUser();
        user2.setId("hashKey02");
        user2.setLoginName("铃儿");
        user2.setHeadPortrait("头像地址:url002");
        map.put("user02", user2);
        //添加json字符串数据
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.putAll("key", map);
        //设置key缓存时间 1:key 2:时间  3:指定 单位秒
        Boolean key1 = redisTemplate.expire("key", 60, TimeUnit.SECONDS);
        System.out.println(key1);
        //查询hash中所有的key (会乱码)
        Set<Object> key = stringRedisTemplate.opsForHash().keys("key");

        //1: entries 取值方式
        Map<Object,Object> map2 = redisTemplate.opsForHash().entries("key");
        //若是没有这个hashKey则返回null
        Object hashKey01 = map2.get("hashKey01");
        System.out.println("查看obj真实类型:" + hashKey01.getClass().getName());

        //2: values 取值方式
        List<Object> oUserList = redisTemplate.opsForHash().values("key");
        for (Object o : oUserList) {
            System.out.println("查看obj真实类型:" + o.getClass().getName());
            RUser user1 = (RUser) o;
            System.out.println(user1.toString());
        }
        return new ResultResponse(true, "");
    }

    /**
     * Redis String数据结构 存取例子
     */
    @GetMapping("/redisString")
    @ResponseBody
    public ResultResponse redisString() {
        //根据key删除redis的数据
        redisTemplate.delete("key");
        RUser user = new RUser();
        user.setId("001");
        user.setLoginName("铃铃");
        user.setHeadPortrait("头像地址:url");
        String stringUser = JSONObject.toJSONString(user);
        //添加String类型的key-value
        redisTemplate.opsForValue().set("key", stringUser);

        //获取String类型的value
        Object jsonUser = redisTemplate.opsForValue().get("key");
        //转成真实对象
        RUser rUser = JSONObject.parseObject(jsonUser.toString(), RUser.class);
        System.out.println(rUser.toString());
        return new ResultResponse(true, rUser);
    }

    /**
     * Redis Hash数据结构 存取对象例子
     */
    @GetMapping("/redisHashList")
    @ResponseBody
    public ResultResponse redisHashList() {
        //根据key删除redis的数据
        redisTemplate.delete("key");
        List<RUser> userList = new ArrayList<>();
        RUser user = new RUser();
        user.setId("001");
        user.setLoginName("铃铃");
        user.setHeadPortrait("头像地址:url");
        userList.add(user);
        //添加json字符串数据
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put("key", "hk", userList);
        List<Object> oUserList = redisTemplate.opsForHash().values("key");
        for (Object o : oUserList) {
            System.out.println("查看obj真实类型:" + o.getClass().getName());
            List<RUser> userList1 = (List<RUser>) o;
            System.out.println(userList1.toString());
        }
        return new ResultResponse(true, userList);
    }

    /**
     * Redis Hash数据结构 存取对象例子
     */
    @GetMapping("/redisHash")
    @ResponseBody
    public ResultResponse redisHash() {
        //根据key删除redis的数据
        redisTemplate.delete("key");
        RUser user = new RUser();
        user.setId("001");
        user.setLoginName("铃铃");
        user.setHeadPortrait("头像地址:url");
        //添加json字符串数据
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put("key", "hk", user);
        List<Object> userList = redisTemplate.opsForHash().values("key");
        for (Object oUser : userList) {
            RUser sUser = (RUser) oUser;
            System.out.println(sUser.toString());
        }
        return new ResultResponse(true, userList);
    }

    /**
     * Redis List数据结构 存取例子
     */
    @GetMapping("/redisList")
    @ResponseBody
    public ResultResponse redisList() {
        List<String> list = new ArrayList<>();
        //测试5个
        for (int i = 0; i < 5; i++) {
            RUser user = new RUser();
            user.setId("id" + i);
            user.setLoginName("name" + i);
            user.setHeadPortrait("headPortrait" + i);
            //转成json字符串
            String stringUser = JSON.toJSONString(user);
            list.add(stringUser);
        }
        //插入多条数据 返回行数
        Long l = stringRedisTemplate.opsForList().rightPushAll("key", list);
        System.out.println(l);
        //获取最新一条
        Object index = stringRedisTemplate.opsForList().index("key", stringRedisTemplate.opsForList().size("key") - 1);
        System.out.println(index);
        //获取指定一条 参数1:key 2:开始索引 3:结束索引
        List<String> hList = stringRedisTemplate.opsForList().range("key", 0, stringRedisTemplate.opsForList().size("key") - 1);
        for (String json : hList) {
            //转成真实对象
            RUser rUser = JSONObject.parseObject(json, RUser.class);
            System.out.println(rUser.toString());
        }
        System.out.println(hList.toString());
        //获取后边第一条=最新一条
        String s = stringRedisTemplate.opsForList().rightPop("key");
        System.out.println(s);
        //解析json对象
        //转成真实对象
        RUser cUser = JSONObject.parseObject(s, RUser.class);
        System.out.println(cUser.toString());

        //解析json数组(若不是数组转成数组会报错)
        JSONArray jsonArray = JSONArray.parseArray(s);
        for (Object json : jsonArray) {
            //转成真实对象
            RUser rUser = JSONObject.parseObject(json.toString(), RUser.class);
            System.out.println(rUser.toString());
        }
        return new ResultResponse(true, "123");
    }

    public void test() {
        System.out.println(Thread.currentThread().getName() + " 已连接正在等待消息...");
        String key;
        key = "lock:" + "id";
        boolean flag;
        try {
            //尝试加锁
            do {
                flag = stringRedisTemplate.opsForValue().setIfAbsent(key, Thread.currentThread().getName());
            } while (!flag);
            stringRedisTemplate.expire(key, 3600, TimeUnit.SECONDS);
            System.out.println(Thread.currentThread().getName() + " 获取redis锁成功");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放锁
            if (key != null) {
                stringRedisTemplate.delete(key);
                System.out.println(Thread.currentThread().getName() + " 处理完成释放锁成功");
            }
        }
    }

}
