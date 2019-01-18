package com.reimu747.pokemon.service.impl;

import com.reimu747.pokemon.dao.TokuseiDao;
import com.reimu747.pokemon.model.vo.TokuseiVO;
import com.reimu747.pokemon.service.TokuseiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName TokuseiServiceImpl
 * @Author Reimu747
 * @Date 2019/1/18 3:53
 * @Description
 * @Version 1.0
 **/
@Service
public class TokuseiServiceImpl implements TokuseiService {
    @Autowired
    private TokuseiDao tokuseiDao;

    @Override
    public List<TokuseiVO> getAllTokusei() {
        return tokuseiDao.getAllTokusei();
    }

    @Override
    public TokuseiVO getTokuseiByName(String name) {
        return tokuseiDao.getTokuseiByName(name);
    }
}
