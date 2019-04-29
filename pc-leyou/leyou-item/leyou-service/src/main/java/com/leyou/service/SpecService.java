package com.leyou.service;

import com.leyou.entity.Brand;
import com.leyou.entity.SpecGroup;
import com.leyou.entity.SpecParam;
import com.leyou.mapper.SpecGroupMapper;
import com.leyou.mapper.SpecParamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SpecService {
    @Autowired
    private  SpecGroupMapper specGroupMapper;

    @Autowired
    private SpecParamMapper specParamMapper;

    public List<SpecGroup> querySpecGroup(Long cid) {
        SpecGroup spec = new SpecGroup();
        spec.setCid(cid);
        List<SpecGroup> select = specGroupMapper.select(spec);
        if(CollectionUtils.isEmpty(select)){
            throw  new RuntimeException("查询规格组为空");
        }
        return select;
    }

    public void saveSpecGroup(SpecGroup specGroup) {
        if(specGroup.getId() != null){
            specGroupMapper.updateSpecGroup(specGroup.getName(),specGroup.getId());
        }else {
            specGroupMapper.insertSelective(specGroup);
        }
    }

    public void deleteSpecGroup(Long id) {
        SpecGroup spec = new SpecGroup();
        spec.setId(id);
        specGroupMapper.delete(spec);
    }

    public List<SpecParam> querySpecParam(Long gid) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        List<SpecParam> specParams = specParamMapper.select(specParam);
        if(CollectionUtils.isEmpty(specParams)){
            throw new RuntimeException("查询分组信息为空");
        }
        return specParams;
    }

    public void saveSpecParam(SpecParam specParam) {
        if(specParam.getId() != null){
            specParamMapper.updateByPrimaryKey(specParam);
        }else {
            specParamMapper.insert(specParam);
        }
    }

    public void deleteSpecParam(Long id) {
        SpecParam specParam = new SpecParam();
        specParam.setId(id);
        specParamMapper.delete(specParam);
    }
}
