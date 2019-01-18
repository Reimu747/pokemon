package com.reimu747.pokemon.dao;

import com.reimu747.pokemon.model.vo.TokuseiVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Reimu747
 */
@Mapper
@Repository
public interface TokuseiDao {
    /**
     * 获取所有特性的列表
     *
     * @return 所有特性的列表
     */
    @Select(value = "SELECT `name`, `description` FROM tokusei")
    List<TokuseiVO> getAllTokusei();

    /**
     * 通过特性名称查找对应特性
     *
     * @param name 特性名称
     * @return 对应特性
     */
    @Select(value = "SELECT `name`, `description` FROM tokusei WHERE name = #{name}")
    TokuseiVO getTokuseiByName(@Param(value = "name") String name);
}