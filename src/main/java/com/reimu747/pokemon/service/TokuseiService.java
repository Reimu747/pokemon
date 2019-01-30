package com.reimu747.pokemon.service;

import com.reimu747.pokemon.model.vo.TokuseiVO;

import java.util.List;

/**
 * @Author Reimu747
 */
public interface TokuseiService {
    /**
     * 获取所有特性的列表
     *
     * @return 所有特性列表
     */
    List<TokuseiVO> getAllTokusei();

    /**
     * 获取名称对应的特性
     *
     * @param name 名称
     * @return 对应的特性
     */
    TokuseiVO getTokuseiByName(String name);
}
