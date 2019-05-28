package com.sm.dao;

import com.sm.entity.Student;
import com.sm.entity.StudentVO;

import java.sql.SQLException;
import java.util.List;

public interface StudentDAO {
    /**
     * 查询所有学生（视图对象）
     * @return  List<StudentVO>
     * @throws SQLException
     */
    List<StudentVO> selectAll() throws SQLException;
    //根据关键词模糊查询
    List<StudentVO> selectByKeywords(String keywords) throws SQLException;
    //根据院系id查询所有学生
    List<StudentVO> selectByDepartmentId(int departmentId) throws SQLException;
    //根据班级id查询所有学生
    List<StudentVO> selectByClassId(int classId) throws SQLException;
    //更新学生信息
    int updateStudent(Student student) throws SQLException;
    //根据id删除学生
    int deleteById(String id) throws SQLException;
    //新增学生
    int insertStudent(Student student) throws SQLException;
    //根据院系id统计学生人数
    int countByDepartmentId(int departmentId) throws SQLException;
    //根据班级id统计学生人数
    int countByClassId(int classId) throws SQLException;
}
