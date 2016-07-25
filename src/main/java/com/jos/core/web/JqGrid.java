package com.jos.core.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jos.community.utils.Reflections;


public class JqGrid<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2122168011946763221L;
	
	private int totalpages;
	private int page;
	private int rows;
	private int currentPageRecords;

	private Long totalrecords;
	private List<T> griddata;
	private List<Column> cols = new ArrayList<Column>();
	
	public int getCurrentPageRecords() {
		return currentPageRecords;
	}

	public void setCurrentPageRecords(int currentPageRecords) {
		this.currentPageRecords = currentPageRecords;
	}


	public int getTotalpages() {
		return totalpages;
	}

	public void setTotalpages(int totalpages) {
		this.totalpages = totalpages;
	}

	public Long getTotalrecords() {
		return totalrecords;
	}

	public void setTotalrecords(Long totalrecords) {
		this.totalrecords = totalrecords;
	}

	public List<T> getGriddata() {
		return griddata;
	}

	public void setGriddata(List<T> griddata) {
		this.griddata = griddata;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public List<Column> getCols() {
		return cols;
	}

	public void setCols(List<Column> cols) {
		this.cols = cols;
	}
	
	public String toJson() {
		return generateJsonData();
	}
	
	/**
	 * { "total": "xxx", "page": "yyy", "records": "zzz", "rows" : [ {"id" :"1",
	 * "cell" :["cell11", "cell12", "cell13"]}, ] }
	 * 
	 * @param pager
	 * @return
		 */
	private String generateJsonData() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");

		sb.append("\"page\":\"");
		sb.append(this.getPage());
		// sb.append();
		sb.append("\",");
		sb.append("\"total\":\"");
		sb.append(this.getTotalpages());
		sb.append("\",");
		sb.append("\"records\":\"");
		sb.append(this.getTotalrecords());
		sb.append("\",");
		int i = 0;
		int colNum = this.cols.size();
		sb.append("\"rows\" : [");
		while (i < this.currentPageRecords) {
			T data = this.griddata.get(i);
			int id = i;
			sb.append("{ \"id\":\"");
			sb.append(id);
			sb.append("\",");
			sb.append(" \"cell\" :[");
			int j = 0;
			for (Column col : this.cols) {
				sb.append("\"");
				String name = col.getName();
				String dataStr = "";
				Object o = Reflections.getFieldValueWithDot(data, name);
				if (o != null)
					dataStr = o.toString();
				sb.append(dataStr);
				if (j == colNum - 1) {
					sb.append("\"");
				} else
					sb.append("\",");
				j++;
			}
			sb.append("]");
			if (i == this.currentPageRecords - 1) {
				sb.append("}");
			} else
				sb.append("},");
			i++;
		}
		sb.append("]");

		sb.append("}");
		return sb.toString();
	}
	
	public void addCols(String... names) {
		for (String colName : names) {
			Column newColumn = new Column();
			newColumn.setName(colName);
			this.cols.add(newColumn);
		}
	}

}
