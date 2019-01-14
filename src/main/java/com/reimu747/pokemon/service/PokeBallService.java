package com.reimu747.pokemon.service;

import com.reimu747.pokemon.model.vo.PokeBallVO;
import com.reimu747.pokemon.model.vo.PokemonInstanceVO;

/**
 * @author Reimu747
 */
public interface PokeBallService
{
    /**
     * 通过待抓pokemon和精灵球，计算捕获率
     *
     * @param pokemon 待抓pokemon，包含HP能力值，当前HP，捕获度，异常状态
     * @param ball    精灵球，包含捕获度
     * @return 捕获率
     */
    double getCatchRate(PokemonInstanceVO pokemon, PokeBallVO ball);

    // TODO 计算战斗中的捕获率
}
