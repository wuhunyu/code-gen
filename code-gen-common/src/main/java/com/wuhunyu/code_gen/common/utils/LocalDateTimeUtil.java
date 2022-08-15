package com.wuhunyu.code_gen.common.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * LocalDateTime工具类
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/15 11:24
 */

public final class LocalDateTimeUtil {

    private LocalDateTimeUtil() {
    }

    /**
     * 标准日期时间格式
     */
    public static final String DATE_TIME_FORMATTER_STR = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期时间格式化对象
     */
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER_STR);

    /**
     * 反格式化日期时间
     *
     * @param dateTimeStr 日期时间字符串
     * @return LocalDateTime对象
     */
    public static LocalDateTime parseLocalDateTime(String dateTimeStr) {
        return LocalDateTime.from(LocalDateTimeUtil.DATE_TIME_FORMATTER.parse(dateTimeStr));
    }

    /**
     * 格式化日期时间
     *
     * @param localDateTime LocalDateTime对象
     * @return 日期时间字符串
     */
    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        return DATE_TIME_FORMATTER.format(localDateTime);
    }

}
