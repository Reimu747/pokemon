package com.reimu747.pokemon.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @ClassName Result
 * @Author Reimu747
 * @Date 2019/1/14 13:13
 * @Description
 * @Version 1.0
 **/
@Data
public class Result<T>
{
    @JsonProperty("result_code")
    private Integer resultCode;

    private String msg;

    private T data;

    private Long timestamp;
}
