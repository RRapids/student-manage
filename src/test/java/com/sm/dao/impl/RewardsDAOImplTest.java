package com.sm.dao.impl;

import com.sm.dao.RewardsDAO;
import com.sm.entity.Rewards;
import com.sm.factory.DAOFactory;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class RewardsDAOImplTest {
    private RewardsDAO rewardsDAO = DAOFactory.getRewardsDAOInstance();

    @Test
    public void getAll() {
        List<Rewards> rewardsList = null;
        try {
            rewardsList = rewardsDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        rewardsList.forEach(rewards -> System.out.println(rewards));
    }

    @Test
    public void deleteById() throws SQLException {
        int n = rewardsDAO.deleteById("3");
        assertEquals(1,n);
    }

    @Test
    public void selectByKeywords() {
        List<Rewards> rewardsList = null;
        try {
            rewardsList = rewardsDAO.selectByKeywords("小");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        rewardsList.forEach(rewards -> System.out.println(rewards));
    }

    @Test
    public void insertRewards() throws SQLException {
        Rewards rewards = new Rewards();
        rewards.setId("4");
        rewards.setType("奖励");
        rewards.setRewardsDate(new Date());
        rewards.setNumber("18021321");
        rewards.setName("小花");
        rewards.setReason("篮球比赛第一");
        int n = rewardsDAO.insertRewards(rewards);
        assertEquals(1,n);
    }
}