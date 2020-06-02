package com.leyou.dao;

import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Component
@org.apache.ibatis.annotations.Mapper
public interface BrandMapper extends Mapper<Brand> {

    List<Brand> findBrandByPage(@Param("key") String key,
                                @Param("sortBy")String sortBy,
                                @Param("desc")Boolean desc);


    List<Brand> findBrandByLimit(@Param("key") String key,
                                 @Param("page") int page,
                                 @Param("rows") Integer rows,
                                 @Param("sortBy") String sortBy,
                                 @Param("desc") Boolean desc);

    Long findBrandCount(@Param("key") String key,
                        @Param("sortBy")String sortBy,
                        @Param("desc")Boolean descc);

    @Insert("insert into tb_category_brand values(#{cid},#{bid})")
    void addBrandAndCategory(Long bid, Long cid);

    void addBrand(Brand brand);
    @Delete("delete from tb_category_brand where brand_id=#{id}")
    void deleteBrandAndCategory(Long id);
    @Delete("delete from tb_brand where id=#{id}")
    void deleteById(Long id);
    @Select("select * from tb_category where id in(select category_id from tb_category_brand where brand_id=#{id})")
    List<Category> findCategoryByBrandId(Long id);
    @Update("update tb_brand set name=#{name},image=#{image},letter=#{letter} where id=#{id}")
    void updateById(Brand brand);
    /*
     * 根据bid查询brandName
     * */
    @Select("select b.* from tb_brand b,tb_category_brand c where b.id=c.brand_id and c.category_id=#{cid}")
    List<Brand> findBrandByCid(Long cid);

    @Select("select * from tb_brand where id=#{id}")
    Brand findBrandByid(long id);
}


