package com.reimu747.pokemon.dao;

import com.reimu747.pokemon.model.vo.WazaVO;
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
public interface WazaDao
{
    /**
     * 获取所有招式的列表
     *
     * @return 所有招式的列表
     */
    @Select(value = "SELECT waza.name, type.name AS typeName, waza.power, waza.hit_rate AS hitRate, waza_type.name " +
            "AS wazaTypeName, waza.is_direct_attack AS isDirectAttack, waza.is_kotei_waza AS isKoteiWaza, waza" +
            ".has_secondary_effect AS hasSecondaryEffect, waza.is_sound AS isSound\n" +
            "FROM waza\n" +
            "INNER JOIN type ON waza.type_id = type.id\n" +
            "INNER JOIN waza_type ON waza.waza_type_id = waza_type.id")
    List<WazaVO> getAllWaza();

    /**
     * 通过招式名称查找对应招式
     *
     * @param name 招式名称
     * @return 对应招式
     */
    @Select(value = "SELECT waza.name, type.name AS typeName, waza.power, waza.hit_rate AS hitRate, waza_type.name " +
            "AS wazaTypeName, waza.is_direct_attack AS isDirectAttack, waza.is_kotei_waza AS isKoteiWaza, waza" +
            ".has_secondary_effect AS hasSecondaryEffect, waza.is_sound AS isSound\n" +
            "FROM waza\n" +
            "INNER JOIN type ON waza.type_id = type.id\n" +
            "INNER JOIN waza_type ON waza.waza_type_id = waza_type.id\n" +
            "WHERE waza.name = #{name}")
    WazaVO getWazaByName(@Param(value = "name") String name);
}
