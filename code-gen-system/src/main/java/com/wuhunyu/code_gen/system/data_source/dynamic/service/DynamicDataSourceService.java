package com.wuhunyu.code_gen.system.data_source.dynamic.service;

import com.wuhunyu.code_gen.common.domain.SelectData;
import com.wuhunyu.code_gen.system.data_source.dynamic.domain.query.TableQuery;
import com.wuhunyu.code_gen.system.data_source.dynamic.domain.vo.TableDataVo;

import java.util.List;

/**
 * 动态数据源 处理
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/23 19:34
 */

public interface DynamicDataSourceService {

    /**
     * 查询指定数据源id下的所有表信息
     *
     * @param dataSourceId 数据源id
     * @param userId       用户id
     * @return 表信息
     */
    List<SelectData> listNewTables(Long dataSourceId, Long userId);

    /**
     * 导入表数据
     *
     * @param dataSourceId 数据源id
     * @param userId       用户id
     */
    void importTableData(Long dataSourceId, Long userId);

    /**
     * 分页查询表数据
     *
     * @param tableQuery 表查询对象
     * @param userId     用户id
     * @return 表数据
     */
    List<TableDataVo> pageTableDataVos(TableQuery tableQuery, Long userId);

    /**
     * 统计表数据量
     *
     * @param tableQuery 表查询对象
     * @param userId     用户id
     * @return 表数据量
     */
    Long countTableData(TableQuery tableQuery, Long userId);

}
