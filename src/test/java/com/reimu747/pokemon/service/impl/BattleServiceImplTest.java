package com.reimu747.pokemon.service.impl;

import com.reimu747.pokemon.dao.PokemonDao;
import com.reimu747.pokemon.dao.TokuseiDao;
import com.reimu747.pokemon.dao.TypeDao;
import com.reimu747.pokemon.dao.WazaDao;
import com.reimu747.pokemon.model.enums.NatureEnum;
import com.reimu747.pokemon.model.vo.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName BattleServiceImplTest
 * @Author Reimu747
 * @Date 2019/1/12 9:06
 * @Description
 * @Version 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class BattleServiceImplTest
{
    @Autowired
    private PokemonServiceImpl pokemonService;
    @Autowired
    private BattleServiceImpl battleService;
    @Autowired
    private PokemonDao pokemonDao;
    @Autowired
    private WazaDao wazaDao;
    @Autowired
    private TypeDao typeDao;
    @Autowired
    private TokuseiDao tokuseiDao;

    // 全满个体值
    private IvsVO allFullIvsVo = IvsVO
            .builder()
            .hpIvs(31).attackIvs(31).defenseIvs(31)
            .specialAttackIvs(31).specialDefenseIvs(31).speedIvs(31)
            .build();
    // 全满努力值
    private BsVO allFullBsVo = BsVO
            .builder()
            .hpBs(252).attackBs(255).defenseBs(255)
            .specialAttackBs(255).specialDefenseBs(255).speedBs(255)
            .build();
    // 满物攻满速度4HP努力值
    private BsVO fullAttackBsVo = BsVO
            .builder()
            .hpBs(4).attackBs(252).defenseBs(0)
            .specialAttackBs(0).specialDefenseBs(0).speedBs(252)
            .build();
    // 物攻0，其余全满个体值
    private IvsVO zeroAttackIvsVo = IvsVO
            .builder()
            .hpIvs(31).attackIvs(0).defenseIvs(31)
            .specialAttackIvs(31).specialDefenseIvs(31).speedIvs(31)
            .build();
    // mega沙奈朵 努力值分配
    private BsVO gardevoirBsVo = BsVO
            .builder()
            .hpBs(252).attackBs(0).defenseBs(36)
            .specialAttackBs(192).specialDefenseBs(4).speedBs(20)
            .build();

    @Test
    public void getSameTypeCorrectionTest()
    {
        // 初始化招式
        WazaVO wazaVO = wazaDao.getWazaByName("十万伏特");

        // TODO 初始化场地
        FieldVO fieldVO = new FieldVO();

        // 初始化pokemonInstanceVO
        PokemonVO pokemonVO = pokemonDao.getPokemonVOByName("皮卡丘");
        PokemonInstanceVO pokemonInstanceVO = PokemonInstanceVO.builder()
                .ivsVO(allFullIvsVo)
                .bsVO(allFullBsVo)
                .level(100)
                .nature(NatureEnum.BASHFUL)
                .wazaOne(wazaVO)
                .build();
        BeanUtils.copyProperties(pokemonVO, pokemonInstanceVO);

        pokemonInstanceVO.setStatsVO(pokemonService.getStats(pokemonVO, allFullIvsVo, allFullBsVo, 100,
                NatureEnum.BASHFUL));

        // 通过反射来测试private方法
        Class clazz = battleService.getClass();
        try
        {
            @SuppressWarnings("unchecked")
            Method method = clazz.getDeclaredMethod("getSameTypeCorrection", PokemonInstanceVO.class, WazaVO.class,
                    FieldVO.class);
            method.setAccessible(true);
            Object res = method.invoke(battleService, pokemonInstanceVO, pokemonInstanceVO.getWazaOne(), fieldVO);
            Assert.assertEquals(1.5D, res);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void getTypeRelationCorrectionTest()
    {
        // 防守方属性
        TypeVO typeVOOne = typeDao.getTypeVOByName("龙");
        TypeVO typeVOTwo = typeDao.getTypeVOByName("地面");
        TypeVO typeVOThree = typeDao.getTypeVOByName("水");
        TypeVO typeVOFour = null;

        // 招式
        WazaVO wazaVO = wazaDao.getWazaByName("十万伏特");

        // 通过反射来测试private方法
        Class clazz = battleService.getClass();
        try
        {
            @SuppressWarnings("unchecked")
            Method method = clazz.getDeclaredMethod("getTypeRelationCorrection", TypeVO.class, WazaVO.class);
            method.setAccessible(true);

            Object res1 = method.invoke(battleService, typeVOOne, wazaVO);
            Assert.assertEquals(0.5D, res1);

            Object res2 = method.invoke(battleService, typeVOTwo, wazaVO);
            Assert.assertEquals(0.0D, res2);

            Object res3 = method.invoke(battleService, typeVOThree, wazaVO);
            Assert.assertEquals(2.0D, res3);

            Object res4 = method.invoke(battleService, typeVOFour, wazaVO);
            Assert.assertEquals(1.0D, res4);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void getCriticalHitRateTest()
    {
        // 初始化pokemonInstanceVO
        PokemonVO pokemonVO = pokemonDao.getPokemonVOByName("皮卡丘");
        PokemonInstanceVO pokemonInstanceVO = PokemonInstanceVO.builder()
                .ivsVO(allFullIvsVo)
                .bsVO(allFullBsVo)
                .level(100)
                .nature(NatureEnum.BASHFUL)
                .build();
        BeanUtils.copyProperties(pokemonVO, pokemonInstanceVO);

        pokemonInstanceVO.setStatsVO(pokemonService.getStats(pokemonVO, allFullIvsVo, allFullBsVo, 100,
                NatureEnum.BASHFUL));

        // 通过反射来测试private方法
        Class clazz = battleService.getClass();
        try
        {
            @SuppressWarnings("unchecked")
            Method method = clazz.getDeclaredMethod("getCriticalHitRate", PokemonInstanceVO.class, boolean.class);
            method.setAccessible(true);

            Object res1 = method.invoke(battleService, pokemonInstanceVO, false);
            Assert.assertEquals(1.0D, res1);

            Object res2 = method.invoke(battleService, pokemonInstanceVO, true);
            Assert.assertEquals(1.5D, res2);

            pokemonInstanceVO.setTokuseiVO(tokuseiDao.getTokuseiByName("狙击手"));

            Object res3 = method.invoke(battleService, pokemonInstanceVO, false);
            Assert.assertEquals(1.0D, res3);

            Object res4 = method.invoke(battleService, pokemonInstanceVO, true);
            Assert.assertEquals(2.25D, res4);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void getDamageRangeTest()
    {
        // 招式 舍身冲撞
        WazaVO doubleEdge = wazaDao.getWazaByName("舍身冲撞");
        // 特性 亲子爱
        TokuseiVO parentalBond = tokuseiDao.getTokuseiByName("亲子爱");

        // 初始化 Mega袋兽 50级固执满物攻 带舍身冲撞 被威吓一次
        PokemonVO pokemonVO = pokemonDao.getPokemonVOByName("Mega袋兽");
        PokemonInstanceVO megaKangaskhan = PokemonInstanceVO.builder()
                .ivsVO(allFullIvsVo)
                .bsVO(fullAttackBsVo)
                .level(50)
                .nature(NatureEnum.ADAMANT)
                .wazaOne(doubleEdge)
                .tokuseiVO(parentalBond)
                .attackLevel(0)
                .build();
        megaKangaskhan.setStatsVO(pokemonService.getStats(pokemonVO, allFullIvsVo, fullAttackBsVo, 50,
                NatureEnum.ADAMANT));
        BeanUtils.copyProperties(pokemonVO, megaKangaskhan);

        // 初始化 Mega沙奈朵 50级内敛 gardevoirBsVo努力值分配
        pokemonVO = pokemonDao.getPokemonVOByName("Mega沙奈朵");
        PokemonInstanceVO gardevoir = PokemonInstanceVO.builder()
                .ivsVO(allFullIvsVo)
                .bsVO(gardevoirBsVo)
                .level(50)
                .nature(NatureEnum.MODEST)
                .defenseLevel(0)
                .build();
        gardevoir.setStatsVO(pokemonService.getStats(pokemonVO, allFullIvsVo, gardevoirBsVo, 50, NatureEnum.MODEST));
        BeanUtils.copyProperties(pokemonVO, gardevoir);

        double[] damageRange = battleService.getDamageRange(megaKangaskhan, gardevoir,
                megaKangaskhan.getWazaOne(), new FieldVO(), false);

        Assert.assertEquals(2, damageRange.length);
        Assert.assertArrayEquals(new double[]{1D, 1D}, damageRange, 0D);

    }

    @Test
    public void getPossibleDamages()
    {
        // 招式 舍身冲撞
        WazaVO doubleEdge = wazaDao.getWazaByName("舍身冲撞");
        // 特性 亲子爱
        TokuseiVO parentalBond = tokuseiDao.getTokuseiByName("亲子爱");

        // 初始化 Mega袋兽 50级固执满物攻 带舍身冲撞 被威吓一次
        PokemonVO pokemonVO = pokemonDao.getPokemonVOByName("Mega袋兽");
        PokemonInstanceVO megaKangaskhan = PokemonInstanceVO.builder()
                .ivsVO(allFullIvsVo)
                .bsVO(fullAttackBsVo)
                .level(50)
                .nature(NatureEnum.ADAMANT)
                .wazaOne(doubleEdge)
                .tokuseiVO(parentalBond)
                .attackLevel(0)
                .build();
        megaKangaskhan.setStatsVO(pokemonService.getStats(pokemonVO, allFullIvsVo, fullAttackBsVo, 50,
                NatureEnum.ADAMANT));
        BeanUtils.copyProperties(pokemonVO, megaKangaskhan);

        // 初始化 Mega沙奈朵 50级内敛 gardevoirBsVo努力值分配
        pokemonVO = pokemonDao.getPokemonVOByName("Mega沙奈朵");
        PokemonInstanceVO gardevoir = PokemonInstanceVO.builder()
                .ivsVO(allFullIvsVo)
                .bsVO(gardevoirBsVo)
                .level(50)
                .nature(NatureEnum.MODEST)
                .defenseLevel(0)
                .build();
        gardevoir.setStatsVO(pokemonService.getStats(pokemonVO, allFullIvsVo, gardevoirBsVo, 50, NatureEnum.MODEST));
        BeanUtils.copyProperties(pokemonVO, gardevoir);

        List<Integer> possibleDamages = battleService.getPossibleDamages(megaKangaskhan, gardevoir,
                megaKangaskhan.getWazaOne(), new FieldVO(), false);

        Assert.assertEquals(Arrays.asList(181, 183, 187, 188, 191, 192, 195, 196, 198, 202, 203, 206, 207, 210, 211,
                215), possibleDamages);
    }
}
