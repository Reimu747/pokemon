package com.reimu747.pokemon.service.impl;

import com.reimu747.pokemon.dao.WazaDao;
import com.reimu747.pokemon.model.vo.WazaVO;
import com.reimu747.pokemon.service.WazaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName WazaServiceImpl
 * @Author Reimu747
 * @Date 2019/1/18 4:32
 * @Description
 * @Version 1.0
 **/
@Service
public class WazaServiceImpl implements WazaService {
    @Autowired
    private WazaDao wazaDao;

    @Override
    public List<WazaVO> getAllWaza() {
        return wazaDao.getAllWaza();
    }

    @Override
    public WazaVO getWazaByName(String name) {
        return wazaDao.getWazaByName(name);
    }
}
