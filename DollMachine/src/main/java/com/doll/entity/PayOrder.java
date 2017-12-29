package com.doll.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * PayOrder entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "pay_order", catalog = "dollmachine")
public class PayOrder implements java.io.Serializable {

	// Fields

	private String outtradeno;
	private Integer userId;
	private Timestamp insertTime;
	private Timestamp payTime;
	private Integer payCoin;
	private Integer totalAmount;
	private String tradeNo;
	private String channel;
	private Integer orderStatus;
	private Integer priceId;

	// Constructors

	/** default constructor */
	public PayOrder() {
	}

	/** minimal constructor */
	public PayOrder(String outtradeno) {
		this.outtradeno = outtradeno;
	}

	/** full constructor */
	public PayOrder(String outtradeno, Integer userId, Timestamp insertTime,
			Timestamp payTime, Integer payCoin, Integer totalAmount,
			String tradeNo, String channel, Integer orderStatus, Integer priceId) {
		this.outtradeno = outtradeno;
		this.userId = userId;
		this.insertTime = insertTime;
		this.payTime = payTime;
		this.payCoin = payCoin;
		this.totalAmount = totalAmount;
		this.tradeNo = tradeNo;
		this.channel = channel;
		this.orderStatus = orderStatus;
		this.priceId = priceId;
	}

	// Property accessors
	@Id
	@Column(name = "outtradeno", unique = true, nullable = false, length = 100)
	public String getOuttradeno() {
		return this.outtradeno;
	}

	public void setOuttradeno(String outtradeno) {
		this.outtradeno = outtradeno;
	}

	@Column(name = "user_id")
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	@Column(name = "pay_coin")
	public Integer getPayCoin() {
		return this.payCoin;
	}

	public void setPayCoin(Integer payCoin) {
		this.payCoin = payCoin;
	}

	@Column(name = "total_amount")
	public Integer getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Column(name = "trade_no", length = 100)
	public String getTradeNo() {
		return this.tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	@Column(name = "channel", length = 100)
	public String getChannel() {
		return this.channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	@Column(name = "order_status")
	public Integer getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Column(name = "price_id")
	public Integer getPriceId() {
		return this.priceId;
	}

	public void setPriceId(Integer priceId) {
		this.priceId = priceId;
	}

}