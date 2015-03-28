package com.sunwave.app.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * SysRole entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "sys_role", catalog = "inmm")
public class SysRole implements java.io.Serializable {

	// Fields

	private Integer id;
	private String roleName;
	private Set<SysUser> sysUsers = new HashSet<SysUser>(0);
	private Set<SysPermission> sysPermissions = new HashSet<SysPermission>(0);

	// Constructors

	/** default constructor */
	public SysRole() {
	}

	/** full constructor */
	public SysRole(String roleName, Set<SysUser> sysUsers,
			Set<SysPermission> sysPermissions) {
		this.roleName = roleName;
		this.sysUsers = sysUsers;
		this.sysPermissions = sysPermissions;
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

	@Column(name = "role_name", length = 50)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysRoles")
	public Set<SysUser> getSysUsers() {
		return this.sysUsers;
	}

	public void setSysUsers(Set<SysUser> sysUsers) {
		this.sysUsers = sysUsers;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "sys_role_permission", catalog = "inmm", joinColumns = { @JoinColumn(name = "role_id", updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "permission_id", updatable = false) })
	public Set<SysPermission> getSysPermissions() {
		return this.sysPermissions;
	}

	public void setSysPermissions(Set<SysPermission> sysPermissions) {
		this.sysPermissions = sysPermissions;
	}
	
	@Transient 
	public Set<String> getPerssionsName(){
		Set<String> perssionsName = new HashSet<String>();
		for(SysPermission sysPermission:getSysPermissions()){
			perssionsName.add(sysPermission.getPermissionName());
		}
		return perssionsName;
	}

}