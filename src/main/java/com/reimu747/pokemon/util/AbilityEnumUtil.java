package com.reimu747.pokemon.util;

import com.reimu747.pokemon.model.enums.AbilityEnum;

/**
 * @ClassName AbilityEnumUtil
 * @Author Reimu747
 * @Date 2019/1/15 7:46
 * @Description
 * @Version 1.0
 **/
public class AbilityEnumUtil
{
    public static AbilityEnum getAbilityById(int id)
    {
        switch (id)
        {
            case 1:
                return AbilityEnum.HP;
            case 2:
                return AbilityEnum.ATTACK;
            case 3:
                return AbilityEnum.DEFENSE;
            case 4:
                return AbilityEnum.SPECIAL_ATTACK;
            case 5:
                return AbilityEnum.SPECIAL_DEFENSE;
            case 6:
                return AbilityEnum.SPEED;
            default:
                return null;
        }
    }
}
