package com.reimu747.pokemon.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @ClassName BsVO
 * @Author Reimu747
 * @Date 2019/1/5 17:06
 * @Description
 * @Version 1.0
 **/
@Data
@Builder
public class BsVO
{
    private Integer hpBs;
    private Integer attackBs;
    private Integer defenseBs;
    private Integer specialAttackBs;
    private Integer specialDefenseBs;
    private Integer speedBs;
}
