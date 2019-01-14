package com.reimu747.pokemon.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName WazaDaoTest
 * @Author Reimu747
 * @Date 2019/1/5 6:38
 * @Description
 * @Version 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class WazaDaoTest
{
    @Autowired
    private WazaDao wazaDao;

    @Test
    public void getWazaByNameTest()
    {
        System.out.println(wazaDao.getWazaByName("十万伏特"));
    }
}
