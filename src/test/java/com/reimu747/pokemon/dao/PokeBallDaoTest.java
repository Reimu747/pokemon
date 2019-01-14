package com.reimu747.pokemon.dao;

import com.reimu747.pokemon.model.vo.PokeBallVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName PokeBallDaoTest
 * @Author Reimu747
 * @Date 2019/1/14 7:26
 * @Description
 * @Version 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class PokeBallDaoTest
{
    @Autowired
    private PokeBallDao pokeBallDao;

    @Test
    public void getPokeBallVOByNameTest()
    {
        Assert.assertEquals("精灵球", pokeBallDao.getPokeBallVOByName("精灵球").getName());
        Assert.assertEquals(1.0D, pokeBallDao.getPokeBallVOByName("精灵球").getCatchRate(), 0D);
        Assert.assertNull(pokeBallDao.getPokeBallVOByName("究极球").getCatchRate());
    }
}
