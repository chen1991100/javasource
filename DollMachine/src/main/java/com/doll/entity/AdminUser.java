package com.doll.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AdminUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "admin_user", catalog = "dollmachine")
public class AdminUser implements java.io.Serializable {

	// Fields

	private String username;
	private String password;
	private Timestamp insertTime;

	// Constructors

	/** default constructor */
	public AdminUser() {
	}

	/** minimal constructor */
	public AdminUser(String username) {
		this.username = username;
	}

	/** full constructor */
	public AdminUser(String username, String password, Timestamp insertTime) {
		this.username = username;
		this.password = password;
		this.insertTime = insertTime;
	}

	// Property accessors
	@Id
	@Column(name = "username", unique = true, nullable = false, length = 20)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", length = 50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "insert_time", length = 19)
	public Timestamp getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

}