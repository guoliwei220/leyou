package com.leyou.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.entity.Brand;
import com.leyou.mapper.BrandMapper;
import com.leyou.utils.PageResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandMapper brandMapper;


    public PageResult<Brand> queryBrandByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        //分页
        PageHelper.startPage(page,rows);
        //过滤
        Example example = new Example(Brand.class);
        if(StringUtils.isNotBlank(key)){
            example.createCriteria().orLike("name","%"+key+"%").orEqualTo("letter",key.toUpperCase());
        }
        //排序
        if(StringUtils.isNotBlank(sortBy)){
            String orderByClause = sortBy+(desc ? " desc" : " asc");
            example.setOrderByClause(orderByClause);
        }
        //查询
        List<Brand> brands = brandMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(brands)){
            throw new RuntimeException("无此品牌");
        }
        //解析分页
        PageInfo<Brand> brandPageInfo = new PageInfo<>(brands);
        return new PageResult<>(brandPageInfo.getTotal(),brands);
    }
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        brand.setId(null);
        //新增品牌
        int count = brandMapper.insertSelective(brand);
        //新增关联表
        for (Long cid : cids) {
            count = brandMapper.insertBranCategory(cid, brand.getId());
        }
        if(count != 1){
            throw new RuntimeException("新增品牌失败");
        }
    }

    public Brand getBrandNameById(Long brandId) {
        Brand brand = brandMapper.selectByPrimaryKey(brandId);
        if(brand == null){
            throw new RuntimeException("无此品牌");
        }
        return brand;
    }

    public List<Brand> queryBrandByCid(Long cid) {
        List<Brand> brandList = brandMapper.queryBrandByCid(cid);
        if(brandList == null){
            throw new RuntimeException("无此品牌");
        }
        return brandList;
    }
}
