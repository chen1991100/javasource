package com.doll.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.doll.dao.impl.OrderDao;
import com.doll.dao.impl.PriceDao;
import com.doll.entity.Price;

@Service
public class PriceService {
	@Autowired
	private PriceDao priceDao;
	
	@Cacheable(value="orderCache")
	public List<Price> list() {
		return priceDao.list();
	}
	@Cacheable(value="orderCache",key="'price_config_'+#price_id")
	public Price getOne(int price_id) {
		return priceDao.getOne(price_id);
	};
	
}
