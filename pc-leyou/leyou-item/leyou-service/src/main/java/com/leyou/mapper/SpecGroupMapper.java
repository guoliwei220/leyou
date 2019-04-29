package com.leyou.mapper;

import com.leyou.entity.SpecGroup;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface SpecGroupMapper extends Mapper<SpecGroup> {

    @Update("UPDATE `tb_spec_group` SET `name` =#{name} WHERE `id` = #{id};")
    void updateSpecGroup(@Param("name") String name, @Param("id") Long id);

}
