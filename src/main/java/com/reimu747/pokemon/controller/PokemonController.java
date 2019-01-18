package com.reimu747.pokemon.controller;

import com.reimu747.pokemon.model.Result;
import com.reimu747.pokemon.model.enums.AbilityEnum;
import com.reimu747.pokemon.model.enums.ErrorCodeEnum;
import com.reimu747.pokemon.model.enums.NatureEnum;
import com.reimu747.pokemon.model.request.PokemonRequest;
import com.reimu747.pokemon.model.response.StatsResponse;
import com.reimu747.pokemon.model.vo.*;
import com.reimu747.pokemon.service.PokemonService;
import com.reimu747.pokemon.util.AbilityEnumUtil;
import com.reimu747.pokemon.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName PokemonController
 * @Author Reimu747
 * @Date 2019/1/14 12:27
 * @Description
 * @Version 1.0
 **/
@Slf4j
@RestController
public class PokemonController {
    @Autowired
    PokemonService pokemonService;

    /**
     * 通过pokemon名称，返回对应的pokemon
     *
     * @param name 名称
     * @return 对应的pokemon
     */
    @GetMapping(value = "/get/pokemon")
    public Result<PokemonVO> getByName(@RequestParam("name") String name) {
        log.info("name: {}", name);

        PokemonVO pokemon = pokemonService.getPokemon(name);
        if (pokemon != null) {
            return ResultUtil.ok(pokemon);
        }

        log.error("数据库中没有{} 的信息！", name);
        return ResultUtil.failWithMsg(ErrorCodeEnum.INVALID_PARAM);
    }

    /**
     * 返回所有pokemon的列表，仅包含全国id和名称
     *
     * @return 列表
     */
    @GetMapping(value = "get/all")
    public Result<List<SimplePokemonVO>> getAll() {
        List<SimplePokemonVO> list = pokemonService.getAllPokemon();
        return ResultUtil.ok(list);
    }

    /**
     * 返回能力值
     *
     * @param pokemonRequest 包含名称，个体值，努力值，等级，性格
     * @return 能力值
     */
    @PostMapping(value = "get/stats")
    public Result<StatsResponse> getStats(@Validated @RequestBody PokemonRequest pokemonRequest,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors())
        {
            log.error("pokemonRequest: {}", pokemonRequest);
            return ResultUtil.failWithMsg(ErrorCodeEnum.INVALID_PARAM);
        }

        log.info("pokemonRequest: {}", pokemonRequest);
        PokemonVO pokemon = pokemonService.getPokemon(pokemonRequest.getName());

        int increaseAbilityIndex = pokemonRequest.getIncreaseAbilityIndex();
        int decreaseAbilityIndex = pokemonRequest.getDecreaseAbilityIndex();
        AbilityEnum increaseAbility = AbilityEnumUtil.getAbilityById(increaseAbilityIndex + 2);
        AbilityEnum decreaseAbility = AbilityEnumUtil.getAbilityById(decreaseAbilityIndex + 2);
        NatureEnum nature = null;
        for (NatureEnum natureEnum : NatureEnum.values()) {
            if (natureEnum.getIncraseAbility() == increaseAbility && natureEnum.getDecraseAbility() == decreaseAbility) {
                nature = natureEnum;
            }
        }

        IvsVO ivs = IvsVO.builder().build();
        BeanUtils.copyProperties(pokemonRequest, ivs);
        BsVO bs = BsVO.builder().build();
        BeanUtils.copyProperties(pokemonRequest, bs);
        int level = pokemonRequest.getLevel();

        StatsVO stats = pokemonService.getStats(pokemon, ivs, bs, level, nature);

        StatsResponse res = StatsResponse.builder().build();
        BeanUtils.copyProperties(stats, res);

        return ResultUtil.ok(res);
    }
}
