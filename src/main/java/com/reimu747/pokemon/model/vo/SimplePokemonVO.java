package com.reimu747.pokemon.model.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName SimplePokemonVO
 * @Author Reimu747
 * @Date 2019/1/15 6:40
 * @Description
 * @Version 1.0
 **/
@Data
@Builder
public class SimplePokemonVO
{
    private Integer id;
    private String name;
}
