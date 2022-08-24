package com.wuhunyu.code_gen.system.data_source.basic.service;

import com.wuhunyu.code_gen.common.domain.SelectData;
import com.wuhunyu.code_gen.system.data_source.basic.domain.dto.DataSourceInfoDto;
import com.wuhunyu.code_gen.system.data_source.basic.domain.query.DataSourceInfoQuery;
import com.wuhunyu.code_gen.system.data_source.basic.domain.vo.DataSourceInfoVo;

import java.util.List;

/**
 * 数据源 处理
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/19 17:18
 */

public interface DataSourceInfoService {

    /**
     * 分页查询数据源列表数据
     *
     * @param dataSourceInfoQuery 数据源查询对象
     * @param userId              用户id
     * @return 数据源列表数据
     */
    List<DataSourceInfoVo> pageDataSourceInfo(DataSourceInfoQuery dataSourceInfoQuery, Long userId);

    /**
     * 统计数据源列表的数量
     *
     * @param dataSourceInfoQuery 数据源查询对象
     * @param userId              用户id
     * @return 数据源列表的数量
     */
    Long countDataSourceInfo(DataSourceInfoQuery dataSourceInfoQuery, Long userId);

    /**
     * 查询指定用户下的所有数据源信息
     *
     * @param userId 用户id
     * @return 数据源信息
     */
    List<SelectData> listDataSourceInfoForSelect(Long userId);

    /**
     * 根据数据源id查询数据源信息
     *
     * @param dataSourceId 数据源id
     * @param userId       用户id
     * @return 数据源信息
     */
    DataSourceInfoDto findDataSourceInfoDtoByDataSourceId(Long dataSourceId, Long userId);

    /**
     * 新增一条数据源记录
     *
     * @param dataSourceInfoDto 数据源信息
     * @param userId            用户id
     */
    void insertDataSourceInfo(DataSourceInfoDto dataSourceInfoDto, Long userId);

    /**
     * 修改一条数据源记录
     *
     * @param dataSourceInfoDto 数据源信息
     * @param userId            用户id
     */
    void updateDataSourceInfo(DataSourceInfoDto dataSourceInfoDto, Long userId);

    /**
     * 删除一条数据源记录
     *
     * @param dataSourceId 数据源id
     * @param userId       用户id
     */
    void deleteDataSourceInfoByDataSourceId(Long dataSourceId, Long userId);

    /**
     * 测试数据库连接是否正常
     *
     * @param dataSourceInfoDto 数据源信息
     * @return 是否正常(true : 正常 ; false : 不正常)
     */
    boolean checkConnection(DataSourceInfoDto dataSourceInfoDto);

}
