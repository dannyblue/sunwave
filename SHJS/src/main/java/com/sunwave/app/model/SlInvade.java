package com.sunwave.app.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * SlInvade entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "sl_invade", catalog = "inmm")
public class SlInvade implements java.io.Serializable {

	// Fields

	private Integer invadeId;
	private SlArea slArea;
	private String imsi;
	private String imei;
	private Timestamp recordDate;
	private Integer operator;
	private String posinfo;
	private Integer cancelState;
	private Integer cancelUserId;
	private String cancelCause;
	
	private Date recordDateS;
	private Date recordDateE;

	// Constructors

	/** default constructor */
	public SlInvade() {
	}

	/** minimal constructor */
	public SlInvade(String imsi, String imei, Timestamp recordDate,
			Integer operator, String posinfo) {
		this.imsi = imsi;
		this.imei = imei;
		this.recordDate = recordDate;
		this.operator = operator;
		this.posinfo = posinfo;
	}

	/** full constructor */
	public SlInvade(SlArea slArea, String imsi, String imei,
			Timestamp recordDate, Integer operator, String posinfo,
			Integer cancelState, Integer cancelUserId, String cancelCause) {
		this.slArea = slArea;
		this.imsi = imsi;
		this.imei = imei;
		this.recordDate = recordDate;
		this.operator = operator;
		this.posinfo = posinfo;
		this.cancelState = cancelState;
		this.cancelUserId = cancelUserId;
		this.cancelCause = cancelCause;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "invade_id", unique = true, nullable = false)
	public Integer getInvadeId() {
		return this.invadeId;
	}

	public void setInvadeId(Integer invadeId) {
		this.invadeId = invadeId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "area_id")
	public SlArea getSlArea() {
		return this.slArea;
	}

	public void setSlArea(SlArea slArea) {
		this.slArea = slArea;
	}

	@Column(name = "imsi", nullable = false, length = 16)
	public String getImsi() {
		return this.imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	@Column(name = "imei", nullable = false, length = 16)
	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	@Column(name = "record_date", nullable = false, length = 19)
	public Timestamp getRecordDate() {
		return this.recordDate;
	}

	public void setRecordDate(Timestamp recordDate) {
		this.recordDate = recordDate;
	}

	@Column(name = "operator", nullable = false)
	public Integer getOperator() {
		return this.operator;
	}

	public void setOperator(Integer operator) {
		this.operator = operator;
	}

	@Column(name = "posinfo", nullable = false, length = 50)
	public String getPosinfo() {
		return this.posinfo;
	}

	public void setPosinfo(String posinfo) {
		this.posinfo = posinfo;
	}

	@Column(name = "cancel_state")
	public Integer getCancelState() {
		return this.cancelState;
	}

	public void setCancelState(Integer cancelState) {
		this.cancelState = cancelState;
	}

	@Column(name = "cancel_user_id")
	public Integer getCancelUserId() {
		return this.cancelUserId;
	}

	public void setCancelUserId(Integer cancelUserId) {
		this.cancelUserId = cancelUserId;
	}

	@Column(name = "cancel_cause", length = 65535)
	public String getCancelCause() {
		return this.cancelCause;
	}

	public void setCancelCause(String cancelCause) {
		this.cancelCause = cancelCause;
	}

	@Transient
	public Date getRecordDateS() {
		return recordDateS;
	}

	public void setRecordDateS(Date recordDateS) {
		this.recordDateS = recordDateS;
	}

	@Transient
	public Date getRecordDateE() {
		return recordDateE;
	}

	public void setRecordDateE(Date recordDateE) {
		this.recordDateE = recordDateE;
	}

}