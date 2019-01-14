package com.reimu747.pokemon.service.impl;

import com.reimu747.pokemon.model.enums.StatusConditionEnum;
import com.reimu747.pokemon.model.vo.PokeBallVO;
import com.reimu747.pokemon.model.vo.PokemonInstanceVO;
import com.reimu747.pokemon.service.PokeBallService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * TODO 会心捕捉概率、精灵球摇动次数的概率
 * 捕获概率由以下步骤计算得出：
 * 1、计算B = floor((3 × HP能力值 - 2 × 当前HP) × pokemon捕获率 × 精灵球捕获修正 × 异常状态修正 ÷ (3 × HP能力值) × 4096);
 * 2、计算G = 0x000FFFF0 ÷ (0x00FF0000 ÷ B) ^ 0.25;
 * 3、在0x0000 - 0xFFFF范围内产生4个随机数。如果每一个数都小于G，那么捕获成功。即捕获概率 = (G / 0xFFFF) ^ 4。
 * 当随机数小于G时，精灵球摇动一次。即精灵球每次摇动的概率是G / 0xFFFF
 *
 * @ClassName PokeBallServiceImpl
 * @Author Reimu747
 * @Date 2019/1/14 7:35
 * @Description
 * @Version 1.0
 **/
@Service
public class PokeBallServiceImpl implements PokeBallService
{
    /**
     * 捕获率相关
     */
    private static final double MAX_CATCH_RATE = 1.0D;
    private static final int SUCCESS_CATCH = 255;
    private static final int MAX_RAND = 0xFFFF;
    private static final int DECISON_TIMES = 4;

    /**
     * 捕获修正相关
     */
    private static final String BEAST_BALL_NAME = "究极球";
    private static final int[] ULTRA_BEAST_ID_ARRAY = {793, 794, 795, 796, 797, 798, 799, 805, 806};
    private static final double BEAST_BALL_CATCH_ULTRA_BEAST_CORRECTION = 5.0D;
    private static final double BEAST_BALL_CATCH_OTHERS_CORRECTION = 0.1D;

    /**
     * 异常状态修正相关
     */
    private static final double NO_STATUS_CONDITION = 1.0D;
    private static final double FREEZE_OR_SLEEP = 2.0D;
    private static final double OTHER_STATUS_CONDITION = 1.5D;

    /**
     * B相关
     */
    private static final double B_PARAM_ONE = 4096;

    /**
     * G相关
     */
    private static final int G_PARAM_ONE = 0x000F_FFF0;
    private static final int G_PARAM_TWO = 0x00FF_0000;

    @Override
    public double getCatchRate(PokemonInstanceVO pokemon, PokeBallVO ball)
    {
        double paramB = getB(pokemon, ball);
        if (paramB >= SUCCESS_CATCH)
        {
            return MAX_CATCH_RATE;
        }
        else
        {
            double paramG = getG(paramB);
            // TODO 此处计算概率存疑
            return Math.pow(paramG / MAX_RAND, DECISON_TIMES);
        }
    }

    /**
     * 根据B计算G
     *
     * @param paramB B
     * @return G
     */
    private double getG(double paramB)
    {
        return G_PARAM_ONE / (Math.sqrt(Math.sqrt(G_PARAM_TWO / paramB)));
    }

    /**
     * 计算B
     *
     * @param pokemon 待抓pokemon，包含HP能力值，当前HP，捕获度，异常状态
     * @param ball    精灵球，包含捕获度
     * @return B
     */
    private double getB(PokemonInstanceVO pokemon, PokeBallVO ball)
    {
        int maxHp = pokemon.getStatsVO().getHpStats();
        int hpNow = pokemon.getHpNow();
        int catchRate = pokemon.getCatchRate();

        // 捕获修正
        double catchCorrection = getCatchCorrection(pokemon, ball);
        // 异常状态修正
        double statusConditionCorrection = getStatusCorrection(pokemon);

        return (int) ((3 * maxHp - 2 * hpNow) * catchRate * catchCorrection * statusConditionCorrection / (3 * maxHp) * B_PARAM_ONE) / B_PARAM_ONE;
    }

    /**
     * TODO 待完善，可能还有其他能够影响捕获修正的因素没有考虑
     * 通过待抓pokemon和精灵球，计算捕获修正
     *
     * @param pokemon 待抓pokemon，包含HP能力值，当前HP，捕获度，异常状态
     * @param ball    精灵球，包含捕获度
     * @return 捕获修正
     */
    private double getCatchCorrection(PokemonInstanceVO pokemon, PokeBallVO ball)
    {
        if (ball.getCatchRate() != null)
        {
            return ball.getCatchRate();
        }
        else
        {
            // 究极球
            if (BEAST_BALL_NAME.equals(ball.getName()))
            {
                // 究极异兽
                if (Arrays.binarySearch(ULTRA_BEAST_ID_ARRAY, pokemon.getNationalPokedexId()) != -1)
                {
                    return BEAST_BALL_CATCH_ULTRA_BEAST_CORRECTION;
                }
                else
                {
                    return BEAST_BALL_CATCH_OTHERS_CORRECTION;
                }
            }
        }
        return 1.0D;
    }

    /**
     * 计算异常状态修正
     *
     * @param pokemon 待抓pokemon，包含异常状态
     * @return 异常状态修正
     */
    private double getStatusCorrection(PokemonInstanceVO pokemon)
    {
        // TODO 用Optional和lambda表达式实现
        StatusConditionEnum statusCondition = pokemon.getStatusConditionEnum();
        if (statusCondition == null)
        {
            return NO_STATUS_CONDITION;
        }
        else if (statusCondition.equals(StatusConditionEnum.SLEEP) || statusCondition.equals(StatusConditionEnum.FREEZE))
        {
            return FREEZE_OR_SLEEP;
        }
        else
        {
            return OTHER_STATUS_CONDITION;
        }
    }
}
