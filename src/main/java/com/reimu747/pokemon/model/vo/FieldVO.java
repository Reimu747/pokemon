package com.reimu747.pokemon.model.vo;

import com.reimu747.pokemon.model.enums.TerrainEnum;
import com.reimu747.pokemon.model.enums.WeatherEnum;
import lombok.Data;

/**
 * 场地
 *
 * @ClassName FieldVO
 * @Author Reimu747
 * @Date 2019/1/12 6:59
 * @Description
 * @Version 1.0
 **/
@Data
public class FieldVO
{
    /**
     * 场地和天气
     */
    private TerrainEnum terrain;
    private WeatherEnum weather;

    /**
     * 重力、反射壁、光墙、守住、识破、极光幕
     */
    private Boolean isGravity;
    private Boolean isReflect;
    private Boolean isLightScreen;
    private Boolean isProtect;
    private Boolean isForesight;
    private Boolean isAuroraVeil;

    // TODO 帮助、友情防守、寄生种子、多打
}
