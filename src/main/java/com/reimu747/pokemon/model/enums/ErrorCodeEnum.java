package com.reimu747.pokemon.model.enums;

/**
 * @Author Reimu747
 */
public enum ErrorCodeEnum
{
    // 响应码
    SUCCESS(200, "success"),
    INVALID_PARAM(400, "param invalid"),
    E_SYSTEM(500, "service is unavailable");

    private int code;
    private String msg;

    ErrorCodeEnum(int code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }
}
