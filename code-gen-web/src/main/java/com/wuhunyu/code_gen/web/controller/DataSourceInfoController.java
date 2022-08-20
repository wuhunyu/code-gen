package com.wuhunyu.code_gen.web.controller;

import com.wuhunyu.code_gen.auth.AuthContextHolder;
import com.wuhunyu.code_gen.common.domain.SelectData;
import com.wuhunyu.code_gen.common.response.Result;
import com.wuhunyu.code_gen.common.response.TableResult;
import com.wuhunyu.code_gen.system.data_source.domain.dto.DataSourceInfoDto;
import com.wuhunyu.code_gen.system.data_source.domain.query.DataSourceInfoQuery;
import com.wuhunyu.code_gen.system.data_source.domain.vo.DataSourceInfoVo;
import com.wuhunyu.code_gen.system.data_source.service.DataSourceInfoService;
import com.wuhunyu.code_gen.system.operation_type.OperationTypeGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * 数据源
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/20 17:00
 */

@RestController
@RequestMapping("/dataSourceInfo")
@RequiredArgsConstructor
@Slf4j
public class DataSourceInfoController {

    private final DataSourceInfoService dataSourceInfoService;

    /**
     * 分页查询数据源列表数据
     *
     * @param dataSourceInfoQuery 数据源查询对象
     * @return 数据源列表数据
     */
    @PostMapping("/page")
    public TableResult<DataSourceInfoVo> pageDataSourceInfo(
            @RequestBody @Validated DataSourceInfoQuery dataSourceInfoQuery) {
        // 用户id
        Long userId = AuthContextHolder.getUserId();
        Long size = dataSourceInfoService.countDataSourceInfo(dataSourceInfoQuery, userId);
        if (size == 0L) {
            return Result.page(Collections.emptyList(), size);
        }
        // 数据源列表数据
        List<DataSourceInfoVo> dataSourceInfoVos =
                dataSourceInfoService.pageDataSourceInfo(dataSourceInfoQuery, userId);
        return Result.page(dataSourceInfoVos, size);
    }

    /**
     * 查询当前用户的所有数据源信息
     *
     * @return 数据源信息
     */
    @GetMapping
    public List<SelectData> listDataSourceInfoForSelect() {
        return dataSourceInfoService.listDataSourceInfoForSelect(AuthContextHolder.getUserId());
    }

    /**
     * 新增一条数据源记录
     *
     * @param dataSourceInfoDto 数据源信息
     */
    @PostMapping("/insert")
    public void insertDataSourceInfo(@RequestBody @Validated(OperationTypeGroup.Insert.class)
                                             DataSourceInfoDto dataSourceInfoDto) {
        dataSourceInfoService.insertDataSourceInfo(dataSourceInfoDto, AuthContextHolder.getUserId());
    }

    /**
     * 修改一条数据源记录
     *
     * @param dataSourceInfoDto 数据源信息
     */
    @PutMapping
    public void updateDataSourceInfo(@RequestBody @Validated(OperationTypeGroup.Update.class)
                                             DataSourceInfoDto dataSourceInfoDto) {
        dataSourceInfoService.updateDataSourceInfo(dataSourceInfoDto, AuthContextHolder.getUserId());
    }

    /**
     * 删除一条数据源记录
     *
     * @param dataSourceId 数据源id
     */
    @DeleteMapping("/{dataSourceId}")
    public void deleteDataSourceInfoByDataSourceId(@PathVariable("dataSourceId") Long dataSourceId) {
        dataSourceInfoService.deleteDataSourceInfoByDataSourceId(dataSourceId, AuthContextHolder.getUserId());
    }

}
