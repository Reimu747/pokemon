package com.reimu747.pokemon.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestController
 * @Author lenovo
 * @Date 2019/1/14 12:30
 * @Description
 * @Version 1.0
 **/
@RestController(value = "/test")
public class TestController
{
    @GetMapping(value = "/health/check")
    public String healthCheck()
    {
        return "service v0.0.1 is healthy!";
    }
}
