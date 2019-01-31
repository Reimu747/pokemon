package com.reimu747.pokemon.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.reimu747.pokemon.model.vo.PokemonInstanceVO;
import lombok.Data;

/**
 * @ClassName BattleRequest
 * @Author Reimu747
 * @Date 2019/1/18 4:48
 * @Description
 * @Version 1.0
 **/
@Data
public class BattleRequest {
    // TODO 数据校验
    /**
     * 攻击pokemon的数据
     */
    @JsonProperty(value = "attack_pokemon_name")
    private String attackPokemonName;

    @JsonProperty(value = "attack_hp_stats")
    private Integer attackHpStats;
    @JsonProperty(value = "attack_attack_stats")
    private Integer attackAttackStats;
    @JsonProperty(value = "attack_defense_stats")
    private Integer attackDefenseStats;
    @JsonProperty(value = "attack_special_attack_stats")
    private Integer attackSpecialAttackStats;
    @JsonProperty(value = "attack_special_defense_stats")
    private Integer attackSpecialDefenseStats;
    @JsonProperty(value = "attack_speed_stats")
    private Integer attackSpeedStats;

    @JsonProperty(value = "attack_level")
    private Integer attackLevel;

    @JsonProperty(value = "attack_current_hp")
    private Integer attackCurrentHp;

    @JsonProperty(value = "attack_tokusei")
    private String attackTokusei;
    @JsonProperty(value = "attack_item")
    private String attackItem;
    @JsonProperty(value = "attack_waza")
    private String attackWaza;

    @JsonProperty(value = "attack_ability_level")
    private Integer attackAbilityLevel;
    @JsonProperty(value = "special_attack_ability_level")
    private Integer specialAttackAbilityLevel;

    @JsonProperty(value = "attack_status_condition")
    private String attackStatusCondition;

    /**
     * 防御pokemon的数据
     */
    @JsonProperty(value = "defense_pokemon_name")
    private String defensePokemonName;

    @JsonProperty(value = "defense_hp_stats")
    private Integer defenseHpStats;
    @JsonProperty(value = "defense_attack_stats")
    private Integer defenseAttackStats;
    @JsonProperty(value = "defense_defense_stats")
    private Integer defenseDefenseStats;
    @JsonProperty(value = "defense_special_attack_stats")
    private Integer defenseSpecialAttackStats;
    @JsonProperty(value = "defense_special_defense_stats")
    private Integer defenseSpecialDefenseStats;
    @JsonProperty(value = "defense_speed_stats")
    private Integer defenseSpeedStats;

    @JsonProperty(value = "defense_level")
    private Integer defenseLevel;

    @JsonProperty(value = "defense_current_hp")
    private Integer defenseCurrentHp;

    @JsonProperty(value = "defense_tokusei")
    private String defenseTokusei;
    @JsonProperty(value = "defense_item")
    private String defenseItem;
    @JsonProperty(value = "defense_waza")
    private String defenseWaza;

    @JsonProperty(value = "defense_ability_level")
    private Integer defenseAbilityLevel;
    @JsonProperty(value = "special_defense_ability_level")
    private Integer specialDefenseAbilityLevel;

    @JsonProperty(value = "defense_status_condition")
    private String defenseStatusCondition;

    /**
     * 是否会心
     */
    @JsonProperty(value = "is_critical_hit")
    private Boolean isCriticalHit;

    /**
     * 场地
     */
    @JsonProperty(value = "terrain_index")
    private Integer terrainIndex;
    @JsonProperty(value = "weather_index")
    private Integer weatherIndex;
    @JsonProperty(value = "is_gravity")
    private Boolean isGravity;
    @JsonProperty(value = "is_reflect")
    private Boolean isReflect;
    @JsonProperty(value = "is_light_screen")
    private Boolean isLightScreen;
    @JsonProperty(value = "is_protect")
    private Boolean isProtect;
    @JsonProperty(value = "is_foresight")
    private Boolean isForesight;
    @JsonProperty(value = "is_aurora_veil")
    private Boolean isAuroraVeil;

    // TODO 其他场地影响
}
