package com.wuhunyu.code_gen.system.data_source.enums;

import com.wuhunyu.code_gen.common.domain.SelectData;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据库类型
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/19 13:12
 */

public enum DBTypeEnum {

    /**
     * MySQL
     */
    MYSQL(1, "MySQL");

    /**
     * 枚举值
     */
    private final int type;

    /**
     * 数据库类型名称
     */
    private final String typeName;

    DBTypeEnum(int type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public int getType() {
        return type;
    }

    public String getTypeName() {
        return typeName;
    }

    /**
     * 返回 数据库类型 列表信息
     *
     * @return 数据库类型 列表信息
     */
    public static List<SelectData> listDBTypes() {
        return Arrays.stream(DBTypeEnum.values())
                .map(dbTypeEnum -> new SelectData(
                        dbTypeEnum.getTypeName(),
                        String.valueOf(dbTypeEnum.getType())))
                .collect(Collectors.toList());
    }

}
