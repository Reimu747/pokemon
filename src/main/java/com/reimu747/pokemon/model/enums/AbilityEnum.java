package com.reimu747.pokemon.model.enums;

/**
 * @Author Reimu747
 */
public enum AbilityEnum
{
    // 六项能力值
    HP(1),
    ATTACK(2),
    DEFENSE(3),
    SPECIAL_ATTACK(4),
    SPECIAL_DEFENSE(5),
    SPEED(6);

    private int id;

    AbilityEnum(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }
}
