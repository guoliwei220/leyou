package com.leyou.web;

import com.leyou.entity.Category;
import com.leyou.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @RequestMapping("list")
    public ResponseEntity<List<Category>> getCategoryByPid(@RequestParam("pid") Long pid){
        List<Category> categoryByPid = categoryService.getCategoryByPid(pid);
        return ResponseEntity.ok(categoryByPid);
    }
}
