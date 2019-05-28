package com.sm.service;

import com.sm.entity.CClass;

import java.util.List;
import java.util.Map;

public interface CClassService {
    //根据院系id查询所有班级
    List<CClass> selectByDepartmentId(int departmentId);
    //查询所有班级
    List<CClass> selectAll();
    //新增班级
    int addClass(CClass cClass);
    //删除班级
    void deleteClass(long id);
}
