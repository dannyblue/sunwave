package com.sunwave.app.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * SlArea entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "sl_area", catalog = "inmm")
public class SlArea implements java.io.Serializable {

	// Fields

	private Integer areaId;
	private SlArea slArea;
//	private Integer parentId;
	private String areaName;
	private Integer areaGrade;
	private Float buildingX;
	private Float buildingY;
	private Integer areaType;
	private Integer areaOrder;
//	private Set<SysUser> sysUsers = new HashSet<SysUser>(0);
//	private Set<SlPhone> slPhones = new HashSet<SlPhone>(0);
//	private Set<SlMapConfig> slMapConfigs = new HashSet<SlMapConfig>(0);
//	private Set<SlInvade> slInvades = new HashSet<SlInvade>(0);
//	private Set<SlMsg> slMsgs = new HashSet<SlMsg>(0);
//	private Set<SlCall> slCalls = new HashSet<SlCall>(0);
//	private Set<SlArea> slAreas = new HashSet<SlArea>(0);

	// Constructors

	/** default constructor */
	public SlArea() {
	}

	/** minimal constructor */
	public SlArea(String areaName, Integer areaGrade, Integer areaType) {
		this.areaName = areaName;
		this.areaGrade = areaGrade;
		this.areaType = areaType;
	}

	/** full constructor */
	public SlArea(/*SlArea slArea,*/ String areaName, Integer areaGrade,
			Float buildingX, Float buildingY, Integer areaType,
			Integer areaOrder/*, Set<SysUser> sysUsers, Set<SlPhone> slPhones,
			Set<SlMapConfig> slMapConfigs, Set<SlInvade> slInvades,
			Set<SlMsg> slMsgs, Set<SlCall> slCalls, Set<SlArea> slAreas*/) {
//		this.slArea = slArea;
		this.areaName = areaName;
		this.areaGrade = areaGrade;
		this.buildingX = buildingX;
		this.buildingY = buildingY;
		this.areaType = areaType;
		this.areaOrder = areaOrder;
//		this.sysUsers = sysUsers;
//		this.slPhones = slPhones;
//		this.slMapConfigs = slMapConfigs;
//		this.slInvades = slInvades;
//		this.slMsgs = slMsgs;
//		this.slCalls = slCalls;
//		this.slAreas = slAreas;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "area_id", unique = true, nullable = false)
	public Integer getAreaId() {
		return this.areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	public SlArea getSlArea() {
		return this.slArea;
	}

	public void setSlArea(SlArea slArea) {
		this.slArea = slArea;
	}

	@Column(name = "area_name", nullable = false, length = 50)
	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Column(name = "area_grade", nullable = false)
	public Integer getAreaGrade() {
		return this.areaGrade;
	}

	public void setAreaGrade(Integer areaGrade) {
		this.areaGrade = areaGrade;
	}

	@Column(name = "building_x", precision = 12, scale = 0)
	public Float getBuildingX() {
		return this.buildingX;
	}

	public void setBuildingX(Float buildingX) {
		this.buildingX = buildingX;
	}

	@Column(name = "building_y", precision = 12, scale = 0)
	public Float getBuildingY() {
		return this.buildingY;
	}

	public void setBuildingY(Float buildingY) {
		this.buildingY = buildingY;
	}

	@Column(name = "area_type", nullable = false)
	public Integer getAreaType() {
		return this.areaType;
	}

	public void setAreaType(Integer areaType) {
		this.areaType = areaType;
	}

	@Column(name = "area_order")
	public Integer getAreaOrder() {
		return this.areaOrder;
	}

	public void setAreaOrder(Integer areaOrder) {
		this.areaOrder = areaOrder;
	}

//	@Column(name = "parent_id", nullable = false)
//	public Integer getParentId() {
//		return parentId;
//	}
//
//	public void setParentId(Integer parentId) {
//		this.parentId = parentId;
//	}

//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "slArea")
//	public Set<SysUser> getSysUsers() {
//		return this.sysUsers;
//	}
//
//	public void setSysUsers(Set<SysUser> sysUsers) {
//		this.sysUsers = sysUsers;
//	}
//
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "slArea")
//	public Set<SlPhone> getSlPhones() {
//		return this.slPhones;
//	}
//
//	public void setSlPhones(Set<SlPhone> slPhones) {
//		this.slPhones = slPhones;
//	}
//
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "slArea")
//	public Set<SlMapConfig> getSlMapConfigs() {
//		return this.slMapConfigs;
//	}
//
//	public void setSlMapConfigs(Set<SlMapConfig> slMapConfigs) {
//		this.slMapConfigs = slMapConfigs;
//	}
//
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "slArea")
//	public Set<SlInvade> getSlInvades() {
//		return this.slInvades;
//	}
//
//	public void setSlInvades(Set<SlInvade> slInvades) {
//		this.slInvades = slInvades;
//	}
//
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "slArea")
//	public Set<SlMsg> getSlMsgs() {
//		return this.slMsgs;
//	}
//
//	public void setSlMsgs(Set<SlMsg> slMsgs) {
//		this.slMsgs = slMsgs;
//	}
//
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "slArea")
//	public Set<SlCall> getSlCalls() {
//		return this.slCalls;
//	}
//
//	public void setSlCalls(Set<SlCall> slCalls) {
//		this.slCalls = slCalls;
//	}
//
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "slArea")
//	public Set<SlArea> getSlAreas() {
//		return this.slAreas;
//	}
//
//	public void setSlAreas(Set<SlArea> slAreas) {
//		this.slAreas = slAreas;
//	}
	
}