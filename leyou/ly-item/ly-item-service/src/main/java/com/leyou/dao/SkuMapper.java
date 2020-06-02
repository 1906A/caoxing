package com.leyou.dao;

import com.leyou.pojo.Sku;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Component
@org.apache.ibatis.annotations.Mapper
public interface SkuMapper extends Mapper<Sku> {

    @Select("select s.* from tb_sku s where s.enable=1 and  s.spu_id=#{spuId}")
    List<Sku> findSkuBySpuId(Long spuId);
}
