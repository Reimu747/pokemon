package com.reimu747.pokemon.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @ClassName StatsResponse
 * @Author Reimu747
 * @Date 2019/1/15 9:13
 * @Description
 * @Version 1.0
 **/
@Data
@Builder
public class StatsResponse
{
    @JsonProperty(value = "hp_stats")
    private Integer hpStats;
    @JsonProperty(value = "attack_stats")
    private Integer attackStats;
    @JsonProperty(value = "defense_stats")
    private Integer defenseStats;
    @JsonProperty(value = "special_attack_stats")
    private Integer specialAttackStats;
    @JsonProperty(value = "special_defense_stats")
    private Integer specialDefenseStats;
    @JsonProperty(value = "speed_stats")
    private Integer speedStats;
}
