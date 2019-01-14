package com.reimu747.pokemon.model.enums;

/**
 * @Author Reimu747
 */
public enum StatusConditionEnum
{
    // 异常状态
    BURN(1, "灼伤", "处于灼伤状态的宝可梦攻击下降，同时每回合结束时损失HP。"),
    FREEZE(2, "冰冻", "处于冰冻状态的宝可梦无法使用招式，除非使用某些招式或者是被某些招式击中。"),
    PARALYSIS(3, "麻痹", "处于麻痹状态的宝可梦速度下降，同时每一个回合有可能无法使用招式。"),
    POISON(4, "中毒", "处于中毒或剧毒状态的宝可梦每回合结束时损失HP。"),
    BADLY_POISON(5, "剧毒", "处于中毒或剧毒状态的宝可梦每回合结束时损失HP。"),
    SLEEP(6, "睡眠", "处于睡眠状态的宝可梦无法使用大部分招式。");

    private int id;
    private String name;
    private String description;

    StatusConditionEnum(int id, String name, String description)
    {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }
}
