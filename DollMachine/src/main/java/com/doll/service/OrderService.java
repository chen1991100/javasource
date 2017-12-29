package com.doll.service;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.doll.dao.impl.OrderDao;
import com.doll.entity.PayOrder;

@Service
public class OrderService {
	@Autowired
	private OrderDao orderDao;
	
	@CacheEvict(value="orderCache",key="'order'+#id")
	public void updateById(int id){
		
	}
	
	public void orderPaySuccess() {
		
	}

	public PayOrder getOne(String outTradeNo) {
		return orderDao.getOne(outTradeNo);
	}

	public String insertPayInfo(PayOrder payOrder) {
		return orderDao.insertPayInfo(payOrder);
	}

	public int updatePayOrder(String out_trade_no, String trade_no,
			int total_amount) {
		int ret = 0;
		PayOrder payOrder = orderDao.getOneForUpdate(out_trade_no);
		if(payOrder!=null&&payOrder.getTotalAmount()==total_amount){
			if(payOrder.getOrderStatus()==1){
				ret=1;
			}else{
				payOrder.setOrderStatus(1);
				payOrder.setPayTime(new Timestamp(System.currentTimeMillis()));
				payOrder.setTradeNo(trade_no);
				if(orderDao.updateForHql(trade_no,out_trade_no)>0){
					ret=1;
				} 
			}
		}
		return ret;
	}
}
