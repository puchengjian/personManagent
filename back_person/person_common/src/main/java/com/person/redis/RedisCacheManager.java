package com.person.redis;

import com.person.constant.RedisConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author: pzy
 * @create: 2019/8/29 9:49
 */
@Slf4j
public class RedisCacheManager implements CacheManager {

    @Autowired
    private RedisService redisService;

    private int expire = 1800;

    private String keyPrefix = RedisConst.SHIRO_CACHE_KEY;

    private String principalIdFieldName = "authCacheKey or id";

    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        log.debug("get cache, name=" + name);
        Cache cache = caches.get(name);

        if (cache == null) {
            cache = new RedisCache<K, V>(redisService, keyPrefix + name + ":", expire, principalIdFieldName);
            caches.put(name, cache);
        }

        return cache;
    }


    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public String getPrincipalIdFieldName() {
        return principalIdFieldName;
    }

    public void setPrincipalIdFieldName(String principalIdFieldName) {
        this.principalIdFieldName = principalIdFieldName;
    }
}
