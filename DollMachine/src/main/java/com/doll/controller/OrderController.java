package com.doll.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doll.entity.BarOrder;
import com.doll.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/one")
	@ResponseBody
	public BarOrder showOrderInfo(HttpServletRequest request,HttpServletResponse response){
		int id = 1;
		BarOrder barOrder = orderService.getOrderInfoById(id);
		request.setAttribute("barOrder", barOrder);
		return barOrder;
	}
	@RequestMapping("/update")
	public void update(){
		int id = 1;
		orderService.updateById(id);
	}
}
