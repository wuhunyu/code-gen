package com.wuhunyu.code_gen.common.constants;

/**
 * 公共常量
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/15 19:33
 */

public final class CommonConstant {

    private CommonConstant() {
    }

    /**
     * 默认异常提示
     */
    public static final String DEFAULT_EXCEPTION_MSG = "系统资产繁忙";

    /**
     * 空白字符串
     */
    public static final String BLANK_STR = "";

    /**
     * 无权限访问 code
     */
    public static final int NO_AUTH_CODE = 502;

    /**
     * 无权限访问 msg
     */
    public static final String NO_AUTH_MSG = "无权限访问";

    /**
     * 英文校验正则表达式
     */
    public static final String ENGLISH_CHAR_STR_REGEXP = "^[a-zA-Z_-]{1,120}$";

    /**
     * 纯英文校验正则表达式
     */
    public static final String PURE_ENGLISH_CHAR_STR_REGEXP = "^[a-zA-Z]{1,120}$";

    /**
     * yyyy-MM-dd
     */
    public static final String DATE_FORMATTER_STR = "yyyy-MM-dd";

    /**
     * yyyy-MM-dd HH:mm:ss,标准日期时间格式
     */
    public static final String DATE_TIME_FORMATTER_STR = "yyyy-MM-dd HH:mm:ss";

    /**
     * 最小无效日期时间取值
     */
    public static final long MIN_IN_VALID_DATE_TIME = 0L;

    /**
     * 最大无效日期时间取值(2099-12-31 12:59:59:999)
     */
    public static final long MAX_IN_VALID_DATE_TIME = 4102376399999L;

    /**
     * 无效值
     */
    public static final int INVALID_NUM = -1;

}
