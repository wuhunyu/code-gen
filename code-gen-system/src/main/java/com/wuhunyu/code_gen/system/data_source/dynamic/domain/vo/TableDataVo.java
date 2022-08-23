package com.wuhunyu.code_gen.system.data_source.dynamic.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.wuhunyu.code_gen.common.constants.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 表格数据Vo
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/23 19:49
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableDataVo {

    /**
     * 表id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long tableId;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 类名
     */
    private String className;

    /**
     * 表注释
     */
    private String tableComment;

    /**
     * 创建日期时间
     */
    @JsonFormat(pattern = CommonConstant.DATE_TIME_FORMATTER_STR)
    private LocalDateTime createDateTime;

}
