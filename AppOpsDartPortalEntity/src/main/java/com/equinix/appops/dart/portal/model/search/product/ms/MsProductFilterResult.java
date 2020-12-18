package com.equinix.appops.dart.portal.model.search.product.ms;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.equinix.appops.dart.portal.entity.mservices.MsSiebelAssetDa;
import com.equinix.appops.dart.portal.entity.mservices.SnowAssetDa;
import com.equinix.appops.dart.portal.entity.mservices.SnowConfigItemDa;
import com.equinix.appops.dart.portal.entity.mservices.SnowConfigItemXa;
import com.equinix.appops.dart.portal.model.search.product.ProductFilter;

public class MsProductFilterResult implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4629466434754077860L;
	private ProductFilter productFilter;
	private List<MsSiebelAssetDa> sblList;
	private List<SnowAssetDa> snowList;
	private List<SnowConfigItemDa> snowConfigItemDaList;
	private List<SnowConfigItemXa> snowConfigItemXaList;
	private Set<String> sblRowIds;
	private long totalRows;
	
	
	
	public Set<String> getSblRowIds() {
		return sblRowIds;
	}
	public void setSblRowIds(Set<String> sblRowIds) {
		this.sblRowIds = sblRowIds;
	}
	public ProductFilter getProductFilter() {
		return productFilter;
	}
	public void setProductFilter(ProductFilter productFilter) {
		this.productFilter = productFilter;
	}
	public List<MsSiebelAssetDa> getSblList() {
		return sblList;
	}
	public void setSblList(List<MsSiebelAssetDa> sblList) {
		this.sblList = sblList;
	}
	
	public List<SnowConfigItemDa> getSnowConfigItemDaList() {
		return snowConfigItemDaList;
	}
	public void setSnowConfigItemDaList(List<SnowConfigItemDa> snowConfigItemDaList) {
		this.snowConfigItemDaList = snowConfigItemDaList;
	}
	
	public List<SnowConfigItemXa> getSnowConfigItemXaList() {
		return snowConfigItemXaList;
	}
	public void setSnowConfigItemXaList(List<SnowConfigItemXa> snowConfigItemXaList) {
		this.snowConfigItemXaList = snowConfigItemXaList;
	}
	public long getTotalRows() {
		return totalRows;
	}
	public List<SnowAssetDa> getSnowList() {
		return snowList;
	}
	public void setSnowList(List<SnowAssetDa> snowList) {
		this.snowList = snowList;
	}
	public void setTotalRows(long totalRows) {
		this.totalRows = totalRows;
	}
	

}
