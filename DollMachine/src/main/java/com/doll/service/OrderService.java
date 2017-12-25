package com.doll.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.doll.dao.impl.OrderDao;
import com.doll.entity.BarOrder;

@Service
public class OrderService {
	@Autowired
	private OrderDao orderDao;
	@Cacheable(value="orderCache",key="'order'+#id")
	public BarOrder getOrderInfoById(int id){
		return orderDao.getOrderInfoById(id);
	};
	
	@CacheEvict(value="orderCache",key="'order'+#id")
	public void updateById(int id){
		
	}
}
