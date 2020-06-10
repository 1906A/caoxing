package com.leyou.controller;

import com.leyou.common.PageResult;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.service.SpuService;
import com.leyou.vo.SpuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spu")
public class SpuController {

    @Autowired
    private SpuService spuService;
    /*
    * 商品展示
    * */
    @RequestMapping("page")
    public PageResult<SpuVo> page(@RequestParam("key") String key,
                                  @RequestParam("saleable") Integer saleable,
                                  @RequestParam("page") Integer page,
                                  @RequestParam("rows") Integer rows){
        PageResult<SpuVo> pageResult=spuService.findSpuByLimit(key,saleable,page,rows);
        return pageResult;
    }
    /*
    * 保存商品信息
    * */
    @RequestMapping("saveOrUpdategoods")
    public void saveOrUpdategoods(@RequestBody SpuVo spuVo){

        if(spuVo.getId()!=null){
            spuService.updateGoods(spuVo);
        }else {
            spuService.saveOrUpdategoods(spuVo);
        }

    }

    @RequestMapping("detail/{id}")
    public SpuDetail findSpuDetailbySpuId(@PathVariable("id")Long spuId){
        return spuService.findSpuDetailbySpuId(spuId);
    }

    @RequestMapping("deleteBySpuId/{id}")
    public void deleteBySpuId(@PathVariable("id") Long spuId){
        spuService.deleteBySpuId(spuId);
    }

    @RequestMapping("upOrDown")
    public void upOrDown(@RequestParam("spuId") Long spuId,@RequestParam("saleable") int saleable){
        spuService.upOrDown(spuId,saleable);
    }

    /*
    * 根据spuId查询spu
    * */
    @RequestMapping("findSpuById")
    public Spu findSpuById(@RequestParam("spuId") Long spuId){
        return spuService.findSpuById(spuId);
    }


    /*
    * 根据spuId查询spu封装进spuVo
    * */
    @RequestMapping("findSpuBySpuId")
    public SpuVo findSpuBySpuId(@RequestParam("spuId") Long spuId){
        return spuService.findSpuBySpuId(spuId);
    }
}
