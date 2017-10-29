package com.blysin.leshop.admin.dao;

import com.blysin.leshop.admin.domain.FilterChailMap;
import com.blysin.leshop.common.mybatis.MyBaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 代码生成器
 * @since 2017-10-29
 */
@Mapper
public interface FilterChailMapDao extends MyBaseMapper<FilterChailMap> {

    LinkedList<FilterChailMap> findMapping();
}