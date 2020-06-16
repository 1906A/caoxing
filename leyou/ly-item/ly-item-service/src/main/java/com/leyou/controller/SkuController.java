package com.leyou.controller;

import com.leyou.Sku;
import com.leyou.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("sku")
public class SkuController {

    @Autowired
    private SkuService skuService;

    @RequestMapping("list")
    public List<Sku> findSkuBySpuId(@RequestParam Long id){
        return skuService.findSkuBySpuId(id);
    }
}
