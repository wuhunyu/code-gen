package com.wuhunyu.code_gen.system.data_source.basic.repository;

import com.wuhunyu.code_gen.system.data_source.basic.domain.DataSourceInfo;
import com.wuhunyu.code_gen.system.data_source.basic.domain.query.DataSourceInfoQuery;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 数据源 数据访问
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/19 12:12
 */

public interface DataSourceInfoRepository {

    /**
     * 分页查询数据源列表数据
     *
     * @param useEnvironmentId    环境id
     * @param dataSourceInfoQuery 数据源查询对象
     * @return 数据源列表数据
     */
    List<DataSourceInfo> pageDataSourceInfo(Long useEnvironmentId, DataSourceInfoQuery dataSourceInfoQuery);

    /**
     * 统计指定用户下的所有数据源记录数
     *
     * @param useEnvironmentId 环境id
     * @param startDatetime    起始日期时间
     * @param endDatetime      结束日期时间
     * @return 数据源记录数量
     */
    Long countDataSourceInfo(Long useEnvironmentId, LocalDateTime startDatetime, LocalDateTime endDatetime);

    /**
     * 查询指定用户下的全部数据源信息
     *
     * @param useEnvironmentId 环境id
     * @return 数据源信息
     */
    List<DataSourceInfo> listDataSourceInfos(Long useEnvironmentId);

    /**
     * 查询指定数据源id的数据源信息
     *
     * @param dataSourceId     数据源id
     * @param useEnvironmentId 环境id
     * @return 数据源信息
     */
    DataSourceInfo findDataSourceInfoByDataSourceId(Long dataSourceId, Long useEnvironmentId);

    /**
     * 新增一条数据源记录
     *
     * @param dataSourceInfo   数据源信息
     * @param useEnvironmentId 环境id
     */
    void insertDataSourceInfo(DataSourceInfo dataSourceInfo, Long useEnvironmentId);

    /**
     * 修改一条数据源记录
     *
     * @param dataSourceInfo   数据源信息
     * @param useEnvironmentId 环境id
     */
    void updateDataSourceInfo(DataSourceInfo dataSourceInfo, Long useEnvironmentId);

    /**
     * 删除一条数据源记录
     *
     * @param dataSourceId     数据源id
     * @param useEnvironmentId 环境id
     */
    void deleteDataSourceInfoByDataSourceId(Long dataSourceId, Long useEnvironmentId);

}
