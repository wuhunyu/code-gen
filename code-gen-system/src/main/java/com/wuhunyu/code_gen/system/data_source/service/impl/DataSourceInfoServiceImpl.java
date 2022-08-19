package com.wuhunyu.code_gen.system.data_source.service.impl;

import com.wuhunyu.code_gen.common.domain.SelectData;
import com.wuhunyu.code_gen.system.data_source.domain.dto.DataSourceInfoDto;
import com.wuhunyu.code_gen.system.data_source.domain.query.DataSourceInfoQuery;
import com.wuhunyu.code_gen.system.data_source.domain.vo.DataSourceInfoVo;
import com.wuhunyu.code_gen.system.data_source.repository.DataSourceInfoRepository;
import com.wuhunyu.code_gen.system.data_source.service.DataSourceInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据源 处理实现
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/19 17:30
 */

@Service("dataSourceInfoService")
@Primary
@RequiredArgsConstructor
@Slf4j
public class DataSourceInfoServiceImpl implements DataSourceInfoService {

    private final DataSourceInfoRepository dataSourceInfoRepository;

    @Override
    public List<DataSourceInfoVo> pageDataSourceInfo(DataSourceInfoQuery dataSourceInfoQuery, Long userId) {
        return null;
    }

    @Override
    public Long countDataSourceInfo(DataSourceInfoQuery dataSourceInfoQuery, Long userId) {
        return null;
    }

    @Override
    public List<SelectData> listDataSourceInfoForSelect(Long userId) {
        return null;
    }

    @Override
    public void insertDataSourceInfo(DataSourceInfoDto dataSourceInfoDto, Long userId) {

    }

    @Override
    public void updateDataSourceInfo(DataSourceInfoDto dataSourceInfoDto, Long userId) {

    }

    @Override
    public void deleteDataSourceInfoByDataSourceId(Long dataSourceId, Long userId) {

    }
}
