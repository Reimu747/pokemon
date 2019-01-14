package com.reimu747.pokemon.service.impl;

import com.reimu747.pokemon.dao.PokeBallDao;
import com.reimu747.pokemon.dao.PokemonDao;
import com.reimu747.pokemon.model.enums.NatureEnum;
import com.reimu747.pokemon.model.vo.BsVO;
import com.reimu747.pokemon.model.vo.IvsVO;
import com.reimu747.pokemon.model.vo.PokemonInstanceVO;
import com.reimu747.pokemon.model.vo.PokemonVO;
import com.reimu747.pokemon.service.PokeBallService;
import com.reimu747.pokemon.service.PokemonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName PokeBallServiceImplTest
 * @Author lenovo
 * @Date 2019/1/14 8:53
 * @Description
 * @Version 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class PokeBallServiceImplTest
{
    @Autowired
    private PokemonService pokemonService;
    @Autowired
    private PokeBallService pokeBallService;
    @Autowired
    private PokemonDao pokemonDao;
    @Autowired
    private PokeBallDao pokeBallDao;

    // 全满个体值
    private IvsVO allFullIvsVo = IvsVO
            .builder()
            .hpIvs(31).attackIvs(31).defenseIvs(31)
            .specialAttackIvs(31).specialDefenseIvs(31).speedIvs(31)
            .build();
    // 全0努力值
    private BsVO allZeroBsVo = BsVO
            .builder()
            .hpBs(252).attackBs(255).defenseBs(255)
            .specialAttackBs(255).specialDefenseBs(255).speedBs(255)
            .build();

    @Test
    public void getCatchRateTest()
    {
        pokemonDao.getPokemonVOByName("费洛美螂");
        PokemonInstanceVO pheromosa = PokemonInstanceVO.builder()
                .ivsVO(allFullIvsVo)
                .bsVO(allZeroBsVo)
                .level(50)
                .nature(NatureEnum.ADAMANT)
                .build();
        pheromosa.setStatsVO(pokemonService.getStats(pokemonDao.getPokemonVOByName("费洛美螂"),
                allFullIvsVo, allZeroBsVo, 50, NatureEnum.ADAMANT));
        BeanUtils.copyProperties(pokemonDao.getPokemonVOByName("费洛美螂"), pheromosa);
        pheromosa.setHpNow(pheromosa.getStatsVO().getHpStats());

        System.out.println(pokeBallService.getCatchRate(pheromosa, pokeBallDao.getPokeBallVOByName("究极球")));
    }
}
