package com.leyou.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.entity.*;
import com.leyou.mapper.SkuMapper;
import com.leyou.mapper.SpuDetailMapper;
import com.leyou.mapper.SpuMapper;
import com.leyou.mapper.StockMapper;
import com.leyou.utils.PageResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import sun.plugin.javascript.navig.Array;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodsService {
    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private SkuMapper skuMapper;

    public PageResult<Spu> queryGoods(Integer page, Integer rows, Boolean saleable, String key) {
        //分页
        PageHelper.startPage(page,rows);
        //过滤
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(key)){
            criteria.andLike("title","%"+key+"%");
        }
        if(saleable != null){
            criteria.andEqualTo("saleable",saleable);
        }
        //排序
        example.setOrderByClause("last_update_time desc");
        //查询
        List<Spu> spus = spuMapper.selectByExample(example);
        //解析分类和品牌名称
        loadCategoryAndBrandName(spus);
        PageInfo<Spu> spuPageInfo = new PageInfo<>(spus);
        return new PageResult<>(spuPageInfo.getTotal(),spus);

    }

    private void loadCategoryAndBrandName(List<Spu> spus) {
        for (Spu spu : spus) {
            //处理分类名称
            List<String> names = categoryService.queryByIdlist(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3())).stream().map(Category::getName).collect(Collectors.toList());
            spu.setCname(StringUtils.join(names,"/"));
            //处理品牌名称
            spu.setBname(brandService.getBrandNameById(spu.getBrandId()).getName());
        }
    }
    @Transactional
    public void saveGoods(Spu spu) {
        //保存spu
        spu.setId(null);
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(spu.getCreateTime());
        spu.setValid(1);
        spu.setSaleable(1);
        spuMapper.insert(spu);
        //保存spu_detail
        spu.getSpuDetail().setSpuId(spu.getId());
        spuDetailMapper.insert(spu.getSpuDetail());
        //保存sku
        //保存stock
        List<Sku> skuList = spu.getSkus();
        saveStockAndSku(skuList,spu);
    }

    public SpuDetail getSpuDetailById(Long id) {
        SpuDetail spuDetail = spuDetailMapper.selectByPrimaryKey(id);
        if(spuDetail == null){
            throw new RuntimeException("查询spuDetail为空");
        }
        return spuDetail;
    }

    public List<Sku> getSkuBySpuId(Long spuId) {
        Sku sku1 = new Sku();
        sku1.setSpuId(spuId);
        List<Sku> skuList = skuMapper.select(sku1);
        if(CollectionUtils.isEmpty(skuList)){
            throw new RuntimeException("查询sku为空");
        }
        for (Sku sku : skuList) {
            Stock stock = stockMapper.selectByPrimaryKey(sku.getId());
            if(stock == null){
                throw new RuntimeException("查询stock为空");
            }
            sku.setStock(stock.getStock());
        }
        return skuList;
    }

    @Transactional
    public void updateGoods(Spu spu) {
        //删除sku  stock
        Sku sku = new Sku();
        sku.setId(spu.getId());
        List<Sku> skus = spu.getSkus();
        if(!CollectionUtils.isEmpty(skus)){
            skuMapper.delete(sku);
            List<Long> idList = skus.stream().map(Sku::getId).collect(Collectors.toList());
            stockMapper.deleteByIdList(idList);
        }
        //修改spu
        spu.setValid(null);
        spu.setSaleable(null);
        spu.setLastUpdateTime(new Date());
        spu.setCreateTime(null);
        int spuCount = spuMapper.updateByPrimaryKeySelective(spu);
        if(spuCount != 1){
            throw new RuntimeException("更新spu失败");
        }
        //修改spuDetail
        int spuDetailCount = spuDetailMapper.updateByPrimaryKeySelective(spu.getSpuDetail());
        if(spuDetailCount != 1){
            throw new RuntimeException("更新spuDetail失败");
        }
        //新增sku,stock
        List<Sku> skuList = spu.getSkus();
        saveStockAndSku(skuList,spu);
    }
    public void saveStockAndSku(List<Sku> skuList,Spu spu){
        for (Sku sku1 : skuList) {
            if(sku1.getEnable() != 1){
                continue;
            }
            sku1.setId(null);
            sku1.setSpuId(spu.getId());
            sku1.setCreateTime(new Date());
            sku1.setLastUpdateTime(sku1.getCreateTime());
            skuMapper.insert(sku1);
            Stock stock = new Stock();
            stock.setStock(sku1.getStock());
            stock.setSkuId(sku1.getId());
            stockMapper.insert(stock);
        }
    }
}
