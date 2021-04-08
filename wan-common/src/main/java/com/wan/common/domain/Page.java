package com.wan.common.domain;

/**
 * 分页信息
 * @author wmj
 *
 */
public class Page {
	
	/**
	 * 每页数据量
	 */
	Integer limit;
	
	/**
	 * 页码
	 */
	Integer page;
	
	/**
	 * 排序的列名
	 */
	String sort;
	
	/**
	 * 排序方式'asc' 'desc'
	 */
	String sortOrder;

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
}
