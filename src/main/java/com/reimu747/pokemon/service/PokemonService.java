package com.reimu747.pokemon.service;

import com.reimu747.pokemon.model.enums.NatureEnum;
import com.reimu747.pokemon.model.vo.*;

import java.util.List;

/**
 * @author Reimu747
 */
public interface PokemonService
{
    /**
     * 通过pokemon名称获取对应的pokemon
     *
     * @param name 名称
     * @return 对应的pokemon
     */
    PokemonVO getPokemon(String name);

    /**
     * 获取所有pokemon列表，仅包含全国id和名称
     *
     * @return 列表
     */
    List<SimplePokemonVO> getAllPokemon();

    /**
     * 计算pokemon能力值
     *
     * @param pokemonVO  pokemonVO 含有种族值
     * @param ivsVO      个体值
     * @param bsVO       努力值
     * @param level      等级
     * @param natureEnum 性格
     * @return 能力值
     */
    StatsVO getStats(PokemonVO pokemonVO, IvsVO ivsVO, BsVO bsVO, int level, NatureEnum natureEnum);

    /**
     * 计算个体值的范围
     *
     * @param pokemonVO  pokemonVO 含有种族值
     * @param bsVO       努力值
     * @param statsVO    能力值
     * @param level      等级
     * @param natureEnum 性格
     * @return 应返回一个长度为2的IvsVO数组，代表个体值的上下界
     * @throws Exception TODO 输入数据有误异常
     */
    IvsVO[] getIvsRange(PokemonVO pokemonVO, BsVO bsVO, StatsVO statsVO, int level, NatureEnum natureEnum) throws Exception;

    /**
     * 计算觉醒力量属性
     *
     * @param ivsVO pokemon个体值
     * @return 觉醒力量属性
     */
    TypeVO getHiddenPowerType(IvsVO ivsVO);
}
