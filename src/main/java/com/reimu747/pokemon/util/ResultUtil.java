package com.reimu747.pokemon.util;

import com.reimu747.pokemon.model.Result;
import com.reimu747.pokemon.model.enums.ErrorCodeEnum;

/**
 * @ClassName ResultUtil
 * @Author Reimu747
 * @Date 2019/1/14 13:17
 * @Description
 * @Version 1.0
 **/
public class ResultUtil
{
    public static <T> Result<T> ok(T data)
    {
        Result<T> result = new Result<>();
        result.setResultCode(ErrorCodeEnum.SUCCESS.getCode());
        result.setMsg(ErrorCodeEnum.SUCCESS.getMsg());
        result.setData(data);
        result.setTimestamp(System.currentTimeMillis());
        return result;
    }

    public static <T> Result<T> failWithMsg(ErrorCodeEnum errorCode)
    {
        Result<T> result = new Result<>();
        result.setResultCode(errorCode.getCode());
        result.setMsg(errorCode.getMsg());
        result.setData(null);
        result.setTimestamp(System.currentTimeMillis());
        return result;
    }
}
