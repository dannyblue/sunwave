package com.sunwave.app.model;

import java.sql.Timestamp;

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
 * SlMapConfig entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "sl_map_config", catalog = "inmm")
public class SlMapConfig implements java.io.Serializable {

	// Fields

	private Integer configId;
	private SlArea slArea;
	private String configUrl;
	private String configUserCode;
	private Timestamp configDate;
	private String configContent;
	private Integer configType;
	private Integer inUse;

	// Constructors

	/** default constructor */
	public SlMapConfig() {
	}

	/** minimal constructor */
	public SlMapConfig(String configUserCode, Timestamp configDate,
			Integer configType, Integer inUse) {
		this.configUserCode = configUserCode;
		this.configDate = configDate;
		this.configType = configType;
		this.inUse = inUse;
	}

	/** full constructor */
	public SlMapConfig(SlArea slArea, String configUrl, String configUserCode,
			Timestamp configDate, String configContent, Integer configType,
			Integer inUse) {
		this.slArea = slArea;
		this.configUrl = configUrl;
		this.configUserCode = configUserCode;
		this.configDate = configDate;
		this.configContent = configContent;
		this.configType = configType;
		this.inUse = inUse;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "config_id", unique = true, nullable = false)
	public Integer getConfigId() {
		return this.configId;
	}

	public void setConfigId(Integer configId) {
		this.configId = configId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "area_id")
	public SlArea getSlArea() {
		return this.slArea;
	}

	public void setSlArea(SlArea slArea) {
		this.slArea = slArea;
	}

	@Column(name = "config_url", length = 200)
	public String getConfigUrl() {
		return this.configUrl;
	}

	public void setConfigUrl(String configUrl) {
		this.configUrl = configUrl;
	}

	@Column(name = "config_user_code", nullable = false, length = 50)
	public String getConfigUserCode() {
		return this.configUserCode;
	}

	public void setConfigUserCode(String configUserCode) {
		this.configUserCode = configUserCode;
	}

	@Column(name = "config_date", nullable = false, length = 19)
	public Timestamp getConfigDate() {
		return this.configDate;
	}

	public void setConfigDate(Timestamp configDate) {
		this.configDate = configDate;
	}

	@Column(name = "config_content", length = 65535)
	public String getConfigContent() {
		return this.configContent;
	}

	public void setConfigContent(String configContent) {
		this.configContent = configContent;
	}

	@Column(name = "config_type", nullable = false)
	public Integer getConfigType() {
		return this.configType;
	}

	public void setConfigType(Integer configType) {
		this.configType = configType;
	}

	@Column(name = "in_use", nullable = false)
	public Integer getInUse() {
		return this.inUse;
	}

	public void setInUse(Integer inUse) {
		this.inUse = inUse;
	}

}