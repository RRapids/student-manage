package com.sm.dao;

import com.sm.entity.Rewards;

import java.sql.SQLException;
import java.util.List;

public interface RewardsDAO {
    //查询所有
    List<Rewards> getAll() throws SQLException;
    //根据Id删除
    int deleteById(String id) throws SQLException;
    //根据关键字查询
    List<Rewards> selectByKeywords(String keywords) throws SQLException;
    //新增奖惩
    int insertRewards(Rewards rewards) throws SQLException;

}
