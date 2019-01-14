package com.reimu747.pokemon.model.vo;

import com.reimu747.pokemon.model.enums.WazaTypeEnum;
import lombok.Data;

/**
 * 技能
 * @ClassName WazaVO
 * @Author Reimu747
 * @Date 2019/1/12 6:35
 * @Description
 * @Version 1.0
 **/
@Data
public class WazaVO
{
    private String name;
    private String typeName;
    private Integer power;
    private Integer hitRate;
    private String wazaTypeName;
}
