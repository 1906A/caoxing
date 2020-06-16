package com.leyou.dao;

import com.leyou.Stock;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
@Component
@org.apache.ibatis.annotations.Mapper
public interface StockMapper extends Mapper<Stock> {
}
