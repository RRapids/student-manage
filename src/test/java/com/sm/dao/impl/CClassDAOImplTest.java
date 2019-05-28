package com.sm.dao.impl;

import com.sm.dao.CClassDAO;
import com.sm.entity.CClass;
import com.sm.factory.DAOFactory;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CClassDAOImplTest {
    private CClassDAO cClassDAO = DAOFactory.getCClassDAOInstance();
    @Test
    public void selectByDepartmentId() {
        List<CClass> cClassList = null;
        try {
             cClassList =  cClassDAO.selectByDepartmentId(5);
            if (cClassList != null){
                System.out.println(cClassList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insertClass() {
        CClass cClass = new CClass();
        cClass.setDepartmentId(2);
        cClass.setClassName("测试班级");
        try {
            int n = cClassDAO.insertClass(cClass);
            assertEquals(1,n);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteClassById() {
        try {
            cClassDAO.deleteClassById(20);
        } catch (SQLException e) {
            System.out.println("异常");
        }
    }

    @Test
    public void selectAll() {
        List<CClass> cClassList = null;
        try {
            cClassList = cClassDAO.selectAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cClassList.forEach(cClass -> System.out.println(cClass));

    }
}