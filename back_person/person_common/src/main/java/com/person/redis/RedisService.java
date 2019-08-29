package com.person.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: pzy
 * @create: 2019/8/5 17:56
 */
@Component
@Slf4j
public class RedisService {

    private RedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        this.redisTemplate = redisTemplate;
    }

    /**
     * 添加缓存
     * @param key key值
     * @param value value
     * @return boolean
     */
    public boolean set(final String key, Object value) {
        boolean flag = true;

        try {
            ValueOperations operations = redisTemplate.opsForValue();
            operations.set(key, value);
        } catch (Exception e) {
            log.error("添加缓存异常:{}", e);
            flag = false;
        }

        return flag;
    }

    /**
     * 添加缓存
     * @param key key值
     * @param value value
     * @param time 缓存过期时间
     * @return boolean
     */
    public boolean set(final String key, Object value, long time) {
        boolean flag = true;

        try {
            ValueOperations operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("redis缓存异常:{}", e);
            flag = false;
        }

        return flag;
    }

    /**
     * 根据key获取value
     * @param key key
     * @return Object
     */
    public <T> T get(final String key) {
        Object value = null;
        try {
            ValueOperations operations = redisTemplate.opsForValue();
            value = operations.get(key);
        } catch (Exception e) {
            log.error("根据key获取缓存异常:{}", e);
        }

        return (T) value;
    }

    /**
     * 根据key删除value
     * @param key key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     *  删除多个元素
     * @param keys 元素key
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     *  删除多个元素
     * @param keys 元素key
     */
    public void remove(final List<String> keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 判断key是否存在
     * @param key key
     * @return Boolean
     */
    public Boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 根据key获取过期时间
     * @param key key值
     * @return Long 过期时间
     */
    public Long getExpire(final String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 头部添加List集合
     * @param key key
     * @param value List集合数据
     * @param <T> 泛型
     */
    public <T> void lFirstPush(final String key, List<T> value) {
        ListOperations list = redisTemplate.opsForList();
        list.leftPushAll(key, value);
    }

    /**
     * 头部添加List数据
     * @param key key
     * @param value 对象数据
     */
    public void lFirstPush(final String key, Object value) {
        ListOperations list = redisTemplate.opsForList();
        list.leftPush(key, value);
    }

    /**
     * 尾部添加List集合
     * @param key key
     * @param value List集合数据
     * @param <T> 泛型
     */
    public <T> void lLastPush(final String key, List<T> value) {
        ListOperations list = redisTemplate.opsForList();
        list.rightPushAll(key, value);
    }

    /**
     * 尾部添加List集合
     * @param key key
     * @param value value
     */
    public void lLastPush(final String key, Object value) {
        ListOperations list = redisTemplate.opsForList();
        list.rightPush(key, value);
    }

    /**
     * 根据key 开始和结束下表获取List集合
     * @param key key
     * @param startIndex 开始下标
     * @param endIndex 结束下标
     * @param <T> 泛型
     * @return List<T>
     */
    public <T> List<T> lRange(final String key, long startIndex, long endIndex) {
        try {
            ListOperations list = redisTemplate.opsForList();

            return (List<T>) list.range(key, startIndex, endIndex);
        } catch (Exception e) {
            log.error("异常：{}", e);
            return null;
        }
    }

    /**
     * 根据key和下标获取List数据
     * @param key key
     * @param index 下标
     * @param <T> 泛型
     * @return List<T>
     */
    public <T> T lIndex(final String key, long index) {
        try {
            ListOperations list = redisTemplate.opsForList();

            return (T) list.index(key, index);
        } catch (Exception e) {
            log.error("异常：{}", e);
            return null;
        }
    }

}
