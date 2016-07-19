package com.jos.community.module.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;

import com.jos.community.base.BaseEntity;
import com.jos.community.system.module.entity.SecurityRole;

@Entity
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Column(name = "account_name")
	private String accountName;

	@Column(name = "delete_status")
	private int deleteStatus;

	private String description;

	private String locked;

	private String password;

	@Column(name = "user_name")
	private String userName;

	// bi-directional many-to-many association to SecurityRole
	@ManyToMany(mappedBy = "users")
	private List<SecurityRole> securityRoles;

	public User() {
	}

	public String getAccountName() {
		return this.accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public int getDeleteStatus() {
		return this.deleteStatus;
	}

	public void setDeleteStatus(int deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocked() {
		return this.locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<SecurityRole> getSecurityRoles() {
		return securityRoles;
	}

	public void setSecurityRoles(List<SecurityRole> securityRoles) {
		this.securityRoles = securityRoles;
	}

}