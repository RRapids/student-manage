package com.sm.service.impl;

import com.sm.dao.CClassDAO;
import com.sm.dao.DepartmentDAO;
import com.sm.dao.StudentDAO;
import com.sm.entity.CClass;
import com.sm.entity.Department;
import com.sm.factory.DAOFactory;
import com.sm.service.CClassService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CClassServiceImpl implements CClassService {
    private CClassDAO cClassDAO = DAOFactory.getCClassDAOInstance();
    private StudentDAO studentDAO = DAOFactory.getStudentDAOInstance();
    @Override
    public List<CClass> selectByDepartmentId(int departmentId) {
        List<CClass> cClassList = new ArrayList<>();
        try {
            cClassList = cClassDAO.selectByDepartmentId(departmentId);
        } catch (SQLException e) {
            System.out.println("通过院系id查询出现异常");
        }
        return cClassList;
    }

    @Override
    public List<CClass> selectAll() {
        List<CClass> cClassList = new ArrayList<>();
        try {
            cClassList = cClassDAO.selectAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cClassList;
    }

    @Override
    public int addClass(CClass cClass) {
        int result = 0;
        try {
            result = cClassDAO.insertClass(cClass);
        } catch (SQLException e) {
            System.out.println("新增班级异常");
        }
        return result;
    }

    @Override
    public void deleteClass(long id) {

        try {
            cClassDAO.deleteClassById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
