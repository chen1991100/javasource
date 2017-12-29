package com.doll.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.doll.entity.Price;

@Repository
public class PriceDao extends BaseDaoImpl<Price, Integer> {

	public List<Price> list() {
		return super.getHqList("from Price order by rmb asc", null);
	}

	public Price getOne(int price_id) {
		return super.get(price_id);
	}

}
