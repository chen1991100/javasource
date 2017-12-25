package com.doll.dao.impl;

import org.springframework.stereotype.Repository;

import com.doll.entity.BarOrder;

@Repository
public class OrderDao extends BaseDaoImpl<BarOrder, Integer> {
	
	public BarOrder getOrderInfoById(int id){
		return super.get(id);
	};
}
