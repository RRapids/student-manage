package com.sm.service.impl;

import com.sm.entity.Department;
import com.sm.factory.ServiceFactory;
import com.sm.service.DepartmentService;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class DepartmentServiceImplTest {
    private DepartmentService departmentService = ServiceFactory.getDepartmentServiceInstance();
    @Test
    public void selectAll() {
        List<Department> departmentList = departmentService.selectAll();
        departmentList.forEach(department -> System.out.println(department));
    }

    @Test
    public void selectDepartmentInfo() {
        List<Map> mapList = departmentService.selectDepartmentInfo();
        mapList.forEach(map -> {
            System.out.println(map.get("department")+","+map.get("classCount")+"个班，"+map.get("studentCount")+"个学生");
        });
    }
}