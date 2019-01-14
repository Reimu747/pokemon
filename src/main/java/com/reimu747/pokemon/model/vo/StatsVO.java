package com.reimu747.pokemon.model.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName StatsVO
 * @Author Reimu747
 * @Date 2019/1/6 4:27
 * @Description
 * @Version 1.0
 **/
@Data
@Builder
public class StatsVO
{
    private Integer hpStats;
    private Integer attackStats;
    private Integer defenseStats;
    private Integer specialAttackStats;
    private Integer specialDefenseStats;
    private Integer speedStats;
}
