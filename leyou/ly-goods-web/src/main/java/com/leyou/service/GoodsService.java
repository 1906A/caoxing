package com.leyou.service;

import com.leyou.client.*;
import com.leyou.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

@Service
public class GoodsService {

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

    /*
     * 请求商品详情的微服务
     * 1：spu
     * 2：spudetail
     * 3：sku
     * 4：规格参数组
     * 5：规格参数详情
     * 6：三级分类
     * */
    public Map<String,Object> item(Long spuId){

        //1:spu
        Spu spu = spuClient.findSpuById(spuId);


        //2:spuDetail
        SpuDetail spuDetail = spuClient.findSpuDetailbySpuId(spuId);


        //3:sku
        List<Sku> skuList = skuClient.findSkuBySpuId(spuId);


        //4:规格参数组及组内信息
        List<SpecGroup> specGroupList = specClient.groups(spu.getCid3());


        //5:规格参数详情
        List<SpecParam> specparamByCidAndGeneric = specClient.findSpecparamByCidAndGeneric(spu.getCid3(),false);
        //规格参数的特殊属性
        Map<Long,String> paramMap=new HashMap<>();
        specparamByCidAndGeneric.forEach(param->{
            paramMap.put(param.getId(),param.getName());
        });


        //6:三级分类
        Category category1 = categoryClient.findCategoryById(spu.getCid1());
        Category category2 = categoryClient.findCategoryById(spu.getCid2());
        Category category3 = categoryClient.findCategoryById(spu.getCid3());

        List<Category> categoryList=new ArrayList<>();
        categoryList.add(category1);
        categoryList.add(category2);
        categoryList.add(category3);


        //7:查询品牌
        Brand brand = brandClient.findBrandByid(spu.getBrandId());

        Map<String,Object> map=new  HashMap<>();

        map.put("spu",spu);
        map.put("spuDetail",spuDetail);
        map.put("skuList",skuList);
        map.put("specGroupList",specGroupList);
        map.put("paramMap",paramMap);
        map.put("categoryList",categoryList);
        map.put("brand",brand);

        return map;
    }
    /*
    * 创建静态页面
    * */
    public void createHtml(Long spuId) {

        PrintWriter writer=null;

        try {
            // 创建thymeleaf上下文对象
            Context context = new Context();
            // 把数据放入上下文对象
            /*context.setVariable("spu",spu);
            context.setVariable("spuDetail",spuDetail);
            context.setVariable("skuList",skuList);
            context.setVariable("specGroupList",specGroupList);
            context.setVariable("categoryList",categoryList);
            context.setVariable("brand",brand);
            context.setVariable("paramMap",paramMap);*/
            context.setVariables(this.item(spuId));
            // 创建输出流
            File file = new File("D:\\jiyun\\nginx-1.16.1\\nginx-1.16.1\\html\\" + spuId + ".html");
            writer = new PrintWriter(file);
            // 执行页面静态化方法
            templateEngine.process("item", context, writer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            //关闭流
            writer.close();
        }


    }

    /*
    * 删除静态页面
    * */
    public void deleteHtml(Long spuId) {
        File file = new File("D:\\jiyun\\nginx-1.16.1\\nginx-1.16.1\\html\\" + spuId + ".html");
        if(file!=null && file.exists()){
            file.delete();
        }
    }
}
