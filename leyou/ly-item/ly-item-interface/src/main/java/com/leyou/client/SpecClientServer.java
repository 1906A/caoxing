package com.leyou.client;

import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@RequestMapping("spec")
public interface SpecClientServer {
    @RequestMapping("specParamsBycid")
    public List<SpecParam> findSpecParamByCidAndSearch(@RequestParam Long cid);

    /*
     * 根据cid查询规格组数据
     * */
    @RequestMapping("groups/{cid}")
    public List<SpecGroup> groups(@PathVariable("cid") Long cid);

    /*
     * 根据cid3和非通用属性查询规格参数详情
     * */
    @RequestMapping("findSpecparamByCidAndGeneric")
    public List<SpecParam> findSpecparamByCidAndGeneric(@RequestParam Long cid);
}
