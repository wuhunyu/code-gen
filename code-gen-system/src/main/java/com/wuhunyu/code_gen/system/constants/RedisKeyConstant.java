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
    public static final class UserKeys {

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
    public static final class UserEnvironmentKeys {

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

    /**
     * 基类keys
     */
    public static final class BaseClassKeys {

        private BaseClassKeys() {
        }

        public static final String PREFIX = RedisKeyConstant.PREFIX + "baseClass:";

        /**
         * 基类配置信息
         */
        public static final String BASE_CLASS_MAP = PREFIX + "baseClassMap:";

        /**
         * 用户 基类关系
         */
        public static final String BASE_CLASS_SET = PREFIX + "baseClassSet:";

    }

    /**
     * 类型映射
     */
    public static final class ClassTypeKeys {

        private ClassTypeKeys() {
        }

        public static final String PREFIX = RedisKeyConstant.PREFIX + "classType:";

        /**
         * 类型映射信息
         */
        public static final String CLASS_TYPE_MAP = PREFIX + "classTypeMap:";

        /**
         * 类型映射 环境 关系
         */
        public static final String CLASS_TYPE_SET = PREFIX + "classTypeSet:";

    }

    /**
     * 数据源关系
     */
    public static final class DataSourceKeys {

        private DataSourceKeys() {
        }

        public static final String PREFIX = RedisKeyConstant.PREFIX + "dataSource:";

        /**
         * 数据源信息
         */
        public static final String DATA_SOURCE_MAP = PREFIX + "dataSourceMap:";

        /**
         * 用户&数据源 关系
         */
        public static final String DATA_SOURCE_SET = PREFIX + "dataSourceSet:";

    }

}
