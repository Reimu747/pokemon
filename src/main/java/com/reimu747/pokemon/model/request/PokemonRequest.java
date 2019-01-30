package com.reimu747.pokemon.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
    @NotNull
    private String name;

    @NotNull
    @Min(value = 0)
    @Max(value = 31)
    @JsonProperty(value = "hp_ivs")
    private Integer hpIvs;
    @NotNull
    @Min(value = 0)
    @Max(value = 31)
    @JsonProperty(value = "attack_ivs")
    private Integer attackIvs;
    @NotNull
    @Min(value = 0)
    @Max(value = 31)
    @JsonProperty(value = "defense_ivs")
    private Integer defenseIvs;
    @NotNull
    @Min(value = 0)
    @Max(value = 31)
    @JsonProperty(value = "special_attack_ivs")
    private Integer specialAttackIvs;
    @NotNull
    @Min(value = 0)
    @Max(value = 31)
    @JsonProperty(value = "special_defense_ivs")
    private Integer specialDefenseIvs;
    @NotNull
    @Min(value = 0)
    @Max(value = 31)
    @JsonProperty(value = "speed_ivs")
    private Integer speedIvs;

    @NotNull
    @Min(value = 0)
    @Max(value = 255)
    @JsonProperty(value = "hp_bs")
    private Integer hpBs;
    @NotNull
    @Min(value = 0)
    @Max(value = 255)
    @JsonProperty(value = "attack_bs")
    private Integer attackBs;
    @NotNull
    @Min(value = 0)
    @Max(value = 255)
    @JsonProperty(value = "defense_bs")
    private Integer defenseBs;
    @NotNull
    @Min(value = 0)
    @Max(value = 255)
    @JsonProperty(value = "special_attack_bs")
    private Integer specialAttackBs;
    @NotNull
    @Min(value = 0)
    @Max(value = 255)
    @JsonProperty(value = "special_defense_bs")
    private Integer specialDefenseBs;
    @NotNull
    @Min(value = 0)
    @Max(value = 255)
    @JsonProperty(value = "speed_bs")
    private Integer speedBs;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer level;

    @NotNull
    @Min(value = 0)
    @Max(value = 4)
    @JsonProperty("increase_ability_index")
    private Integer increaseAbilityIndex;
    @NotNull
    @Min(value = 0)
    @Max(value = 4)
    @JsonProperty("decrease_ability_index")
    private Integer decreaseAbilityIndex;
}
