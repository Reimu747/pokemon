package com.reimu747.pokemon.dao;

import com.reimu747.pokemon.model.vo.PokemonVO;
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
public interface PokemonDao
{
    /**
     * 通过口袋妖怪的名字，从数据库获取对应的口袋妖怪
     *
     * @param name pokemon's name
     * @return pokemonVO
     */
    @Select(value = "SELECT `national_pokedex_id` AS nationalPokedexId, `name` AS name, `type_one` AS typeOne, " +
            "`type_two` AS typeTwo, `ability_one` AS abilityOne,`ability_two` AS abilityTwo, `ability_invisible` AS " +
            "abilityInvisible, `hp_ss` AS hpSs, `attack_ss` AS attackSs, `defense_ss` AS defenseSs, " +
            "`special_attack_ss` AS specialAttackSs, `special_defense_ss` AS specialDefenseSs, `speed_ss` AS speedSs," +
            " `catch_rate` AS catchRate FROM pokemon WHERE name = #{name}")
    PokemonVO getPokemonVOByName(@Param(value = "name") String name);

    /**
     * 获取所有pokemon的列表，仅包含全国id和名称
     *
     * @return 列表
     */
    @Select(value = "SELECT `national_pokedex_id` AS nationalPokedexId, `name` AS name FROM pokemon ORDER BY " +
            "national_pokedex_id;")
    List<PokemonVO> getAllPokemon();
}