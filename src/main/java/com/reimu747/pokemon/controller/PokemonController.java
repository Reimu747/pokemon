package com.reimu747.pokemon.controller;

import com.reimu747.pokemon.model.Result;
import com.reimu747.pokemon.model.enums.ErrorCodeEnum;
import com.reimu747.pokemon.model.vo.PokemonVO;
import com.reimu747.pokemon.service.PokemonService;
import com.reimu747.pokemon.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName PokemonController
 * @Author Reimu747
 * @Date 2019/1/14 12:27
 * @Description
 * @Version 1.0
 **/
@RestController()
public class PokemonController
{
    @Autowired
    PokemonService pokemonService;

    /**
     * 通过pokemon名称，返回对应的pokemon
     *
     * @param name 名称
     * @return 对应的pokemon
     */
    @GetMapping(value = "/get/pokemon")
    public Result<PokemonVO> a(@RequestParam("name") String name)
    {
        PokemonVO pokemon = pokemonService.getPokemon(name);
        if (pokemon != null)
        {
            return ResultUtil.ok(pokemon);
        }
        return ResultUtil.failWithMsg(ErrorCodeEnum.INVALID_PARAM);
    }
}
