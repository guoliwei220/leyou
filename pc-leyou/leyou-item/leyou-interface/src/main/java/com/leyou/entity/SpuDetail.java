package com.leyou.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "tb_spu_detail")
public class SpuDetail {
    @Id
    private Long spuId;

    private String description;

    private  String specifications;

    private String specTemplate;

    private  String packingList;

    private String afterService;
}
