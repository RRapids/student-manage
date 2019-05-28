package com.sm.dao;

import com.sm.entity.CClass;

import java.sql.SQLException;
import java.util.List;

/**
 * 班级DAO接口
 */
public interface CClassDAO {
    /**
     * 按照院系id查询班级
     * @param departmentId
     * @return List<CClass>
     * @throws SQLException
     */
    List<CClass> selectByDepartmentId(int departmentId) throws SQLException;
    //查询所有班级
    List<CClass> selectAll() throws SQLException;
    //新增班级
    int insertClass(CClass cClass) throws SQLException;
    //删除班级
    long deleteClassById(long id) throws SQLException;
    //根据院系id统计班级数
    int cuntByDepartmentId(int departmentId) throws SQLException;
}
