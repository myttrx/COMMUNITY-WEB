package com.jos.community.system.module.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.jos.community.base.BaseEntity;

import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the security_permission database table.
 * 
 */
@Entity
@Table(name = "security_permission")
@NamedQuery(name = "SecurityPermission.findAll", query = "SELECT s FROM SecurityPermission s")
public class SecurityPermission extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Column(name = "permission_desc")
	private String permissionDesc;

	@Column(name = "permission_name")
	private String permissionName;

	private String status;

	// bi-directional many-to-many association to SecurityResource
	@ManyToMany(mappedBy = "securityPermissions")
	private List<SecurityResource> securityResources;

	// bi-directional many-to-many association to SecurityRole
	@ManyToMany
	@JoinTable(name = "security_role_permission", joinColumns = { @JoinColumn(name = "permission_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	private List<SecurityRole> securityRoles;

	public SecurityPermission() {
	}

	public String getPermissionDesc() {
		return this.permissionDesc;
	}

	public void setPermissionDesc(String permissionDesc) {
		this.permissionDesc = permissionDesc;
	}

	public String getPermissionName() {
		return this.permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<SecurityResource> getSecurityResources() {
		return this.securityResources;
	}

	public void setSecurityResources(List<SecurityResource> securityResources) {
		this.securityResources = securityResources;
	}

	public List<SecurityRole> getSecurityRoles() {
		return this.securityRoles;
	}

	public void setSecurityRoles(List<SecurityRole> securityRoles) {
		this.securityRoles = securityRoles;
	}

}