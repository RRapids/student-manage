package com.sm.service.impl;

import com.sm.factory.ServiceFactory;
import com.sm.service.AdminService;
import com.sm.utils.ResultEntity;
import org.junit.Test;

public class AdminServiceImplTest {
    private AdminService adminService = ServiceFactory.getAdminServiceInstance();
    @Test
    public void adminLogin() {
        ResultEntity resultEntity = adminService.adminLogin("aaa@qq.com", "aaa");
        System.out.println(resultEntity);
    }
}