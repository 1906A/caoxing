package com.leyou.service;

import com.leyou.dao.SpecParamMapper;
import com.leyou.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecParamService {

    @Autowired
    SpecParamMapper specParamMapper;
    /*
    * 查询规格参数组数据
    * */
    public List<SpecParam> params(Long gid) {
        SpecParam specParam=new SpecParam();
        specParam.setGroupId(gid);

        return specParamMapper.select(specParam);
    }
    /*
    * 添加规格参数组数据
    * */
    public void saveSpecParam(SpecParam specParam) {
        specParamMapper.insert(specParam);
    }
    /*
    * 修改规格参数组数据
    * */
    public void updateSpecParam(SpecParam specParam) {
        specParamMapper.updateByPrimaryKey(specParam);
    }
    /*
    * 删除规格参数组数据
    * */
    public void deleteSpecParam(Long id) {
        specParamMapper.deleteByPrimaryKey(id);
    }

    public List<SpecParam> findSpecParamByCid(Long cid) {
        return specParamMapper.findSpecParamByCid(cid);
    }

    /*
     * 根据三级分类id+搜索条件为1的参数查询规格参数
     * */
    public List<SpecParam> findSpecParamByCidAndSearch(Long cid) {
        SpecParam specParam = new SpecParam();
        specParam.setCid(cid);
        specParam.setSearching(true);

        return specParamMapper.select(specParam);
    }
    /*
     * 根据cid3和非通用属性查询规格参数详情
     * */
    public List<SpecParam> findSpecparamByCidAndGeneric(Long cid) {
        return specParamMapper.findSpecparamByCidAndGeneric(cid);
    }
}
