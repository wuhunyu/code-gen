package com.wuhunyu.code_gen.system.constants;

/**
 * redis key常量
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/15 17:28
 */

public final class RedisKeyConstant {

    private RedisKeyConstant() {
    }

    /**
     * 前缀
     */
    public static final String PREFIX = "CodeGen:";

    /**
     * 用户key
     */
    public final static class UserKeys {

        private UserKeys() {
        }

        public static final String PREFIX = RedisKeyConstant.PREFIX + "user:";

        /**
         * 用户信息
         */
        public static final String USER_MAP = PREFIX + "userMap:";

        /**
         * 用户登录名称信息
         */
        public static final String LOGIN_NAME_MAP = PREFIX + "loginNameMap";

    }

    /**
     * 环境keys
     */
    public final static class UserEnvironmentKeys {

        private UserEnvironmentKeys() {
        }

        public static final String PREFIX = RedisKeyConstant.PREFIX + "userEnvironment:";

        /**
         * 环境信息
         */
        public static final String USER_ENVIRONMENT_MAP = PREFIX + "userEnvironmentMap:";

        /**
         * 用户 环境关系
         */
        public static final String USER_ENVIRONMENT_SET = PREFIX + "userEnvironmentSet:";

    }

}
