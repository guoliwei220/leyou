package com.leyou.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Data
@Table(name="tb_spu")
public class Spu {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private String title;

    private String subTitle;

    private Long cid1;

    private Long cid2;

    private Long cid3;

    private Long brandId;

    private Long saleable;

    private Long valid;
    @JsonIgnore
    private Date createTime;

    @JsonIgnore //不需要返回的数据
    private Date lastUpdateTime;

    @Transient  //扩展字段
    private String cname;

    @Transient
    private String bname;
}
