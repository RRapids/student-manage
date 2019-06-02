package com.sm.factory;

import com.sm.dao.*;
import com.sm.dao.impl.*;

public class DAOFactory {
    public static AdminDAO getAdminDAOInstance() {
        return new AdminDAOImpl();
    }
    public static DepartmentDAO getDepartmentDAOInstance() {
        return new DepartmentDAOImpl();
    }
    public static CClassDAO getCClassDAOInstance(){
        return new CClassDAOImpl();
    }
    public static StudentDAO getStudentDAOInstance(){
        return new StudentDAOImpl();
    }
    public static RewardsDAO getRewardsDAOInstance(){
        return new RewardsDAOImpl();
    }
}
