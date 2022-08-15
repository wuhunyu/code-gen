package com.wuhunyu.code_gen.framework.utils;

import com.wuhunyu.code_gen.RedisApplication;
import com.wuhunyu.code_gen.common.sequence.SequenceInstance;
import com.wuhunyu.code_gen.common.utils.RedisUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/15 10:32
 */

@Slf4j
@SpringBootTest(classes = RedisApplication.class)
class RedisUtilTest {

    @Test
    void set() {
        RedisUtil.set("wuhunyu", "wuhunyu");
    }

    @Test
    void setExpire() {
        RedisUtil.set("expire", "wuhunyu", 10, TimeUnit.SECONDS);
    }

    @Test
    void get() {
        String str = RedisUtil.get("wuhunyu");
        System.out.println(str);
    }

    @Test
    void expire() {
        RedisUtil.expire("wuhunyu", 10, ChronoUnit.SECONDS);
    }

    @Test
    void exists() {
        System.out.println("存在" + RedisUtil.exists("wuhunyu"));
    }

    @Test
    void delete() {
        System.out.println(RedisUtil.delete("wuhunyu"));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class User {

        private Long userId;

        private String userName;

        private Integer age;

        private LocalDateTime birth;

    }

    @Test
    void hSet() throws IllegalAccessException {
        User user = new User(1543806743933992962L, "wuhunyu", 25, LocalDateTime.now());
        RedisUtil.hSet("user", user);
    }

    @Test
    void testHSet() {
        RedisUtil.hSet("user", "age", 25);
        RedisUtil.hSet("user", "birth", LocalDateTime.now());
    }

    @Test
    void hGet() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Integer id = RedisUtil.hGet("user", "id", Integer.class);
        String userName = RedisUtil.hGet("user", "userName", String.class);
        Integer age = RedisUtil.hGet("user", "age", Integer.class);
        LocalDateTime birth = RedisUtil.hGet("user", "birth", LocalDateTime.class);
        System.out.println(id);
        System.out.println(userName);
        System.out.println(age);
        System.out.println(birth);
    }

    @Test
    void hGetAll() throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        User user = RedisUtil.hGetAll("user", User.class);
        System.out.println(user);
    }

    @Test
    void hDel() {
        RedisUtil.hDel("user", "userName");
    }

    @Test
    void sAdd() {
        RedisUtil.sAdd("zset", "wuhunyu1");
        RedisUtil.sAdd("zset", "wuhunyu2");
        RedisUtil.sAdd("zset", "wuhunyu3");
    }

    @Test
    void sRemove() {
        RedisUtil.sRemove("zset", "wuhunyu2");
    }

    @Test
    void zRangeByScore() {
        long end = System.currentTimeMillis();
        long start = end - 1000L * 60 * 60 * 24 * 3;
        Set<ZSetOperations.TypedTuple<String>> zset1 = RedisUtil.zRangeByScore("zset", start, end, 0, 2);
        Set<ZSetOperations.TypedTuple<String>> zset2 = RedisUtil.zRangeByScore("zset", start, end, 2, 2);
        System.out.println(zset1);
        System.out.println(zset2);
    }

    @Test
    void testZRangeByScore() {
        long end = System.currentTimeMillis();
        long start = end - 1000L * 60 * 60 * 24 * 3;
        Long size = RedisUtil.countZSetByScore("zset", start, end);
        System.out.println(size);
    }

    @Test
    void test() {
        log.info("id: {}", SequenceInstance.INSTANCE.nextId());
    }

}