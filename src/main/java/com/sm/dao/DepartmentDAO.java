package com.sm.dao;

import com.sm.entity.Department;

import java.sql.SQLException;
import java.util.List;

/**
 * 院系DAO接口
 */
public interface DepartmentDAO {
    /**
     * 查询所有院系
     * @return List<Department>
     * @throws SQLException
     */
    List<Department> getAll() throws SQLException;
    //删除
    long deleteDepartmentById (long id) throws SQLException;
    //新增
    int insertDepartment(Department department) throws SQLException;
}
