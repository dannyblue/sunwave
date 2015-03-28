package com.sunwave.app.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * SysPermission entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "sys_permission", catalog = "inmm")
public class SysPermission implements java.io.Serializable {

	// Fields

	private Integer id;
	private String permissionName;
	private Set<SysRole> sysRoles = new HashSet<SysRole>(0);

	// Constructors

	/** default constructor */
	public SysPermission() {
	}

	/** full constructor */
	public SysPermission(String permissionName, Set<SysRole> sysRoles) {
		this.permissionName = permissionName;
		this.sysRoles = sysRoles;
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

	@Column(name = "permission_name", length = 50)
	public String getPermissionName() {
		return this.permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysPermissions")
	public Set<SysRole> getSysRoles() {
		return this.sysRoles;
	}

	public void setSysRoles(Set<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
	}

}