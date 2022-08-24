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
     * @param useEnvironmentId    环境id
     * @return 数据源列表数据
     */
    List<DataSourceInfoVo> pageDataSourceInfo(DataSourceInfoQuery dataSourceInfoQuery, Long useEnvironmentId);

    /**
     * 统计数据源列表的数量
     *
     * @param dataSourceInfoQuery 数据源查询对象
     * @param useEnvironmentId    环境id
     * @return 数据源列表的数量
     */
    Long countDataSourceInfo(DataSourceInfoQuery dataSourceInfoQuery, Long useEnvironmentId);

    /**
     * 查询指定用户下的所有数据源信息
     *
     * @param useEnvironmentId 环境id
     * @return 数据源信息
     */
    List<SelectData> listDataSourceInfoForSelect(Long useEnvironmentId);

    /**
     * 根据数据源id查询数据源信息
     *
     * @param dataSourceId     数据源id
     * @param useEnvironmentId 环境id
     * @return 数据源信息
     */
    DataSourceInfoDto findDataSourceInfoDtoByDataSourceId(Long dataSourceId, Long useEnvironmentId);

    /**
     * 新增一条数据源记录
     *
     * @param dataSourceInfoDto 数据源信息
     * @param useEnvironmentId  环境id
     */
    void insertDataSourceInfo(DataSourceInfoDto dataSourceInfoDto, Long useEnvironmentId);

    /**
     * 修改一条数据源记录
     *
     * @param dataSourceInfoDto 数据源信息
     * @param useEnvironmentId  环境id
     */
    void updateDataSourceInfo(DataSourceInfoDto dataSourceInfoDto, Long useEnvironmentId);

    /**
     * 删除一条数据源记录
     *
     * @param dataSourceId     数据源id
     * @param useEnvironmentId 环境id
     */
    void deleteDataSourceInfoByDataSourceId(Long dataSourceId, Long useEnvironmentId);

    /**
     * 测试数据库连接是否正常
     *
     * @param dataSourceInfoDto 数据源信息
     * @return 是否正常(true : 正常 ; false : 不正常)
     */
    boolean checkConnection(DataSourceInfoDto dataSourceInfoDto);

}
