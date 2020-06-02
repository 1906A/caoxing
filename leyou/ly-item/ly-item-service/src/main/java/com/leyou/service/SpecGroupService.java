package com.leyou.service;

import com.leyou.dao.SpecGroupMapper;
import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecGroupService {

    @Autowired
    private SpecGroupMapper specGroupMapper;
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

       return specGroupMapper.select(specGroup);
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
