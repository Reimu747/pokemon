package com.reimu747.pokemon.model.vo;

import com.reimu747.pokemon.model.enums.NatureEnum;
import com.reimu747.pokemon.model.enums.StatusConditionEnum;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * pokemon实例类，继承自PokemonVO类，包含每只pokemon独有的数据
 * @ClassName PokemonInstanceVO
 * @Author Reimu747
 * @Date 2019/1/12 5:07
 * @Description
 * @Version 1.0
 **/
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class PokemonInstanceVO extends PokemonVO
{
    /**
     * 个体值
     */
    private IvsVO ivsVO;

    /**
     * 努力值
     */
    private BsVO bsVO;

    /**
     * 能力值
     */
    private StatsVO statsVO;

    /**
     * 等级
     */
    private Integer level;
    /**
     * 性格
     */
    private NatureEnum nature;
    /**
     * 特性
     */
    private TokuseiVO tokuseiVO;
    /**
     * 异常状态
     */
    private StatusConditionEnum statusConditionEnum;
    /**
     * 道具
     */
    private ItemVO itemVO;

    /**
     * 技能
     */
    private WazaVO wazaOne;
    private WazaVO wazaTwo;
    private WazaVO wazaThree;
    private WazaVO wazaFour;

    /**
     * 能力等级
     */
    private Integer attackLevel;
    private Integer defenseLevel;
    private Integer specialAttackLevel;
    private Integer specialDefenseLevel;

    /**
     * 觉醒力量属性
     */
    private TypeVO hiddenPowerType;

    /**
     * 现有生命值
     */
    private Integer hpNow;

    /**
     * 性别
     */
    private String gender;

    // TODO 状态数组(充电等)、其他能力能级
    /**
     * 状态
     */
    private Boolean isFlashFire;
    private Boolean isDig;
}
