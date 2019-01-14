package com.reimu747.pokemon.dao;

import com.reimu747.pokemon.model.vo.TokuseiVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author Reimu747
 */
@Mapper
@Repository
public interface TokuseiDao
{
    /**
     * 通过特性名称查找对应特性
     * @param name 特性名称
     * @return 对应特性
     */
    @Select(value = "SELECT `name`, `description` FROM tokusei WHERE name = #{name}")
    TokuseiVO getTokuseiByName(@Param(value = "name") String name);
}