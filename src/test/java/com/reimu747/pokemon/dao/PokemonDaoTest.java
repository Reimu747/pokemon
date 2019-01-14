package com.reimu747.pokemon.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName PokemonDaoTest
 * @Author Reimu747
 * @Date 2019/1/14 7:09
 * @Description
 * @Version 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class PokemonDaoTest
{
    @Autowired
    private PokemonDao pokemonDao;

    @Test
    public void getPokemonVOByNameTest()
    {
        Assert.assertEquals(255L, (long) pokemonDao.getPokemonVOByName("皮卡丘").getCatchRate());
    }
}
