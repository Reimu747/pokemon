package com.reimu747.pokemon.service.impl;

import com.reimu747.pokemon.dao.ItemDao;
import com.reimu747.pokemon.model.vo.ItemVO;
import com.reimu747.pokemon.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ItemServiceImpl
 * @Author Reimu747
 * @Date 2019/1/18 4:24
 * @Description
 * @Version 1.0
 **/
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemDao itemDao;

    @Override
    public List<ItemVO> getAllItem() {
        return itemDao.getAllItem();
    }

    @Override
    public ItemVO getItemByName(String name) {
        return itemDao.getItemByName(name);
    }
}
