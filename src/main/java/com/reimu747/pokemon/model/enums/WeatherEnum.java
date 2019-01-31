package com.reimu747.pokemon.model.enums;

/**
 * @Author Reimu747
 */
public enum WeatherEnum
{
    // 天气
    RAIN(1, "下雨"),
    HEAVY_RAIN(2, "大雨"),
    HARSH_SUNLIGHT(3, "大晴天"),
    EXTREMELY_HARSH_SUNLIGHT(4, "大日照"),
    HAIL(5, "冰雹"),
    SANDSTORM(6, "沙暴"),
    STRONG_WINDS(7, "乱流");

    private int id;
    private String name;

    WeatherEnum(int id, String name)
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
