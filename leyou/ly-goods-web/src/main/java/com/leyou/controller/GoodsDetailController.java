package com.leyou.controller;


import com.leyou.client.*;
import com.leyou.pojo.*;
import jdk.nashorn.internal.ir.CatchNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    BrandClient brandClient;
    @Autowired
    TemplateEngine templateEngine;

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

        //4:规格参数组及组内信息
        List<SpecGroup> specGroupList = specClient.groups(spu.getCid3());
        model.addAttribute("specGroupList",specGroupList);

        //5:规格参数详情
        List<SpecParam> specparamByCidAndGeneric = specClient.findSpecparamByCidAndGeneric(spu.getCid3(),false);
        //规格参数的特殊属性
        Map<Long,String> paramMap=new HashMap<>();
        specparamByCidAndGeneric.forEach(param->{
            paramMap.put(param.getId(),param.getName());
        });
        model.addAttribute("paramMap",paramMap);

        //6:三级分类
        Category category1 = categoryClient.findCategoryById(spu.getCid1());
        Category category2 = categoryClient.findCategoryById(spu.getCid2());
        Category category3 = categoryClient.findCategoryById(spu.getCid3());

        List<Category> categoryList=new ArrayList<>();
        categoryList.add(category1);
        categoryList.add(category2);
        categoryList.add(category3);
        model.addAttribute("categoryList",categoryList);

        //7:查询品牌
        Brand brand = brandClient.findBrandByid(spu.getBrandId());
        model.addAttribute("brand",brand);


        createHTML(spu,spuDetail,skuList,specGroupList,paramMap,categoryList,brand);
        
        return "item";
    }

    private void createHTML(Spu spu, SpuDetail spuDetail, List<Sku> skuList, List<SpecGroup> specGroupList, Map<Long, String> paramMap, List<Category> categoryList, Brand brand) {

        PrintWriter writer=null;
        try {
            //1:创建上下文
            Context context = new Context();

            //2:写入文件
            context.setVariable("spu",spu);
            context.setVariable("spuDetail",spuDetail);
            context.setVariable("skuList",skuList);
            context.setVariable("specGroupList",specGroupList);
            context.setVariable("paramMap",paramMap);
            context.setVariable("categoryList",categoryList);
            context.setVariable("brand",brand);

            //3:写入文件,写入流
            File file = new File("D:\\jiyun\\nginx-1.16.1\\nginx-1.16.1\\html\\"+spu.getId()+".html");
            writer=new PrintWriter(file);

            //4:执行静态化
            templateEngine.process("skuDetail",context,writer);


        }catch (FileNotFoundException e){
            e.printStackTrace();
        }finally {
            if(writer!=null){
                writer.close();
            }
        }
    }
}
