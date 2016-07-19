package com.jos.community.system.module.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.jos.community.base.BaseEntity;

import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the security_resource database table.
 * 
 */
@Entity
@Table(name = "security_resource")
@NamedQuery(name = "SecurityResource.findAll", query = "SELECT s FROM SecurityResource s")
public class SecurityResource extends BaseEntity{
	private static final long serialVersionUID = 1L;

	@Column(name = "resource_content")
	private String resourceContent;

	@Column(name = "resource_desc")
	private String resourceDesc;

	@Column(name = "resource_name")
	private String resourceName;

	@Column(name = "resource_order")
	private int resourceOrder;

	@Column(name = "resource_type")
	private String resourceType;

	// bi-directional many-to-one association to SecurityResourceTree
	@OneToMany(mappedBy = "securityResource")
	private List<SecurityResourceTree> securityResourceTrees;

	// bi-directional many-to-many association to SecurityPermission
	@ManyToMany
	@JoinTable(name = "security_permission_resource", joinColumns = { @JoinColumn(name = "resource_id") }, inverseJoinColumns = { @JoinColumn(name = "permission_id") })
	private List<SecurityPermission> securityPermissions;

	public SecurityResource() {
	}

	public String getResourceContent() {
		return this.resourceContent;
	}

	public void setResourceContent(String resourceContent) {
		this.resourceContent = resourceContent;
	}

	public String getResourceDesc() {
		return this.resourceDesc;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}

	public String getResourceName() {
		return this.resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public int getResourceOrder() {
		return this.resourceOrder;
	}

	public void setResourceOrder(int resourceOrder) {
		this.resourceOrder = resourceOrder;
	}

	public String getResourceType() {
		return this.resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public List<SecurityResourceTree> getSecurityResourceTrees() {
		return this.securityResourceTrees;
	}

	public void setSecurityResourceTrees(
			List<SecurityResourceTree> securityResourceTrees) {
		this.securityResourceTrees = securityResourceTrees;
	}

	public SecurityResourceTree addSecurityResourceTree(
			SecurityResourceTree securityResourceTree) {
		getSecurityResourceTrees().add(securityResourceTree);
		securityResourceTree.setSecurityResource(this);

		return securityResourceTree;
	}

	public SecurityResourceTree removeSecurityResourceTree(
			SecurityResourceTree securityResourceTree) {
		getSecurityResourceTrees().remove(securityResourceTree);
		securityResourceTree.setSecurityResource(null);

		return securityResourceTree;
	}

	public List<SecurityPermission> getSecurityPermissions() {
		return this.securityPermissions;
	}

	public void setSecurityPermissions(
			List<SecurityPermission> securityPermissions) {
		this.securityPermissions = securityPermissions;
	}

}