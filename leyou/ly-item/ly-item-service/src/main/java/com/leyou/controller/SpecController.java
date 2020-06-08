package com.leyou.controller;

import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import com.leyou.service.SpecGroupService;
import com.leyou.service.SpecParamService;
import jdk.nashorn.internal.ir.ReturnNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spec")
public class SpecController {

    @Autowired
    private SpecGroupService specGroupService;

    @Autowired
    private SpecParamService specParamService;

    /*
    * 编辑规格组数据
    * */
    @RequestMapping("group")
    public void saveSpecGroup(@RequestBody SpecGroup specGroup){

        if(specGroup.getId()==null){
            specGroupService.saveSpecGroup(specGroup);
        }else{
            specGroupService.updateSpecGroup(specGroup);
        }

    }
    /*
    * 查询规格组数据
    * */
    @RequestMapping("groups/{cid}")
    public List<SpecGroup> groups(@PathVariable("cid") Long cid){
        return specGroupService.groups(cid);
    }
    /*
    * 删除规格组数据
    * */
    @RequestMapping("group/{id}")
    public void deleteById(@PathVariable("id") Long id){
       specGroupService.deleteById(id);
    }

    /*
    * 查询规格参数组数据
    * */
    @RequestMapping("params")
    public List<SpecParam> params(@RequestParam Long gid){

        return specParamService.params(gid);
    }
    /*
    * 编辑规格参数组数据
    * */
    @RequestMapping("param")
    public void param(@RequestBody SpecParam specParam){

        if(specParam.getId()==null){
            specParamService.saveSpecParam(specParam);
        }else{
            specParamService.updateSpecParam(specParam);
        }

    }
    /*
    * 删除规格参数组数据
    * */
    @RequestMapping("param/{id}")
    public void deleteSpecParam(@PathVariable("id")Long id){
        specParamService.deleteSpecParam(id);
    }
    /*
    * 根据分类id查询规格参数
    * */
    @RequestMapping("specParams")
    public List<SpecParam> findSpecParamByCid(@RequestParam Long cid){
        return specParamService.findSpecParamByCid(cid);
    }

    /*
    * 根据三级分类id+搜索条件为1的参数查询规格参数
    * */
    @RequestMapping("specParamsBycid")
    public List<SpecParam> findSpecParamByCidAndSearch(@RequestParam Long cid){
        return specParamService.findSpecParamByCidAndSearch(cid);
    }

    /*
    * 根据cid3和非通用属性查询规格参数详情
    * */
    @RequestMapping("findSpecparamByCidAndGeneric")
    public List<SpecParam> findSpecparamByCidAndGeneric(@RequestParam Long cid,@RequestParam("generic")boolean generic){
        return specParamService.findSpecparamByCidAndGeneric(cid,generic);
    }

}
