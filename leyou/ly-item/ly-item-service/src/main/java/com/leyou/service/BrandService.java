package com.leyou.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.PageResult;
import com.leyou.dao.BrandMapper;
import com.leyou.Brand;
import com.leyou.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    BrandMapper brandMapper;

    public PageResult<Brand> findBrandByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
        PageHelper.startPage(page,rows);
        List<Brand> brandList=brandMapper.findBrandByPage(key,sortBy,desc);

        PageInfo<Brand> pageInfo=new PageInfo<Brand>(brandList);

        return new PageResult<Brand>(pageInfo.getTotal(),pageInfo.getList());
    }


    public PageResult<Brand> findBrandByLimit(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
        //PageHelper.startPage(page,rows);
        Long brandCount=brandMapper.findBrandCount(key,sortBy,desc);

        List<Brand> brandList=brandMapper.findBrandByLimit(key,(page-1)*rows,rows,sortBy,desc);

        //PageInfo<Brand> pageInfo=new PageInfo<Brand>(brandList);

        return new PageResult<Brand>(brandCount,brandList);
    }

    public void brandCategorySave(Brand brand, List<Long> cids) {
        brandMapper.insert(brand);
        cids.forEach(id->{
            brandMapper.addBrandAndCategory(brand.getId(),id);
        });
    }

    public void insertBrand(Brand brand) {
        brandMapper.insertSelective(brand);
    }

    public void addBrandAndCategory(Long bid, Long cid) {
        brandMapper.addBrandAndCategory(bid,cid);
    }

    public void addBrand(Brand brand) {
        brandMapper.addBrand(brand);
    }

    public void deleteById(Long id) {
        brandMapper.deleteById(id);
        brandMapper.deleteBrandAndCategory(id);
    }

    public List<Category> findCategoryByBrandId(Long id) {
        return brandMapper.findCategoryByBrandId(id);
    }

    public void updateBrand(Brand brand, List<Long> cids) {
        brandMapper.updateById(brand);
        brandMapper.deleteBrandAndCategory(brand.getId());
        cids.forEach(cid ->{
            brandMapper.addBrandAndCategory(brand.getId(),cid);
        });
    }

    public List<Brand> findBrandByCid(Long cid) {

        return brandMapper.findBrandByCid(cid);
    }

    public Brand findBrandByid(long id) {
        return brandMapper.findBrandByid(id);
    }
}
