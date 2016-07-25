package com.jos.community.system.module.entity;


import javax.persistence.*;

import com.jos.community.base.BaseEntity;


/**
 * The persistent class for the code_table database table.
 * 
 */
@Entity
@Table(name = "code_table")
@NamedQuery(name = "CodeTable.findAll", query = "SELECT c FROM CodeTable c")
public class CodeTable extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String code;

	private String description;

	@Column(name = "sort_order")
	private int sortOrder;

	private String type;

	public CodeTable() {
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}