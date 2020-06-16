package com.leyou.service;

import com.leyou.dao.SkuMapper;
import com.leyou.dao.StockMapper;
import com.leyou.Sku;
import com.leyou.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkuService {

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;

    public List<Sku> findSkuBySpuId(Long spuId) {
        List<Sku> skuList = skuMapper.findSkuBySpuId(spuId);
        skuList.forEach(sku -> {
            Stock stock=stockMapper.selectByPrimaryKey(sku.getId());
            sku.setStock(stock.getStock());
        });
        return skuList;
    }
}
