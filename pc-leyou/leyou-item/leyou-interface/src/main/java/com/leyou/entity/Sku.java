package com.leyou.entity;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Data
@Table(name = "tb_sku")
public class Sku {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private Long spuId;

    private String title;

    private String images;

    private Long price;

    private String indexes;

    private String ownSpec;

    private Integer enable;

    private Date createTime;

    private Date lastUpdateTime;

    @Transient
    private  Integer stock;
}
