package com.doll.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Price entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "price", catalog = "dollmachine")
public class Price implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer coin;
	private Integer rmb;

	// Constructors

	/** default constructor */
	public Price() {
	}

	/** full constructor */
	public Price(Integer coin, Integer rmb) {
		this.coin = coin;
		this.rmb = rmb;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "coin")
	public Integer getCoin() {
		return this.coin;
	}

	public void setCoin(Integer coin) {
		this.coin = coin;
	}

	@Column(name = "rmb")
	public Integer getRmb() {
		return this.rmb;
	}

	public void setRmb(Integer rmb) {
		this.rmb = rmb;
	}

}