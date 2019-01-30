package com.reimu747.pokemon.dao;

import com.reimu747.pokemon.model.vo.ItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Reimu747
 */
@Mapper
@Repository
public interface ItemDao {
    /**
     * 获取所有道具列表
     *
     * @return 所有道具列表
     */
    @Select(value = "SELECT `name`, `description` FROM item")
    List<ItemVO> getAllItem();

    /**
     * 获取名称对应的道具
     *
     * @param name 名称
     * @return 对应的道具
     */
    @Select(value = "SELECT `name`, `description` FROM item WHERE name = #{name}")
    ItemVO getItemByName(@Param(value = "name") String name);
}
