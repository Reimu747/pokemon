package com.reimu747.pokemon.model.enums;

/**
 * @Author Reimu747
 */
public enum TerrainEnum
{
    // 场地
    ELECTRIC(1, "电气场地"),
    GRASSY(2, "青草场地"),
    MISTY(3, "薄雾场地"),
    PSYCHIC(4, "精神场地");

    private int id;
    private String name;

    TerrainEnum(int id, String name)
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
