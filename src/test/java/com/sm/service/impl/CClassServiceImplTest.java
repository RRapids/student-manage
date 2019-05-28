package com.sm.service.impl;

import com.sm.entity.CClass;
import com.sm.entity.Department;
import com.sm.factory.ServiceFactory;
import com.sm.service.CClassService;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class CClassServiceImplTest {

    private CClassService cClassService = ServiceFactory.getCClassServiceInstance();
    @Test
    public void selectByDepartmentId() {
        List<CClass> cClassList = cClassService.selectByDepartmentId(2);
        cClassList.forEach(cClass -> System.out.println(cClass));
    }

    @Test
    public void addClass() {
        CClass cClass = new CClass();
        cClass.setDepartmentId(2);
        cClass.setClassName("测试班级");
        int n = cClassService.addClass(cClass);
        System.out.println(n);
    }

    @Test
    public void deleteClass() {
        cClassService.deleteClass(21);
    }

    @Test
    public void selectAll() {
        cClassService.selectAll();
    }

}