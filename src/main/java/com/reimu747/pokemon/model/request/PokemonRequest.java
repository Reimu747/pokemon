package com.reimu747.pokemon.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @ClassName PokemonRequest
 * @Author Reimu747
 * @Date 2019/1/15 8:47
 * @Description
 * @Version 1.0
 **/
@Data
public class PokemonRequest
{
    private String name;

    @JsonProperty(value = "hp_ivs")
    private Integer hpIvs;
    @JsonProperty(value = "attack_ivs")
    private Integer attackIvs;
    @JsonProperty(value = "defense_ivs")
    private Integer defenseIvs;
    @JsonProperty(value = "special_attack_ivs")
    private Integer specialAttackIvs;
    @JsonProperty(value = "special_defense_ivs")
    private Integer specialDefenseIvs;
    @JsonProperty(value = "speed_ivs")
    private Integer speedIvs;

    @JsonProperty(value = "hp_bs")
    private Integer hpBs;
    @JsonProperty(value = "attack_bs")
    private Integer attackBs;
    @JsonProperty(value = "defense_bs")
    private Integer defenseBs;
    @JsonProperty(value = "special_attack_bs")
    private Integer specialAttackBs;
    @JsonProperty(value = "special_defense_bs")
    private Integer specialDefenseBs;
    @JsonProperty(value = "speed_bs")
    private Integer speedBs;

    private Integer level;

    @JsonProperty("increase_ability_index")
    private Integer increaseAbilityIndex;
    @JsonProperty("decrease_ability_index")
    private Integer decreaseAbilityIndex;
}
