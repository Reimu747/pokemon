package com.reimu747.pokemon.model.vo;

import lombok.Data;

/**
 * @ClassName TypeVO
 * @Author Reimu747
 * @Date 2019/1/5 6:19
 * @Description
 * @Version 1.0
 **/
@Data
public class TypeVO
{
    private Integer id;
    private String name;
    private String notEffective;
    private String notVeryEffective;
    private String superEffective;
}
