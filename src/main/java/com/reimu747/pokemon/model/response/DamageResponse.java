package com.reimu747.pokemon.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @ClassName DamageResponse
 * @Author Reimu747
 * @Date 2019/1/18 7:05
 * @Description
 * @Version 1.0
 **/
@Data
@Builder
public class DamageResponse {
    @JsonProperty(value = "min_damage_rate")
    private Double minDamageRate;
    @JsonProperty(value = "max_damage_rate")
    private Double maxDamageRate;
    @JsonProperty(value = "possible_damages")
    private List possibleDamages;
}
