package com.jos.community.system.module.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the security_resource_tree database table.
 * 
 */
@Entity
@Table(name="security_resource_tree")
@NamedQuery(name="SecurityResourceTree.findAll", query="SELECT s FROM SecurityResourceTree s")
public class SecurityResourceTree implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "tree_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int treeId;

	@Column(name="node_desc")
	private String nodeDesc;

	@Column(name="node_level")
	private int nodeLevel;

	@Column(name="node_name")
	private String nodeName;

	@Column(name="node_order")
	private int nodeOrder;

	//bi-directional many-to-one association to SecurityResource
	@ManyToOne
	@JoinColumn(name="resource_id")
	private SecurityResource securityResource;

	//bi-directional many-to-one association to SecurityResourceTree
	@ManyToOne
	@JoinColumn(name="parent_id")
	private SecurityResourceTree securityResourceTree;

	//bi-directional many-to-one association to SecurityResourceTree
	@OneToMany(mappedBy="securityResourceTree")
	private List<SecurityResourceTree> securityResourceTrees;

	public SecurityResourceTree() {
	}

	public int getTreeId() {
		return this.treeId;
	}

	public void setTreeId(int treeId) {
		this.treeId = treeId;
	}

	public String getNodeDesc() {
		return this.nodeDesc;
	}

	public void setNodeDesc(String nodeDesc) {
		this.nodeDesc = nodeDesc;
	}

	public int getNodeLevel() {
		return this.nodeLevel;
	}

	public void setNodeLevel(int nodeLevel) {
		this.nodeLevel = nodeLevel;
	}

	public String getNodeName() {
		return this.nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public int getNodeOrder() {
		return this.nodeOrder;
	}

	public void setNodeOrder(int nodeOrder) {
		this.nodeOrder = nodeOrder;
	}

	public SecurityResource getSecurityResource() {
		return this.securityResource;
	}

	public void setSecurityResource(SecurityResource securityResource) {
		this.securityResource = securityResource;
	}

	public SecurityResourceTree getSecurityResourceTree() {
		return this.securityResourceTree;
	}

	public void setSecurityResourceTree(SecurityResourceTree securityResourceTree) {
		this.securityResourceTree = securityResourceTree;
	}

	public List<SecurityResourceTree> getSecurityResourceTrees() {
		return this.securityResourceTrees;
	}

	public void setSecurityResourceTrees(List<SecurityResourceTree> securityResourceTrees) {
		this.securityResourceTrees = securityResourceTrees;
	}

	public SecurityResourceTree addSecurityResourceTree(SecurityResourceTree securityResourceTree) {
		getSecurityResourceTrees().add(securityResourceTree);
		securityResourceTree.setSecurityResourceTree(this);

		return securityResourceTree;
	}

	public SecurityResourceTree removeSecurityResourceTree(SecurityResourceTree securityResourceTree) {
		getSecurityResourceTrees().remove(securityResourceTree);
		securityResourceTree.setSecurityResourceTree(null);

		return securityResourceTree;
	}

}