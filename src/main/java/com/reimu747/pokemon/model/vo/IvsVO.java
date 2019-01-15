package com.reimu747.pokemon.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @ClassName IvsVO
 * @Author Reimu747
 * @Date 2019/1/5 16:56
 * @Description
 * @Version 1.0
 **/
@Data
@Builder
public class IvsVO
{
    private Integer hpIvs;
    private Integer attackIvs;
    private Integer defenseIvs;
    private Integer specialAttackIvs;
    private Integer specialDefenseIvs;
    private Integer speedIvs;
}
