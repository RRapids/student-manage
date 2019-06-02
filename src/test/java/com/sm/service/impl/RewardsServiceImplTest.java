package com.sm.service.impl;

import com.sm.entity.Rewards;
import com.sm.factory.ServiceFactory;
import com.sm.service.RewardsService;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class RewardsServiceImplTest {
    private RewardsService rewardsService = ServiceFactory.getRewardsServiceInstance();

    @Test
    public void getAll() {
        List<Rewards> rewardsList = rewardsService.getAll();
        rewardsList.forEach(rewards -> System.out.println(rewards));

    }

    @Test
    public void deleteById() {

    }

    @Test
    public void selectByKeywords() {
        List<Rewards> rewardsList = rewardsService.selectByKeywords("兰");
        rewardsList.forEach(rewards -> System.out.println(rewards));
    }

    @Test
    public void insertRewards() {
        Rewards rewards = new Rewards();
        rewards.setId("5");
        rewards.setType("奖励");
        rewards.setRewardsDate(new Date());
        rewards.setNumber("180213221");
        rewards.setName("张三");
        rewards.setReason("篮球比赛第一");
        rewardsService.insertRewards(rewards);
    }
}