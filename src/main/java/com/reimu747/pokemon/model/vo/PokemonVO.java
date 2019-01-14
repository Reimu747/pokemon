package com.reimu747.pokemon.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * pokemon基类，包含图鉴中的数据
 * @ClassName PokemonVO
 * @Author Reimu747
 * @Date 2019/1/5 10:00
 * @Description
 * @Version 1.0
 **/
@Data
public class PokemonVO
{
    /**
     * 全国图鉴id
     */
    @JsonProperty(value = "national_pokedex_id")
    private Integer nationalPokedexId;
    /**
     * 名称
     */
    private String name;
    /**
     * 属性1
     */
    @JsonProperty(value = "type_one")
    private String typeOne;
    /**
     * 属性2
     */
    @JsonProperty(value = "type_two")
    private String typeTwo;
    /**
     * 特性1
     */
    @JsonProperty(value = "ability_one")
    private String abilityOne;
    /**
     * 特性2
     */
    @JsonProperty(value = "ability_two")
    private String abilityTwo;
    /**
     * 隐藏特性
     */
    @JsonProperty(value = "ability_invisible")
    private String abilityInvisible;

    /**
     * 种族值
     */
    @JsonProperty(value = "hp_ss")
    private Integer hpSs;
    @JsonProperty(value = "attack_ss")
    private Integer attackSs;
    @JsonProperty(value = "defense_ss")
    private Integer defenseSs;
    @JsonProperty(value = "special_attack_ss")
    private Integer specialAttackSs;
    @JsonProperty(value = "special_defense_ss")
    private Integer specialDefenseSs;
    @JsonProperty(value = "speed_ss")
    private Integer speedSs;

    /**
     * 捕获率
     */
    @JsonProperty(value = "catch_rate")
    private Integer catchRate;
}
