package com.sunwave.app.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Transient;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * SlMsg entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "sl_msg", catalog = "inmm")
public class SlMsg implements java.io.Serializable {

	// Fields

	private Integer msgId;
	private SlArea slArea;
	private String imsi;
	private String targNum;
	private Timestamp recordDate;
	private Integer operator;
	private String posinfo;
	private String msgContent;
	private Integer cancelState;
	private Integer cancelUserId;
	private String cancelCause;
	
	private Date recordDateS;
	private Date recordDateE;

	// Constructors

	/** default constructor */
	public SlMsg() {
	}

	/** minimal constructor */
	public SlMsg(String imsi, String targNum, Timestamp recordDate,
			Integer operator, String posinfo) {
		this.imsi = imsi;
		this.targNum = targNum;
		this.recordDate = recordDate;
		this.operator = operator;
		this.posinfo = posinfo;
	}

	/** full constructor */
	public SlMsg(SlArea slArea, String imsi, String targNum,
			Timestamp recordDate, Integer operator, String posinfo,
			String msgContent, Integer cancelState, Integer cancelUserId,
			String cancelCause) {
		this.slArea = slArea;
		this.imsi = imsi;
		this.targNum = targNum;
		this.recordDate = recordDate;
		this.operator = operator;
		this.posinfo = posinfo;
		this.msgContent = msgContent;
		this.cancelState = cancelState;
		this.cancelUserId = cancelUserId;
		this.cancelCause = cancelCause;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "msg_id", unique = true, nullable = false)
	public Integer getMsgId() {
		return this.msgId;
	}

	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
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

	@Column(name = "targ_num", nullable = false, length = 16)
	public String getTargNum() {
		return this.targNum;
	}

	public void setTargNum(String targNum) {
		this.targNum = targNum;
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

	@Column(name = "msg_content", length = 65535)
	public String getMsgContent() {
		return this.msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
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