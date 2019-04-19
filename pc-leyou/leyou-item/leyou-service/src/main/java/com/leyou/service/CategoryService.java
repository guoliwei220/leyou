package com.leyou.service;

import com.leyou.entity.Category;
import com.leyou.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    public  List<Category> getCategoryByPid(Long pid) {
        Category t = new Category();
        t.setParentId(pid);
        List<Category> categoryList = categoryMapper.select(t);
        if(CollectionUtils.isEmpty(categoryList)){
            throw new RuntimeException("无此商品！");
        }
        return categoryList;
    }
}
