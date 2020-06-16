package com.leyou.dao;

import com.leyou.SpuDetail;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
@Component
@org.apache.ibatis.annotations.Mapper
public interface SpuDetailMapper extends Mapper<SpuDetail> {
}
