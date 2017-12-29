package com.doll.dao.impl;

import org.springframework.stereotype.Repository;

import com.doll.entity.PayOrder;
import com.sun.org.apache.bcel.internal.generic.DSUB;

@Repository
public class OrderDao extends BaseDaoImpl<PayOrder, String> {

	public PayOrder getOne(String outTradeNo) {
		return super.get(outTradeNo);
	}

	public String insertPayInfo(PayOrder payOrder) {
		return super.save(payOrder);
	}
	
	public PayOrder getOneForUpdate(String outTradeNo){
		return super.getHqlObjForUpdate("from PayOrder where outtradeno=?", outTradeNo);
	}

	public int updateForHql(String trade_no,String out_trade_no) {
		return super.executeSql("update pay_order set pay_time=now(),order_status=1,trade_no=? where outtradeno=?", trade_no,out_trade_no);
	}
	
}
