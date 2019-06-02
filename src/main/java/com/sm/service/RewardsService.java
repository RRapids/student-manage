package com.sm.service;

import com.sm.entity.Rewards;

import java.util.List;

public interface RewardsService {
    //查询所有
    List<Rewards> getAll();
    //根据Id删除
    int deleteById(String id);
    //关键字搜索
    List<Rewards> selectByKeywords(String keywords);
    //新增奖惩
    int insertRewards(Rewards rewards);
}
