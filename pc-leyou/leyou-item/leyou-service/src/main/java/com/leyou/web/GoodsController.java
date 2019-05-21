package com.leyou.web;

import com.leyou.entity.Sku;
import com.leyou.entity.Spu;
import com.leyou.entity.SpuDetail;
import com.leyou.service.GoodsService;
import com.leyou.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 保存商品
     * @param spu
     * @return
     */
    @PostMapping("goods")
    public ResponseEntity<Void> saveGoods(@RequestBody Spu spu){
        goodsService.saveGoods(spu);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("spu/detail/{spuId}")
    public ResponseEntity<SpuDetail> getSpuDetailById(@PathVariable("spuId") Long spuId){
        return ResponseEntity.ok(goodsService.getSpuDetailById(spuId));
    }
    @GetMapping("/sku/list")
    public ResponseEntity<List<Sku>> getSkuBySpuId(@RequestParam(value = "id") Long spuId){
        return ResponseEntity.ok(goodsService.getSkuBySpuId(spuId));
    }

    @PutMapping("goods")
    public ResponseEntity<Void> updateGoods(@RequestBody Spu spu){
        goodsService.updateGoods(spu);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
