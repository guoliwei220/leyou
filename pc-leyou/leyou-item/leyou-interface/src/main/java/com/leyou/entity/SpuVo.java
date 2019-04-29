package com.leyou.entity;

import lombok.Data;

import java.util.Date;
@Data
public class SpuVo {

    private Long id;

    private String title;

    private String subTitle;

    private Long cid1;

    private Long cid2;

    private Long cid3;

    private Long brandId;

    private Long saleable;

    private Date createTime;

    private String cname;

    private String bname;

}
