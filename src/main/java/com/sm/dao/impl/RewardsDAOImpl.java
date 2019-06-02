package com.sm.dao.impl;

import com.sm.dao.RewardsDAO;
import com.sm.entity.Rewards;
import com.sm.utils.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RewardsDAOImpl implements RewardsDAO {
    @Override
    public List<Rewards> getAll() throws SQLException {
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql = "SELECT * FROM t_rewards";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List<Rewards> rewardsList = convert(rs);
        rs.close();
        pstmt.close();
        jdbcUtil.closeConnection();
        return rewardsList;
    }

    @Override
    public int deleteById(String id) throws SQLException {
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql = "DELETE FROM t_rewards WHERE id = ? ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, id);
        int n = pstmt.executeUpdate();
        pstmt.close();
        connection.close();
        return n;
    }

    @Override
    public List<Rewards> selectByKeywords(String keywords) throws SQLException {
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql = "SELECT * FROM t_rewards WHERE student_number LIKE ? OR student_name LIKE ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, "%" + keywords + "%");
        pstmt.setString(2, "%" + keywords + "%");
        ResultSet rs = pstmt.executeQuery();
        List<Rewards> rewardsList = convert(rs);
        rs.close();
        pstmt.close();
        jdbcUtil.closeConnection();
        return rewardsList;
    }

    @Override
    public int insertRewards(Rewards rewards) throws SQLException {
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql = "INSERT INTO t_rewards VALUES (?,?,?,?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1,rewards.getId());
        pstmt.setString(2,rewards.getType());
        pstmt.setDate(3,new Date(rewards.getRewardsDate().getTime()));
        pstmt.setString(4,rewards.getNumber());
        pstmt.setString(5,rewards.getName());
        pstmt.setString(6,rewards.getReason());
        int n = pstmt.executeUpdate();
        pstmt.close();
        connection.close();
        return n;
    }

    private List<Rewards> convert(ResultSet rs) throws SQLException{
        List<Rewards> rewardsList = new ArrayList<>();
        while (rs.next()){
            Rewards rewards = new Rewards();
            rewards.setId(rs.getString("id"));
            rewards.setType(rs.getString("type"));
            rewards.setRewardsDate(rs.getDate("rewardsdate"));
            rewards.setNumber(rs.getString("student_number"));
            rewards.setName(rs.getString("student_name"));
            rewards.setReason(rs.getString("reason"));
            rewardsList.add(rewards);
        }
        return rewardsList;
    }
}
