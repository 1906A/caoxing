package com.leyou.controller;


import com.leyou.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GoodsDetailController {

    /*@Autowired
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
    TemplateEngine templateEngine;*/

    @Autowired
    GoodsService goodsService;

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

       /* //1:spu
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
        model.addAttribute("brand",brand);*/

       //查询数据
       model.addAllAttributes(goodsService.item(spuId));
       //写入静态页面
       goodsService.createHtml(spuId);

        return "item";
    }

    /*private void createHtml(Spu spu, SpuDetail spuDetail, List<Sku> skuList, List<SpecGroup> specGroupList, List<Category> categoryList, Brand brand,Map<Long, String> paramMap) {

        PrintWriter writer=null;

        try {
            // 创建thymeleaf上下文对象
            Context context = new Context();
            // 把数据放入上下文对象
            context.setVariable("spu",spu);
            context.setVariable("spuDetail",spuDetail);
            context.setVariable("skuList",skuList);
            context.setVariable("specGroupList",specGroupList);
            context.setVariable("categoryList",categoryList);
            context.setVariable("brand",brand);
            context.setVariable("paramMap",paramMap);

            // 创建输出流
            File file = new File("D:\\jiyun\\nginx-1.16.1\\nginx-1.16.1\\html\\" + spu.getId() + ".html");
            writer = new PrintWriter(file);
            // 执行页面静态化方法
            templateEngine.process("item", context, writer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            //关闭流
            writer.close();
        }


    }*/
}
