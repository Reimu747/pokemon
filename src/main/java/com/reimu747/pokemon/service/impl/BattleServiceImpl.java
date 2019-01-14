package com.reimu747.pokemon.service.impl;

import com.reimu747.pokemon.dao.TypeDao;
import com.reimu747.pokemon.model.vo.FieldVO;
import com.reimu747.pokemon.model.vo.PokemonInstanceVO;
import com.reimu747.pokemon.model.vo.TypeVO;
import com.reimu747.pokemon.model.vo.WazaVO;
import com.reimu747.pokemon.service.BattleService;
import com.reimu747.pokemon.util.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 战斗伤害按照下面的步骤计算。由于并不知道游戏内部具体的实现，所以此步骤也在根据实际情况不断调整中
 * 伤害计算步骤：
 * 1、计算修正后的攻击力、防御力、技能威力，不进行取整;
 * 2、令A = floor(攻击力 × 技能威力 × (攻击方等级 × 2 ÷ 5 + 2) ÷ 防御力 ÷ 50 + 2);
 * 3、令B = floor(A × 会心倍率);
 * 4、令C = floor(B × 随机数 ÷ 100)，其中随机数∈[85,100]且为整数;
 * 5、令D = floor(C × 属性一致加成);
 * 6、令E = floor(D × 属性克制系数);
 * 7、令F = round(E × 其他加成),如果大于防御方现有的血量，F记为防御方现有的血量；如果小于1，记为1。
 * F即为最终扣除的血量
 *
 * @ClassName BattleServiceImpl
 * @Author Reimu747
 * @Date 2019/1/12 7:11
 * @Description
 * @Version 1.0
 **/
@Service
public class BattleServiceImpl implements BattleService
{
    @Autowired
    private TypeDao typeDao;

    /**
     * 最小、最大伤害值
     */
    private static final int MIN_DAMAGE_RATE = 0;
    private static final int MAX_DAMAGE_RATE = 1;

    /**
     * 攻击、防御相关
     */
    private static final String PHYSICAL_MOVE = "物理招式";
    private static final String SPECIAL_MOVE = "特殊招式";
    private static final double NOT_ATTACK_MOVE = 0.0D;
    private static final double BASE_MOLECULAR = 2;
    private static final double BASE_DENOMINATOR = 2;

    /**
     * 会心相关
     */
    private static final double NOT_CRITICAL_HIT = 1.0D;
    private static final double CRITICAL_HIT = 1.5D;
    private static final double SUPER_CRITICAL_HIT = 2.25D;
    private static final String SNIPER_TOKUSEI = "狙击手";

    /**
     * 随机数
     */
    private static final int MIN_RAND = 85;
    private static final int MAX_RAND = 100;

    /**
     * 属性一致加成相关
     */
    private static final double SAME_TYPE_CORRECTION = 1.5D;
    private static final double NOT_SAME_TYPE_CORRECTION = 1.0D;

    /**
     * 属性克制倍率相关
     */
    private static final double NOT_EFFECTIVE = 0D;
    private static final double NOT_VERY_EFFECTIVE = 0.5D;
    private static final double SUPER_EFFECTIVE = 2.0D;
    private static final double OTHER_TYPE_RELATION = 1.0D;
    private static final String TYPE_TABLE_SEPARATOR = ",";

    /**
     * 其他加成相关
     */
    private static final String PARENTAL_BOND = "亲子爱";

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
    @Override
    public double[] getDamageRange(PokemonInstanceVO attackPokemon, PokemonInstanceVO defensePokemon,
                                   WazaVO waza, FieldVO field, boolean isCriticalHit)
    {
        // 获取可能造成的伤害值
        List<Integer> possibleDamages = getPossibleDamages(attackPokemon, defensePokemon, waza, field, isCriticalHit);
        // 获取最大最小伤害值
        int minDamage = possibleDamages.get(0);
        int maxDamage = possibleDamages.get(possibleDamages.size() - 1);
        // 防守方生命值
        int defenseHp = defensePokemon.getStatsVO().getHpStats();
        // 伤害百分比，舍入三位有效数字
        if (minDamage >= defenseHp)
        {
            return new double[]{MAX_DAMAGE_RATE, MAX_DAMAGE_RATE};
        }
        else if (maxDamage >= defenseHp)
        {
            return new double[]{MathUtil.splitAndFloor((double) minDamage / defenseHp, 3), MAX_DAMAGE_RATE};
        }
        else
        {
            return new double[]{MathUtil.splitAndFloor((double) minDamage / defenseHp, 3),
                    MathUtil.splitAndFloor((double) maxDamage / defenseHp, 3)};
        }
    }

    @Override
    public List<Integer> getPossibleDamages(PokemonInstanceVO attackPokemon, PokemonInstanceVO defensePokemon,
                                            WazaVO waza, FieldVO field, boolean isCriticalHit)
    {
        // 攻击方等级
        double level = attackPokemon.getLevel();
        // 技能威力
        double wazaPower = getWazaPower(waza);
        // 攻击方攻击力
        double attackPoint;
        // 如果是物理招式
        if (PHYSICAL_MOVE.equals(waza.getWazaTypeName()))
        {
            // 以物攻能力值和物攻能力等级计算
            attackPoint = getRealStats(attackPokemon.getStatsVO().getAttackStats(), attackPokemon.getAttackLevel());
        }
        // 如果是特殊招式
        else if (SPECIAL_MOVE.equals(waza.getWazaTypeName()))
        {
            // 以特攻能力值和特攻能力等级计算
            attackPoint = getRealStats(attackPokemon.getStatsVO().getSpecialAttackStats(),
                    attackPokemon.getSpecialAttackLevel());
        }
        // 如果不是攻击招式
        else
        {
            return Collections.singletonList(MIN_DAMAGE_RATE);
        }
        // 防守方防御力
        double defensePoint;
        // 如果是物理招式
        if (PHYSICAL_MOVE.equals(waza.getWazaTypeName()))
        {
            // 以物防能力值和物防能力等级计算
            defensePoint = getRealStats(defensePokemon.getStatsVO().getDefenseStats(),
                    defensePokemon.getDefenseLevel());
        }
        // 如果是特殊招式
        else if (SPECIAL_MOVE.equals(waza.getWazaTypeName()))
        {
            // 以特防能力值和特防能力等级计算
            defensePoint = getRealStats(defensePokemon.getStatsVO().getSpecialDefenseStats(),
                    defensePokemon.getSpecialDefenseLevel());
        }
        // 如果不是攻击招式
        else
        {
            return Collections.singletonList(MIN_DAMAGE_RATE);
        }
        // A = floor(攻击力 × 技能威力 × (攻击方等级 × 2 ÷ 5 + 2) ÷ 防御力 ÷ 50 + 2)
        int damageA = (int) (attackPoint * wazaPower * (level * 2 / 5 + 2) / defensePoint / 50 + 2);
        // B = floor(A × 会心倍率)
        int damageB = (int) (damageA * getCriticalHitRate(attackPokemon, isCriticalHit));

        List<Integer> possibleDamages = new ArrayList<>();
        // C = floor(B × 随机数 ÷ 100)，其中随机数∈[85,100]且为整数
        for (int i = MIN_RAND; i <= MAX_RAND; i++)
        {
            possibleDamages.add((int) ((double) damageB * i / MAX_RAND));
        }
        // D = floor(C × 属性一致加成)
        for (int i = 0; i < possibleDamages.size(); i++)
        {
            possibleDamages.set(i, (int) (possibleDamages.get(i) * getSameTypeCorrection(attackPokemon, waza, field)));
        }
        // E = floor(D × 属性克制系数)
        for (int i = 0; i < possibleDamages.size(); i++)
        {
            possibleDamages.set(i, (int) (possibleDamages.get(i) * getTypeRelationRate(defensePokemon, waza)));
        }
        // F = round(E × 其他加成)
        for (int i = 0; i < possibleDamages.size(); i++)
        {
            possibleDamages.set(i, (int) (possibleDamages.get(i) * getOtherCorrection(attackPokemon)));
        }
        return possibleDamages;
    }

    /**
     * 计算修正后的攻击力和防御力
     *
     * @param originalStats 原能力值
     * @param statsLevel    能力等级
     * @return 实际能力值
     */
    private double getRealStats(int originalStats, int statsLevel)
    {
        if (statsLevel == 0)
        {
            return originalStats;
        }
        else if (statsLevel > 0)
        {
            return originalStats * ((BASE_MOLECULAR + statsLevel) / BASE_DENOMINATOR);
        }
        else
        {
            return originalStats * (BASE_MOLECULAR / (BASE_DENOMINATOR - statsLevel));
        }
    }

    /**
     * TODO 待完善，可能还有其他能够影响招式威力的因素没有考虑
     * 计算招式威力威力
     *
     * @param waza 招式
     * @return 实际威力
     */
    private double getWazaPower(WazaVO waza)
    {
        return waza.getPower();
    }

    /**
     * TODO 待完善，可能还有其他能够影响属性克制加成的因素没有考虑
     * 计算属性克制加成
     *
     * @param defensePokemon 防御方pokemon
     * @param waza           攻击方使用的技能
     * @return 加成修正数值
     */
    private double getTypeRelationRate(PokemonInstanceVO defensePokemon, WazaVO waza)
    {
        // 属性克制倍率
        // 防御方的两个属性
        TypeVO defenseTypeOne = typeDao.getTypeVOByName(defensePokemon.getTypeOne());
        TypeVO defenseTypeTwo = typeDao.getTypeVOByName(defensePokemon.getTypeTwo());

        return getTypeRelationCorrection(defenseTypeOne, waza) * getTypeRelationCorrection(defenseTypeTwo, waza);
    }

    /**
     * TODO 待完善，可能还有其他能够影响属性一致加成的因素没有考虑
     * 计算属性一致加成
     *
     * @param attackPokemon 攻击方pokemon
     * @param waza          攻击方使用的技能
     * @param field         场地
     * @return 属性一致加成
     */
    private double getSameTypeCorrection(PokemonInstanceVO attackPokemon, WazaVO waza, FieldVO field)
    {
        // 属性一致加成
        boolean isSameType =
                attackPokemon.getTypeOne().equals(waza.getTypeName()) || attackPokemon.getTypeTwo().equals(waza.getTypeName());
        return (isSameType ? SAME_TYPE_CORRECTION : NOT_SAME_TYPE_CORRECTION);
    }

    /**
     * 通过招式属性和防御方属性，计算属性克制倍率
     *
     * @param defenseType 防御方属性，可以为null
     * @param waza        攻击招式
     * @return 返回属性克制倍率
     */
    private double getTypeRelationCorrection(TypeVO defenseType, WazaVO waza)
    {
        // TODO 待完善，许多特殊情况没有判断
        // 攻击方属性
        TypeVO attackType = typeDao.getTypeVOByName(waza.getTypeName());

        // 要返回的属性克制倍率
        double typeRelation = OTHER_TYPE_RELATION;

        // 三个字符串，分别代表0x，1/2x，2x
        Optional<String> notEffectiveString = Optional.ofNullable(attackType.getNotEffective());
        Optional<String> notVeryEffectiveString = Optional.ofNullable(attackType.getNotVeryEffective());
        Optional<String> superEffective = Optional.ofNullable(attackType.getSuperEffective());

        // 如果防御方属性在0x字符串中，则返回0倍
        if (notEffectiveString.isPresent())
        {
            String typeRelationStr = notEffectiveString.get();
            typeRelation = setTypeRelationRate(typeRelationStr, defenseType, NOT_EFFECTIVE);
        }
        if (typeRelation == NOT_EFFECTIVE)
        {
            return typeRelation;
        }
        // 如果防御方属性在1/2x字符串中，则返回1/2倍
        if (notVeryEffectiveString.isPresent())
        {
            String typeRelationStr = notVeryEffectiveString.get();
            typeRelation = setTypeRelationRate(typeRelationStr, defenseType, NOT_VERY_EFFECTIVE);
        }
        if (typeRelation == NOT_VERY_EFFECTIVE)
        {
            return typeRelation;
        }
        // 如果防御方属性在2x字符串中，则返回2倍
        if (superEffective.isPresent())
        {
            String typeRelationStr = superEffective.get();
            typeRelation = setTypeRelationRate(typeRelationStr, defenseType, SUPER_EFFECTIVE);
        }

        // 否则返回1倍
        return typeRelation;
    }

    /**
     * 如果防御方属性在属性关系字符串中，则返回传入的属性克制倍率，否则返回默认倍率
     *
     * @param typeRelationStr  从type表对应字段中抽取的属性关系字符串，以逗号分隔
     * @param defenseType      防御方的属性
     * @param typeRelationRate 属性克制倍率
     * @return 属性克制倍率
     */
    private double setTypeRelationRate(String typeRelationStr, TypeVO defenseType, double typeRelationRate)
    {
        // 判断防御方属性是否为null(第二属性)，如果为null，则直接返回默认倍率
        if (defenseType == null)
        {
            return OTHER_TYPE_RELATION;
        }
        // 从字符串中寻找防御方的属性id，如果找到了，就返回对应的倍率，否则返回1倍
        String[] strArray = typeRelationStr.split(TYPE_TABLE_SEPARATOR);
        for (String str : strArray)
        {
            if (str.equals(defenseType.getId().toString()))
            {
                return typeRelationRate;
            }
        }
        return OTHER_TYPE_RELATION;
    }

    /**
     * TODO 待完善，可能还有其他能够影响会心倍率的因素没有考虑
     * 计算会心倍率
     *
     * @param attackPokemon 攻击方pokemon
     * @param isCriticalHit 是否会心
     * @return 返回会心倍率
     */
    private double getCriticalHitRate(PokemonInstanceVO attackPokemon, boolean isCriticalHit)
    {
        // 如果非会心，返回非会心倍率
        if (!isCriticalHit)
        {
            return NOT_CRITICAL_HIT;
        }
        else
        {
            // 如果攻击方特性是狙击手，返回超级会心倍率
            if (SNIPER_TOKUSEI.equals(attackPokemon.getTokuseiVO().getName()))
            {
                return SUPER_CRITICAL_HIT;
            }
            // 否则返回普通会心倍率
            else
            {
                return CRITICAL_HIT;
            }
        }
    }

    /**
     * TODO 待完善，可能还有其他能够影响属性一致加成的因素没有考虑
     * 计算其他加成倍率
     *
     * @param attackPokemon 攻击方pokemon
     * @return 其他加成倍率
     */
    private double getOtherCorrection(PokemonInstanceVO attackPokemon)
    {
        // 如果特性是亲子爱
        if (PARENTAL_BOND.equals(attackPokemon.getTokuseiVO().getName()))
        {
            return 1.25D;
        }
        return 1.0D;
    }
}
