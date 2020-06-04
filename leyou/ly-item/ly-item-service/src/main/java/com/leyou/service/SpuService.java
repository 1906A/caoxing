package com.leyou.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.PageResult;
import com.leyou.dao.SkuMapper;
import com.leyou.dao.SpuDetailMapper;
import com.leyou.dao.SpuMapper;
import com.leyou.dao.StockMapper;
import com.leyou.pojo.Sku;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.pojo.Stock;
import com.leyou.vo.SpuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SpuService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;

    public PageResult<SpuVo> findSpuByLimit(String key, Integer saleable, Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        List<SpuVo> spuList=spuMapper.findSpuByLimit(key,saleable);

        PageInfo<SpuVo> pageInfo=new PageInfo<SpuVo>(spuList);

        return new PageResult<SpuVo>(pageInfo.getTotal(),pageInfo.getList());
    }

    /*
     * 保存商品信息
     * */
    public void saveOrUpdategoods(SpuVo spuVo) {
        Date date = new Date();
        Spu spu = new Spu();
        spu.setTitle(spuVo.getTitle());
        spu.setSubTitle(spuVo.getSubTitle());
        spu.setBrandId(spuVo.getBrandId());
        spu.setCid1(spuVo.getCid1());
        spu.setCid2(spuVo.getCid2());
        spu.setCid3(spuVo.getCid3());
        spu.setSaleable(false);
        spu.setValid(true);
        spu.setCreateTime(date);
        spu.setLastUpdateTime(date);
        spuMapper.insert(spu);

        SpuDetail spuDetail=spuVo.getSpuDetail();
        spuDetail.setSpuId(spu.getId());
        spuDetailMapper.insert(spuDetail);

        List<Sku> skus=spuVo.getSkus();
        skus.forEach(sku -> {
            sku.setSpuId(spu.getId());
            sku.setEnable(true);
            sku.setCreateTime(date);
            sku.setLastUpdateTime(date);
            skuMapper.insert(sku);

            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockMapper.insert(stock);
        });
    }

    public SpuDetail findSpuDetailbySpuId(Long spuId) {

        return spuDetailMapper.selectByPrimaryKey(spuId);
    }
/*
*
* 修改商品信息
* */
    public void updateGoods(SpuVo spuVo) {
        Date date = new Date();
        spuVo.setSaleable(null);
        spuVo.setValid(null);
        spuVo.setCreateTime(null);
        spuVo.setLastUpdateTime(date);
        spuMapper.updateByPrimaryKeySelective(spuVo);

        SpuDetail spuDetail=spuVo.getSpuDetail();
        spuDetail.setSpuId(spuVo.getId());
        spuDetailMapper.updateByPrimaryKeySelective(spuDetail);

        List<Sku> skus = spuVo.getSkus();
        skus.forEach(sku -> {
            sku.setEnable(false);
            skuMapper.updateByPrimaryKey(sku);

            stockMapper.deleteByPrimaryKey(sku.getId());
        });

        List<Sku> skus1=spuVo.getSkus();
        skus1.forEach(sku -> {
            sku.setSpuId(spuVo.getId());
            sku.setEnable(true);
            sku.setCreateTime(date);
            sku.setLastUpdateTime(date);
            skuMapper.insert(sku);

            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockMapper.insert(stock);
        });

    }

    public void deleteBySpuId(Long spuId) {
        List<Sku> skuList = skuMapper.findSkuBySpuId(spuId);
        skuList.forEach(sku -> {
            sku.setEnable(false);
            skuMapper.updateByPrimaryKeySelective(sku);

            stockMapper.deleteByPrimaryKey(sku.getId());
        });

        spuDetailMapper.deleteByPrimaryKey(spuId);

        spuMapper.deleteByPrimaryKey(spuId);
    }

    public void upOrDown(Long spuId,int saleable) {
        Spu spu = new Spu();
        spu.setId(spuId);
        spu.setSaleable(saleable==1?true:false);

        spuMapper.updateByPrimaryKeySelective(spu);
    }

    public Spu findSpuById(Long spuId) {
        return spuMapper.selectByPrimaryKey(spuId);
    }
}
