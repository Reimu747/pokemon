package com.reimu747.pokemon.model.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 特性
 * @ClassName TokuseiVO
 * @Author Reimu747
 * @Date 2019/1/12 6:29
 * @Description
 * @Version 1.0
 **/
@Data
@Builder
public class TokuseiVO
{
    private String name;
    private String description;
}
