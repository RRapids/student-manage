package com.sm.dao.impl;

import cn.hutool.db.sql.SqlBuilder;
import com.sm.dao.StudentDAO;
import com.sm.entity.Student;
import com.sm.entity.StudentVO;
import com.sm.utils.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    @Override
    public List<StudentVO> selectAll() throws SQLException {
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql = "SELECT t1.*,t2.class_name,t3.department_name\n" +
                "FROM t_student1 t1\n" +
                "LEFT JOIN t_class t2\n" +
                "ON t1.`class_id`=t2.`id`\n" +
                "LEFT JOIN t_department t3\n" +
                "ON t2.`department_id`=t3.`id`";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List<StudentVO> studentList = convert(rs);
        rs.close();
        pstmt.close();
        jdbcUtil.closeConnection();
        return studentList;
    }

    @Override
    public List<StudentVO> selectByKeywords(String keywords) throws SQLException {
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql = "SELECT t1.*,t2.class_name,t3.department_name\n" +
                "FROM t_student1 t1\n" +
                "LEFT JOIN t_class t2\n" +
                "ON t1.`class_id`=t2.`id`\n" +
                "LEFT JOIN t_department t3\n" +
                "ON t2.`department_id`=t3.`id`\n" +
                "WHERE t1.student_name LIKE ? OR t1.address LIKE ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, "%" + keywords + "%");
        pstmt.setString(2, "%" + keywords + "%");
        ResultSet rs = pstmt.executeQuery();
        List<StudentVO> studentList = convert(rs);
        rs.close();
        pstmt.close();
        jdbcUtil.closeConnection();
        return studentList;
    }

    @Override
    public List<StudentVO> selectByDepartmentId(int departmentId) throws SQLException {
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql = "SELECT t1.*,t2.class_name,t3.department_name\n" +
                "FROM t_student1 t1\n" +
                "LEFT JOIN t_class t2\n" +
                "ON t1.`class_id`=t2.`id`\n" +
                "LEFT JOIN t_department t3\n" +
                "ON t2.`department_id`=t3.`id`\n" +
                "WHERE t3.id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, departmentId);
        ResultSet rs = pstmt.executeQuery();
        List<StudentVO> studentList = convert(rs);
        rs.close();
        pstmt.close();
        jdbcUtil.closeConnection();
        return studentList;
    }

    @Override
    public List<StudentVO> selectByClassId(int classId) throws SQLException {
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql = "SELECT t1.*,t2.class_name,t3.department_name\n" +
                "FROM t_student1 t1\n" +
                "LEFT JOIN t_class t2\n" +
                "ON t1.`class_id`=t2.`id`\n" +
                "LEFT JOIN t_department t3\n" +
                "ON t2.`department_id`=t3.`id`\n" +
                "WHERE t1.class_id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, classId);
        ResultSet rs = pstmt.executeQuery();
        List<StudentVO> studentList = convert(rs);
        rs.close();
        pstmt.close();
        jdbcUtil.closeConnection();
        return studentList;
    }

    @Override
    public int updateStudent(Student student) throws SQLException {
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql = "UPDATE t_student1 SET address = ?,phone = ? WHERE id = ? ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, student.getAddress());
        pstmt.setString(2, student.getPhone());
        pstmt.setString(3, student.getId());
        int n = pstmt.executeUpdate();
        pstmt.close();
        connection.close();
        return n;
    }

    @Override
    public int deleteById(String id) throws SQLException {
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql = "DELETE FROM t_student1 WHERE id = ? ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, id);
        int n = pstmt.executeUpdate();
        pstmt.close();
        connection.close();
        return n;
    }

    @Override
    public int insertStudent(Student student) throws SQLException {
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql = "INSERT INTO t_student1 VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, student.getId());
        pstmt.setInt(2, student.getClassId());
        pstmt.setString(3, student.getStudentName());
        pstmt.setString(4, student.getAvatar());
        pstmt.setString(5, student.getGender());
        pstmt.setDate(6, new Date(student.getBirthday().getTime()));
        pstmt.setString(7, student.getAddress());
        pstmt.setString(8, student.getPhone());
        int n = pstmt.executeUpdate();
        pstmt.close();
        connection.close();
        return n;
    }

    @Override
    public int countByDepartmentId(int departmentId) throws SQLException {
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql = "SELECT COUNT(*)\n" +
                "FROM t_student1 t1\n" +
                "LEFT JOIN t_class t2\n" +
                "ON t1.`class_id` = t2.`id`\n" +
                "LEFT JOIN t_department t3\n" +
                "ON t2.`department_id`= t3.`id`\n" +
                "WHERE  t3.id = ? ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, departmentId);
        ResultSet rs = pstmt.executeQuery();
        int rowCount = 0;
        if (rs.next()) {
            rowCount = rs.getInt(1);
        }
        rs.close();
        pstmt.close();
        jdbcUtil.closeConnection();
        return rowCount;
    }

    @Override
    public int countByClassId(int classId) throws SQLException {
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql = "SELECT COUNT(*) FROM t_student1  WHERE class_id=? ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, classId);
        ResultSet rs = pstmt.executeQuery();
        int rowCount = 0;
        if (rs.next()) {
            rowCount = rs.getInt(1);
        }
        rs.close();
        pstmt.close();
        jdbcUtil.closeConnection();
        return rowCount;
    }

    private List<StudentVO> convert(ResultSet rs) throws SQLException {
        List<StudentVO> studentList = new ArrayList<>();
        while (rs.next()) {
            StudentVO student = new StudentVO();
            student.setId(rs.getString("id"));
            student.setStudentName(rs.getString("student_name"));
            student.setGender(rs.getString("gender"));
            student.setAvatar(rs.getString("avatar"));
            student.setPhone(rs.getString("phone"));
            student.setAddress(rs.getString("address"));
            student.setBirthday(rs.getDate("birthday"));
            student.setDepartmentName(rs.getString("department_name"));
            student.setClassName(rs.getString("class_name"));
            studentList.add(student);
        }
        return studentList;
    }
}
