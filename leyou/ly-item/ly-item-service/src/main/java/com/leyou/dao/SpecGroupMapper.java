package com.leyou.dao;

import com.leyou.pojo.SpecGroup;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
@Component
public interface SpecGroupMapper extends Mapper<SpecGroup> {
}
