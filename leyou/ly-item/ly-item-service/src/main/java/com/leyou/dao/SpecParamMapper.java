package com.leyou.dao;

import com.leyou.pojo.SpecParam;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Component
@org.apache.ibatis.annotations.Mapper
public interface SpecParamMapper extends Mapper<SpecParam> {

    @Select("select * from tb_spec_param p,tb_category c where p.cid=c.id and c.id=#{cid}")
    List<SpecParam> findSpecParamByCid(Long cid);
}
