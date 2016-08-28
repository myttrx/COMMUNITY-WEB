package com.jos.community.system.module.model;

public class ResourceTreeModel {

	private String treeId;
	private String resourceId;
	private String treeNodeDesc;
	private String treeNodeName;
	private String treeNodeOrder;
	private String parentId;
	private String parentNodeName;

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getTreeNodeDesc() {
		return treeNodeDesc;
	}

	public void setTreeNodeDesc(String treeNodeDesc) {
		this.treeNodeDesc = treeNodeDesc;
	}

	public String getTreeNodeName() {
		return treeNodeName;
	}

	public void setTreeNodeName(String treeNodeName) {
		this.treeNodeName = treeNodeName;
	}

	public String getTreeNodeOrder() {
		return treeNodeOrder;
	}

	public void setTreeNodeOrder(String treeNodeOrder) {
		this.treeNodeOrder = treeNodeOrder;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentNodeName() {
		return parentNodeName;
	}

	public void setParentNodeName(String parentNodeName) {
		this.parentNodeName = parentNodeName;
	}

	public String getResourceId() {
		return resourceId;
	}
	
}
