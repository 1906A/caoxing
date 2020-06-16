package com.leyou.service;

import com.leyou.dao.SpecGroupMapper;
import com.leyou.dao.SpecParamMapper;
import com.leyou.SpecGroup;
import com.leyou.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpecGroupService {

    @Autowired
    private SpecGroupMapper specGroupMapper;
    @Autowired
    private SpecParamMapper specParamMapper;


    /*
    * 添加
    * */
    public void saveSpecGroup(SpecGroup specGroup) {
        specGroupMapper.insert(specGroup);
    }
    /*
    * 查询
    * */
    public List<SpecGroup> groups(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);

        //根据分类id查询规格参数组及组内的参数列表
        List<SpecGroup> groupList=new ArrayList<>();
        groupList=specGroupMapper.select(specGroup);
        groupList.forEach(group->{
            SpecParam param =new SpecParam();
            param.setGroupId(group.getId());
            group.setParams(specParamMapper.select(param));
        });

       return groupList;
    }
    /*
    * 删除
    * */
    public void deleteById(Long id) {
        specGroupMapper.deleteByPrimaryKey(id);
    }

    public void updateSpecGroup(SpecGroup specGroup) {
        specGroupMapper.updateByPrimaryKey(specGroup);
    }
}
