package com.wuhunyu.code_gen.system.data_source.dynamic.enums;

/**
 * 是否为主键
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/23 19:12
 */

public enum FieldPrimaryKeyEnum {

    /**
     * 主键
     */
    PRIMARY_KEY(1, "主键"),

    /**
     * 非主键
     */
    NON_PRIMARY_KEY(2, "非主键");

    private final int primaryKey;

    private final String msg;

    FieldPrimaryKeyEnum(int primaryKey, String msg) {
        this.primaryKey = primaryKey;
        this.msg = msg;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public String getMsg() {
        return msg;
    }
}
