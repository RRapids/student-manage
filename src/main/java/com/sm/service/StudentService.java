package com.sm.service;

import com.sm.entity.Student;
import com.sm.entity.StudentVO;

import java.util.List;

public interface StudentService {
    List<StudentVO> selectAll();
    //根据关键词模糊查询
    List<StudentVO> selectByKeywords(String keywords);
    //根据院系id查询所有学生
    List<StudentVO> selectByDepartmentId(int departmentId);
    //根据班级id查询所有学生
    List<StudentVO> selectByClassId(int classId);
    //更新学生信息
    int updateStudent(Student student);
    //根据id删除学生
    int deleteById(String id);
    //新增
    int insertStudent(Student student);
    /**
     *
     * @param classId
     * @return
     */
    int countStudentByClassId(int classId);
}
