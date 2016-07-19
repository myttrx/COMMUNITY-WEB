package com.jos.community.system.module.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.jos.community.base.BaseEntity;
import com.jos.community.module.entity.User;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the security_role database table.
 * 
 */
@Entity
@Table(name="security_role")
@NamedQuery(name="SecurityRole.findAll", query="SELECT s FROM SecurityRole s")
public class SecurityRole extends BaseEntity{
	private static final long serialVersionUID = 1L;

	@Column(name="role_code")
	private String roleCode;

	@Column(name="role_desc")
	private String roleDesc;

	@Column(name="role_level")
	private int roleLevel;

	@Column(name="role_name")
	private String roleName;

	//bi-directional many-to-many association to SecurityPermission
	@ManyToMany(mappedBy="securityRoles")
	private List<SecurityPermission> securityPermissions;

	// bi-directional many-to-many association to User
	@ManyToMany
	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
	private List<User> users;
	
	public SecurityRole() {
	}

	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleDesc() {
		return this.roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public int getRoleLevel() {
		return this.roleLevel;
	}

	public void setRoleLevel(int roleLevel) {
		this.roleLevel = roleLevel;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<SecurityPermission> getSecurityPermissions() {
		return this.securityPermissions;
	}

	public void setSecurityPermissions(List<SecurityPermission> securityPermissions) {
		this.securityPermissions = securityPermissions;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}