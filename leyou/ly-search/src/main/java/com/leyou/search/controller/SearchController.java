package com.leyou.search.controller;

import com.leyou.common.PageResult;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import com.leyou.search.client.BrandClient;
import com.leyou.search.client.CategoryClient;
import com.leyou.search.item.Goods;
import com.leyou.search.pojo.SearchRequest;
import com.leyou.search.pojo.SearchResult;
import com.leyou.search.repository.GoodsRepository;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    CategoryClient categoryClient;
    @Autowired
    BrandClient brandClient;

    @RequestMapping("page")
    public PageResult<Goods> page(@RequestBody SearchRequest searchRequest){
        System.out.println(searchRequest.getKey()+"====="+searchRequest.getPage());

        //去es索引库做查询 or的关系
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        //构造条件在es中查询all用and的关系
        builder.withQuery(QueryBuilders.matchQuery("all",searchRequest.getKey()).operator(Operator.AND));
        //分页查询
        builder.withPageable(PageRequest.of(searchRequest.getPage()-1,searchRequest.getSize()));
        //根据新品排序
        builder.withSort(SortBuilders.fieldSort(searchRequest.getSortBy())
                .order(searchRequest.isDescending()? SortOrder.DESC:SortOrder.ASC));

        String categoryName="categoryName";
        String brandName="brandName";

        builder.addAggregation(AggregationBuilders.terms(categoryName).field("cid3"));
        builder.addAggregation(AggregationBuilders.terms(brandName).field("brandId"));

        AggregatedPage<Goods> search= (AggregatedPage<Goods>) goodsRepository.search(builder.build());

        LongTerms categoryAgg= (LongTerms) search.getAggregation(categoryName);
        List<Category> categoryList=new  ArrayList<>();
        categoryAgg.getBuckets().forEach(bucket -> {
            Long categoryId = (Long) bucket.getKey();
            Category category = categoryClient.findCategoryById(categoryId);
            categoryList.add(category);
        });

        LongTerms brandAgg= (LongTerms) search.getAggregation(brandName);
        List<Brand> brandList=new  ArrayList<>();
        brandAgg.getBuckets().forEach(bucket -> {
            Long brandId = (Long) bucket.getKey();
            Brand brand = brandClient.findBrandByid(brandId);
            brandList.add(brand);
        });

        //执行查询  totalelement 总条数  content 查询内容  totalpage  总页数
        //Page<Goods> search = goodsRepository.search(builder.build());
        return  new SearchResult(search.getTotalElements(),search.getContent(),search.getTotalPages(),
                categoryList,brandList);
    }
}
