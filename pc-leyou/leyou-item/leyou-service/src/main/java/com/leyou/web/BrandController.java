package com.leyou.web;

import com.leyou.entity.Brand;
import com.leyou.service.BrandService;
import com.leyou.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("brand")
@RestController
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 查询品牌管理
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param key
     * @return
     */

    @GetMapping("page")
    public ResponseEntity<PageResult> queryBrandByPage(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                                       @RequestParam(value = "rows",defaultValue = "5") Integer rows,
                                                       @RequestParam(value = "sortBy",required =false) String sortBy,
                                                       @RequestParam(value = "desc",defaultValue = "false") Boolean desc,
                                                       @RequestParam(value = "key",required = false) String key){
        return ResponseEntity.ok(brandService.queryBrandByPage(page,rows,sortBy,desc,key));

    }

    /**
     * 新增品牌
     * @param brand
     * @param cids
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand , @RequestParam("cids") List<Long> cids){
        brandService.saveBrand(brand,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandByCid(@PathVariable("cid") Long cid){
        List<Brand> brands = brandService.queryBrandByCid(cid);
        return ResponseEntity.ok(brands);
    }
}
