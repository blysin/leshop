package com.blysin.leshop.common.mybatis;

/**
 * Created by Blysin on 2017/4/20.
 */

import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.*;

/**
 * 整合了几个通用Mapper
 * 包括基本的增删改、findAll、分页、条件获取、主键获取等方法
 *
 * @author Blysin
 *
 * @param <T>
 */

public interface MyBaseMapper<T> extends BaseMapper<T>, RowBoundsMapper<T>,ConditionMapper<T>,IdsMapper<T> {

}
