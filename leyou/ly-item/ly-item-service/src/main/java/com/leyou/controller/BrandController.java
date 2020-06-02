package com.leyou.controller;

import com.leyou.common.PageResult;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import com.leyou.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    BrandService brandService;

    @RequestMapping("page")
    public Object findBrandByPage(@RequestParam("key")String key,
                                  @RequestParam("page")Integer page,
                                  @RequestParam("rows")Integer rows,
                                  @RequestParam("sortBy")String sortBy,
                                  @RequestParam("desc")Boolean desc){
        //PageResult<Brand> brandList=brandService.findBrandByPage(key,page,rows,sortBy,desc);
        PageResult<Brand> brandList=brandService.findBrandByLimit(key,page,rows,sortBy,desc);

        return  brandList;
    }

    @RequestMapping("addOrEditBrand")
    public void addOrEditBrand(Brand brand,
                               @RequestParam("cids") List<Long> cids){

        if(brand.getId()!=null){
            brandService.updateBrand(brand,cids);
        }else{
            brandService.addBrand(brand);
            cids.forEach(id->{
                brandService.addBrandAndCategory(brand.getId(),id);
            });
        }

    }

    @RequestMapping("deleteById/{id}")
    public void deleteById(@PathVariable("id")Long id){
        brandService.deleteById(id);
    }

    @RequestMapping("bid/{id}")
    public List<Category> findCategoryByBrandId(@PathVariable("id")Long id){
        return brandService.findCategoryByBrandId(id);
    }
    /*
    * 根据分类id查询品牌
    * */
    @RequestMapping("cid/{cid}")
    public List<Brand> findBrandByCid(@PathVariable("cid") Long cid){
        return brandService.findBrandByCid(cid);
    }

    @RequestMapping("findBrandByid")
    public Brand findBrandByid(@RequestParam("id") long id){
        return brandService.findBrandByid(id);
    }
}
