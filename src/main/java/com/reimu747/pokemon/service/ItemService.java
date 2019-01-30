package com.reimu747.pokemon.service;

import com.reimu747.pokemon.model.vo.ItemVO;

import java.util.List;

/**
 * @Author Reimu747
 */
public interface ItemService {
    /**
     * 获取所有道具列表
     *
     * @return 所有道具列表
     */
    List<ItemVO> getAllItem();

    /**
     * 获取名称对应的道具
     *
     * @param name 名称
     * @return 对应的道具
     */
    ItemVO getItemByName(String name);
}
