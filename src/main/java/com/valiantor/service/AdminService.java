package com.valiantor.service;

import com.valiantor.dao.AdminDao;
import com.valiantor.entity.Admin;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminService {

    @Resource(name="adminDao")
    private AdminDao adminDao;

    /**
     * 登录，判断用户和密码对应管理员是存在，如果存在，则返回true,否则返回false
     * @param aId
     * @param password
     * @return
     */
    public boolean login(String aId,String password){
        Admin admin = adminDao.findAdminByAIdAndPassword(aId, password);
        if(admin == null)  return false;
        return true;
    }
}
