package com.wuhunyu.code_gen.system.data_source.dynamic.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wuhunyu.code_gen.common.constants.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 表数据
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/22 13:20
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableData {

    /**
     * 表id
     */
    private Long tableId;

    /**
     * 数据源id
     */
    private Long dataSourceId;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表注释
     */
    private String tableComment;

    /**
     * 更新日期时间
     */
    @JsonFormat(pattern = CommonConstant.DATE_TIME_FORMATTER_STR)
    private LocalDateTime updateDateTime;

}
