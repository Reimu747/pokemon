package com.reimu747.pokemon.dao;

import com.reimu747.pokemon.model.vo.ItemVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName ItemDaoTest
 * @Author Reimu747
 * @Date 2019/2/1 17:06
 * @Description
 * @Version 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class ItemDaoTest
{
    @Autowired
    private ItemDao itemDao;

    @Test
    public void getItemByNameTest()
    {
        ItemVO item = itemDao.getItemByName("属性宝石");
        Assert.assertEquals("对应属性的宝石。携带后，对应属性的招式威力仅会增强1次。", item.getDescription());
    }
}
