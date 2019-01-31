package com.reimu747.pokemon.service.impl;

import com.reimu747.pokemon.dao.TypeDao;
import com.reimu747.pokemon.dao.WazaDao;
import com.reimu747.pokemon.model.enums.StatusConditionEnum;
import com.reimu747.pokemon.model.enums.WeatherEnum;
import com.reimu747.pokemon.model.vo.*;
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
 * 3、令B = floor(A × 会心倍率 × 天气修正);
 * 4、令C = floor(B × 随机数 ÷ 100)，其中随机数∈[85,100]且为整数;
 * 5、令D = floor(C × 属性一致加成);
 * 6、令E = floor(D × 属性克制系数);
 * 7、令F = floor(E × 灼伤修正);
 * 8、令G = round(F × 其他加成),如果大于防御方现有的血量，G记为防御方现有的血量；如果小于1，记为1。
 * G即为最终扣除的血量
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
    @Autowired
    private WazaDao wazaDao;

    /**
     * 最小、最大伤害值
     */
    private static final int MIN_DAMAGE_RATE = 0;
    private static final int MAX_DAMAGE_RATE = 1;
    private static final int MIN_DAMAGE = 1;

    /**
     * 攻击、防御相关
     */
    private static final String PHYSICAL_MOVE = "物理招式";
    private static final String SPECIAL_MOVE = "特殊招式";
    private static final String STATUS_MOVE = "变化招式";
    private static final double NOT_ATTACK_MOVE = 0.0D;
    private static final double BASE_MOLECULAR = 2;
    private static final double BASE_DENOMINATOR = 2;
    private static final double STATS_RATE_ONE = 0.5D;
    private static final double STATS_RATE_TWO = 1.5D;

    /**
     * 招式相关
     */
    private static final String[] PULSE_WAZAS = {"波导弹", "水之波动", "龙之波动", "恶之波动", "根源波动"};
    private static final String[] KOTEI_WAZAS = {"音爆", "龙之怒", "地球上投", "黑夜魔影", "精神波", "愤怒门牙", "蛮干", "双倍奉还", "镜面反射",
            "忍耐", "金属爆炸", "搏命", "自然之怒", "巨人卫士·阿罗拉", "地裂", "断头钳", "角钻", "绝对零度"};
    private static final double POWER_RATE_ONE = 1.5D;
    private static final double POWER_RATE_TWO = 1.3D;
    private static final double POWER_RATE_THREE = 0.5D;
    private static final double POWER_RATE_FOUR = 0.75D;
    private static final double HP_RATE_ONE = 1.0D / 3.0D;

    /**
     * 会心相关
     */
    private static final double NOT_CRITICAL_HIT = 1.0D;
    private static final double CRITICAL_HIT = 1.5D;

    /**
     * 天气相关
     */
    private static final double WEATHER_RATE_ONE = 0.5D;
    private static final double WEATHER_RATE_TWO = 1.0D;
    private static final double WEATHER_RATE_THREE = 1.5D;

    /**
     * 随机数
     */
    private static final int MIN_RAND = 85;
    private static final int MAX_RAND = 100;

    /**
     * 属性一致加成相关
     */
    private static final double SUPER_SAME_TYPE_CORRECTION = 2.0D;
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
     * 灼伤修正
     */
    private static final double BURN_RATE_ONE = 0.5D;
    private static final double BURN_RATE_TWO = 1.0D;

    /**
     * 其他加成相关
     */
    private static final double OTHER_RATE_ONE = 0.5D;
    private static final double OTHER_RATE_TWO = 1.0D;
    private static final double OTHER_RATE_THREE = 1.25D;
    private static final double OTHER_RATE_FOUR = 1.5D;
    private static final double OTHER_RATE_FIVE = 2.0D;

    /**
     * 特性
     */
    private static final String PARENTAL_BOND = "亲子爱";
    private static final String SNIPER = "狙击手";
    private static final String OVERGROW = "茂盛";
    private static final String THICK_FAT = "厚脂肪";
    private static final String BLAZE = "猛火";
    private static final String SOLAR_POWER = "太阳之力";
    private static final String TOUGH_CLAWS = "硬爪";
    private static final String TORRENT = "激流";
    private static final String MEGA_LAUNCHER = "超级发射器";
    private static final String TINTED_LENS = "有色眼镜";
    private static final String SWARM = "虫之预感";
    private static final String ADAPTABILITY = "适应力";

    /**
     * 属性
     */
    private static final String GRASS = "草";
    private static final String ICE = "冰";
    private static final String FIRE = "火";
    private static final String WATER = "水";
    private static final String BUG = "虫";

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
        int defenseHp = defensePokemon.getHpNow();
        // 伤害百分比，舍入三位有效数字
        return new double[]{MathUtil.splitAndFloor((double) minDamage / defenseHp, 3),
                MathUtil.splitAndFloor((double) maxDamage / defenseHp, 3)};
    }

    @Override
    public List<Integer> getPossibleDamages(PokemonInstanceVO attackPokemon, PokemonInstanceVO defensePokemon,
                                            WazaVO waza, FieldVO field, boolean isCriticalHit)
    {
        // 如果属性克制为无效，伤害值为0
        if (getTypeRelationRate(defensePokemon, waza) == NOT_EFFECTIVE)
        {
            return Collections.singletonList(MIN_DAMAGE_RATE);
        }
        // 如果是固定伤害招式，不进入伤害计算公式
        if (Arrays.asList(KOTEI_WAZAS).contains(waza.getName()))
        {
            List<Integer> koteiWazasDamages = getKoteiWazasDamages(attackPokemon, defensePokemon, waza, field,
                    isCriticalHit);
            List<Integer> possibleDamages = new ArrayList<>(koteiWazasDamages);

            for (int i = 0; i < possibleDamages.size(); i++)
            {
                if (possibleDamages.get(i) < MIN_DAMAGE && !"蛮干".equals(waza.getName()))
                {
                    possibleDamages.set(i, MIN_DAMAGE);
                }
                else if (possibleDamages.get(i) > defensePokemon.getHpNow())
                {
                    possibleDamages.set(i, defensePokemon.getHpNow());
                }
            }
            return possibleDamages;
        }
        // 攻击方等级
        double level = attackPokemon.getLevel();
        // 技能威力
        double wazaPower = getWazaPower(attackPokemon, waza, field);
        // 攻击方攻击力
        double attackPoint;
        // 如果不是攻击招式
        if (STATUS_MOVE.equals(waza.getWazaTypeName()))
        {
            return Collections.singletonList(MIN_DAMAGE_RATE);
        }
        // 否则
        else
        {
            attackPoint = getRealAttack(attackPokemon, defensePokemon, waza, field);
        }
        // 防守方防御力
        double defensePoint;
        // 如果不是攻击招式
        if (STATUS_MOVE.equals(waza.getWazaTypeName()))
        {
            return Collections.singletonList(MIN_DAMAGE_RATE);
        }
        // 否则
        else
        {
            defensePoint = getRealDefense(attackPokemon, defensePokemon, waza);
        }
        // A = floor(攻击力 × 技能威力 × (攻击方等级 × 2 ÷ 5 + 2) ÷ 防御力 ÷ 50 + 2)
        int damageA = (int) (attackPoint * wazaPower * (level * 2 / 5 + 2) / defensePoint / 50 + 2);
        // B = floor(A × 会心倍率 × 天气修正)
        int damageB =
                (int) (damageA * getCriticalHitRate(attackPokemon, isCriticalHit) * getWeatherRate(attackPokemon,
                        defensePokemon, waza, field, isCriticalHit));

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
        // F = floor(E × 灼伤修正)
        for (int i = 0; i < possibleDamages.size(); i++)
        {
            possibleDamages.set(i, (int) (possibleDamages.get(i) * getBurnCorrection(attackPokemon, defensePokemon,
                    waza, field, isCriticalHit)));
        }
        // G = round(F × 其他加成)
        // 如果伤害小于1，记为1；如果大于防御方现有血量，记为防御方现有血量
        for (int i = 0; i < possibleDamages.size(); i++)
        {
            possibleDamages.set(i, (int) (possibleDamages.get(i) * getOtherCorrection(attackPokemon, defensePokemon,
                    waza, field, isCriticalHit)));
            if (possibleDamages.get(i) < MIN_DAMAGE)
            {
                possibleDamages.set(i, MIN_DAMAGE);
            }
            else if (possibleDamages.get(i) > defensePokemon.getHpNow())
            {
                possibleDamages.set(i, defensePokemon.getHpNow());
            }
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
    private double getRealStats(double originalStats, int statsLevel)
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
     * TODO 待完善，可能还有其他能够影响攻击力的因素没有考虑
     * 计算修正后的攻击力
     *
     * @param attackPokemon  攻击方pokemon
     * @param defensePokemon 防御方pokemon
     * @param waza           招式
     * @param field          场地
     * @return 修正后的攻击力
     */
    private double getRealAttack(PokemonInstanceVO attackPokemon, PokemonInstanceVO defensePokemon, WazaVO waza,
                                 FieldVO field)
    {
        double attack;
        int attackLevel;

        // 如果是物理招式
        if (PHYSICAL_MOVE.equals(waza.getWazaTypeName()))
        {
            // 以物攻能力值和物攻能力等级计算
            attack = attackPokemon.getStatsVO().getAttackStats();
            attackLevel = attackPokemon.getAttackLevel();
        }
        // 如果是特殊招式
        else if (SPECIAL_MOVE.equals(waza.getWazaTypeName()))
        {
            // 以特攻能力值和特攻能力等级计算
            attack = attackPokemon.getStatsVO().getSpecialAttackStats();
            attackLevel = attackPokemon.getSpecialAttackLevel();
        }
        // 如果不是攻击招式
        else
        {
            attack = 0;
            attackLevel = 0;
        }

        // 特性 厚脂肪
        boolean thickFatEffective =
                THICK_FAT.equals(defensePokemon.getTokuseiVO().getName()) && (ICE.equals(waza.getTypeName()) || FIRE.equals(waza.getTypeName()));
        if (thickFatEffective)
        {
            attack *= STATS_RATE_ONE;
        }
        // 特性 太阳之力
        boolean solarPowerEffective =
                SOLAR_POWER.equals(attackPokemon.getTokuseiVO().getName()) && (WeatherEnum.HARSH_SUNLIGHT == field.getWeather() || WeatherEnum.EXTREMELY_HARSH_SUNLIGHT == field.getWeather());
        if (solarPowerEffective)
        {
            if (SPECIAL_MOVE.equals(waza.getWazaTypeName()))
            {
                attack *= STATS_RATE_TWO;
            }
        }

        attack = getRealStats(attack, attackLevel);
        return attack;
    }

    /**
     * TODO 待完善，可能还有其他能够影响防御力的因素没有考虑
     * 计算修正后的防御力
     *
     * @param attackPokemon  攻击方pokemon
     * @param defensePokemon 防御方pokemon
     * @param waza           招式
     * @return 修正后的攻击力
     */
    private double getRealDefense(PokemonInstanceVO attackPokemon, PokemonInstanceVO defensePokemon, WazaVO waza)
    {
        double defense;
        int defenseLevel;

        // 如果是物理招式
        if (PHYSICAL_MOVE.equals(waza.getWazaTypeName()))
        {
            // 以物防能力值和物防能力等级计算
            defense = defensePokemon.getStatsVO().getDefenseStats();
            defenseLevel = defensePokemon.getDefenseLevel();
        }
        // 如果是特殊招式
        else if (SPECIAL_MOVE.equals(waza.getWazaTypeName()))
        {
            // 以特防能力值和特防能力等级计算
            defense = defensePokemon.getStatsVO().getSpecialDefenseStats();
            defenseLevel = defensePokemon.getSpecialDefenseLevel();
        }
        // 如果不是攻击招式
        else
        {
            defense = 0;
            defenseLevel = 0;
        }

        defense = getRealStats(defense, defenseLevel);
        return defense;
    }

    /**
     * TODO 待完善，可能还有其他能够影响招式威力的因素没有考虑
     * 计算招式威力威力
     *
     * @param attackPokemon 攻击方pokemon
     * @param waza          招式
     * @param field         场地
     * @return 实际威力
     */
    private double getWazaPower(PokemonInstanceVO attackPokemon, WazaVO waza, FieldVO field)
    {
        Optional<TokuseiVO> attackTokusei = Optional.ofNullable(attackPokemon.getTokuseiVO());
        TypeVO wazaType = typeDao.getTypeVOByName(waza.getTypeName());

        double power = waza.getPower();

        if (attackTokusei.isPresent())
        {
            // 特性 茂盛
            if (OVERGROW.equals(attackTokusei.get().getName()) && attackPokemon.getHpNow() <= attackPokemon.getStatsVO().getHpStats() * HP_RATE_ONE && GRASS.equals(wazaType.getName()))
            {
                power *= POWER_RATE_ONE;
            }
            // 特性 猛火
            if (BLAZE.equals(attackTokusei.get().getName()) && attackPokemon.getHpNow() <= attackPokemon.getStatsVO().getHpStats() * HP_RATE_ONE && FIRE.equals(wazaType.getName()))
            {
                power *= POWER_RATE_ONE;
            }
            // 特性 激流
            if (TORRENT.equals(attackTokusei.get().getName()) && attackPokemon.getHpNow() <= attackPokemon.getStatsVO().getHpStats() * HP_RATE_ONE && WATER.equals(wazaType.getName()))
            {
                power *= POWER_RATE_ONE;
            }
            // 特性 虫之预感
            if (SWARM.equals(attackTokusei.get().getName()) && attackPokemon.getHpNow() <= attackPokemon.getStatsVO().getHpStats() * HP_RATE_ONE && BUG.equals(wazaType.getName()))
            {
                power *= POWER_RATE_ONE;
            }
            // 特性 硬爪
            if (TOUGH_CLAWS.equals(attackTokusei.get().getName()) && waza.getIsDirectAttack() == 1)
            {
                power *= POWER_RATE_TWO;
            }
            // 特性 超级发射器
            if (MEGA_LAUNCHER.equals(attackTokusei.get().getName()))
            {
                List<String> pulseWazas = Arrays.asList(PULSE_WAZAS);
                if (pulseWazas.contains(waza.getName()))
                {
                    power *= POWER_RATE_ONE;
                }
            }
        }

        return power;
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
                attackPokemon.getTypeOne().equals(waza.getTypeName()) || (attackPokemon.getTypeTwo() != null && attackPokemon.getTypeTwo().equals(waza.getTypeName()));
        double res = NOT_SAME_TYPE_CORRECTION;

        if (isSameType)
        {
            res = SAME_TYPE_CORRECTION;

            Optional<TokuseiVO> attackTokusei = Optional.ofNullable(attackPokemon.getTokuseiVO());
            if (attackTokusei.isPresent())
            {
                // 特性 适应力
                if (ADAPTABILITY.equals(attackTokusei.get().getName()))
                {
                    res = SUPER_SAME_TYPE_CORRECTION;
                }
            }
        }

        return res;
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
        // 返回会心倍率
        else
        {
            return CRITICAL_HIT;
        }
    }

    /**
     * TODO 待完善，可能还有其他能够影响属性一致加成的因素没有考虑
     * 计算其他加成倍率
     *
     * @param attackPokemon  攻击方pokemon
     * @param defensePokemon 防御方pokemon
     * @param waza           招式
     * @param field          场地
     * @param isCriticalHit  是否会心
     * @return 其他加成倍率
     */
    private double getOtherCorrection(PokemonInstanceVO attackPokemon, PokemonInstanceVO defensePokemon, WazaVO waza,
                                      FieldVO field, boolean isCriticalHit)
    {
        double res = OTHER_RATE_TWO;
        Optional<TokuseiVO> attackTokusei = Optional.ofNullable(attackPokemon.getTokuseiVO());

        if (attackTokusei.isPresent())
        {
            // 特性 亲子爱
            if (PARENTAL_BOND.equals(attackTokusei.get().getName()))
            {
                res *= OTHER_RATE_THREE;
            }
            // 特性 有色眼镜
            if (TINTED_LENS.equals(attackTokusei.get().getName()))
            {
                if (getTypeRelationRate(defensePokemon, waza) == NOT_VERY_EFFECTIVE || getTypeRelationRate(defensePokemon, waza) == NOT_VERY_EFFECTIVE * NOT_VERY_EFFECTIVE)
                {
                    res *= OTHER_RATE_FIVE;
                }
            }
            // 特性 狙击手
            if (SNIPER.equals(attackTokusei.get().getName()) && isCriticalHit)
            {
                res *= OTHER_RATE_FOUR;
            }
        }

        return res;
    }

    /**
     * TODO 待完善，可能还有其他能够影响天气修正的因素没有考虑
     * 天气修正
     *
     * @param attackPokemon  攻击方pokemon
     * @param defensePokemon 防守方pokemon
     * @param waza           招式
     * @param field          场地
     * @param isCriticalHit  是否会心
     * @return 天气修正
     */
    private double getWeatherRate(PokemonInstanceVO attackPokemon, PokemonInstanceVO defensePokemon, WazaVO waza,
                                  FieldVO field, boolean isCriticalHit)
    {
        double res = WEATHER_RATE_TWO;
        TypeVO wazaType = typeDao.getTypeVOByName(waza.getTypeName());
        // 天气
        if (field.getWeather() != null)
        {
            // 大晴天
            if (field.getWeather() == WeatherEnum.HARSH_SUNLIGHT)
            {
                if (FIRE.equals(wazaType.getName()))
                {
                    res *= WEATHER_RATE_THREE;
                }
                else if (WATER.equals(wazaType.getName()))
                {
                    res *= WEATHER_RATE_ONE;
                }
            }
        }

        return res;
    }

    /**
     * TODO 待完善，可能还有其他能够影响灼伤修正的因素没有考虑
     * 灼伤修正
     *
     * @param attackPokemon  攻击方pokemon
     * @param defensePokemon 防守方pokemon
     * @param waza           招式
     * @param field          场地
     * @param isCriticalHit  是否会心
     * @return 灼伤修正
     */
    private double getBurnCorrection(PokemonInstanceVO attackPokemon, PokemonInstanceVO defensePokemon, WazaVO waza,
                                     FieldVO field, boolean isCriticalHit)
    {
        double res = BURN_RATE_TWO;
        // 异常状态 灼伤
        if (attackPokemon.getStatusConditionEnum() == StatusConditionEnum.BURN)
        {
            if (PHYSICAL_MOVE.equals(waza.getWazaTypeName()) && waza.getIsKoteiWaza() == 0)
            {
                res *= BURN_RATE_ONE;
            }
        }

        return res;
    }

    /**
     * 获取固定伤害招式可能造成的伤害
     *
     * @param attackPokemon  攻击方pokemon
     * @param defensePokemon 防御方pokemon
     * @param waza           招式
     * @param field          场地
     * @param isCriticalHit  是否会心
     * @return 固定伤害招式可能造成的伤害
     */
    private List<Integer> getKoteiWazasDamages(PokemonInstanceVO attackPokemon, PokemonInstanceVO defensePokemon,
                                               WazaVO waza, FieldVO field, boolean isCriticalHit)
    {
        // {"音爆", "龙之怒", "地球上投", "黑夜魔影", "精神波", "愤怒门牙", "蛮干", "双倍奉还", "镜面反射",
        //            "忍耐", "金属爆炸", "搏命", "自然之怒", "巨人卫士·阿罗拉", "地裂", "断头钳", "角钻", "绝对零度"}
        if (getTypeRelationRate(defensePokemon, waza) == NOT_EFFECTIVE)
        {
            return Collections.singletonList(MIN_DAMAGE_RATE);
        }
        switch (waza.getName())
        {
            case "音爆":
                return Collections.singletonList(wazaDao.getWazaByName(waza.getName()).getPower());
            case "龙之怒":
                return Collections.singletonList(wazaDao.getWazaByName(waza.getName()).getPower());
            case "地球上投":
                return Collections.singletonList(attackPokemon.getLevel());
            case "黑夜魔影":
                return Collections.singletonList(attackPokemon.getLevel());
            case "精神波":
            {
                // TODO 暂定为round(level * rand)
                int minDamage = Math.round(attackPokemon.getLevel() * (float) POWER_RATE_THREE);
                int maxDamage = Math.round(attackPokemon.getLevel() * (float) POWER_RATE_ONE);
                List<Integer> res = new ArrayList<>();
                for (int i = minDamage; i <= maxDamage; i++)
                {
                    res.add(i);
                }
                return res;
            }
            case "愤怒门牙":
                return Collections.singletonList((int) (defensePokemon.getHpNow() * POWER_RATE_THREE));
            case "蛮干":
            {
                if (defensePokemon.getHpNow() > attackPokemon.getHpNow())
                {
                    return Collections.singletonList(defensePokemon.getHpNow() - attackPokemon.getHpNow());
                }
                return Collections.singletonList(MIN_DAMAGE_RATE);
            }
            case "搏命":
                return Collections.singletonList(attackPokemon.getHpNow());
            case "自然之怒":
                return Collections.singletonList((int) (defensePokemon.getHpNow() * POWER_RATE_THREE));
            case "巨人卫士·阿罗拉":
                return Collections.singletonList((int) (defensePokemon.getHpNow() * POWER_RATE_FOUR));
            // 默认 一击必杀招式
            default:
                return Collections.singletonList(defensePokemon.getHpNow());
        }
    }
}
