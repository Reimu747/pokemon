package com.reimu747.pokemon.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName TypeServiceImplTest
 * @Author Reimu747
 * @Date 2019/1/5 7:45
 * @Description
 * @Version 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TypeServiceImplTest
{
    @Autowired
    private TypeServiceImpl typeService;

    @Test
    public void printTypeTableTest()
    {
        typeService.printTypeTable();
    }
}
