package com.jos.community.system.module.vo;

public class ResourceTreeGridRecordVo {

	private String nodeName;
	private String nodeDesc;
	private String parentNodeName;
	private int nodeOrder;
	private int treeId;

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeDesc() {
		return nodeDesc;
	}

	public void setNodeDesc(String nodeDesc) {
		this.nodeDesc = nodeDesc;
	}

	public String getParentNodeName() {
		return parentNodeName;
	}

	public void setParentNodeName(String parentNodeName) {
		this.parentNodeName = parentNodeName;
	}

	public int getNodeOrder() {
		return nodeOrder;
	}

	public void setNodeOrder(int nodeOrder) {
		this.nodeOrder = nodeOrder;
	}

	public int getTreeId() {
		return treeId;
	}

	public void setTreeId(int treeId) {
		this.treeId = treeId;
	}

}
