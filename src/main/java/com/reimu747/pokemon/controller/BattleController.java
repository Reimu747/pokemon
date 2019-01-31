package com.reimu747.pokemon.controller;

import com.reimu747.pokemon.model.Result;
import com.reimu747.pokemon.model.enums.ErrorCodeEnum;
import com.reimu747.pokemon.model.enums.StatusConditionEnum;
import com.reimu747.pokemon.model.enums.TerrainEnum;
import com.reimu747.pokemon.model.enums.WeatherEnum;
import com.reimu747.pokemon.model.request.BattleRequest;
import com.reimu747.pokemon.model.response.DamageResponse;
import com.reimu747.pokemon.model.vo.*;
import com.reimu747.pokemon.service.*;
import com.reimu747.pokemon.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName BattleController
 * @Author Reimu747
 * @Date 2019/1/18 4:46
 * @Description
 * @Version 1.0
 **/
@Slf4j
@RestController
public class BattleController
{
    @Autowired
    private BattleService battleService;
    @Autowired
    private WazaService wazaService;
    @Autowired
    private TokuseiService tokuseiService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private PokemonService pokemonService;

    @PostMapping(value = "/get/damage/range")
    public Result<DamageResponse> getDamageRange(@Validated @RequestBody BattleRequest battleRequest,
                                                 BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            log.error("battleRequest: {}", battleRequest);
            return ResultUtil.failWithMsg(ErrorCodeEnum.INVALID_PARAM);
        }

        log.info("battleRequest: {}", battleRequest);

        WazaVO waza = wazaService.getWazaByName(battleRequest.getAttackWaza());

        TokuseiVO attackTokusei = tokuseiService.getTokuseiByName(battleRequest.getAttackTokusei());
        TokuseiVO defenseTokusei = tokuseiService.getTokuseiByName(battleRequest.getDefenseTokusei());

        ItemVO attackItem = itemService.getItemByName(battleRequest.getAttackItem());
        ItemVO defenseItem = itemService.getItemByName(battleRequest.getDefenseItem());

        String attackStatusCondition = battleRequest.getAttackStatusCondition();
        String defenseStatusCondition = battleRequest.getDefenseStatusCondition();

        PokemonVO pokemonVO = pokemonService.getPokemon(battleRequest.getAttackPokemonName());
        PokemonInstanceVO attackPokemon = PokemonInstanceVO
                .builder()
                .level(battleRequest.getAttackLevel())
                .wazaOne(waza)
                .tokuseiVO(attackTokusei)
                .itemVO(attackItem)
                .attackLevel(battleRequest.getAttackAbilityLevel())
                .specialAttackLevel(battleRequest.getSpecialAttackAbilityLevel())
                .hpNow(battleRequest.getAttackCurrentHp())
                .build();
        for (StatusConditionEnum statusConditionEnum : StatusConditionEnum.values())
        {
            if (attackStatusCondition.equals(statusConditionEnum.getName()))
            {
                attackPokemon.setStatusConditionEnum(statusConditionEnum);
            }
        }
        StatsVO attackStats = StatsVO
                .builder()
                .hpStats(battleRequest.getAttackHpStats())
                .attackStats(battleRequest.getAttackAttackStats())
                .defenseStats(battleRequest.getAttackDefenseStats())
                .specialAttackStats(battleRequest.getAttackSpecialAttackStats())
                .specialDefenseStats(battleRequest.getAttackSpecialDefenseStats())
                .speedStats(battleRequest.getAttackSpeedStats())
                .build();
        attackPokemon.setStatsVO(attackStats);
        BeanUtils.copyProperties(pokemonVO, attackPokemon);

        pokemonVO = pokemonService.getPokemon(battleRequest.getDefensePokemonName());
        PokemonInstanceVO defensePokemon = PokemonInstanceVO
                .builder()
                .level(battleRequest.getDefenseLevel())
                .tokuseiVO(defenseTokusei)
                .itemVO(defenseItem)
                .defenseLevel(battleRequest.getDefenseAbilityLevel())
                .specialDefenseLevel(battleRequest.getSpecialDefenseAbilityLevel())
                .hpNow(battleRequest.getDefenseCurrentHp())
                .build();
        for (StatusConditionEnum statusConditionEnum : StatusConditionEnum.values())
        {
            if (defenseStatusCondition.equals(statusConditionEnum.getName()))
            {
                defensePokemon.setStatusConditionEnum(statusConditionEnum);
            }
        }
        StatsVO defenseStats = StatsVO
                .builder()
                .hpStats(battleRequest.getDefenseHpStats())
                .attackStats(battleRequest.getDefenseAttackStats())
                .defenseStats(battleRequest.getDefenseDefenseStats())
                .specialAttackStats(battleRequest.getDefenseSpecialAttackStats())
                .specialDefenseStats(battleRequest.getDefenseSpecialDefenseStats())
                .speedStats(battleRequest.getDefenseSpeedStats())
                .build();
        defensePokemon.setStatsVO(defenseStats);
        BeanUtils.copyProperties(pokemonVO, defensePokemon);

        // TODO 其他场地影响
        FieldVO field = new FieldVO();
        for (TerrainEnum terrainEnum : TerrainEnum.values())
        {
            if (battleRequest.getTerrainIndex() == terrainEnum.getId())
            {
                field.setTerrain(terrainEnum);
            }
        }
        for (WeatherEnum weatherEnum : WeatherEnum.values())
        {
            if (battleRequest.getWeatherIndex() == weatherEnum.getId())
            {
                field.setWeather(weatherEnum);
            }
        }
        field.setIsGravity(battleRequest.getIsGravity());
        field.setIsReflect(battleRequest.getIsReflect());
        field.setIsLightScreen(battleRequest.getIsLightScreen());
        field.setIsProtect(battleRequest.getIsProtect());
        field.setIsForesight(battleRequest.getIsForesight());
        field.setIsAuroraVeil(battleRequest.getIsAuroraVeil());

        double[] damageRange = battleService.getDamageRange(attackPokemon, defensePokemon, waza, field,
                battleRequest.getIsCriticalHit());
        double minDamage = damageRange[0];
        double maxDamage = damageRange[1];

        List<Integer> possibleDamages = battleService.getPossibleDamages(attackPokemon, defensePokemon, waza, field,
                battleRequest.getIsCriticalHit());

        DamageResponse res = DamageResponse
                .builder()
                .minDamageRate(minDamage)
                .maxDamageRate(maxDamage)
                .possibleDamages(possibleDamages)
                .build();

        return ResultUtil.ok(res);
    }
}
