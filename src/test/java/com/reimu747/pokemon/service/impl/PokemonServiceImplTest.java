package com.reimu747.pokemon.service.impl;

import com.reimu747.pokemon.dao.PokemonDao;
import com.reimu747.pokemon.dao.TypeDao;
import com.reimu747.pokemon.model.vo.BsVO;
import com.reimu747.pokemon.model.vo.IvsVO;
import com.reimu747.pokemon.model.enums.NatureEnum;
import com.reimu747.pokemon.model.vo.PokemonVO;
import com.reimu747.pokemon.model.vo.StatsVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName PokemonServiceImplTest
 * @Author Reimu747
 * @Date 2019/1/5 10:38
 * @Description
 * @Version 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class PokemonServiceImplTest
{
    @Autowired
    private PokemonDao pokemonDao;
    @Autowired
    private PokemonServiceImpl pokemonService;
    @Autowired
    private TypeDao typeDao;

    // 全0个体值
    private IvsVO allZeroIvsVo = IvsVO
            .builder()
            .hpIvs(0).attackIvs(0).defenseIvs(0)
            .specialAttackIvs(0).specialDefenseIvs(0).speedIvs(0)
            .build();
    // 全0努力值
    private BsVO allZeroBsVo = BsVO
            .builder()
            .hpBs(0).attackBs(0).defenseBs(0)
            .specialAttackBs(0).specialDefenseBs(0).speedBs(0)
            .build();
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

    private NatureEnum natureOne = NatureEnum.RASH;
    private NatureEnum natureTwo = NatureEnum.LONELY;
    private NatureEnum natureThree = NatureEnum.LAX;
    private NatureEnum natureFour = NatureEnum.HARDY;

    @Test
    public void getStatsTest()
    {
        PokemonVO pokemonVO = pokemonDao.getPokemonVOByName("皮卡丘");
        System.out.println(pokemonVO);

        StatsVO statsVO = pokemonService.getStats(pokemonDao.getPokemonVOByName("皮卡丘"), allZeroIvsVo, allZeroBsVo, 100, natureFour);
        System.out.println(statsVO);

        statsVO = pokemonService.getStats(pokemonDao.getPokemonVOByName("皮卡丘"), allFullIvsVo, allFullBsVo, 100, natureFour);
        System.out.println(statsVO);

        statsVO = pokemonService.getStats(pokemonDao.getPokemonVOByName("皮卡丘"), allFullIvsVo, allFullBsVo, 100, natureOne);
        System.out.println(statsVO);

        statsVO = pokemonService.getStats(pokemonDao.getPokemonVOByName("皮卡丘"), allZeroIvsVo, allFullBsVo, 100, natureTwo);
        System.out.println(statsVO);

        statsVO = pokemonService.getStats(pokemonDao.getPokemonVOByName("皮卡丘"), allZeroIvsVo, allZeroBsVo, 100, natureThree);
        System.out.println(statsVO);
    }

    @Test
    public void getIvsRangeTest() throws Exception
    {
        StatsVO statsVO = StatsVO
                .builder()
                .hpStats(11)
                .attackStats(5)
                .defenseStats(5)
                .specialAttackStats(6)
                .specialDefenseStats(4)
                .speedStats(6)
                .build();

        IvsVO[] res = pokemonService.getIvsRange(pokemonDao.getPokemonVOByName("拉鲁拉丝"), allZeroBsVo, statsVO, 1, NatureEnum.NAIVE);

        for (IvsVO ivsVO : res)
        {
            System.out.println(ivsVO);
        }

        statsVO = StatsVO
                .builder()
                .hpStats(142)
                .attackStats(69)
                .defenseStats(114)
                .specialAttackStats(89)
                .specialDefenseStats(99)
                .speedStats(85)
                .build();

        res = pokemonService.getIvsRange(pokemonDao.getPokemonVOByName("水君"), allZeroBsVo, statsVO, 40, NatureEnum.BOLD);

        for (IvsVO ivsVO : res)
        {
            System.out.println(ivsVO);
        }

        statsVO = StatsVO
                .builder()
                .hpStats(166)
                .attackStats(154)
                .defenseStats(108)
                .specialAttackStats(150)
                .specialDefenseStats(113)
                .speedStats(179)
                .build();

        res = pokemonService.getIvsRange(pokemonDao.getPokemonVOByName("卡璞·鸣鸣"), allZeroBsVo, statsVO, 60, NatureEnum.MILD);

        for (IvsVO ivsVO : res)
        {
            System.out.println(ivsVO);
        }
    }

    @Test
    public void getHiddenPowerTypeTest()
    {
        // 觉冰个体值
        IvsVO ivs = IvsVO
                .builder()
                .hpIvs(31).attackIvs(0).defenseIvs(30)
                .specialAttackIvs(31).specialDefenseIvs(31).speedIvs(31)
                .build();
        Assert.assertEquals(typeDao.getTypeVOById(15), pokemonService.getHiddenPowerType(ivs));

        // 觉地个体值
        ivs = IvsVO
                .builder()
                .hpIvs(31).attackIvs(0).defenseIvs(31)
                .specialAttackIvs(30).specialDefenseIvs(30).speedIvs(31)
                .build();
        Assert.assertEquals(typeDao.getTypeVOById(5), pokemonService.getHiddenPowerType(ivs));

        // 觉火个体值
        ivs = IvsVO
                .builder()
                .hpIvs(31).attackIvs(0).defenseIvs(31)
                .specialAttackIvs(30).specialDefenseIvs(31).speedIvs(30)
                .build();
        Assert.assertEquals(typeDao.getTypeVOById(10), pokemonService.getHiddenPowerType(ivs));

        // 觉岩个体值
        ivs = IvsVO
                .builder()
                .hpIvs(31).attackIvs(0).defenseIvs(30)
                .specialAttackIvs(31).specialDefenseIvs(30).speedIvs(30)
                .build();
        Assert.assertEquals(typeDao.getTypeVOById(6), pokemonService.getHiddenPowerType(ivs));

        // 觉水个体值
        ivs = IvsVO
                .builder()
                .hpIvs(31).attackIvs(0).defenseIvs(30)
                .specialAttackIvs(30).specialDefenseIvs(31).speedIvs(31)
                .build();
        Assert.assertEquals(typeDao.getTypeVOById(11), pokemonService.getHiddenPowerType(ivs));
    }
}
