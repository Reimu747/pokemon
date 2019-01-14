package com.reimu747.pokemon.dao;

import com.reimu747.pokemon.model.vo.PokeBallVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author Reimu747
 */
@Mapper
@Repository
public interface PokeBallDao
{

    /**
     * 根据名称获取对应的PokeBallVO
     * @param name 名称
     * @return 对应的PokeBallVO
     */
    @Select(value = "SELECT name, catch_rate AS catchRate FROM poke_ball WHERE name = #{name}")
    PokeBallVO getPokeBallVOByName(@Param(value = "name") String name);
}
