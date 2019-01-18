package com.reimu747.pokemon.controller;

import com.reimu747.pokemon.model.Result;
import com.reimu747.pokemon.model.vo.ItemVO;
import com.reimu747.pokemon.model.vo.TokuseiVO;
import com.reimu747.pokemon.service.ItemService;
import com.reimu747.pokemon.service.TokuseiService;
import com.reimu747.pokemon.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName ItemController
 * @Author Reimu747
 * @Date 2019/1/18 4:26
 * @Description
 * @Version 1.0
 **/
@Slf4j
@RestController
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping(value = "/get/all/item")
    public Result<List<ItemVO>> getAllTokusei() {
        List<ItemVO> list = itemService.getAllItem();
        return ResultUtil.ok(list);
    }
}
