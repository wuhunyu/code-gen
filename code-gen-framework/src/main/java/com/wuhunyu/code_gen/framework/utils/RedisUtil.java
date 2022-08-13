package com.wuhunyu.code_gen.framework.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis操作工具类
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/13 14:52
 */

public class RedisUtil {

    private RedisUtil() {
    }

    /**
     * =================================================获取操作对象======================================================
     */

    /**
     * 获取 RedisTemplate 对象
     *
     * @param <K> 键类型
     * @param <V> 值类型
     * @return RedisTemplate 对象
     */
    @SuppressWarnings("unchecked")
    private static <K, V> RedisTemplate<K, V> getRedisTemplate() {
        return SpringUtil.getBean(RedisTemplate.class);
    }

    /**
     * 获取 string 操作对象
     *
     * @param <V> 值类型
     * @return string 操作对象
     */
    private static <V> ValueOperations<String, V> getValueOperations() {
        RedisTemplate<String, V> redisTemplate = RedisUtil.getRedisTemplate();
        return redisTemplate.opsForValue();
    }

    /**
     * 获取 hash 操作对象
     *
     * @param <HK> key类型
     * @param <HV> val类型
     * @return hash 操作对象
     */
    private static <HK, HV> HashOperations<String, HK, HV> getHashOperations() {
        RedisTemplate<String, ?> redisTemplate = RedisUtil.getRedisTemplate();
        return redisTemplate.opsForHash();
    }

    /**
     * 获取 zset 操作对象
     *
     * @param <V> 值类型
     * @return zset 操作对象
     */
    private static <V> ZSetOperations<String, V> getZSetOperations() {
        RedisTemplate<String, V> redisTemplate = RedisUtil.getRedisTemplate();
        return redisTemplate.opsForZSet();
    }

    /**
     * =================================================string 操作方法==================================================
     */

    /**
     * set
     *
     * @param key   key
     * @param value value
     * @param <V>   值类型
     */
    public static <V> void set(String key, V value) {
        ValueOperations<String, V> valueOperations = RedisUtil.getValueOperations();
        valueOperations.set(key, value);
    }

    /**
     * set
     *
     * @param key      key
     * @param value    value
     * @param expire   过期时长
     * @param timeUnit 时间单位
     * @param <V>      值类型
     */
    public static <V> void set(String key, V value, long expire, TimeUnit timeUnit) {
        ValueOperations<String, V> valueOperations = RedisUtil.getValueOperations();
        valueOperations.set(key, value, expire, timeUnit);
    }

    /**
     * 设置过期时间
     *
     * @param key        key
     * @param expire     过期时长
     * @param chronoUnit 时间单位
     * @param <T>        key类型
     * @return 设置过期时间是否成功
     */
    public static <T> Boolean expire(T key, long expire, ChronoUnit chronoUnit) {
        RedisTemplate<T, Object> redisTemplate = RedisUtil.getRedisTemplate();
        return redisTemplate.expire(key, Duration.of(expire, chronoUnit));
    }

    /**
     * 判断是否存在某个key
     *
     * @param key key
     * @param <T> key类型
     * @return 是否存在某个key(true : 存在 ; false : 不存在)
     */
    public static <T> Boolean exists(T key) {
        RedisTemplate<T, Object> redisTemplate = RedisUtil.getRedisTemplate();
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除某个key
     *
     * @param key key
     * @param <T> key类型
     * @return 是否删除成功(true : 成功 ; false : 失败)
     */
    public static <T> Boolean delete(T key) {
        RedisTemplate<T, Object> redisTemplate = RedisUtil.getRedisTemplate();
        return redisTemplate.delete(key);
    }

    /**
     * =================================================hash 操作方法====================================================
     */

    /**
     * 存入整个对象到 hash 中
     *
     * @param mapName hash名称
     * @param data    存入对象
     * @param <T>     值类型
     * @throws IllegalAccessException 反射操作异常
     */
    public static <T> void hSet(String mapName, T data) throws IllegalAccessException {
        Class<?> dataClass = data.getClass();
        Field[] fields = dataClass.getDeclaredFields();
        Map<String, Object> map = new HashMap<>(fields.length);
        for (Field field : fields) {
            field.setAccessible(true);
            Object obj = field.get(data);
            map.put(field.getName(), obj);
        }
        if (CollUtil.isEmpty(map)) {
            return;
        }
        HashOperations<String, String, Object> hashOperations = RedisUtil.getHashOperations();
        hashOperations.putAll(mapName, map);
    }

    /**
     * 替换hash对象的单个值
     *
     * @param mapName hash名称
     * @param key     key名称
     * @param value   value
     * @param <V>     值类型
     */
    public static <V> void hSet(String mapName, String key, V value) {
        HashOperations<String, String, V> hashOperations = RedisUtil.getHashOperations();
        hashOperations.put(mapName, key, value);
    }

    /**
     * 获取 hash对象 的某个属性
     *
     * @param mapName hash名称
     * @param key     key
     * @param <V>     值类型
     * @return 属性值
     */
    public static <V> V hGet(String mapName, String key) {
        HashOperations<String, String, V> hashOperations = RedisUtil.getHashOperations();
        return hashOperations.get(mapName, key);
    }

    /**
     * 获取 hash对象 转换成 普通bean对象
     *
     * @param mapName hash名称
     * @param vClass  值类型
     * @param <V>     普通bean对象类型
     * @return 普通bean对象
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public static <V> V hGetAll(String mapName, Class<V> vClass)
            throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException, NoSuchFieldException {
        HashOperations<String, String, Object> hashOperations = RedisUtil.getHashOperations();
        Map<String, Object> map = hashOperations.entries(mapName);
        // 返回空
        if (CollUtil.isEmpty(map)) {
            return null;
        }
        // 获取无参构造
        Constructor<V> nullParamsConstructor = vClass.getDeclaredConstructor();
        V instance = nullParamsConstructor.newInstance();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            }
            Field field = vClass.getDeclaredField(entry.getKey());
            field.setAccessible(true);
            field.set(instance, entry.getValue());
        }
        return instance;
    }

    /**
     * 删除单个hash对象属性
     *
     * @param mapName hash名称
     * @param key     key
     */
    public static void hDel(String mapName, String key) {
        HashOperations<String, String, Object> hashOperations = RedisUtil.getHashOperations();
        hashOperations.delete(mapName, key);
    }

    /**
     * =================================================zset 操作方法====================================================
     */

    /**
     * 新增 zset 值
     *
     * @param zSetName zset名称
     * @param key      存入key名称
     */
    public static void sAdd(String zSetName, String key) {
        ZSetOperations<String, String> zSetOperations = RedisUtil.getZSetOperations();
        zSetOperations.add(zSetName, key, System.currentTimeMillis());
    }

    /**
     * 移除 zset 中的某个key
     *
     * @param zSetName zset名称
     * @param key      存入key名称
     */
    public static void sRemove(String zSetName, String key) {
        ZSetOperations<String, String> zSetOperations = RedisUtil.getZSetOperations();
        zSetOperations.remove(zSetName, key);
    }

    /**
     * 分页查询
     *
     * @param zSetName  zset名称
     * @param startTime 开始日期时间
     * @param endTime   结束日期时间
     * @param startPage 起始索引
     * @param limit     记录数
     * @return 查询结果
     */
    public static Set<ZSetOperations.TypedTuple<String>> zRangeByScore(String zSetName, long startTime, long endTime,
                                                                       long startPage, long limit) {
        ZSetOperations<String, String> zSetOperations = RedisUtil.getZSetOperations();
        return zSetOperations.rangeByScoreWithScores(zSetName, startTime, endTime, startPage, limit);
    }

    /**
     * 统计记录总数
     *
     * @param zSetName  zset名称
     * @param startTime 开始日期时间
     * @param endTime   结束日期时间
     * @return 记录总数
     */
    public static Long zRangeByScore(String zSetName, long startTime, long endTime) {
        ZSetOperations<String, String> zSetOperations = RedisUtil.getZSetOperations();
        return zSetOperations.count(zSetName, startTime, endTime);
    }

}
