package com.reimu747.pokemon.dao;

import com.reimu747.pokemon.model.vo.TypeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Reimu747
 */
@Mapper
@Repository
public interface TypeDao
{
    /**
     * get all types for a list.
     *
     * @return types list
     */
    @Select(value = "SELECT `id`, `name`, `0x` AS notEffective, `1/2x` AS notVeryEffective, `2x` AS superEffective " +
            "FROM type")
    List<TypeVO> getAllTypes();

    /**
     * 获取属性名称对应的TypeVO
     *
     * @param name 属性名称
     * @return 对应的TypeVO
     */
    @Select(value = "SELECT `id`, `name`, `0x` AS notEffective, `1/2x` AS notVeryEffective, `2x` AS superEffective " +
            "FROM type WHERE name = #{name}")
    TypeVO getTypeVOByName(@Param(value = "name") String name);

    /**
     * 获取id对应的TypeVO
     *
     * @param id 属性表中的id值
     * @return 对应的TypeVO
     */
    @Select(value = "SELECT `id`, `name`, `0x` AS notEffective, `1/2x` AS notVeryEffective, `2x` AS superEffective " +
            "FROM type WHERE id = #{id}")
    TypeVO getTypeVOById(@Param(value = "id") Integer id);
}
