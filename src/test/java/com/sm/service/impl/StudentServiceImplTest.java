package com.sm.service.impl;

import com.sm.entity.Student;
import com.sm.entity.StudentVO;
import com.sm.factory.ServiceFactory;
import com.sm.service.StudentService;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class StudentServiceImplTest {
private StudentService studentService = ServiceFactory.getStudentServiceInstance();
    @Test
    public void selectAll() {
        List<StudentVO> studentVOList = studentService.selectAll();
        studentVOList.forEach(studentVO -> System.out.println(studentVO));
    }

    @Test
    public void selectByKeywords() {
        List<StudentVO> studentVOList = studentService.selectByKeywords("夏");
        studentVOList.forEach(studentVO -> System.out.println(studentVO));
    }

    @Test
    public void selectByDepartmentId() {
        List<StudentVO> studentVOList = studentService.selectByDepartmentId(2);
        studentVOList.forEach(studentVO -> System.out.println(studentVO));
    }

    @Test
    public void selectByClassId() {
        List<StudentVO> studentVOList = studentService.selectByClassId(5);
        studentVOList.forEach(studentVO -> System.out.println(studentVO));
    }

    @Test
    public void updateStudent() {
        Student student = new Student();
        student.setId("1802343305");
        student.setPhone("1399993333");
        student.setAddress("测试地址");
        studentService.updateStudent(student);
    }

    @Test
    public void deleteById() {
    }

    @Test
    public void insertStudent() {
        Student student = new Student();
        student.setId("123456");
        student.setClassId(2);
        student.setStudentName("张新宇");
        student.setAvatar("wenhao.jpg");
        student.setBirthday(new Date());
        student.setGender("男");
        student.setAddress("四川成都");
        student.setPhone("18888889999");
        studentService.insertStudent(student);
    }
}