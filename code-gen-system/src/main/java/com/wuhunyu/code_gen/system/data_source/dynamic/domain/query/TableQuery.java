package com.wuhunyu.code_gen.system.data_source.dynamic.domain.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wuhunyu.code_gen.common.constants.CommonConstant;
import com.wuhunyu.code_gen.system.common.BaseQuery;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 表数据查询
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/23 21:16
 */

@Data
@NoArgsConstructor
public class TableQuery extends BaseQuery {

    /**
     * 开始日期时间
     */
    @JsonFormat(pattern = CommonConstant.DATE_TIME_FORMATTER_STR)
    private LocalDateTime startDatetime;

    /**
     * 结束日期时间
     */
    @JsonFormat(pattern = CommonConstant.DATE_TIME_FORMATTER_STR)
    private LocalDateTime endDatetime;

}
