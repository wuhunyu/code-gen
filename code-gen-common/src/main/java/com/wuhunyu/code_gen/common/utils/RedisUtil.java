package com.wuhunyu.code_gen.common.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.wuhunyu.code_gen.common.constants.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

@Slf4j
public final class RedisUtil {

    /**
     * RedisTemplate bean名称
     */
    private static final String REDIS_TEMPLATE_BEAN_NAME = "stringRedisTemplate";

    private RedisUtil() {
    }

    /**
     * =================================================获取操作对象======================================================
     */

    /**
     * 获取 RedisTemplate 对象
     *
     * @return RedisTemplate 对象
     */
    private static StringRedisTemplate getRedisTemplate() {
        return SpringUtil.getBean(REDIS_TEMPLATE_BEAN_NAME);
    }

    /**
     * 获取 string 操作对象
     *
     * @return string 操作对象
     */
    private static ValueOperations<String, String> getValueOperations() {
        StringRedisTemplate redisTemplate = RedisUtil.getRedisTemplate();
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
        StringRedisTemplate redisTemplate = RedisUtil.getRedisTemplate();
        return redisTemplate.opsForHash();
    }

    /**
     * 获取 zset 操作对象
     *
     * @return zset 操作对象
     */
    private static ZSetOperations<String, String> getZSetOperations() {
        StringRedisTemplate redisTemplate = RedisUtil.getRedisTemplate();
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
     */
    public static void set(String key, String value) {
        ValueOperations<String, String> valueOperations = RedisUtil.getValueOperations();
        valueOperations.set(key, value);
    }

    /**
     * set
     *
     * @param key      key
     * @param value    value
     * @param expire   过期时长
     * @param timeUnit 时间单位
     */
    public static void set(String key, String value, long expire, TimeUnit timeUnit) {
        ValueOperations<String, String> valueOperations = RedisUtil.getValueOperations();
        valueOperations.set(key, value, expire, timeUnit);
    }

    /**
     * get
     *
     * @param key key
     * @return 值
     */
    public static String get(String key) {
        ValueOperations<String, String> valueOperations = RedisUtil.getValueOperations();
        return valueOperations.get(key);
    }

    /**
     * 设置过期时间
     *
     * @param key        key
     * @param expire     过期时长
     * @param chronoUnit 时间单位
     * @return 设置过期时间是否成功
     */
    public static Boolean expire(String key, long expire, ChronoUnit chronoUnit) {
        StringRedisTemplate redisTemplate = RedisUtil.getRedisTemplate();
        return redisTemplate.expire(key, Duration.of(expire, chronoUnit));
    }

    /**
     * 判断是否存在某个key
     *
     * @param key key
     * @return 是否存在某个key(true : 存在 ; false : 不存在)
     */
    public static Boolean exists(String key) {
        StringRedisTemplate redisTemplate = RedisUtil.getRedisTemplate();
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除某个key
     *
     * @param key key
     * @return 是否删除成功(true : 成功 ; false : 失败)
     */
    public static Boolean delete(String key) {
        StringRedisTemplate redisTemplate = RedisUtil.getRedisTemplate();
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
     */
    public static <T> void hSet(String mapName, T data) {
        Class<?> dataClass = data.getClass();
        Field[] fields = dataClass.getDeclaredFields();
        Map<String, String> map = new HashMap<>(fields.length);
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                Object obj = field.get(data);
                if (obj == null) {
                    continue;
                }
                // LocalDateTime
                if (field.getType().isAssignableFrom(LocalDateTime.class)) {
                    LocalDateTime localDateTime = (LocalDateTime) obj;
                    map.put(field.getName(), LocalDateTimeUtil.formatLocalDateTime(localDateTime));
                    continue;
                }
                map.put(field.getName(), obj.toString());
            }
        } catch (IllegalAccessException e) {
            log.error("反射异常: {}", e.getLocalizedMessage(), e);
            throw new RuntimeException(CommonConstant.DEFAULT_EXCEPTION_MSG);
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
        if (value == null) {
            return;
        }
        HashOperations<String, String, String> hashOperations = RedisUtil.getHashOperations();
        if (value instanceof LocalDateTime) {
            LocalDateTime localDateTime = (LocalDateTime) value;
            hashOperations.put(mapName, key, LocalDateTimeUtil.formatLocalDateTime(localDateTime));
            return;
        }
        hashOperations.put(mapName, key, value.toString());
    }

    /**
     * 获取 hash对象 的某个属性
     *
     * @param mapName hash名称
     * @param key     key
     * @param <V>     值类型
     * @return 属性值
     */
    public static <V> V hGet(String mapName, String key, Class<V> vClass) {
        HashOperations<String, String, V> hashOperations = RedisUtil.getHashOperations();
        try {
            V v = hashOperations.get(mapName, key);
            if (v == null) {
                return null;
            }
            return RedisUtil.convertToT(v, vClass);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            log.error("反射异常: {}", e.getLocalizedMessage(), e);
            throw new RuntimeException(CommonConstant.DEFAULT_EXCEPTION_MSG);
        }
    }

    /**
     * 获取 hash对象 转换成 普通bean对象
     *
     * @param mapName hash名称
     * @param vClass  值类型
     * @param <V>     普通bean对象类型
     * @return 普通bean对象
     */
    public static <V> V hGetAll(String mapName, Class<V> vClass) {
        HashOperations<String, String, Object> hashOperations = RedisUtil.getHashOperations();
        Map<String, Object> map = hashOperations.entries(mapName);
        // 返回空
        if (CollUtil.isEmpty(map)) {
            return null;
        }
        try {
            // 获取无参构造
            Constructor<V> nullParamsConstructor = vClass.getDeclaredConstructor();
            V instance = nullParamsConstructor.newInstance();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() == null) {
                    continue;
                }
                try {
                    Field field = vClass.getDeclaredField(entry.getKey());
                    field.setAccessible(true);
                    field.set(instance, RedisUtil.convertToT(entry.getValue(), field.getType()));
                } catch (NoSuchFieldException e) {
                    log.warn("{} 字段不存在", entry.getKey());
                }
            }
            return instance;
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            log.error("反射异常: {}", e.getLocalizedMessage(), e);
            throw new RuntimeException(CommonConstant.DEFAULT_EXCEPTION_MSG);
        }
    }

    /**
     * 强转原基础属性为指定类型
     *
     * @param source 原基础属性
     * @param tClass 指定类型
     * @param <T>    指定类型
     * @return 指定类型的值
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private static <T> T convertToT(Object source, Class<T> tClass)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (source == null) {
            return null;
        }
        String str = source.toString();
        if (tClass.isAssignableFrom(String.class)) {
            // String
            return tClass.cast(str);
        } else if (tClass.isAssignableFrom(LocalDateTime.class)) {
            // LocalDateTime
            return tClass.cast(LocalDateTimeUtil.parseLocalDateTime(str));
        }
        // other
        Method valueOf = tClass.getMethod("valueOf", String.class);
        valueOf.setAccessible(true);
        Object val = valueOf.invoke(null, str);
        return tClass.cast(val);
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
     * =================================================zSet 操作方法====================================================
     */

    /**
     * 新增 zSet 值
     *
     * @param zSetName zSet名称
     * @param key      存入key名称
     */
    public static void zAdd(String zSetName, String key) {
        ZSetOperations<String, String> zSetOperations = RedisUtil.getZSetOperations();
        zSetOperations.add(zSetName, key, System.currentTimeMillis());
    }

    /**
     * 新增 zSet 值
     *
     * @param zSetName zSet名称
     * @param key      存入key名称
     * @param score    分数
     */
    public static void zAdd(String zSetName, String key, double score) {
        ZSetOperations<String, String> zSetOperations = RedisUtil.getZSetOperations();
        zSetOperations.add(zSetName, key, score);
    }

    /**
     * 移除 zSet 中的某个key
     *
     * @param zSetName zSet名称
     * @param key      待移除key名称
     */
    public static void zRemove(String zSetName, String key) {
        ZSetOperations<String, String> zSetOperations = RedisUtil.getZSetOperations();
        zSetOperations.remove(zSetName, key);
    }

    /**
     * 判断一个元素是否存在于 zSet 中
     *
     * @param zSetName zSet名称
     * @param key      key名称
     * @return 是否存在(true : 存在 ; false : 不存在)
     */
    public static boolean zExists(String zSetName, String key) {
        ZSetOperations<String, String> zSetOperations = RedisUtil.getZSetOperations();
        Long rank = zSetOperations.rank(zSetName, key);
        return rank != null;
    }

    /**
     * 分页查询
     *
     * @param zSetName  zSet名称
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
     * 查询全部
     *
     * @param zSetName zSet名称
     * @return 查询结果
     */
    public static Set<String> zRange(String zSetName) {
        ZSetOperations<String, String> zSetOperations = RedisUtil.getZSetOperations();
        return zSetOperations.range(zSetName, 0L, -1L);
    }

    /**
     * 统计记录总数
     *
     * @param zSetName  zSet名称
     * @param startTime 开始日期时间
     * @param endTime   结束日期时间
     * @return 记录总数
     */
    public static Long countZSetByScore(String zSetName, long startTime, long endTime) {
        ZSetOperations<String, String> zSetOperations = RedisUtil.getZSetOperations();
        return zSetOperations.count(zSetName, startTime, endTime);
    }

    /**
     * 统计记录总数
     *
     * @param zSetName zSet名称
     * @return 记录总数
     */
    public static Long countZSet(String zSetName) {
        ZSetOperations<String, String> zSetOperations = RedisUtil.getZSetOperations();
        Long count = zSetOperations.count(zSetName, 0D, -1D);
        return count == null ? 0L : count;
    }

}
