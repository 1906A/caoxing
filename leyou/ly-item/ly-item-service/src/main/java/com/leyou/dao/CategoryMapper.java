package com.leyou.dao;

import com.leyou.pojo.Category;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
@Component
@org.apache.ibatis.annotations.Mapper
public interface CategoryMapper extends Mapper<Category> {
}
