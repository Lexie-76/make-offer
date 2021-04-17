package com.test.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisClient {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 一周有多少秒
     */
    private static final long WEEK_SECONDS = 7 * 24 * 60 * 60;


    /**
     * 将 key，value 存放到redis数据库中，默认设置过期时间为一周
     *
     * @param key
     * @param value
     */
/*    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, JsonUtil.convertObj2String(value), WEEK_SECONDS, TimeUnit.SECONDS);
    }*/
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 将 key，value 存放到redis数据库中，设置过期时间单位是秒
     *
     * @param key
     * @param value
     * //@param expireTime 是 timeout参数
     */
/*    public void set(String key, Object value, long expireTime) {
        redisTemplate.opsForValue().set(key, JsonUtil.convertObj2String(value), expireTime, TimeUnit.SECONDS);
    }*/
    public void set(String key, Object value, int timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 存入多组key value
     * @param map
     */
    public void set(Map<String,String> map) {
        redisTemplate.opsForValue().multiSet(map);
    }

    /**
     * 判断 key 是否在 redis 数据库中
     *
     * @param key
     * @return
     */
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 获取与 key 对应的对象
     * @param key
     * //@param clazz 目标对象类型
     * //@param <T>
     * @return
     */
  /*  public <T> T get(String key, Class<T> clazz) {
        String s = get(key);
        if (s == null) {
            return null;
        }
        return JsonUtil.convertString2Obj(s, clazz);
    }*/

    /**
     * 设置与 key 对应的对象的过期时间
     * 和浏览器交互的时候，可以设置过期时间
     * @param key
     * @param timeout
     * @return
     */
    public void expire(String key, int timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    public void keys(String pattern){
        Set keys = redisTemplate.keys(pattern);
        keys.isEmpty();

    }

    /**
     * 获取 key 对应的字符串
     * @param key
     * @return
     */
/*    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }*/
    public <T> T get(String key) {
        // 因为返回的可能是任何一个类型，所以使用泛型，并对返回值进行强制类型转换
        return (T)redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除 key 对应的 value
     * @param key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void setHashString(String hashName,Map<String,String> map) {
        redisTemplate.opsForHash().putAll(hashName,map);
    }

    public void setHashObject(String hashName, Map<String, Object> map){
        redisTemplate.opsForHash().putAll(hashName,map);
    }

    //加上@EnableCaching开启缓存，使用了@Cacheable注解后，缓存的值将被存入redis数据库中
    //缓存名可以为RedisConfig中自定义的缓存名，键生成器为RedisConig中自定义的键生成器，也可以自己自定义缓存key名
    @Cacheable(cacheNames = "users",keyGenerator ="myKeyGenerator")
    public Map<Object,Object> getHash(String hashName){
        if (redisTemplate.hasKey(hashName)) {
            System.out.println(redisTemplate.opsForHash().entries(hashName));
            return redisTemplate.opsForHash().entries(hashName);
        }else {
            return null;
        }
    }
}
