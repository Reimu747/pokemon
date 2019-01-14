package com.reimu747.pokemon.dao;

import com.reimu747.pokemon.model.vo.TokuseiVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName TokuseiDaoTest
 * @Author Reimu747
 * @Date 2019/1/12 10:50
 * @Description
 * @Version 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TokuseiDaoTest
{
    @Autowired
    TokuseiDao tokuseiDao;

    @Test
    public void getTokuseiByNameTest()
    {
        TokuseiVO actual = tokuseiDao.getTokuseiByName("狙击手");
        TokuseiVO expected = TokuseiVO.builder()
                .name("狙击手")
                .description("攻击击中要害时伤害为正常击中要害伤害的1.5倍。")
                .build();

        Assert.assertEquals(expected, actual);
    }
}
