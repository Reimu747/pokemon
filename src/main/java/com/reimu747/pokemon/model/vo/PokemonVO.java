package com.reimu747.pokemon.model.vo;

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
    private Integer nationalPokedexId;
    /**
     * 名称
     */
    private String name;
    /**
     * 属性1
     */
    private String typeOne;
    /**
     * 属性2
     */
    private String typeTwo;
    /**
     * 特性1
     */
    private String abilityOne;
    /**
     * 特性2
     */
    private String abilityTwo;
    /**
     * 隐藏特性
     */
    private String abilityInvisible;

    /**
     * 种族值
     */
    private Integer hpSs;
    private Integer attackSs;
    private Integer defenseSs;
    private Integer specialAttackSs;
    private Integer specialDefenseSs;
    private Integer speedSs;

    /**
     * 捕获率
     */
    private Integer catchRate;
}
