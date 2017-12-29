package com.doll.dao.impl;

import org.springframework.stereotype.Repository;

import com.doll.entity.AdminUser;

@Repository
public class AdminUserDao extends BaseDaoImpl<AdminUser, String> {

	public AdminUser getAdminUserInfoByUserName(String username) {
		return super.get(username);
	}
	
	
}
