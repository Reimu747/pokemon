package com.reimu747.pokemon.service;

import com.reimu747.pokemon.model.vo.WazaVO;

import java.util.List;

/**
 * @Author Reimu747
 */
public interface WazaService {
    /**
     * 获取所有招式的列表
     *
     * @return 所有招式的列表
     */
    List<WazaVO> getAllWaza();

    /**
     * 获取名称对应的招式
     *
     * @param name 名称
     * @return 对应的招式
     */
    WazaVO getWazaByName(String name);
}
