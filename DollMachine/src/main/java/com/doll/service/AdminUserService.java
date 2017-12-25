package com.doll.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doll.dao.impl.AdminUserDao;
import com.doll.entity.AdminUser;

@Service
public class AdminUserService {
	@Autowired
	private AdminUserDao adminUserDao;
	
	public AdminUser getAdminUserInfoByUserName(String username){
		return adminUserDao.getAdminUserInfoByUserName(username);
	}
}
