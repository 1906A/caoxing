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

    /*
     * 根据cid3和非通用属性查询规格参数详情
     * */
    @Select("select * from tb_spec_param where cid=#{cid} and generic=0")
    List<SpecParam> findSpecparamByCidAndGeneric(Long cid);
}
