package com.wuhunyu.code_gen.system.data_source.repository;

import com.wuhunyu.code_gen.system.data_source.domain.DataSourceInfo;
import com.wuhunyu.code_gen.system.data_source.domain.query.DataSourceInfoQuery;

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
     * @param userId              用户id
     * @param dataSourceInfoQuery 数据源查询对象
     * @return 数据源列表数据
     */
    List<DataSourceInfo> pageDataSourceInfo(Long userId, DataSourceInfoQuery dataSourceInfoQuery);

    /**
     * 统计指定用户下的所有数据源记录数
     *
     * @param userId        用户id
     * @param startDatetime 起始日期时间
     * @param endDatetime   结束日期时间
     * @return 数据源记录数量
     */
    Long countDataSourceInfo(Long userId, LocalDateTime startDatetime, LocalDateTime endDatetime);

    /**
     * 查询指定用户下的全部数据源信息
     *
     * @param userId 用户id
     * @return 数据源信息
     */
    List<DataSourceInfo> listDataSourceInfos(Long userId);

    /**
     * 查询指定数据源id的数据源信息
     *
     * @param dataSourceId 数据源id
     * @param userId       用户id
     * @return 数据源信息
     */
    DataSourceInfo findDataSourceInfoByDataSourceId(Long dataSourceId, Long userId);

    /**
     * 新增一条数据源记录
     *
     * @param dataSourceInfo 数据源信息
     * @param userId         用户id
     */
    void insertDataSourceInfo(DataSourceInfo dataSourceInfo, Long userId);

    /**
     * 修改一条数据源记录
     *
     * @param dataSourceInfo 数据源信息
     * @param userId         用户id
     */
    void updateDataSourceInfo(DataSourceInfo dataSourceInfo, Long userId);

    /**
     * 删除一条数据源记录
     *
     * @param dataSourceId 数据源id
     * @param userId       用户id
     */
    void deleteDataSourceInfoByDataSourceId(Long dataSourceId, Long userId);

}
