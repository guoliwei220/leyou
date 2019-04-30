package com.leyou.web;

import com.leyou.entity.Spu;
import com.leyou.service.GoodsService;
import com.leyou.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;
    @GetMapping("spu/page")
    public ResponseEntity<PageResult<Spu>> queryGoods( @RequestParam(value = "page",defaultValue = "1") Integer page,
                                                       @RequestParam(value = "rows",defaultValue = "5") Integer rows,
                                                      @RequestParam(value ="key", required = false) String key,
                                                      @RequestParam(value ="saleable",required = false) Boolean saleable
                                                      ){
        return ResponseEntity.ok(goodsService.queryGoods(page,rows,saleable,key));


    }
}
