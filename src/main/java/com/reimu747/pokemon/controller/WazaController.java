package com.reimu747.pokemon.controller;

import com.reimu747.pokemon.model.Result;
import com.reimu747.pokemon.model.vo.TokuseiVO;
import com.reimu747.pokemon.model.vo.WazaVO;
import com.reimu747.pokemon.service.WazaService;
import com.reimu747.pokemon.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName WazaController
 * @Author Reimu747
 * @Date 2019/1/18 4:33
 * @Description
 * @Version 1.0
 **/
@Slf4j
@RestController
public class WazaController {
    @Autowired
    private WazaService wazaService;

    @GetMapping("get/all/waza")
    public Result<List<WazaVO>> getAllWaza()
    {
        List<WazaVO> list = wazaService.getAllWaza();
        return ResultUtil.ok(list);
    }
}
