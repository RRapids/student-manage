package com.sm.service.impl;

import com.sm.dao.AdminDAO;
import com.sm.dao.CClassDAO;
import com.sm.dao.DepartmentDAO;
import com.sm.dao.StudentDAO;
import com.sm.entity.CClass;
import com.sm.entity.Department;
import com.sm.entity.Student;
import com.sm.factory.DAOFactory;
import com.sm.service.DepartmentService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentServiceImpl implements DepartmentService {
    private DepartmentDAO departmentDAO = DAOFactory.getDepartmentDAOInstance();
    private CClassDAO cClassDAO = DAOFactory.getCClassDAOInstance();
    private StudentDAO studentDAO = DAOFactory.getStudentDAOInstance();
    @Override
    public List<Department> selectAll() {
        List<Department> departmentList = null;
        try {
            departmentList = departmentDAO.getAll();
        } catch (SQLException e) {
            System.err.print("查询院系信息出现异常");
        }
        return departmentList;
    }

    @Override
    public void deleteDepartment(long id) {
        try {
            departmentDAO.deleteDepartmentById(id);
        } catch (SQLException e) {
            System.err.println("删除院系信息出现异常");

        }
    }

    @Override
    public int addDepartment(Department department) {
        int result = 0;
        try {
            result = departmentDAO.insertDepartment(department);
        } catch (SQLException e) {
            System.err.println("新增院系异常");
        }
        return  result;
    }

    @Override
    public List<Map> selectDepartmentInfo() {
        List<Department> departmentList = null;
        try {
            departmentList = departmentDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Map> mapList = new ArrayList<>();
        for (Department department:departmentList) {
            Map<String,Object> map = new HashMap<>();
            map.put("department",department);
            try {
                map.put("classCount",cClassDAO.cuntByDepartmentId(department.getId()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                map.put("studentCount",studentDAO.countByDepartmentId(department.getId()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            mapList.add(map);
        }
        return mapList;
    }
}
