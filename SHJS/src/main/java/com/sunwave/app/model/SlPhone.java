package com.sunwave.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * SlPhone entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sl_phone", catalog = "inmm")
public class SlPhone implements java.io.Serializable {

	// Fields

	private Integer phoneId;
	private SlArea slArea;
	private String phoneNum;
	private String ownerName;
	private Integer isWhite;
	private Integer operator;

	// Constructors

	/** default constructor */
	public SlPhone() {
	}

	/** full constructor */
	public SlPhone(SlArea slArea, String phoneNum, String ownerName,
			Integer isWhite, Integer operator) {
		this.slArea = slArea;
		this.phoneNum = phoneNum;
		this.ownerName = ownerName;
		this.isWhite = isWhite;
		this.operator = operator;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "phone_id", unique = true, nullable = false)
	public Integer getPhoneId() {
		return this.phoneId;
	}

	public void setPhoneId(Integer phoneId) {
		this.phoneId = phoneId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "area_id")
	public SlArea getSlArea() {
		return this.slArea;
	}

	public void setSlArea(SlArea slArea) {
		this.slArea = slArea;
	}

	@Column(name = "phone_num", length = 20)
	public String getPhoneNum() {
		return this.phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	@Column(name = "owner_name", length = 50)
	public String getOwnerName() {
		return this.ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	@Column(name = "is_white")
	public Integer getIsWhite() {
		return this.isWhite;
	}

	public void setIsWhite(Integer isWhite) {
		this.isWhite = isWhite;
	}

	@Column(name = "operator")
	public Integer getOperator() {
		return this.operator;
	}

	public void setOperator(Integer operator) {
		this.operator = operator;
	}

}