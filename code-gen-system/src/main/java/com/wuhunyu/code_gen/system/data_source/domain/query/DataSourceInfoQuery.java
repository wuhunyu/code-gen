package com.wuhunyu.code_gen.system.data_source.domain.query;

import com.wuhunyu.code_gen.common.constants.CommonConstant;
import com.wuhunyu.code_gen.system.common.BaseQuery;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 数据源 查询类
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/19 16:55
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class DataSourceInfoQuery extends BaseQuery {

    /**
     * 开始日期时间
     */
    @DateTimeFormat(pattern = CommonConstant.DATE_TIME_FORMATTER_STR)
    private LocalDateTime startDatetime;

    /**
     * 结束日期时间
     */
    @DateTimeFormat(pattern = CommonConstant.DATE_TIME_FORMATTER_STR)
    private LocalDateTime endDatetime;

}
