package com.wuhunyu.code_gen.system.data_source.dynamic.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 动态数据源 mapper
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/22 20:56
 */

@Mapper
public interface DynamicDataSourceMapper {

    /**
     * 查询当前连接的数据库名称
     *
     * @return 数据库名称
     */
    String findCurDataBaseName();

    /**
     * 查询所有的表名称
     *
     * @param dataBaseName 数据库名称
     * @return 表名称
     */
    List<String> listTableNames(@Param("dataBaseName") String dataBaseName);

    /**
     * 查询表注释
     *
     * @param dataBaseName 数据库名称
     * @param tableName    表名称
     * @return 表注释
     */
    String findTableCommentByTableName(@Param("dataBaseName") String dataBaseName,
                                       @Param("tableName") String tableName);



}
