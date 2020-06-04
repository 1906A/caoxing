package com.leyou.controller;


import com.leyou.client.CategoryClient;
import com.leyou.client.SkuClient;
import com.leyou.client.SpecClient;
import com.leyou.client.SpuClient;
import com.leyou.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class GoodsDetailController {

    @Autowired
    SpuClient spuClient;
    @Autowired
    SkuClient skuClient;
    @Autowired
    SpecClient specClient;
    @Autowired
    CategoryClient categoryClient;

    @RequestMapping("hello")
    public String hello(Model model){
        String name="张三";

        model.addAttribute("name",name);
        return "hello";
    }
    /*
    * 请求商品详情的微服务
    * 1：spu
    * 2：spudetail
    * 3：sku
    * 4：规格参数组
    * 5：规格参数详情
    * 6：三级分类
    * */
    @RequestMapping("item/{spuId}.html")
    public String item(Model model, @PathVariable("spuId")Long spuId){

        //1:spu
        Spu spu = spuClient.findSpuById(spuId);
        model.addAttribute("spu",spu);

        //2:spuDetail
        SpuDetail spuDetail = spuClient.findSpuDetailbySpuId(spuId);
        model.addAttribute("spuDetail",spuDetail);

        //3:sku
        List<Sku> skuList = skuClient.findSkuBySpuId(spuId);
        model.addAttribute("skuList",skuList);

        //4:规格参数组
        List<SpecGroup> specGroupList = specClient.groups(spu.getCid3());
        model.addAttribute("specGroupList",specGroupList);

        //5:规格参数详情
        List<SpecParam> specparamByCidAndGeneric = specClient.findSpecparamByCidAndGeneric(spu.getCid3());
        model.addAttribute("specparamByCidAndGeneric",specparamByCidAndGeneric);

        //6:三级分类
        Category category1 = categoryClient.findCategoryById(spu.getCid1());
        Category category2 = categoryClient.findCategoryById(spu.getCid2());
        Category category3 = categoryClient.findCategoryById(spu.getCid3());
        model.addAttribute("category1",category1);
        model.addAttribute("category2",category2);
        model.addAttribute("category3",category3);


        model.addAttribute("name",spuId);
        return "item";
    }
}
