package com.sunwave.app.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * SysUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_user", catalog = "inmm")
public class SysUser implements java.io.Serializable {

	// Fields

	private Integer id;
	private SlArea slArea;
	private String username;
	private String userShowName;
	private String password;
	private Integer userType;
	private String salt;
	private Integer locked;
	private Set<SysRole> sysRoles = new HashSet<SysRole>(0);
	private String roleName;
	private String roleId;
	private String roleIdQ;

	// Constructors

	/** default constructor */
	public SysUser() {
	}

	/** full constructor */
	public SysUser(SlArea slArea, String username, String userShowName,
			String password, Integer userType, String salt, Integer locked,
			Set<SysRole> sysRoles) {
		this.slArea = slArea;
		this.username = username;
		this.userShowName = userShowName;
		this.password = password;
		this.userType = userType;
		this.salt = salt;
		this.locked = locked;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "area_id")
	public SlArea getSlArea() {
		return this.slArea;
	}

	public void setSlArea(SlArea slArea) {
		this.slArea = slArea;
	}

	@Column(name = "username", length = 50)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "user_show_name", length = 200)
	public String getUserShowName() {
		return this.userShowName;
	}

	public void setUserShowName(String userShowName) {
		this.userShowName = userShowName;
	}

	@Column(name = "password", length = 200)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "user_type")
	public Integer getUserType() {
		return this.userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	@Column(name = "salt", length = 100)
	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	@Transient
	public String getCredentialsSalt() {
        return username + salt;
    }

	@Column(name = "locked")
	public Integer getLocked() {
		return this.locked;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	@JSONField(serialize=false)
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "sys_user_role", catalog = "inmm", joinColumns = { @JoinColumn(name = "user_id", updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "role_id", updatable = false) })
	public Set<SysRole> getSysRoles() {
		return this.sysRoles;
	}

	public void setSysRoles(Set<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
	}

	@Transient
	public String getRoleName() {
		Set<SysRole> roles = getSysRoles();
		roleName="";
		for(SysRole r :roles){
			roleName+=r.getRoleName()+",";
		}
		if(!"".equals(roleName)){
			roleName=roleName.substring(0, roleName.length()-1);
		}
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Transient
	public String getRoleId() {
		Set<SysRole> roles = getSysRoles();
		roleId="";
		for(SysRole r :roles){
			roleId+=r.getId()+",";
		}
		if(!"".equals(roleId)){
			roleId=roleId.substring(0, roleId.length()-1);
		}
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Transient
	public String getRoleIdQ() {
		return roleIdQ;
	}

	public void setRoleIdQ(String roleIdQ) {
		this.roleIdQ = roleIdQ;
	}

}