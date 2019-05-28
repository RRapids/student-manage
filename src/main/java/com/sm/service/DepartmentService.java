package com.sm.service;

import com.sm.entity.Department;

import java.util.List;
import java.util.Map;

public interface DepartmentService {
    List<Department> selectAll();
    //删除院系
    void deleteDepartment(long id);
    //新增院系
    int addDepartment(Department department);
    //获取所有院系的所有信息
    List<Map> selectDepartmentInfo();
}
