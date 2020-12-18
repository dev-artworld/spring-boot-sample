package com.equinix.appops.dart.portal.common;

import java.io.Serializable;

public class MetaData implements Serializable {
	

	private static final long serialVersionUID = 12121L;

	private Integer totalCount;
	
	private Integer pageCount;
	
	private Integer currentPage;
	
	private Integer pageSize;

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
    

}