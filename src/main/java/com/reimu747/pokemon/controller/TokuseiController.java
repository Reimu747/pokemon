package com.reimu747.pokemon.controller;

import com.reimu747.pokemon.model.Result;
import com.reimu747.pokemon.model.vo.PokemonVO;
import com.reimu747.pokemon.model.vo.TokuseiVO;
import com.reimu747.pokemon.service.TokuseiService;
import com.reimu747.pokemon.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName TokuseiController
 * @Author Reimu747
 * @Date 2019/1/18 3:50
 * @Description
 * @Version 1.0
 **/
@Slf4j
@RestController
public class TokuseiController {
    @Autowired
    private TokuseiService tokuseiService;

    @GetMapping(value = "/get/all/tokusei")
    public Result<List<TokuseiVO>> getAllTokusei() {
        List<TokuseiVO> list = tokuseiService.getAllTokusei();
        return ResultUtil.ok(list);
    }
}
