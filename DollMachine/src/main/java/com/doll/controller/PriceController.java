package com.doll.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doll.entity.Price;
import com.doll.pagebean.DataTableBean;
import com.doll.pagebean.ParamListBean;
import com.doll.service.PriceService;

@Controller
@RequestMapping("/price")
public class PriceController {
	@Autowired
	private PriceService priceService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<Price> list(HttpServletRequest request,HttpServletResponse response){
	    return priceService.list();
	}

}
