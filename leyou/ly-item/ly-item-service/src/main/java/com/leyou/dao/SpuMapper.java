package com.leyou.dao;

import com.leyou.pojo.Spu;
import com.leyou.vo.SpuVo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Component
@org.apache.ibatis.annotations.Mapper
public interface SpuMapper extends Mapper<Spu> {

    List<SpuVo> findSpuByLimit(@RequestParam("key") String key,
                               @RequestParam("saleable") Integer saleable);

}
