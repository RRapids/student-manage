package com.sm.dao.impl;

import com.sm.dao.StudentDAO;
import com.sm.entity.Student;
import com.sm.entity.StudentVO;
import com.sm.factory.DAOFactory;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class StudentDAOImplTest {
    private StudentDAO studentDAO = DAOFactory.getStudentDAOInstance();
    @Test
    public void selectAll() {
        List<StudentVO> studentList = null;
        try {
            studentList = studentDAO.selectAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        studentList.forEach(studentVO -> System.out.println(studentVO));
    }

    @Test
    public void selectByKeywords() {
        List<StudentVO> studentList = null;
        try {
            studentList = studentDAO.selectByKeywords("黄");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        studentList.forEach(studentVO -> System.out.println(studentVO));
    }

    @Test
    public void selectByDepartmentId() {
        List<StudentVO> studentVOList = null;
        try {
            studentVOList = studentDAO.selectByDepartmentId(5);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        studentVOList.forEach(studentVO -> System.out.println(studentVO));
    }

    @Test
    public void selectByClassId() {
        List<StudentVO> studentList = null;
        try {
            studentList = studentDAO.selectByClassId(3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        studentList.forEach(studentVO -> System.out.println(studentVO));
    }

    @Test
    public void updateStudent() throws SQLException {
        Student student = new Student();
        student.setId("1802343306");
        student.setAddress("安徽合肥");
        student.setPhone("18899996666");
        int n = studentDAO.updateStudent(student);
        assertEquals(1,n);
    }

    @Test
    public void deleteById() throws SQLException {
        int n = studentDAO.deleteById("1802343301");
        assertEquals(1,n);
    }

    @Test
    public void insertStudent() throws SQLException {
        Student student = new Student();
        student.setId("123");
        student.setClassId(1);
        student.setStudentName("袁腾飞");
        student.setAvatar("wenhao.jpg");
        student.setBirthday(new Date());
        student.setGender("男");
        student.setAddress("安徽阜阳");
        student.setPhone("18888889999");
        int n = studentDAO.insertStudent(student);
        assertEquals(1,n);
    }

    @Test
    public void countByClassId() {
        int n = 0;
        try {
            n = studentDAO.countByClassId(9);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(n+"位同学");
    }
}