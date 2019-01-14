package com.reimu747.pokemon.model.enums;

/**
 * @Author Reimu747
 */
public enum WazaTypeEnum
{
    // 招式类型
    PHYSICAL_MOVE(1, "物理招式"),
    SPECIAL_MOVE(2, "特殊招式"),
    STATUS_MOVE(3, "变化招式");

    private int id;
    private String name;

    WazaTypeEnum(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }
}
