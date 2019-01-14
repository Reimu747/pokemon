package com.reimu747.pokemon.service;

import com.reimu747.pokemon.model.vo.FieldVO;
import com.reimu747.pokemon.model.vo.PokemonInstanceVO;
import com.reimu747.pokemon.model.vo.WazaVO;

import java.util.List;

/**
 * @author Reimu747
 */
public interface BattleService
{
    /**
     * 计算招式造成伤害的百分比区间
     *
     * @param attackPokemon  攻击方pokemon
     * @param defensePokemon 防御方pokemon
     * @param waza           攻击方使用的技能
     * @param field          场地
     * @param isCriticalHit  是否会心
     * @return 应返回一个长度为2的double数组，代表伤害百分比区间
     */
    double[] getDamageRange(PokemonInstanceVO attackPokemon, PokemonInstanceVO defensePokemon,
                            WazaVO waza, FieldVO field, boolean isCriticalHit);

    /**
     * 计算可能造成的伤害值列表
     *
     * @param attackPokemon  攻击方pokemon
     * @param defensePokemon 防御方pokemon
     * @param waza           攻击方使用的技能
     * @param field          场地
     * @param isCriticalHit  是否会心
     * @return 返回可能造成的伤害值列表，按从小到大排列
     */
    List<Integer> getPossibleDamages(PokemonInstanceVO attackPokemon, PokemonInstanceVO defensePokemon,
                                     WazaVO waza, FieldVO field, boolean isCriticalHit);
}
