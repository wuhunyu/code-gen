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
     * 空白字符串s
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

}
