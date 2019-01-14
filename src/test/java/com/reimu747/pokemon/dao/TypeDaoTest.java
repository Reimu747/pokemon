package com.reimu747.pokemon.dao;

import com.reimu747.pokemon.model.vo.TypeVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @ClassName TypeDaoTest
 * @Author Reimu747
 * @Date 2019/1/5 6:38
 * @Description
 * @Version 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TypeDaoTest
{
    @Autowired
    TypeDao typeDao;

    @Test
    public void getAllTypeTest()
    {
        List<TypeVO> list = typeDao.getAllTypes();
        for (TypeVO typeVO : list)
        {
            System.out.println(typeVO);
        }
    }

    @Test
    public void getTypeVOByNameTest()
    {
        System.out.println(typeDao.getTypeVOByName("电"));
        System.out.println(typeDao.getTypeVOByName("气体"));
    }

    @Test
    public void getTypeVOByIdTest()
    {
        Assert.assertEquals("一般", typeDao.getTypeVOById(1).getName());
        Assert.assertEquals("3,11", typeDao.getTypeVOById(13).getSuperEffective());
    }
}
