package com.sm.factory;

import com.sm.service.*;
import com.sm.service.impl.*;

public class ServiceFactory {
    public static AdminService getAdminServiceInstance() {
        return new AdminServiceImpl();
    }
    public static DepartmentService getDepartmentServiceInstance() {
        return new DepartmentServiceImpl();
    }
    public static CClassService getCClassServiceInstance(){
        return new CClassServiceImpl();
    }
    public static StudentService getStudentServiceInstance(){
        return new StudentServiceImpl();
    }
    public static RewardsService getRewardsServiceInstance(){
        return new RewardsServiceImpl();
    }
}
