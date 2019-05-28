package com.sm.service;

import com.sm.utils.ResultEntity;

public interface AdminService {
    /**
     * 管理员登录
     * @param account
     * @param password
     * @return ResultEntity
     */
    ResultEntity adminLogin(String account, String password);

}