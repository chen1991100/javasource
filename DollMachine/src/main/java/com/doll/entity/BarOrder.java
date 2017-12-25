package com.doll.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * BarOrder entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bar_order", catalog = "springdemo")
public class BarOrder implements java.io.Serializable {

	// Fields

	private Integer id;
	private String outtradeno;
	private String cid;
	private Timestamp insertTime;
	private Timestamp payTime;
	private Integer payHour;
	private Integer totalAmount;
	private String orderNumber;
	private String channel;

	// Constructors

	/** default constructor */
	public BarOrder() {
	}

	/** full constructor */
	public BarOrder(String outtradeno, String cid, Timestamp insertTime,
			Timestamp payTime, Integer payHour, Integer totalAmount,
			String orderNumber, String channel) {
		this.outtradeno = outtradeno;
		this.cid = cid;
		this.insertTime = insertTime;
		this.payTime = payTime;
		this.payHour = payHour;
		this.totalAmount = totalAmount;
		this.orderNumber = orderNumber;
		this.channel = channel;
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

	@Column(name = "outtradeno", length = 100)
	public String getOuttradeno() {
		return this.outtradeno;
	}

	public void setOuttradeno(String outtradeno) {
		this.outtradeno = outtradeno;
	}

	@Column(name = "cid", length = 100)
	public String getCid() {
		return this.cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	@Column(name = "insert_time", length = 19)
	public Timestamp getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

	@Column(name = "pay_time", length = 19)
	public Timestamp getPayTime() {
		return this.payTime;
	}

	public void setPayTime(Timestamp payTime) {
		this.payTime = payTime;
	}

	@Column(name = "pay_hour")
	public Integer getPayHour() {
		return this.payHour;
	}

	public void setPayHour(Integer payHour) {
		this.payHour = payHour;
	}

	@Column(name = "total_amount")
	public Integer getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Column(name = "order_number", length = 100)
	public String getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	@Column(name = "channel", length = 100)
	public String getChannel() {
		return this.channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

}