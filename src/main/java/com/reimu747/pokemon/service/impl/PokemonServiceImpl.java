package com.reimu747.pokemon.service.impl;

import com.reimu747.pokemon.dao.TypeDao;
import com.reimu747.pokemon.model.enums.AbilityEnum;
import com.reimu747.pokemon.model.enums.NatureEnum;
import com.reimu747.pokemon.model.vo.*;
import com.reimu747.pokemon.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @ClassName PokemonServiceImpl
 * @Author Reimu747
 * @Date 2019/1/5 10:36
 * @Description
 * @Version 1.0
 **/
@Service
public class PokemonServiceImpl implements PokemonService
{
    @Autowired
    private TypeDao typeDao;

    /**
     * 脱壳忍者全国ID，Hp能力值
     */
    private static final int SHEDINJA_NATIONAL_POKEDEX_ID = 292;
    private static final int SHEDINJA_HP_STATS = 1;

    /**
     * 性格修正
     */
    private static final double NO_EFFECT_RATE = 1.0D;
    private static final double INCRASE_RATE = 1.1D;
    private static final double DECRASE_RATE = 0.9D;

    /**
     * 个体值范围
     */
    private static final int MIN_IVS = 0;
    private static final int MAX_IVS = 31;

    @Override
    public StatsVO getStats(PokemonVO pokemonVO, IvsVO ivsVO, BsVO bsVO, int level, NatureEnum natureEnum)
    {
        // 各项能力值
        int hpStats;
        int attackStats;
        int defenseStats;
        int specialAttackStats;
        int specialDefenseStats;
        int speedStats;

        // 如果是脱壳忍者，HP能力值为1
        if (pokemonVO.getNationalPokedexId() == SHEDINJA_NATIONAL_POKEDEX_ID)
        {
            hpStats = SHEDINJA_HP_STATS;
        }
        else
        {
            hpStats = getHpStats(pokemonVO.getHpSs(), ivsVO.getHpIvs(), bsVO.getHpBs(), level);
        }

        Optional<AbilityEnum> decrase = Optional.ofNullable(natureEnum.getDecraseAbility());
        Optional<AbilityEnum> incrase = Optional.ofNullable(natureEnum.getIncraseAbility());

        // 先不考虑性格修正，计算能力值并封装
        attackStats = getOtherStats(pokemonVO.getAttackSs(), ivsVO.getAttackIvs(), bsVO.getAttackBs(), level,
                NO_EFFECT_RATE);
        defenseStats = getOtherStats(pokemonVO.getDefenseSs(), ivsVO.getDefenseIvs(), bsVO.getDefenseBs(), level,
                NO_EFFECT_RATE);
        specialAttackStats = getOtherStats(pokemonVO.getSpecialAttackSs(), ivsVO.getSpecialAttackIvs(),
                bsVO.getSpecialAttackBs(), level, NO_EFFECT_RATE);
        specialDefenseStats = getOtherStats(pokemonVO.getSpecialDefenseSs(), ivsVO.getSpecialDefenseIvs(),
                bsVO.getSpecialDefenseBs(), level, NO_EFFECT_RATE);
        speedStats = getOtherStats(pokemonVO.getSpeedSs(), ivsVO.getSpeedIvs(), bsVO.getSpeedBs(), level,
                NO_EFFECT_RATE);

        StatsVO res = StatsVO.builder()
                .hpStats(hpStats)
                .attackStats(attackStats)
                .defenseStats(defenseStats)
                .specialAttackStats(specialAttackStats)
                .specialDefenseStats(specialDefenseStats)
                .speedStats(speedStats)
                .build();

        // 再计算性格修正后的能力值
        // 需要增强的能力值
        incrase.ifPresent(abilityEnum ->
        {
            natureUpdate(res, abilityEnum, INCRASE_RATE);
        });

        // 需要削弱的能力值
        decrase.ifPresent(abilityEnum ->
        {
            natureUpdate(res, abilityEnum, DECRASE_RATE);
        });

        return res;
    }

    @Override
    public IvsVO[] getIvsRange(PokemonVO pokemonVO, BsVO bsVO, StatsVO statsVO, int level, NatureEnum natureEnum) throws Exception
    {
        // TODO 如果能知道pokemon觉醒力量属性，就能进一步缩小个体值范围
        // hp个体值的上下界
        int[] hpIvsRange = getHpIvsRange(pokemonVO, bsVO.getHpBs(), statsVO.getHpStats(), level);

        Optional<AbilityEnum> incrase = Optional.ofNullable(natureEnum.getIncraseAbility());
        Optional<AbilityEnum> decrase = Optional.ofNullable(natureEnum.getDecraseAbility());

        // 其他个体值的上下界
        int[] attackIvsRange;
        int[] defenseIvsRange;
        int[] specialAttackIvsRange;
        int[] specialDefenseIvsRange;
        int[] speedIvsRange;

        // TODO 用Optional和lambda表达式有更好的实现
        // 因为性格修正是成对的，如果incrase不为空，则decrase必不为空；如果incrase为空，则decrase必为空
        // 先计算有性格修正的个体值范围
        if (incrase.isPresent() && decrase.isPresent())
        {
            attackIvsRange = setAttackIvsRange(incrase.get(), decrase.get(), pokemonVO, bsVO, statsVO, level);
            defenseIvsRange = setDenfenseIvsRange(incrase.get(), decrase.get(), pokemonVO, bsVO, statsVO, level);
            specialAttackIvsRange = setSpecialAttackIvsRange(incrase.get(), decrase.get(), pokemonVO, bsVO, statsVO,
                    level);
            specialDefenseIvsRange = setSpecialDenfenseIvsRange(incrase.get(), decrase.get(), pokemonVO, bsVO,
                    statsVO, level);
            speedIvsRange = setSpeedIvsRange(incrase.get(), decrase.get(), pokemonVO, bsVO, statsVO, level);
        }
        // 否则就是没有性格修正
        else
        {
            attackIvsRange = setAttackIvsRange(null, null, pokemonVO, bsVO, statsVO, level);
            defenseIvsRange = setDenfenseIvsRange(null, null, pokemonVO, bsVO, statsVO, level);
            specialAttackIvsRange = setSpecialAttackIvsRange(null, null, pokemonVO, bsVO, statsVO,
                    level);
            specialDefenseIvsRange = setSpecialDenfenseIvsRange(null, null, pokemonVO, bsVO,
                    statsVO, level);
            speedIvsRange = setSpeedIvsRange(null, null, pokemonVO, bsVO, statsVO, level);
        }

        IvsVO minIvs = IvsVO
                .builder()
                .hpIvs(hpIvsRange[0]).attackIvs(attackIvsRange[0]).defenseIvs(defenseIvsRange[0])
                .specialAttackIvs(specialAttackIvsRange[0]).specialDefenseIvs(specialDefenseIvsRange[0]).speedIvs(speedIvsRange[0])
                .build();
        IvsVO maxIvs = IvsVO
                .builder()
                .hpIvs(hpIvsRange[1]).attackIvs(attackIvsRange[1]).defenseIvs(defenseIvsRange[1])
                .specialAttackIvs(specialAttackIvsRange[1]).specialDefenseIvs(specialDefenseIvsRange[1]).speedIvs(speedIvsRange[1])
                .build();
        return new IvsVO[]{minIvs, maxIvs};
    }

    @Override
    public TypeVO getHiddenPowerType(IvsVO ivsVO)
    {
        int hpIvs = ivsVO.getHpIvs() % 2;
        int attackIvs = ivsVO.getAttackIvs() % 2;
        int defenseIvs = ivsVO.getDefenseIvs() % 2;
        int specialAttackIvs = ivsVO.getSpecialAttackIvs() % 2;
        int specialDefenseIvs = ivsVO.getSpecialDefenseIvs() % 2;
        int speedIvs = ivsVO.getSpeedIvs() % 2;

        int res =
                (int) Math.floor((hpIvs + 2 * attackIvs + 4 * defenseIvs + 8 * speedIvs + 16 * specialAttackIvs + 32 * specialDefenseIvs) * 15 / (double) 63);

        return typeDao.getTypeVOById(res + 2);
    }

    /**
     * 计算hp能力值
     *
     * @param hpSs  hp种族值
     * @param hpIvs hp个体值
     * @param hpBs  hp努力值
     * @param level 口袋妖怪等级
     * @return hp能力值
     */
    private int getHpStats(int hpSs, int hpIvs, int hpBs, int level)
    {
        return (hpSs * 2 + hpIvs + hpBs / 4) * level / 100 + 10 + level;
    }

    /**
     * 计算其他能力值
     *
     * @param otherSs    其他种族值
     * @param otherIvs   其他个体值
     * @param otherBs    其他努力值
     * @param level      口袋妖怪等级
     * @param updateRate 性格修正率
     * @return 其他能力值
     */
    private int getOtherStats(int otherSs, int otherIvs, int otherBs, int level, double updateRate)
    {
        return (int) (((otherSs * 2 + otherIvs + otherBs / 4) * level / 100 + 5) * updateRate);
    }

    /**
     * 计算修正后的能力值，并修正
     *
     * @param statsVO     待修正的能力值
     * @param abilityEnum 要修正的能力
     * @param updateRate  修正率
     */
    private void natureUpdate(StatsVO statsVO, AbilityEnum abilityEnum, double updateRate)
    {
        switch (abilityEnum)
        {
            case HP:
                break;
            case ATTACK:
                statsVO.setAttackStats((int) (statsVO.getAttackStats() * updateRate));
                break;
            case DEFENSE:
                statsVO.setDefenseStats((int) (statsVO.getDefenseStats() * updateRate));
                break;
            case SPECIAL_ATTACK:
                statsVO.setSpecialAttackStats((int) (statsVO.getSpecialAttackStats() * updateRate));
                break;
            case SPECIAL_DEFENSE:
                statsVO.setSpecialDefenseStats((int) (statsVO.getSpecialDefenseStats() * updateRate));
                break;
            case SPEED:
                statsVO.setSpeedStats((int) (statsVO.getSpeedStats() * updateRate));
                break;
            default:
        }
    }

    /**
     * 获取hp个体值的上下界
     *
     * @param pokemonVO pokemonVO,含有种族值
     * @param hpBs      hp努力值
     * @param hpStats   hp能力值
     * @param level     等级
     * @return 应返回一个长度为2的数组，代表hp个体值的上下界
     * @throws Exception 数据输入错误异常
     */
    private int[] getHpIvsRange(PokemonVO pokemonVO, int hpBs, int hpStats, int level) throws Exception
    {
        int minHpIvs = MIN_IVS;
        int maxHpIvs = MAX_IVS;

        // 如果不是脱壳忍者
        if (pokemonVO.getNationalPokedexId() != SHEDINJA_NATIONAL_POKEDEX_ID)
        {
            // 从个体值为0开始计算能力值，小于输入的能力值则个体值下界加1
            while (getHpStats(pokemonVO.getHpSs(), minHpIvs, hpBs, level) < hpStats)
            {
                minHpIvs++;
            }

            // 如果此时，通过个体值下界计算得出的能力值大于输入的能力值，则肯定输入数据有误
            if (getHpStats(pokemonVO.getHpSs(), minHpIvs, hpBs, level) != hpStats)
            {
                throw new Exception("输入的数据有误！");
            }
            // 计算种族值的上界
            else if (getHpStats(pokemonVO.getHpSs(), minHpIvs, hpBs, level) == hpStats)
            {
                maxHpIvs = minHpIvs;
                while (getHpStats(pokemonVO.getHpSs(), maxHpIvs, hpBs, level) == hpStats)
                {
                    maxHpIvs++;
                }
                maxHpIvs--;

                // 如果上界超过了31，设为31
                if (maxHpIvs > MAX_IVS)
                {
                    maxHpIvs = MAX_IVS;
                }
            }
        }

        return new int[]{minHpIvs, maxHpIvs};
    }

    /**
     * 获取其他个体值的上下界
     *
     * @param otherSs    种族值
     * @param otherBs    努力值
     * @param otherStats 能力值
     * @param level      等级
     * @param updateRate 修正率
     * @return 应返回一个长度为2的数组，代表个体值的上下界
     * @throws Exception 数据输入错误异常
     */
    private int[] getOtherIvsRange(int otherSs, int otherBs, int otherStats, int level, double updateRate) throws Exception
    {
        int minOtherIvs = MIN_IVS;
        int maxOtherIvs = MAX_IVS;

        // 从个体值为0开始计算能力值，小于输入的能力值则个体值下界加1
        while (getOtherStats(otherSs, minOtherIvs, otherBs, level, updateRate) < otherStats)
        {
            minOtherIvs++;
        }

        // 如果此时，通过个体值下界计算得出的能力值大于输入的能力值，则肯定输入数据有误
        if (getOtherStats(otherSs, minOtherIvs, otherBs, level, updateRate) != otherStats)
        {
            throw new Exception("输入的数据有误！");
        }
        // 计算种族值的上界
        else if (getOtherStats(otherSs, minOtherIvs, otherBs, level, updateRate) == otherStats)
        {
            maxOtherIvs = minOtherIvs;
            while (getOtherStats(otherSs, maxOtherIvs, otherBs, level, updateRate) == otherStats)
            {
                maxOtherIvs++;
            }
            maxOtherIvs--;

            // 如果上界超过了31，设为31
            if (maxOtherIvs > MAX_IVS)
            {
                maxOtherIvs = MAX_IVS;
            }
        }

        return new int[]{minOtherIvs, maxOtherIvs};
    }

    /**
     * 设置pokemon物攻能力值范围
     *
     * @param incraseAbility 增强的能力项
     * @param decraseAbility 减弱的能力项
     * @param pokemonVO      pokemon's vo, 包含种族值
     * @param bsVO           努力值
     * @param statsVO        能力值
     * @param level          等级
     * @return 物攻能力值范围
     * @throws Exception 因为调用了getOtherIvsRange方法，所以要抛出数据输入错误异常
     */
    private int[] setAttackIvsRange(AbilityEnum incraseAbility, AbilityEnum decraseAbility,
                                    PokemonVO pokemonVO, BsVO bsVO, StatsVO statsVO, int level) throws Exception
    {
        int[] res = null;
        // 如果incraseAbility为空，那么必然decraseAbility也为空，直接返回没有性格修正的个体值范围
        if (incraseAbility == null)
        {
            return getOtherIvsRange(pokemonVO.getAttackSs(), bsVO.getAttackBs(), statsVO.getAttackStats(), level,
                    NO_EFFECT_RATE);
        }
        // 先判断是否有性格增强修正
        switch (incraseAbility)
        {
            case ATTACK:
                res = getOtherIvsRange(pokemonVO.getAttackSs(), bsVO.getAttackBs(),
                        statsVO.getAttackStats(), level, INCRASE_RATE);
                break;
            default:
        }
        // 再判断是否有性格削弱修正
        if (res == null)
        {
            switch (decraseAbility)
            {
                case ATTACK:
                    res = getOtherIvsRange(pokemonVO.getAttackSs(), bsVO.getAttackBs(),
                            statsVO.getAttackStats(), level, DECRASE_RATE);
                    break;
                default:
            }
        }
        // 否则即为没有性格修正
        if (res == null)
        {
            res = getOtherIvsRange(pokemonVO.getAttackSs(), bsVO.getAttackBs(), statsVO.getAttackStats(), level,
                    NO_EFFECT_RATE);
        }
        return res;
    }

    /**
     * 设置pokemon物防能力值范围
     *
     * @param incraseAbility 增强的能力项
     * @param decraseAbility 减弱的能力项
     * @param pokemonVO      pokemon's vo, 包含种族值
     * @param bsVO           努力值
     * @param statsVO        能力值
     * @param level          等级
     * @return 物防能力值范围
     * @throws Exception 因为调用了getOtherIvsRange方法，所以要抛出数据输入错误异常
     */
    private int[] setDenfenseIvsRange(AbilityEnum incraseAbility, AbilityEnum decraseAbility,
                                      PokemonVO pokemonVO, BsVO bsVO, StatsVO statsVO, int level) throws Exception
    {
        int[] res = null;
        // 如果incraseAbility为空，那么必然decraseAbility也为空，直接返回没有性格修正的个体值范围
        if (incraseAbility == null)
        {
            return getOtherIvsRange(pokemonVO.getDefenseSs(), bsVO.getDefenseBs(), statsVO.getDefenseStats(), level,
                    NO_EFFECT_RATE);
        }
        // 先判断是否有性格增强修正
        switch (incraseAbility)
        {
            case DEFENSE:
                res = getOtherIvsRange(pokemonVO.getDefenseSs(), bsVO.getDefenseBs(),
                        statsVO.getDefenseStats(), level, INCRASE_RATE);
                break;
            default:
        }
        // 再判断是否有性格削弱修正
        if (res == null)
        {
            switch (decraseAbility)
            {
                case DEFENSE:
                    res = getOtherIvsRange(pokemonVO.getDefenseSs(), bsVO.getDefenseBs(),
                            statsVO.getDefenseStats(), level, DECRASE_RATE);
                    break;
                default:
            }
        }
        // 否则即为没有性格修正
        if (res == null)
        {
            res = getOtherIvsRange(pokemonVO.getDefenseSs(), bsVO.getDefenseBs(),
                    statsVO.getDefenseStats(), level, NO_EFFECT_RATE);
        }
        return res;
    }

    /**
     * 设置pokemon特攻能力值范围
     *
     * @param incraseAbility 增强的能力项
     * @param decraseAbility 减弱的能力项
     * @param pokemonVO      pokemon's vo, 包含种族值
     * @param bsVO           努力值
     * @param statsVO        能力值
     * @param level          等级
     * @return 特攻能力值范围
     * @throws Exception 因为调用了getOtherIvsRange方法，所以要抛出数据输入错误异常
     */
    private int[] setSpecialAttackIvsRange(AbilityEnum incraseAbility, AbilityEnum decraseAbility,
                                           PokemonVO pokemonVO, BsVO bsVO, StatsVO statsVO, int level) throws Exception
    {
        int[] res = null;
        // 如果incraseAbility为空，那么必然decraseAbility也为空，直接返回没有性格修正的个体值范围
        if (incraseAbility == null)
        {
            return getOtherIvsRange(pokemonVO.getSpecialAttackSs(), bsVO.getSpecialAttackBs(),
                    statsVO.getSpecialAttackStats(), level, NO_EFFECT_RATE);
        }
        // 先判断是否有性格增强修正
        switch (incraseAbility)
        {
            case SPECIAL_ATTACK:
                res = getOtherIvsRange(pokemonVO.getSpecialAttackSs(), bsVO.getSpecialAttackBs(),
                        statsVO.getSpecialAttackStats(), level, INCRASE_RATE);
                break;
            default:
        }
        // 再判断是否有性格削弱修正
        if (res == null)
        {
            switch (decraseAbility)
            {
                case SPECIAL_ATTACK:
                    res = getOtherIvsRange(pokemonVO.getSpecialAttackSs(), bsVO.getSpecialAttackBs(),
                            statsVO.getSpecialAttackStats(), level, DECRASE_RATE);
                    break;
                default:
            }
        }
        // 否则即为没有性格修正
        if (res == null)
        {
            res = getOtherIvsRange(pokemonVO.getSpecialAttackSs(), bsVO.getSpecialAttackBs(),
                    statsVO.getSpecialAttackStats(), level, NO_EFFECT_RATE);
        }
        return res;
    }

    /**
     * 设置pokemon特防能力值范围
     *
     * @param incraseAbility 增强的能力项
     * @param decraseAbility 减弱的能力项
     * @param pokemonVO      pokemon's vo, 包含种族值
     * @param bsVO           努力值
     * @param statsVO        能力值
     * @param level          等级
     * @return 特防能力值范围
     * @throws Exception 因为调用了getOtherIvsRange方法，所以要抛出数据输入错误异常
     */
    private int[] setSpecialDenfenseIvsRange(AbilityEnum incraseAbility, AbilityEnum decraseAbility,
                                             PokemonVO pokemonVO, BsVO bsVO, StatsVO statsVO, int level) throws Exception
    {
        int[] res = null;
        // 如果incraseAbility为空，那么必然decraseAbility也为空，直接返回没有性格修正的个体值范围
        if (incraseAbility == null)
        {
            return getOtherIvsRange(pokemonVO.getSpecialDefenseSs(), bsVO.getSpecialDefenseBs(),
                    statsVO.getSpecialDefenseStats(), level, NO_EFFECT_RATE);
        }
        // 先判断是否有性格增强修正
        switch (incraseAbility)
        {
            case SPECIAL_DEFENSE:
                res = getOtherIvsRange(pokemonVO.getSpecialDefenseSs(), bsVO.getSpecialDefenseBs(),
                        statsVO.getSpecialDefenseStats(), level, INCRASE_RATE);
                break;
            default:
        }
        // 再判断是否有性格削弱修正
        if (res == null)
        {
            switch (decraseAbility)
            {
                case SPECIAL_DEFENSE:
                    res = getOtherIvsRange(pokemonVO.getSpecialDefenseSs(), bsVO.getSpecialDefenseBs(),
                            statsVO.getSpecialDefenseStats(), level, DECRASE_RATE);
                    break;
                default:
            }
        }
        // 否则即为没有性格修正
        if (res == null)
        {
            res = getOtherIvsRange(pokemonVO.getSpecialDefenseSs(), bsVO.getSpecialDefenseBs(),
                    statsVO.getSpecialDefenseStats(), level, NO_EFFECT_RATE);
        }
        return res;
    }

    /**
     * 设置pokemon速度能力值范围
     *
     * @param incraseAbility 增强的能力项
     * @param decraseAbility 减弱的能力项
     * @param pokemonVO      pokemon's vo, 包含种族值
     * @param bsVO           努力值
     * @param statsVO        能力值
     * @param level          等级
     * @return 速度能力值范围
     * @throws Exception 因为调用了getOtherIvsRange方法，所以要抛出数据输入错误异常
     */
    private int[] setSpeedIvsRange(AbilityEnum incraseAbility, AbilityEnum decraseAbility,
                                   PokemonVO pokemonVO, BsVO bsVO, StatsVO statsVO, int level) throws Exception
    {
        int[] res = null;
        // 如果incraseAbility为空，那么必然decraseAbility也为空，直接返回没有性格修正的个体值范围
        if (incraseAbility == null)
        {
            return getOtherIvsRange(pokemonVO.getSpeedSs(), bsVO.getSpeedBs(), statsVO.getSpeedStats(), level,
                    NO_EFFECT_RATE);
        }
        // 先判断是否有性格增强修正
        switch (incraseAbility)
        {
            case SPEED:
                res = getOtherIvsRange(pokemonVO.getSpeedSs(), bsVO.getSpeedBs(),
                        statsVO.getSpeedStats(), level, INCRASE_RATE);
                break;
            default:
        }
        // 再判断是否有性格削弱修正
        if (res == null)
        {
            switch (decraseAbility)
            {
                case SPEED:
                    res = getOtherIvsRange(pokemonVO.getSpeedSs(), bsVO.getSpeedBs(),
                            statsVO.getSpeedStats(), level, DECRASE_RATE);
                    break;
                default:
            }
        }
        // 否则即为没有性格修正
        if (res == null)
        {
            res = getOtherIvsRange(pokemonVO.getSpeedSs(), bsVO.getSpeedBs(),
                    statsVO.getSpeedStats(), level, NO_EFFECT_RATE);
        }
        return res;
    }
}
